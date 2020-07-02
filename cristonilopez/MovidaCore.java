package movida.cristonilopez;

import movida.commons.*;
import movida.cristonilopez.maps.Dizionario;
import movida.cristonilopez.maps.albero23.Albero23;
import movida.cristonilopez.ordinamento.InsertionSort;
import movida.cristonilopez.ordinamento.comparators.CompareActiveActor;
import movida.cristonilopez.ordinamento.comparators.CompareVote;
import movida.cristonilopez.ordinamento.comparators.CompareYear;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch {

    SortingAlgorithm sort;
    MapImplementation map;
    Dizionario<Movie> movies;
    Dizionario<Actor> actors;

    public MovidaCore(){
        this.sort = SortingAlgorithm.InsertionSort;
        this.map = MapImplementation.Alberi23;
        this.movies = null;
        this.actors = null;
    }

    protected <T> Dizionario<T> createDizionario(Class<T> c){
        if(map == MapImplementation.Alberi23)
            return new Albero23<T>(c);
        else
            return null; //TODO aggiungere array ordinato
    }

    @Override
    public boolean setSort(SortingAlgorithm a) {
        if(a != sort){
            if(a == SortingAlgorithm.InsertionSort || a == SortingAlgorithm.HeapSort){
                sort = a;
                return true;
            }
            else
                return false;
        }
        return false;
    }

    @Override   //TODO da testare
    public boolean setMap(MapImplementation m) {
        if(m != map){
            if(m == MapImplementation.Alberi23 || m == MapImplementation.ArrayOrdinato){
                map = m;                                                        //Modifichiamo il tipo di dizionario usato
                Dizionario<Actor> newActors = createDizionario(Actor.class);    //Creiamo il nuovo dizionario
                Dizionario<Movie> newMovies = createDizionario(Movie.class);    //Creiamo il nuovo dizionario
                for (Movie movie: getAllMovies()) {                             //Inseriamo i film nel nuovo dizionario
                    newMovies.insert(movie, movie.getTitle());
                }
                for (Person actor: getAllPeople()) {                            
                    Dizionario<Movie> newStarredMovies = createDizionario(Movie.class);     //Cambio l'implementazione anche dei dizionari usati negli attori
                    Dizionario<Movie> newDirectedMovies = createDizionario(Movie.class);
                    for (Movie movie : ((Actor)actor).getMoviesStarred()) {                 //Modifica per i film in cui l'attore ha recitato
                        newStarredMovies.insert(movie, movie.getTitle());
                    }
                    ((Actor)actor).setMoviesStarred(newStarredMovies);          // Assegno il nuovo dizionario
                    for (Movie movie : ((Actor)actor).getMoviesDirected()) {    // Modifica per i film diretti
                        newDirectedMovies.insert(movie, movie.getTitle());
                    }
                    ((Actor)actor).setMoviesDirected(newDirectedMovies);
                    newActors.insert(((Actor)actor), actor.getName());          //Assegno il nuovo dizionario
                }
                movies = newMovies;// Assegno il nuovo dizionario
                actors = newActors;// Assegno il nuovo dizionario
            }
        }
        return false;
    }

    @Override
    public void loadFromFile(File f) {
        if(!f.exists())
        {
            throw new MovidaFileException();
        }

        Scanner file = null;
        try
        {
            file = new Scanner(f);
        } 
        catch (FileNotFoundException e)
        {
            throw new MovidaFileException();
        }
        movies = createDizionario(Movie.class);
        actors = createDizionario(Actor.class);
        boolean continua = true;
        while(continua)
        {
            String title = FileEngine.getTitle(file); //potrebbe essere più utile accorpare tutti questi metodi in un solo
            
            Integer year = FileEngine.getYear(file);  //metodo dato che questi metodi non possono assolutamente essere 
            
            Person director = FileEngine.getDirector(file, actors);//autilizzati in un ordine diverso
            
            Person[] cast = FileEngine.getCast(file, actors);

            Integer votes = FileEngine.getVotes(file);
            
            Movie result = new Movie(title, year, votes, cast, director);

            {
                Actor temp = (Actor)result.getDirector();
                if(temp.getMoviesDirected() == null)
                {
                    temp.setMoviesDirected(createDizionario(Movie.class)); //deve farlo solo se non ha già una moviesDirrrrrrrrected
                }
                temp.addDirected(result);
            }

            {
                for(Person p : result.getCast())
                {
                    Actor a = (Actor) p;
                    if(a.getMoviesStarred() == null)
                    {
                        a.setMoviesStarred(createDizionario(Movie.class)); //deve farlo solo se non ha gia una  moviesStarrred
                    }
                    a.addStarred(result);
                }
            }

            Test.stampa(result);
            movies.insert(result, title);
            //far smaltire la riga vuota, TODO da sistemare
            if(!file.hasNextLine())
            {
                continua = false;
            }
            else
            {
                String temp = file.nextLine();
                if(temp.length() >= 4 || !file.hasNextLine()) //TODO qua prevede che sia formattata male, ma anzichè fermarmi io devo dare err
                {
                    continua = false;
                }
            }
        } 
        file.close();
    }



    @Override
    public void saveToFile(File f) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        movies = null;
        actors = null;
    }

    @Override
    public int countMovies() {
        return movies.count();
    }

    @Override
    public int countPeople() {
        return actors.count();
    }

    /**
     * La funzione elimina un attore se questo non è presente in alcun film
     * @param actor
     * @return true se l'attore è  stato eliminato
     */
    protected boolean tryDeleteActor(Actor actor){
        if (actor.countMoviesDirected() == 0 && actor.countMoviesStarred() == 0)
            return actors.delete(actor.getName());
        else
            return false;
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        Movie m = movies.search(title);
        if(m != null){
            for (Person actor : m.getCast()){               //Elimino da tutte le persone del cast tale film
                ((Actor)actor).deleteMoviesStarred(title);
                tryDeleteActor(((Actor) actor));
            }
            Actor director = (Actor)m.getDirector();        //Elimino dal direttore tale film
            director.deleteMoviesDirected(title);
            tryDeleteActor(director);                       //Se l'attore non è presente in alcun film lo elimino
        }
        return movies.delete(title);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return movies.search(title);
    }

    @Override
    public Person getPersonByName(String name) {
        return actors.search(name);
    }

    @Override
    public Movie[] getAllMovies() {
        return  movies.toArray();
    }

    @Override
    public Person[] getAllPeople() {
        return actors.toArray();
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        Movie[] arrayMovie = getAllMovies(); 
        return (Movie[])ordina(arrayMovie, N, new CompareVote().reversed());
    }
    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        Movie[] arrayMovie = getAllMovies();
        return (Movie[])ordina(arrayMovie, N, new CompareYear().reversed());
    }

    @Override
    public Person[] searchMostActiveActors(Integer N) {
        Actor[] A = (Actor[])getAllPeople();
        return (Person[])ordina(A, N, new CompareActiveActor().reversed());
    }

    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        Actor actor = (Actor)getPersonByName(name);     //Controllo che l'attore sia presente
        if(actor != null){
            return actor.getMoviesStarred();
        }
        else
            return new Movie[0];
    }

    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        Actor actor = (Actor) getPersonByName(name); // Controllo che l'attore sia presente
        if (actor != null) {
            return actor.getMoviesDirected();
        } else
            return new Movie[0];        
    }

    @Override
    public Movie[] searchMoviesByTitle(String title) {
        Movie[] arrayMovie = getAllMovies();
        LinkedList<Movie> containsTitle = new LinkedList<Movie>();  //Lista contenente l'elenco dei film che contengono "title"
        for (Movie movie : arrayMovie) {
            String movieTitle = movie.getTitle();
            if(movieTitle.length() > title.length() && movieTitle.contains(title))  //Controllo se title è contenuto
                containsTitle.add(movie);       //Aggiungo alla lista
        }
        return containsTitle.toArray(new Movie[containsTitle.size()]);
    }

    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        Movie[] arrayMovie = getAllMovies();
        LinkedList<Movie> inYear = new LinkedList<Movie>();     //Non è utile ordinare l'array in quanto anche il miglior ordinamento
        for (Movie movie : arrayMovie) {                        //impiega tempo O(nlogn) mentre una semplice scansione invece impiega O(n)
            if(movie.getYear().compareTo(year) == 0)
                inYear.add(movie);
        }      
        return inYear.toArray(new Movie[inYear.size()]);
    }

    /**
     * Il metodo ordina dato un Array ed un comparator si occupa di ordinare tale array usando l'algoritmo di ordinamento in funzione
     * e i criteri dettati dal comparator
     * @param A Array da ordinare
     * @param N Numero di elementi di cui si è interessati
     * @param c Comparator
     * @return  Array di lunghezza N ordinato secondo <code>c</code>
     */
    protected <T> T[] ordina(T[] A, Integer N, Comparator<T> c){
        if (N > A.length) // Se N è maggiore del numero di film a disposizione, andiamo ad elencarli tutti
            N = A.length;
        T[] most;
        if (sort == SortingAlgorithm.InsertionSort) {
            InsertionSort.sort(A, c);
        } else {
            // TODO richiamo all'heap sort (hint per Davide l'heap sort dopo N delete max ha
            // finito (vedi heapselect))
        }
        most = Arrays.copyOfRange(A, 0, N); // Copio gli N elementi con voti maggiori
        return most;
    }

}
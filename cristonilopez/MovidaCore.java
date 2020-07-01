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
    Dizionario movies;
    Dizionario actors;

    public MovidaCore(){
        this.sort = SortingAlgorithm.InsertionSort;
        this.map = MapImplementation.Alberi23;
        this.movies = null;
        this.actors = null;
    }

    protected Dizionario createDizionario(){
        if(map == MapImplementation.Alberi23)
            return new Albero23();
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

    @Override
    public boolean setMap(MapImplementation m) {
        // TODO Auto-generated method stub
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
        movies = createDizionario();
        actors = createDizionario();
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
                    temp.setMoviesDirected(createDizionario()); //deve farlo solo se non ha già una moviesDirrrrrrrrected
                }
                temp.addDirected(result);
            }

            {
                for(Person p : result.getCast())
                {
                    Actor a = (Actor) p;
                    if(a.getMoviesStarred() == null)
                    {
                        a.setMoviesStarred(createDizionario()); //deve farlo solo se non ha gia una  moviesStarrred
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

    @Override
    public boolean deleteMovieByTitle(String title) {
        return movies.delete(title);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return (Movie)movies.search(title);
    }

    @Override
    public Person getPersonByName(String name) {
        return (Person)actors.search(name);
    }

    @Override
    public Movie[] getAllMovies() {
        return (Movie[]) movies.toArray();
    }

    @Override
    public Person[] getAllPeople() {
        return (Person[])actors.toArray();
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        Movie[] arrayMovie = getAllMovies(); 
        return (Movie[])ordina(arrayMovie, N, new CompareVote());
    }
    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        Movie[] arrayMovie = getAllMovies();
        return (Movie[])ordina(arrayMovie, N, new CompareYear());
    }

    @Override
    public Person[] searchMostActiveActors(Integer N) {
        Person[] A = getAllPeople();
        return (Person[])ordina(A, N, new CompareActiveActor());
    }

    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        Actor actor = (Actor)getPersonByName(name);     //Controllo che l'attore sia presente
        if(actor != null){
            return actor.getMoviesStarred();
        }
        else
            return null;
    }

    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        Actor actor = (Actor) getPersonByName(name); // Controllo che l'attore sia presente
        if (actor != null) {
            return actor.getMoviesDirected();
        } else
            return null;        
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
        return (Movie[])containsTitle.toArray();
    }

    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        Movie[] arrayMovie = getAllMovies();
        LinkedList<Movie> inYear = new LinkedList<Movie>();     //Non è utile ordinare l'array in quanto anche il miglior ordinamento
        for (Movie movie : arrayMovie) {                        //impiega tempo O(nlogn) mentre una semplice scansione invece impiega O(n)
            if(movie.getYear().compareTo(year) == 0)
                inYear.add(movie);
        }      
        return (Movie[])inYear.toArray();
    }

    /**
     * Il metodo ordina dato un Array ed un comparator si occupa di ordinare tale array usando l'algoritmo di ordinamento in funzione
     * e i criteri dettati dal comparator
     * @param A Array da ordinare
     * @param N Numero di elementi di cui si è interessati
     * @param c Comparator
     * @return  Array di lunghezza N ordinato secondo <code>c</code>
     */
    protected Object[] ordina(Object[] A, Integer N, Comparator c){
        if (N > A.length) // Se N è maggiore del numero di film a disposizione, andiamo ad elencarli tutti
            N = A.length;
        Object[] most;
        if (sort == SortingAlgorithm.InsertionSort) {
            InsertionSort.sort(A, c);
        } else {
            // TODO richiamo all'heap sort (hint per Davide l'heap sort dopo N delete max ha
            // finito (vedi heapselect))
        }
        most = Arrays.copyOfRange(A, 0, N - 1); // Copio gli N elementi con voti maggiori
        return most;
    }

}
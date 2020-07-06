package movida.cristonilopez;

import movida.commons.*;
import movida.cristonilopez.maps.Dizionario;
import movida.cristonilopez.maps.asdlab.CodePriorita.DHeap;
import movida.cristonilopez.maps.asdlab.CodePriorita.DHeap.InfoDHeap;
import movida.cristonilopez.maps.asdlab.Grafi.*;
import movida.cristonilopez.maps.albero23.Albero23;
import movida.cristonilopez.ordinamento.InsertionSort;
import movida.cristonilopez.ordinamento.comparators.CompareActiveActor;
import movida.cristonilopez.ordinamento.comparators.CompareVote;
import movida.cristonilopez.ordinamento.comparators.CompareYear;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class MovidaCore implements IMovidaDB, IMovidaConfig, IMovidaSearch, IMovidaCollaborations {

    SortingAlgorithm sort;
    MapImplementation map;
    Dizionario<Movie> movies;
    Dizionario<Actor> actors;
    HashMap<String, Nodo> nodi;
    GrafoLA collaborations;

    public MovidaCore() {
        this.sort = SortingAlgorithm.InsertionSort;
        this.map = MapImplementation.Alberi23;
        this.movies = null;
        this.actors = null;
        this.collaborations = null;
        this.nodi = null;
    }

    protected <T> Dizionario<T> createDizionario(Class<T> c) {
        if (map == MapImplementation.Alberi23)
            return new Albero23<T>(c);
        else
            return null; // TODO aggiungere array ordinato
    }

    @Override
    public boolean setSort(SortingAlgorithm a) {
        if (a != sort) {
            if (a == SortingAlgorithm.InsertionSort || a == SortingAlgorithm.HeapSort) {
                sort = a;
                return true;
            } else
                return false;
        }
        return false;
    }

    @Override // TODO da testare
    public boolean setMap(MapImplementation m) {
        if (m != map) {
            if (m == MapImplementation.Alberi23 || m == MapImplementation.ArrayOrdinato) {
                map = m; // Modifichiamo il tipo di dizionario usato
                Dizionario<Actor> newActors = createDizionario(Actor.class); // Creiamo il nuovo dizionario
                Dizionario<Movie> newMovies = createDizionario(Movie.class); // Creiamo il nuovo dizionario
                for (Movie movie : getAllMovies()) { // Inseriamo i film nel nuovo dizionario
                    newMovies.insert(movie, movie.getTitle());
                }
                for (Person actor : getAllPeople()) {
                    Dizionario<Movie> newStarredMovies = createDizionario(Movie.class); // Cambio l'implementazione
                                                                                        // anche dei dizionari usati
                                                                                        // negli attori
                    Dizionario<Movie> newDirectedMovies = createDizionario(Movie.class);
                    for (Movie movie : ((Actor) actor).getMoviesStarred()) { // Modifica per i film in cui l'attore ha
                                                                             // recitato
                        newStarredMovies.insert(movie, movie.getTitle());
                    }
                    ((Actor) actor).setMoviesStarred(newStarredMovies); // Assegno il nuovo dizionario
                    for (Movie movie : ((Actor) actor).getMoviesDirected()) { // Modifica per i film diretti
                        newDirectedMovies.insert(movie, movie.getTitle());
                    }
                    ((Actor) actor).setMoviesDirected(newDirectedMovies);
                    newActors.insert(((Actor) actor), actor.getName()); // Assegno il nuovo dizionario
                }
                movies = newMovies;// Assegno il nuovo dizionario
                actors = newActors;// Assegno il nuovo dizionario
            }
        }
        return false;
    }

    @Override
    public void loadFromFile(File f) { 

        if (!f.exists()) {
            throw new MovidaFileException();
        }

        Scanner file = null;
        try {
            file = new Scanner(f);
        } catch (FileNotFoundException e) {
            throw new MovidaFileException();
        }

        if (movies == null) {
            movies = createDizionario(Movie.class);
        }
        if (actors == null) {
            actors = createDizionario(Actor.class);
        } 

        Dizionario<Movie> tempMovies = createDizionario(Movie.class); 

        boolean continua = true;
        while (continua) {
            String title = FileEngine.getTitle(file);

            Integer year = FileEngine.getYear(file);

            Person director = FileEngine.getDirector(file); 

            Person[] cast = FileEngine.getCast(file);

            Integer votes = FileEngine.getVotes(file);

            Movie result = new Movie(title, year, votes, cast, director);
            
            Test.stampa(result);

            tempMovies.insert(result, title); 

            if (!file.hasNextLine()) { //controlla la formatazione el file tra un record e un altro e fa smaltire la riga vuota
                continua = false;
            } else {
                String temp = file.nextLine();
                temp = FileEngine.fromTabToSpace(temp).trim(); //rimuovo qualche spazio e tab perchè qualcuno ha la passione per i tab, non so perchè
                if (temp.length() != 0){ //se non è una riga vuota genera un eccezione
                    throw new MovidaFileException();
                }
                else if(!file.hasNextLine()){ //se era una riga vuota e no abbiamo altre rige da leggere, ferma la lettura file
                    continua = false;
                }
            }
        }
                                                // merge della struttura temporanea con quella definitiva
        for (Movie m : tempMovies.toArray()) 
        {
            Actor director = actors.search(m.getDirector().getName()); //cerco se il regista è già nella struttura
            if (director == null)                  //se non lo trovo, lo creo
            {
                Actor actor = new Actor(m.getDirector().getName());
                actors.insert(actor, m.getDirector().getName());
                actor.setMoviesDirected(createDizionario(Movie.class));
                actor.setMoviesStarred(createDizionario(Movie.class));
                director = actor;
            }

            Person[] cast = m.getCast();
            Actor[] actor = new Actor[cast.length];
            for (int i = 0; i < actor.length; i++) 
            {
                actor[i] = actors.search(cast[i].getName()); //cerco se l'attore è già nella struttura
                if (actor[i] == null) //se non lo trovo lo creo
                { 
                    Actor newActor = new Actor(cast[i].getName());
                    actors.insert(newActor, newActor.getName());
                    newActor.setMoviesStarred(createDizionario(Movie.class));
                    newActor.setMoviesDirected(createDizionario(Movie.class));
                    actor[i] = newActor;
                }
            }
            Movie temp = new Movie(m.getTitle(), m.getYear(), m.getVotes(), actor, director);
            director.addDirected(temp); //aggiungo il film diretto dal regista nella sua struttura di film che ha diretto
            for (Actor a : actor) {
                a.addStarred(temp); //aggiungo il film interpretato dal'attore nella sua struttura dei film che ha interpretato
            }
            //cerco se il film sta per essere riaggiunto e rimuovo gli attori che non fanno più parte del cast
            Movie oldMovie = movies.search(temp.getTitle());
            if(oldMovie != null)
            {
                for(Person a: oldMovie.getCast()) //controlo se un attore non fa più farte del cast e nel caso lo rimuovo
                {
                    Actor tempActor = (Actor)a;
                    boolean trovato = false;
                    if(((Actor)a).searchStarredMovie(oldMovie.getTitle()) == oldMovie)
                        trovato = true;
                    //TODO ottimizzazione da approvare
                    //Basta verificare se il riferimento relativo al film che viene aggiornato è uguale a quello vecchio
                    //se così fosse vuol dire che l'attore non ha ricevuto l'aggiornamento al riferimento del record del nuovo film
                    //quindi non è presente nel nuovo cast
                    /*for(Actor b: actor)
                    {
                        if(a == b)
                        {
                            trovato = true;
                            break;
                        }
                    }
                    */
                    if(!trovato)
                    {
                        tempActor.deleteMoviesStarred(oldMovie.getTitle());
                        tryDeleteActor(tempActor);
                    }
                    /**TODO seconda modifica da approvare, perchè fare questo controllo nel ciclo? Basta farlo una volta fuori
                     * if(oldMovie.getDirector().getName().compareTo(director.getName()) !=0)
                     * //controllo se c'è stato un cambio di regista e nel caso rimuovo il film dai
                     * suoi film diretti { Actor oldDirector = (Actor)oldMovie.getDirector();
                     * oldDirector.deleteMoviesDirected(temp.getTitle());
                     * tryDeleteActor(oldDirector); }
                     */
                }
                    if(oldMovie.getDirector().getName().compareTo(director.getName()) != 0){
                     //controllo se c'è stato un cambio di regista e nel caso rimuovo il film dai suoi film diretti { 
                        Actor oldDirector = (Actor)oldMovie.getDirector();
                        oldDirector.deleteMoviesDirected(temp.getTitle());
                        tryDeleteActor(oldDirector); 
                    }
            }

            movies.insert(temp, temp.getTitle()); //aggiungo il film nella struttura principale
        }
        createCollaborations();
        file.close();
    }

    @Override
    public void saveToFile(File f) { //TODO da testare
        FileWriter file;
        try{
            file = new FileWriter(f);
        } catch(Exception e) {
            throw new MovidaFileException();
        }  //TODO aggiungere controllo per lanciare eccezioni
        int c = 0;
        for(Movie m: movies.toArray())
        {
            String toWrite = "";
            toWrite += "Title: " + m.getTitle() + '\n'; //inseriamo il titolo;
            toWrite += "Year: " + m.getYear() + '\n'; //inseriamo l'anno
            toWrite += "Director: " + m.getDirector().getName() + '\n'; //inserisco il regista
            toWrite += "Cast: "; //inseriamo gli attori
            Person[] cast = m.getCast();
            for(int i=0; i<cast.length; i++)
            {
                toWrite += cast[i].getName();
                if( (i + 1) != cast.length ){ //se non è l'ultimo attore
                    toWrite += ", ";          //inserisce i caratteri tra un attore e l'altro
                }
                else{                         //se è l'ultimo attore
                    toWrite += '\n';          //chiude la linea
                }
            }
            toWrite += "Votes: " + m.getVotes() + '\n';
            if((c + 1) != movies.toArray().length){ //se non è l'ultimo elemento
                toWrite += '\n';                    //inserisci una riga vuota
            }
            c++;
            try{
            file.write(toWrite);
            } catch(Exception e) {
                throw new MovidaFileException();
            }
        }
        try{
            file.close();
        } catch(Exception e) {
            throw new MovidaFileException();
        }
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
     * 
     * @param actor
     * @return true se l'attore è stato eliminato
     */
    protected boolean tryDeleteActor(Actor actor) {
        if (actor.countMoviesDirected() == 0 && actor.countMoviesStarred() == 0)
            return actors.delete(actor.getName());
        else
            return false;
    }

    @Override
    public boolean deleteMovieByTitle(String title) {
        Movie m = movies.search(title);
        if (m != null) {
            for (Person actor : m.getCast()) { // Elimino da tutte le persone del cast tale film
                ((Actor) actor).deleteMoviesStarred(title);
                tryDeleteActor(((Actor) actor));
            }
            deleteCollaborationsOfMovie(m);
            Actor director = (Actor) m.getDirector(); // Elimino dal direttore tale film
            director.deleteMoviesDirected(title);
            tryDeleteActor(director); // Se l'attore non è presente in alcun film lo elimino
        }
        return movies.delete(title);
    }

    /**
     * Il metodo elimina tutte le collaborazioni tra gli attori di un film
     * @param movie
     */
    protected void deleteCollaborationsOfMovie(Movie movie){
        Person[] cast = movie.getCast();
        for (int i = 0; i < cast.length-1; i++) {
            for (int j = i+1; j < cast.length; j++) {
                Person a = cast[i];
                Person b = cast[j];
                deleteCollaboration(a, b, movie);
            }
        }
    }

    /**
     * Il metodo elimina la collaborazione tra due attori in un determinato film
     * @param a
     * @param b
     * @param movie
     */
    protected void deleteCollaboration(Person a, Person b, Movie movie){
        Nodo nodoA = nodi.get(a.getName().toLowerCase()); // Cerchiamo i nostri nodi nella hash map
        Nodo nodoB = nodi.get(b.getName().toLowerCase());
        Arco arcoAB = collaborations.sonoAdiacenti(nodoA, nodoB); // Ricerchiamo i nostri archi
        Arco arcoBA = collaborations.sonoAdiacenti(nodoA, nodoB);
        Collaboration collab = (Collaboration) collaborations.infoArco(arcoAB);
        collab.deleteMovie(movie); // Rimuoviamo il film dalla collaborazione
        if (collab.isEmpty()) { // Se non vi è alcun film in cui gli attori hanno recitato insieme
            collaborations.rimuoviArco(arcoAB); // Rimuoviamo gli archi e le collaborazioni
            collaborations.rimuoviArco(arcoBA);
            if (collaborations.gradoUscente(nodoA) == 0) // Se il nostro nodo non ha più archi andiamo ad eliminarlo
                collaborations.rimuoviNodo(nodoA);
            if (collaborations.gradoUscente(nodoB) == 0)
                collaborations.rimuoviNodo(nodoB);
        }
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
        return movies.toArray();
    }

    @Override
    public Person[] getAllPeople() {
        return actors.toArray();
    }

    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        Movie[] arrayMovie = getAllMovies();
        return (Movie[]) ordina(arrayMovie, N, new CompareVote().reversed());
    }

    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        Movie[] arrayMovie = getAllMovies();
        return (Movie[]) ordina(arrayMovie, N, new CompareYear().reversed());
    }

    @Override
    public Person[] searchMostActiveActors(Integer N) {
        Actor[] A = (Actor[]) getAllPeople();
        return (Person[]) ordina(A, N, new CompareActiveActor().reversed());
    }

    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        Actor actor = (Actor) getPersonByName(name); // Controllo che l'attore sia presente
        if (actor != null) {
            return actor.getMoviesStarred();
        } else
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
        LinkedList<Movie> containsTitle = new LinkedList<Movie>(); // Lista contenente l'elenco dei film che contengono
                                                                   // "title"
        for (Movie movie : arrayMovie) {
            String movieTitle = movie.getTitle();
            if (movieTitle.length() > title.length() && movieTitle.contains(title)) // Controllo se title è contenuto
                containsTitle.add(movie); // Aggiungo alla lista
        }
        return containsTitle.toArray(new Movie[containsTitle.size()]);
    }

    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        Movie[] arrayMovie = getAllMovies();
        LinkedList<Movie> inYear = new LinkedList<Movie>(); // Non è utile ordinare l'array in quanto anche il miglior
                                                            // ordinamento
        for (Movie movie : arrayMovie) { // impiega tempo O(nlogn) mentre una semplice scansione invece impiega O(n)
            if (movie.getYear().compareTo(year) == 0)
                inYear.add(movie);
        }
        return inYear.toArray(new Movie[inYear.size()]);
    }

    /**
     * Il metodo ordina dato un Array ed un comparator si occupa di ordinare tale
     * array usando l'algoritmo di ordinamento in funzione e i criteri dettati dal
     * comparator
     * 
     * @param A Array da ordinare
     * @param N Numero di elementi di cui si è interessati
     * @param c Comparator
     * @return Array di lunghezza N ordinato secondo <code>c</code>
     */
    protected <T> T[] ordina(T[] A, Integer N, Comparator<T> c) {
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

 
    /** 
     * Create collaborations crea le collaborazioni e il grafo a queste associate
    */
    protected void createCollaborations(){
        this.collaborations = new GrafoLA();
        this.nodi = new HashMap<>();
        Movie[] Movies = getAllMovies();
        for (Movie movie : Movies) {                // Per ogni film vado a creare le collaborazioni tra gli autori
            Person[] cast = movie.getCast();        //Prendo tutte le persone del cast
            for (int i = 0; i < cast.length - 1; i++) {   //Ciclo su tutte le possibili coppie
                for (int j = i + 1; j < cast.length; j++) {
                    Person a = cast[i];                     
                    Person b = cast[j];
                    createCollaboration(a, b, movie);
                }
            }
        }
    }

    /**
     * Dati due attori e un film il metodo crea la Collaboration tra loro se non esisite, altrimenti
     * aggiunge il film alla collaborazione esistente
     * @param a
     * @param b
     * @param movie
     */
    protected void createCollaboration(Person a, Person b, Movie movie){
        boolean newCollabF = false;
        Nodo nodoA = nodi.get(a.getName().toLowerCase());     //Cerchiamo i nostri nodi nella hash map
        Nodo nodoB = nodi.get(b.getName().toLowerCase());
        if (nodoA != null && nodoB != null) {                 // Se  i nodi sono entrambi presenti controlliamo se sono adiacenti
            Arco arco = collaborations.sonoAdiacenti(nodoA, nodoB);     
            if(arco != null){
                Collaboration collab = (Collaboration) collaborations.infoArco(arco);   //Se sono adiacenti esiste già una collaborazione
                collab.addMovie(movie);                       //Aggiungiamo il movie alla lista dei film
            }
            else
                newCollabF = true;
        }
            else{
                if (nodoA == null) {                            //Se anche uno di loro non è presente si tratta allora di una nuova collaborazione
                    nodoA = collaborations.aggiungiNodo(a);
                    nodi.put(a.getName().toLowerCase(), nodoA); //Creiamo il nodo A se non esiste
                }
                if(nodoB == null){
                    nodoB = collaborations.aggiungiNodo(b);
                    nodi.put(b.getName().toLowerCase(), nodoB); //Creiamo il nodo B se non esiste
                }
                    newCollabF = true;
                
            }
        if(newCollabF){                         //Creo la nuova collaborazione
            Collaboration newCollab = new Collaboration(a, b);
            newCollab.addMovie(movie);          //Inserisco il film
            collaborations.aggiungiArco(nodoA, nodoB, newCollab);
            collaborations.aggiungiArco(nodoB, nodoA, newCollab);   
        }
        
    }
    

    @Override
    public Person[] getDirectCollaboratorsOf(Person actor) {
        Nodo node = nodi.get(actor.getName().toLowerCase());
        Person[] directCollaborator;
        if(node != null){
            directCollaborator = new Person[collaborations.gradoUscente(node)];
            List<Arco> archiIncidenti =  (List <Arco>)collaborations.archiUscenti(node);
            for (int i = 0; i < directCollaborator.length; i++) {
                Nodo dest = archiIncidenti.get(i).dest;
                directCollaborator[i] = (Person)collaborations.infoNodo(dest);
            }
         }
        else
            directCollaborator = new Person[0];
        return directCollaborator;
    }

    @Override
    public Person[] getTeamOf(Person actor) {
        HashMap<Nodo, Boolean> visited = new HashMap<>();   //True nodo visitato, False nodo inesplorato
        Queue<Nodo> frontiera = new LinkedList<>();         //Frontiera per BFS
        LinkedList<Person> team = new LinkedList<>();       //Lista da ritornare
        Nodo radice = nodi.get(actor.getName().toLowerCase());  //Recuperiamo il nodo di origine della visista
        if(radice != null){
            visited.put(radice, true);                          //Visitiamo il nodo
            frontiera.add(radice);                              //Aggiungiamo la radice alla frontiera
            while(!frontiera.isEmpty()){
                Nodo x = frontiera.poll();
                team.add((Person)collaborations.infoNodo(x));   //Aggiungiamo l'attore al team
                visited.putIfAbsent(x, true);
                List<Arco> archi = (List<Arco>)collaborations.archiUscenti(x);  //
                Iterator<Arco> iterator =  archi.iterator();
                while(iterator.hasNext()){
                    Arco arco = iterator.next();
                    Nodo dest = arco.dest;
                    Boolean visitato = visited.get(dest);       //Controlliamo se il nodo è stato visitato
                    if(visitato == null){
                        frontiera.add(dest);
                        visited.put(dest, true);
                    }
                }
            }
        }
        return team.toArray(new Person[team.size()]);
    }

    @Override
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor){
        //Inizializzo le strutture dati
        HashMap<Nodo, Collaboration> visited = new HashMap<>(); //Associa ad ogni nodo la collaborazione con cui lo abbiamo raggiunto 
        HashMap<Nodo, InfoDHeap> infoHeap = new HashMap<>();    //Mantiene i riferimenti agli elementi dell'heap per poter poi riuscire ad eseguire una decrease key
        DHeap coda = new DHeap();
        
        Nodo radice = nodi.get(actor.getName().toLowerCase());     //Recuperiamo il nodo di origine della visista
        if(radice != null){                                        //Controlliamo che l'attore sia presente
            visited.put(radice, new Collaboration(actor, actor));  //Nuova collaborazione di un attore con se stesso
            InfoDHeap info = coda.insert(radice, new Double(0.0)); //Poniamo la radice a distanza 0
            infoHeap.put(radice, info);
            while(!coda.isEmpty()){
                Nodo x = (Nodo) coda.findMin();
                coda.deleteMin();
                List<Arco> archi = (List<Arco>)collaborations.archiUscenti(x);  //Recuperiamo tutti gli archi uscenti dal nostro nodo
                Iterator<Arco> iterator =  archi.iterator();                    //Iteriamo su tutti gli archi
                while(iterator.hasNext()){                                      //Per tutti i nodi y adiacenti
                    Arco arco = iterator.next();
                    Nodo dest = arco.dest;                              //Recuperiamo il nodo adiacente
                    Collaboration oldCollab = visited.get(dest);        //Recuperiamo la vecchia collaborazione con cui abbiamo raggiunto y
                    Collaboration collab = (Collaboration)collaborations.infoArco(arco);    
                    if(oldCollab == null){                              //Se questa non esiste la andiamo ad aggiungere
                        info = coda.insert(dest, -collab.getScore());   //Usiamo sempre un punteggio negativo in quanto la coda con priorità usa un min-heap
                        visited.put(dest, collab);
                        infoHeap.put(dest, info);                       //Salviamo in InfoHeap il riferimento per poter poi riuscire ad eseguire la decrease key
                    }
                    else if(oldCollab.getScore() < collab.getScore()){                //Se il valore di tale collaborazione è minore rispetto a quello di un altra che raggiunge lo stesso nodo
                        coda.decreaseKey(infoHeap.get(dest), -collab.getScore());     //Aggiorno la "distanza"
                        visited.replace(dest, collab);                                //Aggiorno la collaborazione 
                    }
                }
            }
        }
 
        visited.remove(radice);
        return visited.values().toArray(new Collaboration[visited.size()]);
    }

}
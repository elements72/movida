package movida.cristonilopez;

import java.io.File;
import java.util.Arrays;

import movida.commons.*;

public class Test {
    static MovidaCore core;

    public static void main(String[] args) {
        File f = new File("movida/commons/esempio-formato-dati.txt"); // cammino relativo
        core = new MovidaCore();
        core.loadFromFile(f);
        //tests();    //Funzione con vari test
        File f1 = new File("movida/commons/esempio-formato-dati-2.txt");   //cammino relativo
        core.loadFromFile(f1);
        tests();    //Funzione con vari test
        core.clear();
        core.setMap(MapImplementation.ArrayOrdinato);
        core.setSort(SortingAlgorithm.HeapSort);
        System.out.println("array ordinato\n");
        core.loadFromFile(f);
        //tests();    //Funzione con vari test
        core.loadFromFile(f1);
        core.setMap(MapImplementation.Alberi23);
        tests();    //Funzione con vari test
        File fout = new File("movida/cristonilopez/esempio-scrittura-dati.txt");
        core.saveToFile(fout);
        core.clear();
        tests();
    }

    public static void tests(){
        String searchActor = "Anne Hathaway";
        String searchDirector = "Fabio il fruttivendolo";
        String searchMovie = "pulp fiction";
        String searchString = "Force";
        Integer anno = new Integer(1997);
        int N = 20;
        // Tutti i test sulle varie funzioni, commentare se necessario o aggiungerne
        // altri
        // Mancano set map, setAlgo, clear, save,
        System.out.println("Count movie:" + core.countMovies() + "\n");
        System.out.println("Count people:" + core.countPeople() + "\n");
        System.out.println("All movies:" + Arrays.toString(core.getAllMovies()) + "\n");
        System.out.println("All actors:" + Arrays.toString(core.getAllPeople()) + "\n");
        System.out.println("Cerca persona:" + searchActor + core.getPersonByName(searchActor) + "\n");
        System.out.println("Cerca film: " + searchString + core.getMovieByTitle(searchString) + "\n");
        System.out.println("Cerca film in cui ha recitato: " + searchActor
                + Arrays.toString(core.searchMoviesStarredBy(searchActor)) + "\n");
        System.out.println("Film diretti da: " + searchDirector
                + Arrays.toString(core.searchMoviesDirectedBy(searchDirector)) + "\n");
        System.out.println("Film usciti il: " + anno + Arrays.toString(core.searchMoviesInYear(anno)) + "\n");
        System.out.println("Attori più attivi:" + Arrays.toString(core.searchMostActiveActors(N)) + "\n");
        System.out.println(N + " Film più recenti:" + Arrays.toString(core.searchMostRecentMovies(N)) + "\n");
        System.out.println(N + " Film più votati:" + Arrays.toString(core.searchMostVotedMovies(N)) + "\n");
        System.out.println("Film contenenti la stringa:" + searchString
        + Arrays.toString(core.searchMoviesByTitle(searchString)) + "\n");
        System.out.println("Elimino il film:" + searchMovie + " -> " + core.deleteMovieByTitle(searchMovie) + "\n");
        System.out.println("Collaboratori di:" + searchActor + Arrays.toString(core.getDirectCollaboratorsOf(new Person(searchActor))) + "\n");
        System.out.println("Team di:" + searchActor + Arrays.toString(core.getTeamOf(new Person(searchActor))) + "\n");
        System.out.println("Miglior collaborazione di:" + searchActor + Arrays.toString(core.maximizeCollaborationsInTheTeamOf(new Person(searchActor))) + "\n");

    }
        
    public static void stampa(Movie m){
        System.out.println("Titolo: "+m.getTitle());
        System.out.println("Anno: "+m.getYear());
        System.out.print("Cast: ");
        Person[] p = m.getCast();
        for(int i=0; i<p.length; i++)
        {
            System.out.print(p[i].getName()+", ");
        }
        System.out.println("");
        Person d = m.getDirector();
        System.out.println("Direttore: "+d.getName());
        System.out.println("Voti: "+m.getVotes());
        System.out.println("");
    }

}
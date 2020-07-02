package movida.cristonilopez;

import java.io.File;
import java.util.Arrays;

import movida.commons.*;

public class Test {
    
    public static void main(String[] args) {
        File f = new File("/home/antonio/Scrivania/ProgettoAlgoritmi/movida/commons/esempio-formato-dati.txt");
        MovidaCore core = new MovidaCore();
        core.loadFromFile(f);
        for (Movie string : core.searchMostVotedMovies(100)) {
            System.out.println(string.getVotes());
        }
        System.out.println(core.getMovieByTitle("Pulp Fiction").getDirector().getName());
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
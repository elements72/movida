package movida.cristonilopez.ordinamento.comparators;
import java.util.Comparator;
import movida.commons.Movie;

/**
 * Classe usata per comparere il titolo secondo l'usuale confronto tra stringhe.
 * 
 * @param m1 m2 film da confrontare
 * @return Intero: positivo m1 titolo maggiore, negativo m1 titolo minore;
 */

public class CompareTitle implements Comparator<Movie>{

    @Override
    public int compare(Movie m1, Movie m2){
        return  m1.getTitle().compareToIgnoreCase(m2.getTitle());
    }
}
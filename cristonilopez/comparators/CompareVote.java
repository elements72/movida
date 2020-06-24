package movida.cristonilopez.comparatori;

import java.util.Comparator;
import movida.commons.Movie;

/**
 * Classe usata per comparare il voto.
 * @param m1 m2 film da confrontare
 * @return Intero: positivo m1 voto maggiore, negativo m1 voto minore
 */

public class CompareVote implements Comparator<Movie> {

    public int compare(Movie m1, Movie m2) {
        int result = m1.getVotes().compareTo(m2.getVotes());
        if (result < 0)
            return -1;
        else if (result > 0)
            return 1;
        else
            return 0;
    }
}
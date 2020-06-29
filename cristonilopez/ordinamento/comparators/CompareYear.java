package movida.cristonilopez.ordinamento.comparators;
import movida.commons.Movie;
import java.time.Year;
import java.util.Comparator;

    /** 
     *Classe usata per comparare la data di uscita.
     *@param m1 m2 film da confrontare
     *@return Intero: positivo anno successivo, negativo anno precedente
     */ 

public class CompareYear implements Comparator<Movie>{

    public int compare(Movie m1, Movie m2){
        Year firstYear = Year.of(m1.getYear());
        Year secondYear = Year.of(m2.getYear());
        int result = firstYear.compareTo(secondYear);
        if(result < 0)
            return -1;
        else if(result > 0)
            return 1;
        else
            return 0;
    }
}
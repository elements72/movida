package movida.cristonilopez;

import movida.commons.Movie;
import movida.commons.Person;
import movida.cristonilopez.maps.Dizionario;

/**
 * La classe actor estende la classe Person aggiungendo quelle che sono le informazioni
 * sui film diretti e su quelli di cui fa parte del cast
 */

public class Actor extends Person{
    Dizionario<Movie> moviesDirected; //inglese errato, directedMovies
    Dizionario<Movie> moviesStarred;   //inglese errato, starredMovies

    public Actor(String name){
        super(name);
    }
    /**
     * Assegna a un attore l'elenco dei film da lui diretti
     * @param movieDirected
     */
    public void setMoviesDirected(Dizionario<Movie> moviesDirected) {
        this.moviesDirected = moviesDirected;
    }
    /**
     * Assegna a un attore l'elenco dei fil a cui ha preso parte
     * @param movieTakePart
     */
    public void setMoviesStarred(Dizionario<Movie> moviesStarred) {
        this.moviesStarred = moviesStarred;
    }
    /**
     * Aggiunge un film dove l'attore ha partecipato
     * @param movie
     */
    public void addStarred(Movie movie){
        moviesStarred.insert(movie, movie.getTitle());
    }
    /**
     * Aggiunge film diretto
     * @param movie
     */
    public void addDirected(Movie movie){
        moviesDirected.insert(movie, movie.getTitle());
    }
    /**
     * Fornisce i film diretti
     */
    public Movie[] getMoviesDirected() {
        if(moviesDirected == null)
            return null;
        else
            return (Movie[])moviesDirected.toArray();
    }
    /**
     * Fornisce i film dove ha partecipato
     */
    public Movie[] getMoviesStarred() {
        if(moviesStarred == null)
            return null;
        else
            return (Movie[])moviesStarred.toArray();
    }
    /**
     * Rimuove il film tra quelli in cui l'attore ha partecipato
     * @param name titolo del film
     */
    public void deleteMoviesStarred(String name){
        moviesStarred.delete(name);
    }
    
    /**
     * Rimuove il film tra quelli diretti dall'attore
     * @param name titolo del film
     */
    public void deleteMoviesDirected(String name){
        moviesDirected.delete(name);
    }
    /**
     * Fornisce il numero di film dove l'attore è presente
     */
    public int countMoviesStarred(){
        return moviesStarred.count();
    }
    /**
     * Fornisce il numero di film diretti
     */
    public int countMoviesDirected(){
        return moviesDirected.count();
    }

}
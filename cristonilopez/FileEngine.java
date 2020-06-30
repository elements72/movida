package movida.cristonilopez;

import movida.commons.*;
import java.util.Scanner;

public class FileEngine {
    
    /** Lettura del numero di voti del film
     * 
     * Restituisce il numero di voti ricevuti
     * dal film
     * 
     * @param file Scanner che legge dal file selezionato
	 * @return numero di voti del film
	 */
    public static Integer getVotes(Scanner file) {
        try
        {
        String temp = file.nextLine();
        int votes = 0;
        temp = temp.substring(temp.indexOf(':')+1);
        for(int i = 0; i<temp.length(); i++)
        {
            if(Character.isDigit(temp.charAt(i)))
            {
                votes = votes*10 + Character.getNumericValue(temp.charAt(i));
            }
        }
        return votes;
        }
        catch (Exception e)
        {
            throw new MovidaFileException();
        }
    }

    /**Ottenimento lista degli attori
     * 
     * Legge dallo Scanner gli attori partecipanti
     * a un film e ne restituisce l'array
     * 
     * @param file Scanner impostato sulla lettura da file
     * @return array di Person contenente tutti gli attori del film
     */
    public static Person[] getCast(Scanner file) {
        String cast = file.nextLine();
        cast = cast.substring(cast.indexOf(' ')); //non gestisco il +1 perché lo gestisco direttamente nel ciclo
        int i=0, j=0;
        while(cast.indexOf(',', j+1) > 0) //non prevede che in un film ci sia solo un attore
        {
            j = cast.indexOf(',', j+1);
            i++;
        }
        //if(actors.search(nome) != null)  cerco la persona
        //  Person[i] = actors.search(nome);
        //else persona non present{
        //  attore = new attore(nome)
        //  actors.insert(attore, attore.name)
        //  Person[i] = attore;
        //  attore.setStarredMovie(new Dizionario(implementazione corrente))
        //  }
        // creo movie
        // For each person in Person
        // person.insertStarredFilm(movie)
        // Stessa cosa va fatta per le persone che dirigono un film

        Person[] persone = new Person[i];
        j = 0;
        i = 0;
        while(cast.indexOf(',', j+1) > 0)
        {
            persone[i++] = new Person(cast.substring(j+1, cast.indexOf(',', j+1)));
            j = cast.indexOf(',', j+1);
        }
        return persone;
    }

    /**Ottiene il direttore
     * 
     * Legge dallo scanner il direttore del film e lo restituisce
     * 
     * @param fileScanner impostato sula lettura da file
     * @return Person contenente il direttore del film
     */
    public static Person getDirector(Scanner file) { 
        try{
        String directort = file.nextLine();
        directort = directort.substring(directort.indexOf(' ')+1);
        Person director = new Person(directort);
        return director;
        }
        catch (Exception e)
        {
            throw new MovidaFileException();
        }
    }

    /**Ottiene l'anno di produzione
     * 
     * Legge dallo Scanner impostato su file l'anno di direzione del film
     * 
     * @param file Scanner impostato sulla lettura da file
     * @return anno di produzione del film
     */
    public static Integer getYear(Scanner file) {
        try
        {
        String temp = file.nextLine();
        temp = temp.substring(temp.indexOf(':')+1);
        Integer year = 0; //prende la riga contenente l'anno di pubblicaazione, ritaglia via la stringa iniziale e converte in int TODO aggiungere controllo
        for(int i = 0; i<temp.length(); i++)
        {
            if(Character.isDigit(temp.charAt(i)))
            {
                year = year*10 + Character.getNumericValue(temp.charAt(i));
            }
        }
        return year;
        }
        catch (Exception e)
        {
            throw new MovidaFileException();
        }
    }

    /**Otiene il nome del film
     * 
     * Legge dallo Scanner impostato su file il nome del film
     * 
     * @param file Scanner impostato su file
     * @return il nome del film
     */
    public static String getTitle(Scanner file) {
        try
        {
        String title = file.nextLine();
        title = title.substring(title.indexOf(' ')+1);
        return title; //TODO tagliare la strnga dopo il nome del film per rimuovere qualsiasi cosa ci sia dopo che non ci interessa
        }
        catch (Exception e)
        {
            throw new MovidaFileException();
        }
    }

}
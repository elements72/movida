package movida.cristonilopez;

import movida.commons.*;
import movida.cristonilopez.maps.*;
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
    public static Person[] getCast(Scanner file, Dizionario actors) {
        try
        {
            String cast = file.nextLine();
            cast = cast.substring(cast.indexOf(' ')); //non gestisco il +1 perché lo gestisco direttamente nel ciclo
            int i= 1, j=0;
            while(cast.indexOf(',', j+1) > 0)
            {
                j = cast.indexOf(',', j+1);
                i++;
            }
        
            Person[] persone = new Person[i];
            j = 0;
            for(i=0; i<persone.length; i++)  //TODO smaltire ultimo carattere neutro che l'ultimo attore potrebbe avere
            {
                int t = 0;                      //ci permette di leggere l'ultimo attore ( il caso in cui non abbiamo un'ultima virgola) 
                if(cast.indexOf(',', j) < 0)
                {
                    t=cast.length();
                }
                else
                {
                    t=cast.indexOf(',', j);
                }
                String name = cast.substring(j, t).trim();
                Object temp = actors.search(name);
                if( temp != null)  //cerco la persona
                {
                    persone[i] = (Person)temp; //TODO controllare che non dia problemi di casting
                }
                else //l'attore non è ancora nel database
                {
                    Actor actor = new Actor(name);
                    actors.insert(actor, name);
                    persone[i] = actor;
                }

                //persone[i] = new Person(cast.substring(j, t));
                j = t + 1; //smaltisco la virgola di default, guarda dal carattere successivo

            }
         return persone;
    }
    catch (Exception e)
    {
        throw new MovidaFileException();
    }
    }

    /**Ottiene il direttore
     * 
     * Legge dallo scanner il direttore del film e lo restituisce
     * 
     * @param fileScanner impostato sula lettura da file
     * @return Person contenente il direttore del film
     */
    public static Person getDirector(Scanner file, Dizionario actors) { 
        try{
        String directortemp = file.nextLine();
        directortemp = directortemp.substring(directortemp.indexOf(' ')+1).trim();
        Object temp = actors.search(directortemp); //cerco la persona
        Person director;
                if( temp != null)  //la persona è stata trovata e la linko
                {
                    director = (Person)temp; 
                }
                else //l'attore non è ancora nel database
                {
                    Actor actor = new Actor(directortemp);
                    actors.insert(actor, directortemp);
                    director = actor;
                }
        return director;
        }
        catch (Exception e)
        {
            System.out.println("ciao "+e.getMessage());
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
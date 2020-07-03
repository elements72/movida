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
            for(i=0; i<persone.length; i++)  
            {
                int t = 0;     //ci permette di leggere l'ultimo attore ( il caso in cui non abbiamo un'ultima virgola) 
                if(cast.indexOf(',', j) < 0)
                {
                    t=cast.length();
                }
                else
                {
                    t=cast.indexOf(',', j);
                }
                String name = cast.substring(j, t);
                name = fromTabToSpace(name).trim();
                persone[i] = new Person(name);
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
     * Legge dallo scanner il direttore del film e ritorna il regista 
     * in formato Person
     * 
     * @param fileScanner impostato sula lettura da file
     * @return Person contenente il direttore del film
     */
    public static Person getDirector(Scanner file) {
        try{
        String directortemp = file.nextLine();
        directortemp = directortemp.substring(directortemp.indexOf(' ')+1);
        directortemp = fromTabToSpace(directortemp).trim();
        Person director = new Person(directortemp);
        return director;
        }
        catch (Exception e)
        {
            throw new MovidaFileException();
        }
    }

    /**Ottiene l'anno di produzione
     * 
     * Legge dallo Scanner, impostato su file, l'anno di direzione del film
     * 
     * @param file Scanner impostato sulla lettura da file
     * @return anno di produzione del film
     */
    public static Integer getYear(Scanner file) {
        try
        {
        String temp = file.nextLine();
        temp = temp.substring(temp.indexOf(':')+1);
        Integer year = 0; //prende la riga contenente l'anno di pubblicaazione, ritaglia via la stringa iniziale e converte in int
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
        title = fromTabToSpace(title).trim();
        return title;
        }
        catch (Exception e)
        {
            throw new MovidaFileException();
        }
    }

    /**Ritorna una stringa senza tab
     * 
     * Prende una stringa e sostituisce tutti i
     * tab (\t) con uno spazio
     * 
     * @param st stringa da usare
     * @return Striga senza tab
     */
    private static String fromTabToSpace(String st){
        int tabIndex;
        do{
            tabIndex = st.indexOf('\t');
            if(tabIndex > 0)
                st = st.replace('\t', ' ');
        }while(tabIndex > 0);
        return st;
    }
}
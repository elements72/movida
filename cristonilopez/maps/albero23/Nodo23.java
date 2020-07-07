package movida.cristonilopez.maps.albero23;


/**
 * La classe Nodo23 rappresenta un nodo interno del nostro albero
 * presenta un array di chiavi e un array di figli.
 */

public class Nodo23 extends Nodo{

    String[] keys; //contengono le informazioni su i nodi sottostanti
    Nodo[] figli;  //[0]albero sinistro [1] albero centrale [2]albero destro [3]albero "bonus" viene subito rimosso con la split

    public Nodo23(){
        figli = new Nodo[4];
        keys  = new String[3]; 
    }
    /**
     * La funzione search node data una chiave fornisce il nodo dove dobbiamo continuare la ricerca per
     * tale valore.
     * 
     * @param key
     * @return Nodo
     */

    public Nodo searchNode(String key){
        if(key.compareToIgnoreCase(this.keys[0]) <= 0)
            return figli[0];
        else if(keys[1] == null || key.compareToIgnoreCase(keys[1]) <= 0)
            return figli[1];
        else 
            return figli[2];
    }

    /**
     * delteChild permette di rimuovere una foglia da un nodo
     * @param nodo
     */

    public void deleteChild(String key){
        int i = 0; 
        boolean found = false;
        int grado = this.grado();
        while (!found && i < grado) {
             if(((Foglia23<?>)figli[i]).getKey().compareToIgnoreCase(key) == 0) 
                found = true;
            else
                i++;
        }
        if (found) {
            if (i == grado - 1) {   // Se il nodo da eliminare è il sottoalbero più a destra
                keys[i - 1] = null; // Elimino chiave separatrice
                figli[i] = null;    // Elimino riferimento al nodo
            } else {                // Se non è il sottoalbero più a destra eseguo uno shift verso sinistra
                for (int j = i; j < grado; j++)
                    figli[j] = figli[j + 1];
                for (int j = i; j < grado - 1; j++)
                    keys[j] = keys[j + 1];
            }
        } 
        else{
            throw new ExceptionKeyNotFound();
        }

    }

    /**
     * delteChild permette di rimuovere un sottoalbero da un altro nodo.
     * 
     * @param nodo
     */
    public void deleteChild(Nodo nodo){
        boolean found = false;
        int i = 0;
        int grado = this.grado();
        while(!found && i<grado){   //Cerco il nodo da eliminare
            if(figli[i] == nodo)
                found = true;
            else
                i++;
        }
        if(found){                  
            if (i == grado - 1){         //  Se il nodo da eliminare è il sottoalbero più a destra
                keys[i - 1] = null;      //Elimino chiave separatrice
                figli[i] = null;         //Elimino riferimento al nodo
            }
            else{                        //Se non è il sottoalbero più a destra eseguo uno shift verso sinistra
                if(this.grado() == figli.length)    //Se il nodo è pieno (4 elementi) devo iterare fino al penultimo elemento
                    grado--;
                for (int j = i; j < grado; j++) 
                    figli[j] = figli[j + 1];
                for (int j = i; j < grado - 1; j++)
                    keys[j] = keys[j + 1];
                if (this.grado() == figli.length){  //Se il nodo è pieno l'ultimo elemento va rimosso manualmente in quanto lo shift
                    this.figli[grado] = null;       //non elimina l'ultimo elemento in questo caso
                    this.keys[grado-1] = null;
                }
            }  
        }    
    }

    /**
     * Add child permette di aggiungere un nuovo figlio ad un nodo indipendentemente che questo sia una foglia o un nodo interno
     * La chiave rappresenta il massimo valore presente in tale nodo. Che per un nodo interno è il massimo valore presente nel suo al 
     * @param nodo nodo da inserire
     * @param key  chiave a lui legata
     */

    public void addChild(Nodo nodo){
            /*
             Iteriamo cercando la posizione giusta per il nostro nuovo nodo
             Distinguiamo due casi:
             1)La chiave del nuovo nodo è inferiore a quella di una già esistente:
              andiamo a eseguire uno shift verso destra di chiavi e nodi partendo dalla prima chiave che risulta maggiore
             2)La chiave del nuovo nodo è maggiore di tutte quelle presenti:
                1)La chiave associata al nostro nodo è maggiore della chiave dell'ultimo figlio: aggiungiamo il nodo in coda e inseriamo la sua chiave
                2)La chiave associata al nodo è minore di quella dell'ultimo figlio: inseriamo come chiave quella dell' ultimo figlio inse

            */ 
            String key = nodo.getKey();
            for (int i = 0; i < keys.length; i++) {
                if(keys[i] == null){
                    if(nodo instanceof Foglia23){                     //Se sto inserendo una foglia
                        Foglia23<?> foglia = ((Foglia23<?>)figli[i]); //Consideriamo la foglia nella posizione i
                        if(foglia == null){
                            figli[i] = nodo;
                        }
                        else if (key.compareToIgnoreCase(foglia.getKey()) < 0) {    //Confrontiamo il suo valore con quello della nuova foglia
                            keys[i] = key;                      //Se è maggiore la spostiamo in avanti e inseriamo come chiave il valore                                          //della nostra nuova foglia
                            figli[i+1] = figli[i];              //della nostra nuova foglia
                            figli[i] = nodo;
                        }
                        else {
                            figli[i+1] = nodo;                  //Se la nostra nuova foglia presenta un valore maggiore
                            keys[i] = foglia.getKey();          //inseriamo la sua chiave e la inseriamo come figlio più a
                        }
                        break;
                    }
                    else{
                        if (figli[i] == null){                  //Inserimento per nodi vuoti
                            figli[i] = nodo;                    
                        }
                        else if(figli[i].getKey().compareToIgnoreCase(key) <= 0){ //Decidiamo la posizione del nuovo nodo e la chiave separatrice
                            keys[i] = figli[i].getKey();
                            figli[i+1] = nodo;
                        }
                        else{
                            keys[i] = key;
                            figli[i + 1] = figli[i];
                            figli[i] = nodo;
                        }                                       
                        break;
                    }
                }
                else if(keys[i].compareToIgnoreCase(key) > 0){           //Se la chiave legata la nodo/foglia è minore di una già presente in keys
                    //Shift di figli e chiavi                           //Eseguo uno shift verso destra di chiavi e figli    
                    int j;
                    for( j = this.grado(); j>i; j--){
                        figli[j] = figli[j-1];
                    }
                    figli[i] = nodo;
                    for (j = keys.length-1; j > i; j--) {
                        keys[j] = keys[j-1];
                    }
                    keys[i] = key;  //Sostituiamo alla vecchia chiave che risulta maggiore la nuova
                    break;
                }              
            }
            nodo.padre = this;              //Aggiorno il padre del nodo
    }

    /**
     * updateKey permette di aggiornare il valore di una chiave, se questa non è presente nulla viene aggiornato
     * @param oldKey chiave da sostituire
     * @param newKey nuova chiave
     */
    public void updateKey(String oldKey, String newKey){
        for (int i = 0; i < this.grado()-1; i++) {
            if(keys[i].compareToIgnoreCase(oldKey) == 0){
                keys[i] = newKey;
                break;
            }
        }
    }

    public int grado(){
        int grado = 0;
        for (Nodo nodo : figli) 
            if(nodo != null)
                grado++;
        return grado;
    }
    @Override
    public boolean isLeaf() {
        return false;
    }

    /**
     * Il metodo fornisce il fratello destro del nodo se esiste
     * 
     * @return puntatore al fratello destro
     */
    public Nodo23 rightBrother(){
        if(padre != null){
            int i = 0;
            while(!this.equals(padre.figli[i]))
                i++;
            if(i == this.padre.grado()-1)
                return null;
            else
                return (Nodo23)padre.figli[i+1];
        }
        return null;
    }
    /**
     * Il metodo fornisce il fratello sinistro del nodo se esiste
     * altrimenti null
     * @return puntatore al fratello sinistro 
     */
    public Nodo23 leftBrother(){
        if(padre != null){
            int i = 0;
            while(!this.equals(padre.figli[i]))
                i++;
            if(i == 0)
                return null;
            else
                return (Nodo23)padre.figli[i-1];
        }
        return null;
    }

    @Override
    public String toString() {
        String stringa = "";
        for (int i = 0; i < grado()-1; i++) {
            stringa = stringa + " " + keys[i];
        }
        return stringa;
    }

    /**
     * getKey fornisce il massimo elemento presente nell'albero radicato in questo nodo
     * @return foglia con valore massimp
     */
    @Override
    public String getKey(){
        Nodo tmp = figli[this.grado()-1];
        while(!tmp.isLeaf()){
                Nodo23 nodo = ((Nodo23)tmp);         //itero finchè non arrivo una foglia
                tmp = nodo.figli[nodo.grado()-1];    //Mi muovo sempre nell'ultimo figliop a destra               
            }
            return ((Foglia23<?>)tmp).getKey();
    }
}
package movida.cristonilopez.maps.albero23;

import movida.cristonilopez.maps.Coppia;

/** 
 * La classe Foglia23 modella le foglie del notro albero
 * presenta un puntatore al padre e contiene le informazioni del nostro dizionario,
*/

public class Foglia23 extends Nodo {
    public Coppia info; 
    public String key;

    public Foglia23(Coppia info, String key){
        this.padre = null;
        this.info = info;
        this.key  = key;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    public Coppia getInfo() {
        return info;
    }
    @Override
    public String toString() {
        return key;
    }
    @Override
    public String getKey() {
        return key;
    }
}
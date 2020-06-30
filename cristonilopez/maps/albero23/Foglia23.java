package movida.cristonilopez.maps.albero23;

import movida.cristonilopez.maps.Coppia;

/** 
 * La classe Foglia23 modella le foglie del notro albero
 * presenta un puntatore al padre e contiene le informazioni del nostro dizionario,
*/

public class Foglia23 extends Nodo {
    public Coppia info; 

    public Foglia23(Coppia info){
        this.padre = null;
        this.info = info;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    public Object getInfo() {
        return info.getData();
    }
    @Override
    public String toString() {
        return info.getKey();
    }
    @Override
    public String getKey() {
        return info.key;
    }
    public void setInfo(Object info) {
        this.info.data = info;
    }
}
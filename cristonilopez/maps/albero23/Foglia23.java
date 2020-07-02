package movida.cristonilopez.maps.albero23;

/** 
 * La classe Foglia23 modella le foglie del notro albero
 * presenta un puntatore al padre e contiene le informazioni del nostro dizionario,
*/

public class Foglia23<T> extends Nodo {
    public T info; 
    public String key; 

    public Foglia23(T info, String key){
        this.padre = null;
        this.info = info;
        this.key = key;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    public T getInfo() {
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
    public void setInfo(T info) {
        this.info = info;
    }
}
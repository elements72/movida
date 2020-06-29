package movida.cristonilopez.maps.albero23;
/**
 * Classe astratta usata per andare poi a definire le classi nodo e foglia 
 */
public abstract class Nodo {
    Nodo23 padre;
    public abstract boolean isLeaf();
    public abstract String getKey();
}
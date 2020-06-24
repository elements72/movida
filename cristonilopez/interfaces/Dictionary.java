package movida.cristonilopez.interfaces;

public interface Dictionary {

    /**
     * Il metodo insert permette di inserire un nuovo elemento nel dizionario.
     * @param element, key
     */

    public void insert(Object element, Comparable<String> key);

    public void delete(Comparable<String> key);

    public void search(Comparable<String> key);

    /**
     * Il metodo permette di ottenere da un dizionario un Array con i dati inseriti
     * @return Array contenente i dati del dizionario ordinati secondo la loro chiave
     */
    public Object[] toArray();
}
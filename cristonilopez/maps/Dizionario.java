package movida.cristonilopez.maps;

public interface Dizionario {

    /**
     * Il metodo insert permette di inserire un nuovo elemento nel dizionario.
     * @param element, key
     */

    public void insert(Object element, String key);

    public boolean delete(String key);

    public Object search(String key);

    /**
     * Il metodo count fornisce il numero di elementi presenti nella struttura
     * @return Numero di elementi presenti
     */
    public int count();

    /**
     * Il metodo permette di ottenere da un dizionario un Array con i dati inseriti
     * @return Array contenente i dati del dizionario ordinati secondo la loro chiave
     */
    public Object[] toArray();
}
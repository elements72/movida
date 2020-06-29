package movida.cristonilopez.maps;

public interface Dictionary {

    /**
     * Il metodo insert permette di inserire un nuovo elemento nel dizionario.
     * @param element, key
     */

    public void insert(Object element, String key);

    public void delete(String key);

    public Object search(String key);

    /**
     * Il metodo permette di ottenere da un dizionario un Array con i dati inseriti
     * @return Array contenente i dati del dizionario ordinati secondo la loro chiave
     */
    public Coppia[] toArray();
}
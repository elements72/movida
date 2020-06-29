package movida.cristonilopez.maps.albero23;
/**
 * Eccezione generata se nella ricerca di una chiave questa non è presente
 */

public class ExceptionKeyNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public String getMessage() {
        return "Key not found";
    }    
}
package es.progcipfpbatoi.exceptions;

/**
 * Exception donde no exsite el mes
 */
public class NotExistMonth extends Exception {
    /**
     * Constructor de la exception
     */
    public NotExistMonth() {
        super( "No existe el mes" );
    }
}

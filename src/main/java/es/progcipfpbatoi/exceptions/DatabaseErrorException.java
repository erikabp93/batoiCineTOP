package es.progcipfpbatoi.exceptions;

/**
 * Exception de la DB
 */
public class DatabaseErrorException extends Exception{

    /**
     * Obligatoripo poner
     *
     * @param msg String que aparace
     */
    public DatabaseErrorException(String msg) {
        super(msg);
    }

}

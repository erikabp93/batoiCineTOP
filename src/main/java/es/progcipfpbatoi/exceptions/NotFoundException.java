package es.progcipfpbatoi.exceptions;

/**
 * Exception que no encuentra algo
 */
public class NotFoundException extends Exception{
    /**
     * Constructor de la exception
     * @param msg mensaje que queremos que muestre la exception
     */
    public NotFoundException(String msg) {
        super(msg);
    }

}

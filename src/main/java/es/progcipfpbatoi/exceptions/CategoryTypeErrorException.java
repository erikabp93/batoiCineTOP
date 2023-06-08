package es.progcipfpbatoi.exceptions;

/**
 * Execption del tipo de enum
 */
public class CategoryTypeErrorException extends RuntimeException{
    /**
     * Constructor, obligatorio ponerlo, muestra la cadena a mostrar
     * @param msg cadena a mostrar
     */
    public CategoryTypeErrorException(String msg) {
        super(msg);
    }

}

package es.progcipfpbatoi.Validator;

/**
 * Clase validator
 */
public class Validador {

    private Validator validator;

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     */
    public Validador() {
        this.validator = new Validator();
    }

    /**
     * Esta clase sirve para validar la contraseña, lo que se pasa como parámetro se le pasa
     * al método que está en la clase Validator, esta devuelve true o false dependiendo que
     * lo que el método le devuelva
     *
     * @param contrasenya contraseña que se valida
     * @return boolean
     */

    public boolean validarContrasenya(String contrasenya) {
        return validator.validarContrasenya(contrasenya);
    }

    /**
     * Esta clase sirve para validar el nombre del usuario, lo que se pasa como parámetro se
     * le pasa al método que está en la clase Validator, esta devuelve true o false
     * dependiendo que lo que el método le devuelva
     *
     * @param nombreUsuario nombre del usuario
     * @return boolean
     */

    public boolean nombreUsuarioCorrecto(String nombreUsuario) {
        return validator.nombreUsuarioCorrecto(nombreUsuario);
    }

    /**
     * Esta clase sirve para validar el correo, lo que se pasa como parámetro se le pasa
     * al método que está en la clase Validator, esta devuelve true o false dependiendo
     * que lo que el método le devuelva
     *
     * @param correo correo a comprobar
     * @return boolean
     */

    public boolean correoCorrecto(String correo) {
        return validator.correoCorrecto(correo);
    }
}
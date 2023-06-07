package es.progcipfpbatoi.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String SIMBOLOS_ESPECIALES = "[!@#$%^&*(),.?\":{}|<>]";

    /**
     * Este método sirve para comprobar que la contraseña cumple con los requisitos,
     * primero comprueba que la contraseña que se le ha pasado tiene entre 8 y 15
     * caracteres, si es correcto pasa a comprobar si contiene al menos una letra
     * mayúscula, después comprueba que hay al menos un número y finalmente que tenga
     * un símbolo especial. Si todo esta correcto devuelve true pero si en alguna de
     * estas comprobaciones falla devuelve false
     *
     * @param contrasenya
     * @return boolean
     */

    public boolean validarContrasenya(String contrasenya) {

        if (contrasenya.length() < 8 || contrasenya.length() > 15) {
            return false;
        }

        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(contrasenya);
        if (!uppercaseMatcher.find()) {
            return false;
        }

        Pattern numeros = Pattern.compile("\\d");
        Matcher buscarNumeros = numeros.matcher(contrasenya);
        if (!buscarNumeros.find()) {
            return false;
        }

        Pattern simbolosEspeciales = Pattern.compile(SIMBOLOS_ESPECIALES);
        Matcher buscarSimbolos = simbolosEspeciales.matcher(contrasenya);
        if (!buscarSimbolos.find()) {
            return false;
        }
        return true;
    }

    /**
     * Este método hace la función de comprobar que el nombre del usuario no contiene
     * ningún símbolo especial, si la comprobación encuentra un símbolo devuelve false
     * pero si no encuentra ninguno devuelve true
     *
     * @param nombreUsuario
     * @return boolean
     */

    public boolean nombreUsuarioCorrecto(String nombreUsuario) {

        Pattern simbolosEspeciales = Pattern.compile(SIMBOLOS_ESPECIALES);
        Matcher buscarSimbolos = simbolosEspeciales.matcher(nombreUsuario);
        if (buscarSimbolos.find()) {
            return false;
        }
        return true;
    }

    /**
     * Este método comprueba que el correo tenga letras o números antes del "@", que sea
     * gmail o hotmail únicamente y que termine con ".com", si la comprobación falla
     * devuelve false y si el correo pasado como parámetro es correcto devuelve true
     *
     * @param correo
     * @return boolean
     */

    public boolean correoCorrecto(String correo) {

        Pattern correoCorrecto = Pattern.compile("^[A-Za-z0-9]+@[[gmail][hotmail]]+[.com]+$");
        Matcher correoIsCorrecto = correoCorrecto.matcher(correo);
        if (!correoIsCorrecto.find()) {
            return false;
        }
        return true;
    }
}
package es.progcipfpbatoi.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String SIMBOLOS_ESPECIALES = "[!@#$%^&*(),.?\":{}|<>]";

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

    public boolean nombreUsuarioCorrecto(String nombreUsuario) {

        Pattern simbolosEspeciales = Pattern.compile(SIMBOLOS_ESPECIALES);
        Matcher buscarSimbolos = simbolosEspeciales.matcher(nombreUsuario);
        if (buscarSimbolos.find()) {
            return false;
        }
        return true;
    }

    public boolean correoCorrecto(String correo) {

        Pattern correoCorrecto = Pattern.compile("^[A-Za-z0-9]+@[[gmail][hotmail]]+[.com]+$");
        Matcher correoIsCorrecto = correoCorrecto.matcher(correo);
        if (!correoIsCorrecto.find()) {
            return false;
        }
        return true;
    }
}
package es.progcipfpbatoi.Validator;

public class Validador {

    private Validator validator;

    public Validador() {
        this.validator = new Validator();
    }

    public boolean validarContrasenya(String contrasenya) {
        return validator.validarContrasenya(contrasenya);
    }

    public boolean nombreUsuarioCorrecto(String nombreUsuario) {
        return validator.nombreUsuarioCorrecto(nombreUsuario);
    }

    public boolean correoCorrecto(String correo) {
        return validator.correoCorrecto(correo);
    }
}
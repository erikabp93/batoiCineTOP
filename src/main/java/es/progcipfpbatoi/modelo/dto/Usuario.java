package es.progcipfpbatoi.modelo.dto;

/**
 * Clase usuario con la trabaja el programa
 */
public class Usuario {
    private String username;
    private String password;
    private String email;

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Este constructor posee todos atributos de la instancia como parametros
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @param email correo electronico
     */
    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email    = email;
    }

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Este constructor los atributos del username del usuario y su contraseña únicamente
     *
     * @param username nombre de usuario
     * @param password contraseña del user
     */

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Devuelve el username del usuario
     *
     * @return String
     */

    public String getUsername() {
        return username;
    }

    /**
     * Devuelve la contraseña del usuario
     *
     * @return String
     */

    public String getPassword() {
        return password;
    }

    /**
     * Devuelve el correo del usuario
     *
     * @return String
     */

    public String getEmail() {
        return email;
    }
}

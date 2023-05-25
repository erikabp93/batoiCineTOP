package es.progcipfpbatoi.modelo.dto;

public class Usuario {
    private String username;
    private String password;
    private String email;

    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email    = email;
    }

    public String getUsername() {
        return username;
    }
}

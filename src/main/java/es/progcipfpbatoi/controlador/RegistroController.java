package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.Validator.Validador;
import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador que se encargar de que el user se registra en la app
 */
public class RegistroController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRepeat;
    private UsuarioRepository usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;
    private Initializable controladorPadre;
    private String vistaPadre;

    /**
     * Constructor de la clase.
     * @param controladorPadre define qué cpmtrolador usará al volver a la vista anterior.
     * @param vistaPadre define la vista anterior.
     * @param usuarioRepository repositorio de usuarios que se encuentran en la base de datos.
     * @param peliculaSerieRepository repositorio de producciones para las listViews.
     * @param temporadaRepository repositorio de temporadas.
     * @param favoritosRepository repositorio de favoritos para ver si una pelicula está o no en favoritos.
     * @param valoracionesRepository repositorio de valoraciones para dejar o no valorar de nuevo.
     */
    public RegistroController(UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Initializable controladorPadre, String vistaPadre) {
        this.usuarioRepository = usuarioRepository;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
    }

    /**
     * Registra al nuevo usuario en la base de datos si este no existe anteriormente y la contraseña coincide
     * @param event
     */
    @FXML
    private void registro(ActionEvent event) {
        try {
            String correo = email.getText();
            String user = usuario.getText();
            String contrasenya = password.getText();
            String contrasenya2 = passwordRepeat.getText();
            Usuario usuarioNuevo = new Usuario(user, contrasenya, correo);
            boolean usuarioExiste = usuarioRepository.existeUsuario(usuarioNuevo);
            if (contrasenya.equals(contrasenya2)) {
                Validador validador = new Validador();
                if (!validador.correoCorrecto(correo)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("El correo no es correcto");
                    alert.setHeaderText("El correo no puede tener caracteres especiales y ha de ser gmail.com o hotmail.com");
                    alert.setContentText("Escribe un correo correcto por favor");
                    alert.showAndWait();
                    email.setText("");
                } else if (!validador.nombreUsuarioCorrecto(user)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("El nombre del usuario no es correcto");
                    alert.setHeaderText("El usuario no puede contener caracteres especiales");
                    alert.setContentText("Escribe otro usuario por favor");
                    alert.showAndWait();
                    usuario.setText("");
                } else if (!validador.validarContrasenya(contrasenya)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("La contraseña que has escrito no es correcta");
                    alert.setHeaderText("Debe contener entre 8 y 15 caracteres, una mayúscula, un número y un carácter especial");
                    alert.setContentText("Escribe otra contraseña por favor");
                    alert.showAndWait();
                    password.setText("");
                    passwordRepeat.setText("");
                } else if (!usuarioExiste) {
                    usuarioRepository.save(usuarioNuevo);
                    PrincipalController principalController = new PrincipalController(usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, controladorPadre, vistaPadre, usuarioNuevo);
                    ChangeScene.change(event, principalController, "/vistas/principal_vista.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error usuario");
                    alert.setHeaderText("Este usuario ya esta ocupado");
                    alert.setContentText("Escribe otro nombre por favor");
                    alert.showAndWait();
                    email.setText("");
                    usuario.setText("");
                    password.setText("");
                    passwordRepeat.setText("");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error contraseña");
                alert.setHeaderText("Las contraseñas no coinciden");
                alert.setContentText("Vuelve a escribir la contraseña correcta");
                alert.showAndWait();
            }
        } catch (DatabaseErrorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cierra la aplicación
     * @param event evento de la alerta
     */
    @FXML
    public void cerrar(ActionEvent event) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION);
        alert.setTitle( "Salir" );
        alert.setHeaderText( "" );
        alert.setContentText( "¿Estás seguro que quieres cerrar de la aplicación?" );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK ) {
            System.exit(0);
        }
        alert.close();
    }

    /**
     * Inicializa la vista con unos valores para cada elemento.
     * @param location parametro por defecto
     * @param resources parametro por defecto
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
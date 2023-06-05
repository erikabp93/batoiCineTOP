package es.progcipfpbatoi.controlador;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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

    public RegistroController(UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Initializable controladorPadre, String vistaPadre) {
        this.usuarioRepository = usuarioRepository;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
    }

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
                if (!usuarioExiste) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {


    private Stage stage;
    @FXML
    private TextField email;
    @FXML
    private TextField usuario;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordRepeat;
    private UsuarioRepository usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;
    private Initializable controladorPadre;
    private String vistaPadre;

    public RegistroController(Stage stage, UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Initializable controladorPadre, String vistaPadre) {
        this.stage = stage;
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
                    PrincipalController principalController = new PrincipalController(stage, usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, controladorPadre, vistaPadre);
                    ChangeScene.change(event, principalController, "/vistas/principal_vista.fxml");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
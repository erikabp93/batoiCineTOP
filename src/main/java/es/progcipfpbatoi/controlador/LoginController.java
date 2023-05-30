package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.FavoritoDAO;
import es.progcipfpbatoi.modelo.dao.SQLfavoritoDAO;
import es.progcipfpbatoi.modelo.dao.SQLvalorarDAO;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage stage;
    @FXML
    private TextField usuario;
    @FXML
    private TextField password;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private UsuarioRepository usuarioRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;

    public LoginController(Stage stage, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, UsuarioRepository usuarioRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository) {
        this.stage = stage;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.usuarioRepository = usuarioRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        try {
            String user = usuario.getText();
            String contrasenya = password.getText();
            Usuario usuarioNuevo = new Usuario(user, contrasenya);
            boolean usuarioExiste = usuarioRepository.existeUsuario(usuarioNuevo);
            if (usuarioExiste) {
                PrincipalController principalController = new PrincipalController(stage, usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, this, "/vistas/login_vista.fxml");
                ChangeScene.change(event, principalController, "/vistas/principal_vista.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Usuario no encontrado");
                alert.setHeaderText("Este usuario con esta contraseña no existe");
                alert.setContentText("Quieres crear un usuario nuevo?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    RegistroController registroController = new RegistroController(stage, usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, this, "/vistas/login_vista.fxml");
                    ChangeScene.change(event, registroController, "/vistas/registro_vista.fxml");
                }
            }
        } catch (DatabaseErrorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void registrarNuevoUsuario(MouseEvent event) {
        try {
            RegistroController registroController = new RegistroController(stage, usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, this, "/vistas/login_vista.fxml");
            ChangeScene.change(stage, registroController, "/vistas/registro_vista.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
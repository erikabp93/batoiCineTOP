package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.SQLfavoritoDAO;
import es.progcipfpbatoi.modelo.dao.SQLvalorarDAO;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usuario;
    @FXML
    private PasswordField password;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private UsuarioRepository usuarioRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;

    public LoginController(PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, UsuarioRepository usuarioRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository) {
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
                Usuario usuario1 = usuarioRepository.findByUsername(user);
                PrincipalController principalController = new PrincipalController(usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, this, "/vistas/login_vista.fxml", usuario1);
                ChangeScene.change(event, principalController, "/vistas/principal_vista.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Usuario no encontrado");
                alert.setHeaderText("Este usuario con esta contraseña no existe");
                alert.setContentText("Quieres crear un usuario nuevo?");
                ButtonType buttonTypeSi = new ButtonType("Si");
                ButtonType buttonTypeNo = new ButtonType("No");
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get().equals(buttonTypeSi)){
                    RegistroController registroController = new RegistroController(usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, this, "/vistas/login_vista.fxml");
                    ChangeScene.change(event, registroController, "/vistas/registro_vista.fxml");
                } else if (result.get().equals(buttonTypeNo)){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Usuario no encontrado");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Por favor, introduce el usuario y la contraseña correcta");
                    alert2.showAndWait();
                }
            }
        } catch (DatabaseErrorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void registrarNuevoUsuario(MouseEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario nuevo");
            alert.setHeaderText(null);
            alert.setContentText("Va a ser redirigido para poder crearse una cuenta");
            alert.showAndWait();
            RegistroController registroController = new RegistroController(usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, this, "/vistas/login_vista.fxml");
            ChangeScene.change((Stage) event, registroController, "/vistas/registro_vista.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void cerrar(ActionEvent event) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
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
package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.SQLfavoritoDAO;
import es.progcipfpbatoi.modelo.dao.SQLpeliculaSerieDAO;
import es.progcipfpbatoi.modelo.dao.SQLtemporadaDAO;
import es.progcipfpbatoi.modelo.dao.SQLvalorarDAO;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.FavoritosRepository;
import es.progcipfpbatoi.modelo.repositorios.PeliculaSerieRepository;
import es.progcipfpbatoi.modelo.repositorios.UsuarioRepository;
import es.progcipfpbatoi.modelo.repositorios.ValoracionesRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Initializable controladorPadre;
    private String vistaPadre;

    public RegistroController(Stage stage, UsuarioRepository usuarioRepository, Initializable controladorPadre, String vistaPadre) {
        this.stage = stage;
        this.usuarioRepository = usuarioRepository;
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
            if (!usuarioExiste) {
                PrincipalController principalController = new PrincipalController(stage, usuarioRepository, new PeliculaSerieRepository(new SQLpeliculaSerieDAO(), new SQLtemporadaDAO()), new FavoritosRepository(new SQLfavoritoDAO()), new ValoracionesRepository(new SQLvalorarDAO()));
                ChangeScene.change(stage, principalController, "/vistas/principal_vista.fxml");
            }
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
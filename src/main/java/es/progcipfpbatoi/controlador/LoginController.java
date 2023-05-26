package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.FavoritoDAO;
import es.progcipfpbatoi.modelo.dao.PeliculaSerieDAO;
import es.progcipfpbatoi.modelo.dao.TemporadaDAO;
import es.progcipfpbatoi.modelo.dao.ValorarDAO;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.PeliculaSerieRepository;
import es.progcipfpbatoi.modelo.repositorios.TemporadaRepository;
import es.progcipfpbatoi.modelo.repositorios.UsuarioRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usuario;
    @FXML
    private TextField password;
    private FavoritoDAO favoritoDAO;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private UsuarioRepository usuarioRepository;
    private Initializable controladorPadre;
    private String vistaPadre;

    public LoginController(TextField usuario, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, UsuarioRepository usuarioRepository, Initializable controladorPadre, String vistaPadre) {
        this.usuario = usuario;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.usuarioRepository = usuarioRepository;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        try {
            String user = usuario.getText();
            String contrasenya = password.getText();
            Usuario usuarioNuevo = new Usuario(user, contrasenya);
            boolean usuarioExiste = usuarioRepository.existeUsuario(usuarioNuevo);
            if (usuarioExiste) {
                //A la vista principal
            } else {
                RegistroController registroController = new RegistroController(usuarioRepository, this, "/vistas/login_vista.fxml");
                ChangeScene.change(event, registroController, "/vistas/registro_vista.fxml");
            }
        } catch (DatabaseErrorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
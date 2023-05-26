package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.FavoritoDAO;
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

    public LoginController(PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, UsuarioRepository usuarioRepository) {
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.usuarioRepository = usuarioRepository;
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
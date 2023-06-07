package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PeliculasController implements Initializable {

    private Initializable controladorPadre;
    private String vistaPadre;
    private UsuarioRepository usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;
    private Usuario usuario;

    @FXML
    private Label usuarioLabel;

    @FXML
    private ComboBox<Genero> generoDesplegable;

    @FXML
    private TextField filtroBusqueda;

    public PeliculasController(Initializable controladorPadre, String vistaPadre, UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Usuario usuario) {
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
        this.usuarioRepository = usuarioRepository;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.usuario = usuario;
    }

    @FXML
    private void changeToPrincipal(Event event) {
        try {
            ChangeScene.change(event, controladorPadre, vistaPadre);
        } catch (IOException e) {
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

    @FXML
    public void cambiarUsuario(ActionEvent event) throws IOException {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cambiar de usuario");
        alert.setHeaderText("");
        alert.setContentText("¿Estás seguro que quieres cambiar a otro usuario?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK ) {
            LoginController loginController = new LoginController(peliculaSerieRepository, temporadaRepository, usuarioRepository, favoritosRepository, valoracionesRepository);
            ChangeScene.change(event, loginController, "/vistas/login_vista.fxml");
        }
        alert.close();
    }

    @FXML
    private void irBuscar(ActionEvent event) throws IOException {
        Genero genero = generoDesplegable.getValue();
        String textoFiltro = filtroBusqueda.getText();
        BusquedaController busquedaController = new BusquedaController(this, "/vistas/principal_vista.fxml", usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, usuario, genero, textoFiltro);
        ChangeScene.change(event, busquedaController, "/vistas/busqueda_vista.fxml");
        System.out.println("hello world");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLabel.setText("Bienvenido " + usuario.getUsername());

        generoDesplegable.setItems(FXCollections.observableArrayList(Genero.values()));
    }
}

package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BusquedaController implements Initializable {

    private Initializable controladorPadre;
    private String vistaPadre;
    private UsuarioRepository usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;
    private Usuario usuario;
    private Genero genero;
    private String filtroTexto;

    @FXML
    private ListView<Produccion> resultadosListView;
    @FXML
    private Label usuarioLabel;

    @FXML
    private ComboBox<Genero> generoDesplegable;

    @FXML
    private TextField filtroBusqueda;

    public BusquedaController(Initializable controladorPadre, String vistaPadre, UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Usuario usuario, Genero genero, String filtroTexto) {
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
        this.usuarioRepository = usuarioRepository;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.usuario = usuario;
        this.genero = genero;
        this.filtroTexto = filtroTexto;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLabel.setText("Bienvenido " + usuario.getUsername());

        generoDesplegable.setItems(FXCollections.observableArrayList(Genero.values()));

        resultadosListView.setItems(getData());
        resultadosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultadosListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository, valoracionesRepository, usuario, controladorPadre));
    }

    private ObservableList<Produccion> getData() {
        try {
            if (genero == null) {
                System.out.println(filtroTexto);
                return FXCollections.observableArrayList(peliculaSerieRepository.findAll(filtroTexto));
            } else if (filtroTexto.equals("")) {
                System.out.println(genero);
                return FXCollections.observableArrayList(peliculaSerieRepository.findAll(genero));
            } else {
                System.out.println(filtroTexto + " " + genero);
                return FXCollections.observableArrayList(peliculaSerieRepository.findAll(filtroTexto, genero));
            }
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void buscar(ActionEvent event) throws IOException {
        this.genero = generoDesplegable.getValue();
        this.filtroTexto = filtroBusqueda.getText();
        ChangeScene.change(event, this, "/vistas/busqueda_vista.fxml");
        System.out.println("hello world");
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
        alert.setTitle( "Cambiar de usuario" );
        alert.setHeaderText( "" );
        alert.setContentText( "¿Estás seguro que quieres cambiar a otro usuario?" );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK ) {
            LoginController loginController = new LoginController(peliculaSerieRepository, temporadaRepository, usuarioRepository, favoritosRepository, valoracionesRepository);
            ChangeScene.change(event, loginController, "/vistas/login_vista.fxml");
        }
        alert.close();
    }
}

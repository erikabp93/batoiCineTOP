package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    private Stage stage;
    private Initializable controladorPadre;
    private String vistaPadre;
    private UsuarioRepository usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository temporadaRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;
    private Usuario usuario;

    @FXML
    private ListView<Produccion> peliculasListView;

    @FXML
    private ListView<Produccion> seriesListView;

    @FXML
    private Label usuarioLabel;

    public PrincipalController(Stage stage, UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository,
                               TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository,
                               ValoracionesRepository valoracionesRepository, Initializable controladorPadre, String vistaPadre, Usuario usuario) {
        this.stage = stage;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
        this.usuarioRepository = usuarioRepository;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.temporadaRepository = temporadaRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.usuario = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLabel.setText("Bienvenido " + usuario.getUsername());

        peliculasListView.setItems(getDataPeliculas());
        peliculasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        peliculasListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository, usuario));

        seriesListView.setItems(getDataSeries());
        seriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        seriesListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository, usuario));
    }

    private ObservableList<Produccion> getDataPeliculas() {
        try {
            return FXCollections.observableArrayList(valoracionesRepository.findAllPeliculas());
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<Produccion> getDataSeries() {
        try {
            return FXCollections.observableArrayList(valoracionesRepository.findAllSeries());
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }
}

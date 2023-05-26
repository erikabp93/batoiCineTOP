package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.repositorios.FavoritosRepository;
import es.progcipfpbatoi.modelo.repositorios.PeliculaSerieRepository;
import es.progcipfpbatoi.modelo.repositorios.UsuarioRepository;
import es.progcipfpbatoi.modelo.repositorios.ValoracionesRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    private Stage stage;
    private UsuarioRepository usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private FavoritosRepository favoritosRepository;
    private ValoracionesRepository valoracionesRepository;

    @FXML
    private ListView<Produccion> peliculasListView;

    @FXML
    private ListView<Produccion> seriesListView;

    public PrincipalController(Stage stage, UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository) {
        this.stage = stage;
        this.usuarioRepository = usuarioRepository;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliculasListView.setItems(getDataPeliculas());
        peliculasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        peliculasListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController());

        seriesListView.setItems(getDataSeries());
        seriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        seriesListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController());
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

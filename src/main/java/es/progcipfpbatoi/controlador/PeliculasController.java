package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador usado para manejar las peliculas
 */
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
    private ComboBox<String> filtro;

    @FXML
    private TextField filtroBusqueda;

    @FXML
    private ListView<Produccion> peliculasListView;

    @FXML
    private ListView<Produccion> favoritosListView;

    @FXML
    private RadioButton ordenAsc;

    /**
     * Constructor de la clase.
     * @param controladorPadre define qué cpmtrolador usará al volver a la vista anterior.
     * @param vistaPadre define la vista anterior.
     * @param usuarioRepository repositorio de usuarios que se encuentran en la base de datos.
     * @param peliculaSerieRepository repositorio de producciones para las listViews.
     * @param temporadaRepository repositorio de temporadas.
     * @param favoritosRepository repositorio de favoritos para ver si una pelicula está o no en favoritos.
     * @param valoracionesRepository repositorio de valoraciones para dejar o no valorar de nuevo.
     * @param usuario usuario logueado actualmente.
     */
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

    /**
     * Método para cambiar de vista cuando se acciona el botón (texto) del título de la página.
     * @param event define el evento de pulsar el botón.
     * @param event
     */
    @FXML
    private void changeToPrincipal(Event event) {
        try {
            ChangeScene.change(event, controladorPadre, vistaPadre);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cierra la aplicación cuando se pulsa el botón.
     * @param event define el evento de pulsar el botón.
     */
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

    /**
     * Vuelve a la vista de login para iniciar sesión con otro usuario o con el mismo.
     * @param event define el evento de pulsar el botón.
     * @throws IOException exception que lanza por defecto
     */
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

    /**
     * Cambia a la vista de series.
     * @param event
     */
    @FXML
    private void changeToTvShows(Event event) {
        try {
            ChangeScene.change(event, new SeriesController(this, "/vistas/principal_vista.fxml", usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, usuario), "/vistas/series_vista.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para cambiar de vista cuando se acciona el botón de buscar.
     * @param event define el evento de pulsar el botón.
     * @throws IOException
     */
    @FXML
    private void irBuscar(ActionEvent event) throws IOException {
        Genero genero = generoDesplegable.getValue();
        String textoFiltro = filtroBusqueda.getText();
        BusquedaController busquedaController = new BusquedaController(this, "/vistas/principal_vista.fxml", usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, usuario, genero, textoFiltro);
        ChangeScene.change(event, busquedaController, "/vistas/busqueda_vista.fxml");
        System.out.println("hello world");
    }

    /**
     * Obtiene los datos para la lista observable de producciones.
     *
     * @return La lista observable de producciones.
     * @throws RuntimeException si ocurre un DatabaseErrorException durante la búsqueda de datos.
     */
    private ObservableList<Produccion> getData() {
        try {
            return FXCollections.observableArrayList(peliculaSerieRepository.findAllPeliculas());
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene los datos para la lista observable de favoritos.
     *
     * @return La lista observable de producciones.
     * @throws RuntimeException si ocurre un DatabaseErrorException durante la búsqueda de datos.
     */
    private ObservableList<Produccion> getDataFavoritos() {
        try {
            ArrayList<Produccion> favoritos = new ArrayList<>();
            for (Produccion produccion : peliculaSerieRepository.findAllPeliculas()) {
                if (favoritosRepository.yaFavorito(usuario, produccion)) {
                    favoritos.add(produccion);
                }
            }
            return FXCollections.observableArrayList(favoritos);
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ordena la lista de producciones de la base de datos filtrando por peliculas o series.
     * @return el arrayList de producciones ordenadas.
     * @throws DatabaseErrorException
     */
    @FXML
    private void filtrar() {
        if (ordenAsc.isDisable()) {
            ordenAsc.setDisable(false);
        }
        try {
            peliculasListView.setItems(FXCollections.observableArrayList(peliculaSerieRepository.filtrar(true, filtro.getValue(), !ordenAsc.isSelected())));
            peliculasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            peliculasListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository,  valoracionesRepository, usuario, this));
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicializa la vista con unos valores para cada elemento.
     * @param url parametro por defecto
     * @param resourceBundle parametro por defecto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLabel.setText("Bienvenido " + usuario.getUsername());

        peliculasListView.setItems(getData());
        peliculasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        peliculasListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository,  valoracionesRepository, usuario, this));

        favoritosListView.setItems(getDataFavoritos());
        favoritosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        favoritosListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository,  valoracionesRepository, usuario, this));

        generoDesplegable.setItems(FXCollections.observableArrayList(Genero.values()));

        ArrayList<String> filtros = new ArrayList<>();
        filtros.add("Valoración");
        filtros.add("Titulo");
        filtros.add("Año");
        filtros.add("Duración");
        this.filtro.setItems(FXCollections.observableArrayList(filtros));
    }
}

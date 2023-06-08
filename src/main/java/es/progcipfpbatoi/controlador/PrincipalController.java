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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

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

    @FXML
    private ComboBox<Genero> generoDesplegable;

    @FXML
    private TextField filtroBusqueda;

    @FXML
    private Button botonCambiarUsuario;

    private Image imagenCambiarUsuario;

    private URL rutaCambiarUsuario;

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
    public PrincipalController(UsuarioRepository usuarioRepository, PeliculaSerieRepository peliculaSerieRepository, TemporadaRepository temporadaRepository, FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Initializable controladorPadre, String vistaPadre, Usuario usuario) {
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
     * Inicializa la vista con unos valores para cada elemento.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLabel.setText("Bienvenido " + usuario.getUsername());

        rutaCambiarUsuario = getClass().getResource("/images/perfil.png");
        imagenCambiarUsuario = new Image(rutaCambiarUsuario.toString(), 15, 15, false, true);
        botonCambiarUsuario.setGraphic(new ImageView(imagenCambiarUsuario));

        generoDesplegable.setItems(FXCollections.observableArrayList(Genero.values()));

        peliculasListView.setItems(getDataPeliculas());
        peliculasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        peliculasListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository,  valoracionesRepository, usuario, this));

        seriesListView.setItems(getDataSeries());
        seriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        seriesListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository, valoracionesRepository, usuario, this));
    }

    /**
     * Obtiene los datos para la lista observable de peliculas.
     *
     * @return La lista observable de peliculas.
     * @throws RuntimeException si ocurre un DatabaseErrorException durante la búsqueda de datos.
     */
    private ObservableList<Produccion> getDataPeliculas() {
        try {
            ArrayList<Produccion> pelis = new ArrayList<>();
            int iterator = 0;
            do {
                pelis.add(peliculaSerieRepository.findAllPeliculas().get(iterator));
                iterator++;
            } while (iterator < 10 && iterator < peliculaSerieRepository.findAllPeliculas().size());
            return FXCollections.observableArrayList(pelis);
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cambia a la vista de peliculas.
     * @param event
     */
    @FXML
    private void changeToMovies(Event event) {
        try {
            ChangeScene.change(event, new PeliculasController(this, "/vistas/principal_vista.fxml", usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, usuario), "/vistas/peliculas_vista.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
     * Obtiene los datos para la lista observable de series.
     *
     * @return La lista observable de series.
     * @throws RuntimeException si ocurre un DatabaseErrorException durante la búsqueda de datos.
     */
    private ObservableList<Produccion> getDataSeries() {
        try {
            ArrayList<Produccion> series = new ArrayList<>();
            int iterator = 0;
            do {
                series.add(peliculaSerieRepository.findAllSeries().get(iterator));
                iterator++;
            } while (iterator < peliculaSerieRepository.findAllSeries().size() && iterator < 10);

            return FXCollections.observableArrayList(series);
        } catch (DatabaseErrorException e) {
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
        alert.setTitle("Salir");
        alert.setHeaderText("");
        alert.setContentText("¿Estás seguro que quieres cerrar de la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK ) {
            System.exit(0);
        }
        alert.close();
    }

    /**
     * Vuelve a la vista de login para iniciar sesión con otro usuario o con el mismo.
     * @param event define el evento de pulsar el botón.
     * @throws IOException
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
}

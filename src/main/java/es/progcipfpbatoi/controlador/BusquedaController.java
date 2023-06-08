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
     * @param genero es el genero sobre el que filtrará.
     * @param filtroTexto es el texto que filtrará.
     */
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

    /**
     * Inicializa la vista con unos valores para cada elemento.
     * @param url parametro por defecto
     * @param resourceBundle parametro por defecto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLabel.setText("Bienvenido " + usuario.getUsername());

        rutaCambiarUsuario = getClass().getResource("/images/perfil.png");
        imagenCambiarUsuario = new Image(rutaCambiarUsuario.toString(), 15, 15, false, true);
        botonCambiarUsuario.setGraphic(new ImageView(imagenCambiarUsuario));


        generoDesplegable.setItems(FXCollections.observableArrayList(Genero.values()));

        resultadosListView.setItems(getData());
        resultadosListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultadosListView.setCellFactory((ListView<Produccion> l) -> new ProduccionListCellController(favoritosRepository, valoracionesRepository, usuario, controladorPadre));
    }

    /**
     * Obtiene los datos para la lista observable de producciones.
     *
     * @return La lista observable de producciones.
     * @throws RuntimeException si ocurre un DatabaseErrorException durante la búsqueda de datos.
     */
    public ObservableList<Produccion> getData() {
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
     * Método para cambiar de vista cuando se acciona el botón (texto) del título de la página.
     * @param event define el evento de pulsar el botón.
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
     * Cambia a la vista de peliculas.
     * @param event
     */
    @FXML
    private void changeToMovies(Event event) {
        try {
            ChangeScene.change(event, new PeliculasController(controladorPadre, vistaPadre, usuarioRepository, peliculaSerieRepository, temporadaRepository, favoritosRepository, valoracionesRepository, usuario), "/vistas/peliculas_vista.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Vuelve a la vista de búsqueda con los nuevos filtros.
     * @param event
     * @throws IOException
     */
    @FXML
    private void buscar(ActionEvent event) throws IOException {
        this.genero = generoDesplegable.getValue();
        this.filtroTexto = filtroBusqueda.getText();
        ChangeScene.change(event, this, "/vistas/busqueda_vista.fxml");
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

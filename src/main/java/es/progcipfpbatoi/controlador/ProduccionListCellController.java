package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.FavoritosRepository;
import es.progcipfpbatoi.modelo.repositorios.ValoracionesRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Controlador que muestra las celdas de la produccion
 */
public class ProduccionListCellController extends ListCell<Produccion> {

    @FXML
    private AnchorPane root;
    @FXML
    private Label titulo;
    @FXML
    private Label valoracion;
    @FXML
    private Label desc;
    @FXML
    private ImageView imagen;
    @FXML
    private ImageView favorito;

    private ValoracionesRepository valoracionesRepository;
    private FavoritosRepository favoritosRepository;
    private Usuario usuario;
    private Produccion produccion;
    private Initializable controladorPadre;

    /**
     * Constructor de la clase
     * @param favoritosRepository repositorio de favorito
     * @param valoracionesRepository repostiroio de las valoreaciones
     * @param usuario usuario con el que trabaja
     * @param controladorPadre controlador al que vovler
     */
    public ProduccionListCellController(FavoritosRepository favoritosRepository, ValoracionesRepository valoracionesRepository, Usuario usuario, Initializable controladorPadre) {
        this.favoritosRepository = favoritosRepository;
        this.valoracionesRepository = valoracionesRepository;
        this.usuario = usuario;
        this.controladorPadre = controladorPadre;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/vistas/produccion_listCell.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia el color del corazón cuando pasa el ratón por encima.
     */
    @FXML
    private void cambiaColorHover() {
        try {
            if (!favoritosRepository.yaFavorito(usuario, produccion)) {
                favorito.setImage(new Image(getPathImage("/images/add-favourite-hover.png")));
            }
        } catch (URISyntaxException | DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cambia el color del corazón a su color por defecto dependiendo de si és favorito o no
     */
    @FXML
    private void cambiaColorDefault() {
        try {
            if (!favoritosRepository.yaFavorito(usuario, produccion)) {
                favorito.setImage(new Image(getPathImage("/images/add-favourite.png")));
            }
        } catch (URISyntaxException | DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Añade o elimina una producción del repositorio de favoritos para el usuario actual
     */
    @FXML
    private void gestionFavorito() {
        try {
            if (favoritosRepository.yaFavorito(usuario, produccion)) {
                favoritosRepository.delete(produccion, usuario);
                favorito.setImage(new Image(getPathImage("/images/add-favourite.png")));
            } else {
                favoritosRepository.save(produccion, usuario);
                favorito.setImage(new Image(getPathImage("/images/favourite.png")));
            }
        } catch (DatabaseErrorException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cambia a la vista de detalles se la producción seleccionada.
     * @param event
     */
    @FXML
    private void verDetalles(ActionEvent event) {
        try {
            DetallesController detallesController = new DetallesController(usuario, produccion, valoracionesRepository, controladorPadre, "/vistas/principal_vista.fxml");
            ChangeScene.change(event, detallesController, "/vistas/detalles_produccion_vista.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicializa la vista con ls valores y textos adecuados para la producción dada.
     * @param produccion producción de la que se muestran los detalles
     * @param empty de tipo booleano
     */
    @Override
    protected void updateItem(Produccion produccion, boolean empty) {

        super.updateItem(produccion, empty);

        if (empty) {
            setGraphic(null);
        } else {
            this.produccion = produccion;
            try {
                if (favoritosRepository.yaFavorito(this.usuario, produccion)) {
                    favorito.setImage(new Image(getPathImage("/images/favourite.png")));
                }
            } catch (DatabaseErrorException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

            Image image = new Image(ValoracionesRepository.getPoster(produccion.getId()));
            if (image.isError()) {
                try {
                    imagen.setImage(new Image(getPathImage("/images/Image_not_available.png")));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            } else {
                imagen.setImage(image);
            }
            valoracion.setText("Valoración: " + produccion.getValoracionTotal());
            titulo.setText(produccion.getTitulo());
            desc.setText(produccion.getGuion());
            setGraphic(root);
        }
    }

    /**
     * Obtiene la ruta de la imagen
     * @param fileName la imágen que queremos
     * @return la imágen con la ruta
     * @throws URISyntaxException
     */
    private String getPathImage(String fileName) throws URISyntaxException {

        return getClass().getResource(fileName).toURI().toString();
    }
}

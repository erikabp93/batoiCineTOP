package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.repositorios.ValoracionesRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URISyntaxException;

public class ProduccionListCellController extends ListCell<Produccion> {

    @FXML
    private AnchorPane root;
    @FXML
    private Label titulo;
    @FXML
    private Label valoracion;
    @FXML
    private ImageView imagen;
    @FXML
    private ImageView favorito;

    public ProduccionListCellController( ) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/vistas/prduccion_listCell.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cambiaColorHover() {
        try {
            favorito.setImage(new Image(getPathImage("/images/add-favourite-hover.png")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void cambiaColorDefault() {
        try {
            favorito.setImage(new Image(getPathImage("/images/add-favourite.png")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Produccion produccion, boolean empty) {

        super.updateItem(produccion, empty);

        if (empty) {
            setGraphic(null);
        } else {
            Image image = new Image(ValoracionesRepository.getPoster(produccion.getId()));
            imagen.setImage(image);
            int valoracionInt = ValoracionesRepository.getValoracion(produccion.getId());
            valoracion.setText("Valoración: " + valoracionInt);
            titulo.setText(produccion.getTitulo());
            setGraphic(root);
        }
    }

    private String getPathImage(String fileName) throws URISyntaxException {

        return getClass().getResource(fileName).toURI().toString();
    }
}

package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.modelo.repositorios.ValoracionesRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador que nos muestra los detalles
 */
public class DetallesController implements Initializable {

    private Usuario usuario;
    private Produccion produccion;
    private Initializable controladorPadre;
    private String vistaPadre;
    private ValoracionesRepository valoracionesRepository;
    @FXML
    private ToggleButton valoracion1;
    @FXML
    private ToggleButton valoracion2;
    @FXML
    private ToggleButton valoracion3;
    @FXML
    private ToggleButton valoracion4;
    @FXML
    private ToggleButton valoracion5;
    @FXML
    private Label descripcion;
    @FXML
    private ImageView imagen;

    /**
     * Constructor de la clase
     * @param usuario usuario logueado
     * @param produccion producción de la que se verán los detalles
     * @param valoracionesRepository repositorio de valoraciones para ver si ya se ha valorado o no
     * @param controladorPadre controlador desde el que se accede a la vista
     * @param vistaPadre vista desde la que se accede al controlador
     */
    public DetallesController(Usuario usuario, Produccion produccion, ValoracionesRepository valoracionesRepository, Initializable controladorPadre, String vistaPadre) {
        this.usuario = usuario;
        this.produccion = produccion;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
        this.valoracionesRepository = valoracionesRepository;
    }

    /**
     * Vuelve hacia atrás
     * @param event evento que nos lleva a la vista
     */
    @FXML
    private void verMasTarde(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeScene.change(stage, controladorPadre, vistaPadre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Valora la producción con una puntuación del 1-5 y dejando un comentario meidante una ventana emergente
     * @param event evento de la app
     */
    @FXML
    private void valorar(ActionEvent event) {
        try {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Comentario valoración");
            dialog.setHeaderText("Por favor, escriba un comentario para la valoración de la produccion");
            dialog.setContentText("Comentario:");
            String result = String.valueOf(dialog.showAndWait());
            int valoracion;
            if (valoracion1.isSelected()) {
                valoracion = 1;
            } else if (valoracion2.isSelected()) {
                valoracion = 2;
            } else if (valoracion3.isSelected()) {
                valoracion = 3;
            } else if (valoracion4.isSelected()) {
                valoracion = 4;
            } else {
                valoracion = 5;
            }
            valoracionesRepository.save(produccion, usuario, valoracion, result.substring(9, result.length()-1));
            valoracion1.setSelected(false);
            valoracion2.setSelected(false);
            valoracion3.setSelected(false);
            valoracion4.setSelected(false);
            valoracion5.setSelected(false);
        } catch (DatabaseErrorException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cierra la aplicación cuando se pulsa el botón.
     * @param event define el evento de pulsar el botón.
     */
    @FXML
    public void cerrar(ActionEvent event) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
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
     * Inicializa la vista con unos valores para cada elemento.
     * @param location parametro por defecto
     * @param resources parametro por defecto
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        descripcion.setText(produccion.getGuion());
        Image image = new Image(ValoracionesRepository.getPoster(produccion.getId()));
        imagen.setImage(image);
    }
}
package es.progcipfpbatoi.controlador;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que nos permite cambiar de vista
 */
public class ChangeScene {
    /**
     * Constructor por defecto
     */
    public ChangeScene() {
    }

    /**
     * Constructor de la clase.
     * @param stage evento
     * @param controller controlador a usar
     * @param path_to_view_file ruta de la vista
     * @throws IOException exception por defecto
     */
    public static void change(Stage stage, Initializable controller, String path_to_view_file) throws IOException{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ChangeScene.class.getResource(path_to_view_file));
            fxmlLoader.setController(controller);

            AnchorPane rootLayout = fxmlLoader.load();

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
    }

    /**
     * Constructor de la clase con event en vez de stage
     * @param event evento de la accion
     * @param controller controlador a usar
     * @param path_to_view_file ruta de la vista
     * @throws IOException exception por defecto
     */
    public static void change(Event event, Initializable controller, String path_to_view_file) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        change(stage, controller, path_to_view_file);
    }
}
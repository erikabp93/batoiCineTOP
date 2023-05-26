package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.modelo.dto.Produccion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProduccionListCellController extends ListCell<Produccion> {

    @FXML
    private AnchorPane root;

    public ProduccionListCellController( ) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/vista/list_item_producto.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Produccion produccion, boolean empty) {

        super.updateItem(produccion, empty);

        if (empty) {
            setGraphic(null);
        } else {


            setGraphic(root);
        }
    }
}

package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.repositorios.PeliculaSerieRepository;
import es.progcipfpbatoi.util.AlertMessages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TareaSearchController implements Initializable {
    @FXML
    private TextField searchBar;
    private PeliculaSerieRepository peliculaSerieRepository;

    public TareaSearchController (PeliculaSerieRepository peliculaSerieRepository) {
        this.peliculaSerieRepository = peliculaSerieRepository;
    }
    @FXML
    private void searchTask(ActionEvent event) {
        try {
            int id = Integer.parseInt(searchBar.getText());
            Tarea tarea = this.peliculaSerieRepository.getById(id);
            TareaDetailController tareaDetailController = new TareaDetailController(tarea, peliculaSerieRepository, this, "/vistas/tarea_search.fxml");
            ChangeScene.change(event, tareaDetailController, "/vistas/tarea_detail.fxml");
        } catch (NumberFormatException ex) {
            AlertMessages.mostrarAlertWarning("Sólo se deben introducir números");
        } catch (NotFoundException | DatabaseErrorException ex) {
            AlertMessages.mostrarAlertWarning(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

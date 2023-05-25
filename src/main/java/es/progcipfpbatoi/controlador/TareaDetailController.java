package es.progcipfpbatoi.controlador;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.repositorios.PeliculaSerieRepository;
import es.progcipfpbatoi.util.AlertMessages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TareaDetailController implements Initializable {

    @FXML
    private TextField textFieldDescripcion;

    @FXML
    private DatePicker datePickerFecha;

    @FXML
    private CheckBox checkBoxFinalizada;

    private Tarea tarea;
    private PeliculaSerieRepository peliculaSerieRepository;

    private Initializable controladorPadre;
    private String vistaPadre;

    public TareaDetailController(
            Tarea tarea,
            PeliculaSerieRepository peliculaSerieRepository,
            Initializable controladorPadre,
            String vistaPadre) {
        this.tarea = tarea;
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.controladorPadre = controladorPadre;
        this.vistaPadre = vistaPadre;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldDescripcion.setText(tarea.getDescripcion());

        datePickerFecha.setEditable(false);
        datePickerFecha.setOnMouseClicked(e -> {
            if(!datePickerFecha.isEditable()) {
                datePickerFecha.hide();
            }
        });

        datePickerFecha.setValue(tarea.getFechaAltaSinTiempo());

        this.checkBoxFinalizada.setSelected(this.tarea.isFinalizada());
    }

    @FXML
    private void handleChangeInFinalizada() {
        try {
            this.tarea.cambiarEstado();
            this.peliculaSerieRepository.save(tarea);
        } catch (DatabaseErrorException ex) {
            AlertMessages.mostrarAlertError("No se ha podido guardar la tarea. Error en el acceso a la base de datos.");
        }
    }

    @FXML
    private void handleButtonBack(ActionEvent event) {
        try {
            ChangeScene.change(event, controladorPadre, vistaPadre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeTask(ActionEvent event) {
        try {
            this.peliculaSerieRepository.remove(tarea);
            ChangeScene.change(event, controladorPadre, vistaPadre);
        } catch (NotFoundException | DatabaseErrorException ex) {
            ex.printStackTrace();
            AlertMessages.mostrarAlertError(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}

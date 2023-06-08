package es.progcipfpbatoi.util;

import javafx.scene.control.Alert;

/**
 * Clase que trabaja con las alertas
 */
public class AlertMessages {
    /**
     * Comentario obligatorio del constructor
     */
    public AlertMessages() {
    }

    /**
     * Muestra una alerta sonre el mensaje pasado por parámetro
     * Esta alerta es de tipo error.
     *
     * @param msg de tipo String
     */
    public static void mostrarAlertError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta sonre el mensaje pasado por parámetro
     * Esta alerta es de tipo warning.
     *
     * @param msg de tipo String
     */
    public static void mostrarAlertWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Aviso");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta con su cabecera, mensaje y tipo de alerta pasada por parámetro.
     *
     * @param title tipo String
     * @param msg tipo String
     * @param alertType tipo AlertType
     * @return void
     */
    private static void mostrarAlert(String title, String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

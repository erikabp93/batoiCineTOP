/**
 * module por defecto
 */
module es.progcipfpbatoi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens es.progcipfpbatoi.controlador to javafx.fxml;
    exports es.progcipfpbatoi;
}
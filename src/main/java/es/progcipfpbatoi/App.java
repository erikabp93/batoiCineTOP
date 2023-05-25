package es.progcipfpbatoi;

import es.progcipfpbatoi.modelo.dao.SQLpeliculaSerieDAO;
import es.progcipfpbatoi.modelo.dao.SQLtemporadaDAO;
import es.progcipfpbatoi.modelo.repositorios.PeliculaSerieRepository;
import es.progcipfpbatoi.modelo.repositorios.TemporadaRepository;
import es.progcipfpbatoi.services.MySqlConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public void start(Stage stage) throws IOException {
        SQLtemporadaDAO     sqLtemporadaDAO     = new SQLtemporadaDAO();
        SQLpeliculaSerieDAO sqLpeliculaSerieDAO = new SQLpeliculaSerieDAO();

        // Creación del repositorio que será el que interactuará con el controlador.
        PeliculaSerieRepository peliculaSerieRepository = new PeliculaSerieRepository( sqLpeliculaSerieDAO, sqLtemporadaDAO );

        TemporadaRepository temporadaRepository = new TemporadaRepository( sqLtemporadaDAO );
        // Se crea al controlador proporcionando el/los repositorio/s que necesita
        //TareaController tareaController = new TareaController( peliculaSerieRepository );


        stage.setOnCloseRequest( event -> {
            System.out.println( "App closed" );
            new MySqlConnection( SQLtemporadaDAO.IP, SQLtemporadaDAO.DATABASE, SQLtemporadaDAO.USERNAME, SQLtemporadaDAO.PASSWORD ).closeConnection();
        } );

    }

    public static void main(String[] args) {
        launch();
    }

}
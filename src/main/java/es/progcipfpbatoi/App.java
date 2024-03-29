package es.progcipfpbatoi;

import es.progcipfpbatoi.controlador.ChangeScene;
import es.progcipfpbatoi.controlador.LoginController;
import es.progcipfpbatoi.controlador.PrincipalController;
import es.progcipfpbatoi.modelo.dao.*;
import es.progcipfpbatoi.modelo.repositorios.*;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    /**
     * Constructor por defecto, obligatorio ponerlo
     */
    public App() {
    }

    /**
     * Metodo que inicia la aplicacion con sus respectivos DAO, repositorios y controlador
     * @param stage parametro por defecto
     * @throws IOException exception al no acceder a la base de datos
     */
    public void start(Stage stage) throws IOException {
        SQLtemporadaDAO     sqLtemporadaDAO     = new SQLtemporadaDAO();
        SQLpeliculaSerieDAO sqLpeliculaSerieDAO = new SQLpeliculaSerieDAO();
        SQLUsuarioDAO sqLusuarioDAO  = new SQLUsuarioDAO();
        SQLvalorarDAO sqLvalorarDAO = new SQLvalorarDAO();
        SQLfavoritoDAO sqLfavoritoDAO = new SQLfavoritoDAO();

        // Creación del repositorio que será el que interactuará con el controlador.
        PeliculaSerieRepository peliculaSerieRepository = new PeliculaSerieRepository( sqLpeliculaSerieDAO, sqLtemporadaDAO );
        TemporadaRepository temporadaRepository = new TemporadaRepository( sqLtemporadaDAO );
        UsuarioRepository usuarioRepository = new UsuarioRepository(sqLusuarioDAO);
        ValoracionesRepository valoracionesRepository = new ValoracionesRepository(sqLvalorarDAO, peliculaSerieRepository);
        FavoritosRepository favoritosRepository = new FavoritosRepository(sqLfavoritoDAO);
        // Se crea al controlador proporcionando el/los repositorio/s que necesita
        LoginController loginController = new LoginController(peliculaSerieRepository, temporadaRepository, usuarioRepository, favoritosRepository, valoracionesRepository);
        ChangeScene.change(stage, loginController, "/vistas/login_vista.fxml");

        stage.setOnCloseRequest( event -> {
            System.out.println( "App closed" );
            new MySqlConnection( DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD ).closeConnection();
        } );

    }

    /**
     * Metodo que lanza la aplicacion
     * @param args argumento por defecto
     */
    public static void main(String[] args) {
        launch();
    }

}
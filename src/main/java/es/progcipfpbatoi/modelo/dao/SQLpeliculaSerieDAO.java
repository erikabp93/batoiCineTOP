package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.PeliculaSerieDAO;
import es.progcipfpbatoi.modelo.dto.Calificacion;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Tipo;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class SQLpeliculaSerieDAO implements PeliculaSerieDAO {

    private              Connection connection;
    private static final String     TABLE_NAME = "produccion";
    private static final String     IP         = "192.168.18.27";
    private static final String     DATABASE   = "batoiCine_bd";
    private static final String     USERNAME   = "batoi";
    private static final String     PASSWORD   = "1234";

    @Override
    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s", TABLE_NAME );

        ArrayList<Produccion> producciones = new ArrayList<>();
        connection = new MySqlConnection( IP, DATABASE, USERNAME, PASSWORD ).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery( sql );
        ) {

            while ( resultSet.next() ) {
                Produccion produccion = getProduccionFromResultset( resultSet );
                producciones.add( produccion );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en la conexión o acceso a la base de datos (select)" );
        }

        return producciones;
    }

    @Override
    public ArrayList<Produccion> findAll(String text) throws DatabaseErrorException {
        ArrayList<Produccion> producciones = new ArrayList<>();
        for ( Produccion produccion : findAll() ) {
            if ( produccion.empiezaPor( text ) ) {
                producciones.add( produccion );
            }
        }

        return producciones;
    }

    @Override
    public Produccion getById(int id) throws NotFoundException, DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s WHERE id = ?", TABLE_NAME );
        connection = new MySqlConnection( IP, DATABASE, USERNAME, PASSWORD ).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
        ) {
            statement.setInt( 1, id );
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Produccion produccion = getProduccionFromResultset( resultSet );
                if ( produccion.getId() == id ) {
                    return produccion;
                }
            }

            throw new NotFoundException( "No existe una tarea con el id " + id );

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (select)" );
        }
    }

    @Override
    public Produccion findById(int id) throws DatabaseErrorException {
        try {
            return getById( id );
        } catch ( NotFoundException ex ) {
            return null;
        }
    }

    @Override
    public Produccion save(Produccion produccion) throws DatabaseErrorException {
        if ( findById( produccion.getId() ) == null ) {
            return insert( produccion );
        } else {
            return update( produccion );
        }
    }

    private Produccion insert(Produccion produccion) throws DatabaseErrorException {
        String sql = String.format( "INSERT INTO %s (id, descripcion, fechaAlta, finalizada, categoria) VALUES (?,?,?,?,?)",
                TABLE_NAME );
        connection = new MySqlConnection( IP, DATABASE, USERNAME, PASSWORD ).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            preparedStatement.setInt( 1, produccion.getId() );
            preparedStatement.setString( 2, produccion.getDescripcion() );
            preparedStatement.setTimestamp( 3, Timestamp.valueOf( produccion.getFechaAlta() ) );
            preparedStatement.setInt( 4, produccion.isFinalizada() ? 1 : 0 );
            preparedStatement.setInt( 5, produccion.getCategoria().getId() );
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if ( resultSet.next() ) {
                produccion.setId( resultSet.getInt( 1 ) );
            }

            return produccion;

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (insert)" );
        }
    }

    private Produccion update(Produccion produccion) throws DatabaseErrorException {
        String sql = String.format( "UPDATE %s SET descripcion = ?, fechaAlta = ?, finalizada = ?, categoria = ? WHERE id = ?",
                TABLE_NAME );
        connection = new MySqlConnection( IP, DATABASE, USERNAME, PASSWORD ).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setString( 1, produccion.getDescripcion() );
            statement.setTimestamp( 2, Timestamp.valueOf( produccion.getFechaAlta() ) );
            statement.setInt( 3, produccion.isFinalizada() ? 1 : 0 );
            statement.setInt( 4, produccion.getCategoria().getId() );
            statement.setInt( 5, produccion.getId() );
            statement.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (update)" );
        }

        return produccion;
    }

    @Override
    public void remove(Produccion produccion) throws DatabaseErrorException, NotFoundException {
        String sql = String.format( "DELETE FROM %s WHERE id = ?", TABLE_NAME );
        connection = new MySqlConnection( IP, DATABASE, USERNAME, PASSWORD ).getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setInt( 1, produccion.getId() );
            statement.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (delete)" );
        }
    }

    private Produccion getProduccionFromResultset(ResultSet rs) throws SQLException {
        int     id               = rs.getInt( "id" );
        int     duracion         = rs.getInt( "duracion" );
        String  actores          = rs.getString( "actores" );
        String  nombre           = rs.getString( "titulo" );
        HashSet<Genero> genero = (HashSet<Genero>) rs.getObject( "genero" );
        String       director         = rs.getString( "director" );
        String       urlTrailer       = rs.getString( "urlTrailer" );
        String       productor        = rs.getString( "productor" );
        Tipo         tipo             = (Tipo) rs.getObject( "tipo" );
        Calificacion calificacion     = (Calificacion) rs.getObject( "calificacion" );
        String       poster           = rs.getString( "poster" );
        String       guion            = rs.getString( "guion" );
        String       plataforma       = rs.getString( "plataforma" );
        LocalDate    fechaLanzamiento = LocalDate.from( rs.getTimestamp( "fechaLanzamiento" ).toLocalDateTime() );
        int          visualizaciones  = rs.getInt( "visualizaciones" );
        return new Produccion( id, duracion, actores, nombre, genero, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento, visualizaciones );
    }
}
package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.PeliculaSerieDAO;
import es.progcipfpbatoi.modelo.dto.Calificacion;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Tipo;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class SQLpeliculaSerieDAO implements PeliculaSerieDAO {

    private              Connection connection;
    private static final String     TABLE_NAME = "produccion";

    @Override
    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s", TABLE_NAME );

        ArrayList<Produccion> producciones = new ArrayList<>();
        connection = new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

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
        connection = new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

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
        String sql = String.format( "INSERT INTO %s (id, duracion, actores, titulo, genero, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento, visualizaciones) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME );
        connection = new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            preparedStatement.setInt( 1, produccion.getId() );
            preparedStatement.setInt( 2, produccion.getDuracion() );
            preparedStatement.setString( 3, produccion.getActores() );
            preparedStatement.setString( 4, produccion.getTitulo() );
            preparedStatement.setString( 5, produccion.getGenero().toString() );
            preparedStatement.setString( 6, produccion.getDirector() );
            preparedStatement.setString( 7, produccion.getUrlTrailer() );
            preparedStatement.setString( 8, produccion.getProductor() );
            preparedStatement.setString( 9, produccion.getTipo().toString() );
            preparedStatement.setString( 10, produccion.getCalificacion().toString() );
            preparedStatement.setString( 11, produccion.getPoster() );
            preparedStatement.setString( 12, produccion.getGuion() );
            preparedStatement.setString( 13, produccion.getPlataforma() );
            preparedStatement.setDate( 14, Date.valueOf( produccion.getFechaLanzamiento() ) );
            preparedStatement.setInt( 15, produccion.getVisualizaciones() );
            preparedStatement.executeUpdate();
            return produccion;

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (insert)" );
        }
    }

    private Produccion update(Produccion produccion) throws DatabaseErrorException {
        String sql = String.format( "UPDATE %s SET duracion = ?, actores = ?, titulo = ?, genero = ?, director = ?, urlTrailer = ?, productor = ?, tipo = ?, calificacion = ?, poster = ?, guion = ?, plataforma = ?, fechaLanzamiento = ?, visualizaciones = ? WHERE id = ?", TABLE_NAME );
        connection = new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setInt( 1, produccion.getVisualizaciones() );
            statement.setInt( 2, produccion.getDuracion() );
            statement.setString( 3, produccion.getActores() );
            statement.setString( 4, produccion.getTitulo() );
            statement.setString( 5, produccion.getGenero().toString() );
            statement.setString( 6, produccion.getDirector() );
            statement.setString( 7, produccion.getUrlTrailer() );
            statement.setString( 8, produccion.getProductor() );
            statement.setString( 9, produccion.getTipo().toString() );
            statement.setString( 10, produccion.getCalificacion().toString() );
            statement.setString( 11, produccion.getPoster() );
            statement.setString( 12, produccion.getGuion() );
            statement.setString( 13, produccion.getPlataforma() );
            statement.setDate( 14, Date.valueOf( produccion.getFechaLanzamiento() ) );
            statement.setInt( 15, produccion.getId() );
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
        connection = new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();
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
        int             id               = rs.getInt( "id" );
        int             duracion         = rs.getInt( "duracion" );
        String          actores          = rs.getString( "actores" );
        String          nombre           = rs.getString( "titulo" );
        String[] generos = rs.getString("genero").split(",");
        HashSet<Genero> genero = new HashSet<>();
        for (String actual : generos) {
            genero.add(Genero.valueOf(actual.toUpperCase()));
        }
        String          director         = rs.getString( "director" );
        String          urlTrailer       = rs.getString( "urlTrailer" );
        String          productor        = rs.getString( "productor" );
        Tipo            tipo             = Tipo.valueOf(rs.getString("tipo"));
        Calificacion    calificacion     = Calificacion.valueOf(rs.getString("calificacion"));
        String          poster           = rs.getString( "poster" );
        String          guion            = rs.getString( "guion" );
        String          plataforma       = rs.getString( "plataforma" );
        LocalDate       fechaLanzamiento = LocalDate.from( rs.getTimestamp( "fechaLanzamiento" ).toLocalDateTime() );
        int             visualizaciones  = rs.getInt( "visualizaciones" );
        return new Produccion( id, duracion, actores, nombre, genero, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento, visualizaciones );
    }
}
package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Temporada;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * DAO de temporada
 */
public class SQLtemporadaDAO implements TemporadaDAO {

    /**
     * constructor obligatorio
     */
    public SQLtemporadaDAO() {
    }

    private              Connection connection;
    private static final String     TABLE_NAME = "temporadas";

    /**
     * Devuelve todas las temporadas
     *
     * @return arraylist de temporadas
     * @throws DatabaseErrorException lanza la exception
     */
    @Override
    public ArrayList<Temporada> findAll() throws DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s", TABLE_NAME );

        ArrayList<Temporada> temporadas = new ArrayList<>();
        connection = new MySqlConnection( DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD ).getConnection();

        try (
                Statement statement = connection.createStatement() ;
                ResultSet resultSet = statement.executeQuery( sql ) ;
        ) {

            while ( resultSet.next() ) {
                Temporada temporada = getTemporadaFromResultset( resultSet );
                temporadas.add( temporada );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en la conexión o acceso a la base de datos (select)" );
        }

        return temporadas;
    }

    /**
     * Busca la temporada por id
     *
     * @param id id de la temporada a buscar
     * @return temporada encontrada
     * @throws DatabaseErrorException lanza la exception
     */
    @Override
    public Temporada findById(int id) throws DatabaseErrorException {
        String sql = String.format( "SELECT * FROM %s WHERE id = ?", TABLE_NAME );
        connection = new MySqlConnection( DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD ).getConnection();

        try ( PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS ) ) {
            statement.setInt( 1, id );
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Temporada temporada = getTemporadaFromResultset( resultSet );
                if ( temporada.getId() == id ) {
                    return temporada;
                }
            }

            return null;

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (select)" );
        }
    }

    /**
     * Guarda la temporada
     *
     * @param temporada temporada a guardar
     * @return Temporada guardad
     * @throws DatabaseErrorException lanza la exception
     */
    @Override
    public Temporada save(Temporada temporada) throws DatabaseErrorException {
        if ( findById( temporada.getId() ) == null ) {
            return insert( temporada );
        } else {
            return update( temporada );
        }
    }

    /**
     * Inserta la temporada
     *
     * @param temporada temporada a insertar
     * @return Temporada insertada
     * @throws DatabaseErrorException lanza la exception
     */
    private Temporada insert(Temporada temporada) throws DatabaseErrorException {
        String sql = String.format( "INSERT INTO %s (id, id_serie, plot, fechaLanzamiento, numCapitulos) VALUES (?,?,?,?,?)",
                TABLE_NAME );
        connection = new MySqlConnection( DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD ).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            Date date = new Date();
            preparedStatement.setInt( 1, temporada.getId() );
            preparedStatement.setInt( 2, temporada.getId_serie() );
            preparedStatement.setString( 3, temporada.getPlot() );
            preparedStatement.setInt( 4, temporada.getFechaLanzamiento() );
            preparedStatement.setInt( 5, temporada.getNumCapitulos() );

            preparedStatement.executeUpdate();

            return temporada;

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)" );
        }
    }

    /**
     * Actualiza la temporada
     *
     * @param temporada temporada que se actualiza
     * @return temporada actualiza
     * @throws DatabaseErrorException lanza la exception
     */
    private Temporada update(Temporada temporada) throws DatabaseErrorException {
        String sql = String.format("UPDATE %s SET id_serie = ?, plot = ?, fechaLanzamiento = ?, numCapitulos = ? WHERE id = ?",
                TABLE_NAME );
        connection = new MySqlConnection( DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD ).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS )
        ) {
            statement.setInt( 1, temporada.getId_serie() );
            statement.setString( 2, temporada.getPlot() );
            statement.setInt( 3, temporada.getFechaLanzamiento() );
            statement.setInt( 4, temporada.getNumCapitulos() );
            statement.setInt( 5, temporada.getId() );
            statement.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ha ocurrido un error en el acceso o conexión a la base de datos (update)" );
        }

        return temporada;
    }

    /**
     * Convierte de rs a temporada
     *
     * @param rs rs que se pasa por parametro
     * @return Temporada como objeto
     * @throws SQLException lanza la exception
     */
    private Temporada getTemporadaFromResultset(ResultSet rs) throws SQLException {
        int    id                = rs.getInt( "id" );
        int    id_serie          = rs.getInt( "id_serie" );
        String plot              = rs.getString( "plot" );
        int    fecha_lanzamiento = rs.getInt( "fechaLanzamiento" );
        int    numCapitulos      = rs.getInt( "numCapitulos" );

        return new Temporada( id, id_serie, plot, fecha_lanzamiento, numCapitulos );
    }
}

package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.*;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;

import java.sql.*;
import java.util.ArrayList;

/**
 * DAO de favorito
 */
public class SQLfavoritoDAO implements FavoritoDAO{

    private Connection connection;
    private static final String TABLE_NAME = "favoritos";
    private static final String IP = "172.16.226.93";
    private static final String DATABASE = "batoiCine_bd";
    private static final String USERNAME = "batoi";
    private static final String PASSWORD = "1234";

    /**
     * Devuelve todas las producciones que el user tenga en favortios
     *
     * @param usuario usuario por parametro
     * @return ArrayList de producciones
     * @throws DatabaseErrorException lanza la exception
     */
    @Override
    public ArrayList<Produccion> findAll(Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE username LIKE '%s'", TABLE_NAME, usuario.getUsername());

        ArrayList<Produccion> producciones = new ArrayList<>();
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Produccion produccion = getProduccionFromResultset(resultSet);
                producciones.add(produccion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return producciones;
    }

    /**
     * Devuelve true si el usuario ha podido guardar la produccion. En caso contrario devuelve false
     *
     * @param produccion produccion a guardar
     * @param usuario usuario que quiere guardar la produccion
     * @return boolean
     * @throws DatabaseErrorException lanza la exception
     */
    @Override
    public boolean save(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (id_produccion, username) VALUES (?,?)", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        if (yaFavorito(usuario, produccion)) {
            return false;
        }

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setInt(1, produccion.getId());
            preparedStatement.setString(2, usuario.getUsername());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (insert)");
        }
    }

    /**
     * Devuelve true si el usuario ha podido eliminar la produccion. En caso contrario devuelve false
     *
     * @param produccion produccion a eliminar
     * @param usuario usuario que quiere eliminar la produccion
     * @return boolean
     * @throws DatabaseErrorException lanza la exception
     */
    @Override
    public boolean delete(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("DELETE FROM %s WHERE id_produccion =? AND username =?", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        if (!yaFavorito(usuario, produccion)) {
            return false;
        }

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, produccion.getId());
            preparedStatement.setString(2, usuario.getUsername());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (delete)");
        }
    }

    /**
     * Comprueba si la produccion del user ya esta en favorito. Si está, devuelve true. En caso contrario devuelve false
     *
     * @param usuario usuario que comprueba
     * @param produccion produccion a ser comprobada
     * @return boolena
     * @throws DatabaseErrorException
     */
    public boolean yaFavorito(Usuario usuario, Produccion produccion) throws DatabaseErrorException {
        for (Produccion production : findAll(usuario)) {
            if (production.equals(produccion)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convierte de rs a objeto produccion
     *
     * @param rs resulset que vamos a transformar
     * @return Produccion que vamos a devolver
     * @throws SQLException lanza la exception
     */
    private Produccion getProduccionFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_produccion");

        return new Produccion(id);
    }
}

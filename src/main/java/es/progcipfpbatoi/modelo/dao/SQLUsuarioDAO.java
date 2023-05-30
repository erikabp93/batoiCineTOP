package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;

import java.sql.*;

public class SQLUsuarioDAO implements UsuarioDAO {

    private Connection connection;
    private static final String TABLE_NAME = "usuarios";

    @Override
    public Usuario save(Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (username, password, email) VALUES (?,?,?)", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, usuario.getUsername());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.executeUpdate();

            return usuario;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    @Override
    public boolean existeUsuario(Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE username LIKE ? AND password LIKE ?", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
        ) {

            statement.setString(1, usuario.getUsername());
            statement.setString(2, usuario.getPassword());
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }
}
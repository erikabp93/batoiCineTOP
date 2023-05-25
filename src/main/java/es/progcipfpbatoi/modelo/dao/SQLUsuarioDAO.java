package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUsuarioDAO implements UsuarioDAO {

    private Connection connection;
    private static final String TABLE_NAME = "usuarios";
    private static final String IP = "192.168.18.27";
    private static final String DATABASE = "batoiCine_bd";
    private static final String USERNAME = "batoi";
    private static final String PASSWORD = "1234";

    @Override
    public Usuario save(Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (username, password, email) VALUES (?,?,?)", TABLE_NAME);
        connection =  new MySqlConnection(IP, DATABASE, USERNAME, PASSWORD).getConnection();

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
}
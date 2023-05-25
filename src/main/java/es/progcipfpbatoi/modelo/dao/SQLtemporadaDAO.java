package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Temporada;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLtemporadaDAO implements TemporadaDAO {

    private Connection connection;
    private static final String TABLE_NAME = "categorias";
    @Override
    public Temporada findById(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?",TABLE_NAME);
        connection =  new MySqlConnection("localhost", "tasks_db", "root", "123456").getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Temporada temporada = getTemporadaFromResultset(resultSet);
                if (temporada.getId() == id) {
                    return temporada;
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    private Temporada getTemporadaFromResultset(ResultSet rs) throws SQLException {
        /*
        int id = rs.getInt("id");
        Tipo tipo = Tipo.parse(rs.getString("tipo"));
        Prioridad prioridad = Prioridad.parse(rs.getString("prioridad"));
        */
        return new Temporada(0,null,null,null,0);
    }

    //meter inserts
}

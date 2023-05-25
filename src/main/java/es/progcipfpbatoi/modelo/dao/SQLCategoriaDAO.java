package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLCategoriaDAO implements CategoriaDAO{

    private Connection connection;
    private static final String TABLE_NAME = "categorias";
    @Override
    public Categoria findById(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?",TABLE_NAME);
        connection =  new MySqlConnection("localhost", "tasks_db", "root", "123456").getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Categoria categoria = getCategoryFromResultset(resultSet);
                if (categoria.getId() == id) {
                    return categoria;
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    @Override
    public Categoria getByTypeAndPriority(Tipo tipo, Prioridad prioridad) throws NotFoundException, DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE tipo = ? AND prioridad = ?",TABLE_NAME);
        connection =  new MySqlConnection("localhost", "tasks_db", "root", "123456").getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, tipo.toString());
            statement.setString(2, prioridad.toString());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Categoria categoria = getCategoryFromResultset(resultSet);
                if (categoria.getTipo() == tipo && categoria.getPrioridad() == prioridad) {
                    return categoria;
                }
            }

            throw new NotFoundException("No existe una categoria registrada de tipo " + tipo + " y prioridad " + prioridad);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
    }

    private Categoria getCategoryFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Tipo tipo = Tipo.parse(rs.getString("tipo"));
        Prioridad prioridad = Prioridad.parse(rs.getString("prioridad"));
        return new Categoria(id, tipo, prioridad);
    }
}

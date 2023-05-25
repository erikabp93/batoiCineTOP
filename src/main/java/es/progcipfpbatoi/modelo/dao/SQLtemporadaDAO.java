package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Temporada;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLtemporadaDAO implements TemporadaDAO {

    private Connection connection;
    private static final String TABLE_NAME = "temporadas";
    private static final String IP = "172.16.226.88";
    private static final String DATABASE = "batoiCine_bd";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    @Override
    public ArrayList<Temporada> findAll() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<Temporada> temporadas = new ArrayList<>();
        connection =  new MySqlConnection(IP, DATABASE, USERNAME, PASSWORD).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Temporada temporada = getTemporadaFromResultset(resultSet);
                temporadas.add(temporada);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return temporadas;
    }

    @Override
    public Temporada findById(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?",TABLE_NAME);
        connection =  new MySqlConnection(IP, DATABASE, USERNAME, PASSWORD).getConnection();

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
        int id = rs.getInt("id");
        int id_serie = rs.getInt("id_serie");
        String plot = rs.getString("plot");
        LocalDate fecha_lanzamiento = rs.getTimestamp("fechaLanzamiento").toLocalDateTime().toLocalDate();
        int numCapitulos = rs.getInt("numCapitulos");

        return new Temporada(id,id_serie,plot,fecha_lanzamiento,numCapitulos);
    }
}

package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Temporada;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class SQLtemporadaDAO implements TemporadaDAO {

    private Connection connection;
    private static final String TABLE_NAME = "temporadas";
    public static final String IP = "172.16.226.108";
    public static final String DATABASE = "batoiCine_bd";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "1234";

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

    @Override
    public Temporada save(Temporada temporada) throws DatabaseErrorException {
        if (findById(temporada.getId()) == null) {
            return insert(temporada);
        } else {
            return update(temporada);
        }
    }

    private Temporada insert(Temporada temporada) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (id, id_serie, plot, fechaLanzamiento, numCapitulos) VALUES (?,?,?,?,?)",
                TABLE_NAME);
        connection =  new MySqlConnection(IP, DATABASE, USERNAME, PASSWORD).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, temporada.getId());
            preparedStatement.setInt(2, temporada.getId_serie());
            preparedStatement.setString(3, temporada.getPlot());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(temporada.getFechaLanzamiento(), LocalTime.now())));
            preparedStatement.setInt(5, temporada.getNumCapitulos());

            preparedStatement.executeUpdate();

            return temporada;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    private Temporada update(Temporada temporada) throws DatabaseErrorException{
        String sql = String.format("UPDATE %s SET id_serie = ?, plot = ?, fechaLanzamiento = ?, numCapitulos = ? WHERE id = ?",
                TABLE_NAME);
        connection =  new MySqlConnection(IP, DATABASE, USERNAME, PASSWORD).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, temporada.getId_serie());
            statement.setString(3, temporada.getPlot());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(temporada.getFechaLanzamiento(), LocalTime.now())));
            statement.setInt(4, temporada.getNumCapitulos());
            statement.setInt(5, temporada.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (update)");
        }

        return temporada;
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

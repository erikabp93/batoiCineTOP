package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.*;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class SQLfavoritoDAO implements FavoritoDAO{

    private Connection connection;
    private static final String TABLE_NAME = "favoritos";

    @Override
    public ArrayList<Produccion> findAll(Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE username LIKE %s", TABLE_NAME, usuario.getUsername());

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

    @Override
    public boolean save(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (id_produccion, username) VALUES (?,?)", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setInt(1, produccion.getId());
            preparedStatement.setString(2, usuario.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (insert)");
        }
        return false;
    }

    private Produccion getProduccionFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int duracion = rs.getInt("duracion");
        String actores = rs.getString("actores");
        String nombre = rs.getString("titulo");
        String[] generos = rs.getString("genero").split(",");
        HashSet<Genero> genero = new HashSet<>();
        for (String actual : generos) {
            genero.add(Genero.valueOf(actual.toUpperCase()));
        }

        String director = rs.getString("director");
        String urlTrailer = rs.getString("urlTrailer");
        String productor = rs.getString("productor");
        Tipo tipo = Tipo.valueOf(rs.getString("tipo"));
        Calificacion calificacion = Calificacion.valueOf(rs.getString("calificacion"));
        String poster = rs.getString("poster");
        String guion = rs.getString("guion");
        String plataforma = rs.getString("plataforma");
        LocalDate fechaLanzamiento = LocalDate.from(rs.getTimestamp("fechaLanzamiento").toLocalDateTime());
        int visualizaciones = rs.getInt("visualizaciones");
        return new Produccion(id, duracion, actores, nombre, genero, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento, visualizaciones);
    }
}

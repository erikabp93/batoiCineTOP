package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.*;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class SQLvalorarDAO implements ValorarDAO {

    private Connection connection;
    private static final String TABLE_NAME = "valorar";
    @Override
    public ArrayList<Produccion> findAll(Produccion produccion) throws DatabaseErrorException { //ordenado por mejor valoradas
        String sql = String.format("SELECT P.tipo, P.titulo, P.actores, P.genero, P.calificacion, P.plataforma, " +
                "ROUND(AVG(V.valoracion), 2) AS Valoración_Media FROM produccion P INNER JOIN %s V ON " +
                "(P.id = V.id_produccion) WHERE P.id LIKE %d ORDER BY V.valoracion DESC;", TABLE_NAME, produccion.getId());

        ArrayList<Produccion> producciones = new ArrayList<>();
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Produccion production = getProduccionFromResultset(resultSet);
                producciones.add(production);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return producciones;
    }

    private ArrayList<Produccion> valoradasPorUsuario(Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM produccion P INNER JOIN %s V ON (P.id = V.id_produccion) WHERE V.username LIKE %s ORDER BY V.valoracion DESC;", TABLE_NAME, usuario.getUsername());
        ArrayList<Produccion> producciones = new ArrayList<>();
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Produccion production = getProduccionFromResultset(resultSet);
                producciones.add(production);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return producciones;
    }

    @Override
    public boolean save(Produccion produccion, Usuario usuario, int valoracion, String comentario) throws DatabaseErrorException {
        String sql = String.format("INSERT INTO %s (id_produccion, username, valoracion, comentario) VALUES (?,?,?,?)", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        if (yaValorado(produccion, usuario)) {
            return false;
        }
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setInt(1, produccion.getId());
            preparedStatement.setString(2, usuario.getUsername());
            preparedStatement.setInt(3, valoracion);
            preparedStatement.setString(4, comentario);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (insert)");
        }
    }

    private boolean yaValorado(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        for (Produccion production : valoradasPorUsuario(usuario)) {
            if (production.equals(produccion)) {
                return true;
            }
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

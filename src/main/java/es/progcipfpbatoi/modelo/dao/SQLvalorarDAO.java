package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.*;
import es.progcipfpbatoi.services.MySqlConnection;
import es.progcipfpbatoi.util.DatosBD;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

public class SQLvalorarDAO implements ValorarDAO {

    private Connection connection;
    private static final String TABLE_NAME = "valorar";
    @Override
    public ArrayList<Produccion> findAll() throws DatabaseErrorException { //ordenado por mejor valoradas
        String sql = String.format("SELECT V.id_produccion, P.duracion, P.director, P.urlTrailer, P.tipo, P.titulo, P.valoracion_total," +
                "P.productor, P.tipo, P.calificacion, P.poster, P.guion, P.actores, P.genero, P.fechaLanzamiento, P.visualizaciones, P.plataforma, " +
                "ROUND(AVG(V.valoracion), 2) AS Valoración_Media FROM produccion P INNER JOIN %s V ON " +
                "(P.id = V.id_produccion) ORDER BY V.valoracion DESC;", TABLE_NAME);

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
        String sql = String.format("SELECT * FROM produccion P INNER JOIN %s V ON (P.id = V.id_produccion) WHERE V.username LIKE \"%s\" ORDER BY V.valoracion DESC;", TABLE_NAME, usuario.getUsername());
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            if (produccion.getTipo().equals(Tipo.MOVIE)) {
                alert.setTitle("Película ya valorada");
                alert.setHeaderText(usuario.getUsername() + " ya has valorado esta película");
            } else if (produccion.getTipo().equals(Tipo.SERIE)) {
                alert.setTitle("Serie ya valorada");
                alert.setHeaderText(usuario.getUsername() + " ya has valorado esta serie");
            } else {
                alert.setTitle("Producción ya valorada");
                alert.setHeaderText(usuario.getUsername() + " ya has valorado esta producción");
            }
            alert.setContentText("Quieres cambiar la valoración?");
            ButtonType botonSi = new ButtonType("Si");
            ButtonType botonNo = new ButtonType("No");

            alert.getButtonTypes().setAll(botonSi, botonNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == botonSi){
                borrarValoracion(produccion, usuario);
            } else if (result.get() == botonNo) {
                return false;
            }
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

    private void borrarValoracion(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        String sql = String.format("DELETE FROM %s WHERE username LIKE ? AND id_produccion = ?", TABLE_NAME);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, usuario.getUsername());
            statement.setInt(2, produccion.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (delete)");
        }
    }

    private Produccion getProduccionFromResultset(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_produccion");
        int duracion = rs.getInt("duracion");
        String actores = rs.getString("actores");
        String nombre = rs.getString("titulo");
        HashSet<Genero> genero = null;
        if (rs.getString("genero") != null) {
            String[] generos = rs.getString("genero").split(",");
            genero = new HashSet<>();
            for (String actual : generos) {
                genero.add(Genero.valueOf(actual.toUpperCase()));
            }
        }

        String director = rs.getString("director");
        String urlTrailer = rs.getString("urlTrailer");
        String productor = rs.getString("productor");
        Tipo tipo = null;
        if (rs.getString("tipo") != null) {
            tipo = Tipo.valueOf(rs.getString("tipo"));
        }
        Calificacion calificacion = null;
        if (rs.getString("calificacion") != null) {
            calificacion = Calificacion.valueOf(rs.getString("calificacion"));
        }
        String poster = rs.getString("poster");
        String guion = rs.getString("guion");
        String plataforma = rs.getString("plataforma");
        LocalDate fechaLanzamiento = null;
        if (rs.getTimestamp("fechaLanzamiento") != null) {
            fechaLanzamiento = LocalDate.from(rs.getTimestamp("fechaLanzamiento").toLocalDateTime());
        }
        int visualizaciones = rs.getInt("visualizaciones");
        float valoracionTotal = rs.getFloat("valoracion_total");
        return new Produccion(id, duracion, actores, nombre, genero, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento, visualizaciones, valoracionTotal);
    }

    public String getPoster(int id) throws DatabaseErrorException {
        String sql = String.format("SELECT poster FROM %s INNER JOIN produccion ON (id_produccion = id) WHERE id_produccion LIKE %d GROUP BY id_produccion ", TABLE_NAME, id);
        connection =  new MySqlConnection(DatosBD.IP, DatosBD.DATABASE, DatosBD.USERNAME, DatosBD.PASSWORD).getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            if (resultSet.next()) {
                return resultSet.getString("poster");
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }
    }
}

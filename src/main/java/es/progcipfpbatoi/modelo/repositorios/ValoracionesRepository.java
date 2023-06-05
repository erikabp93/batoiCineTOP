package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.SQLvalorarDAO;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class ValoracionesRepository {

    private static SQLvalorarDAO sqlValorarDAO;
    private static PeliculaSerieRepository peliculaSerieRepository;

    public ValoracionesRepository(SQLvalorarDAO sqlValorarDAO, PeliculaSerieRepository peliculaSerieRepository) {
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.sqlValorarDAO = sqlValorarDAO;
    }

    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        return sqlValorarDAO.findAll();
    }

    public boolean save(Produccion produccion, Usuario usuario, int valoracion, String comentario) throws DatabaseErrorException, SQLException {
        return sqlValorarDAO.save(produccion, usuario, valoracion, comentario);
    }

    public ArrayList<Produccion> findAllPeliculas() throws DatabaseErrorException {
        ArrayList<Produccion> peliculas = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo().toString().equals("MOVIE")) {
                peliculas.add(produccion);
            }
        }
        return peliculas;
    }

    public ArrayList<Produccion> findAllSeries() throws DatabaseErrorException {
        ArrayList<Produccion> series = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo().toString().equals("SERIE") || produccion.getTipo().toString().equals("tv_show")) {
                series.add(produccion);
            }
        }
        return series;
    }

    public static int getValoracion(int id) {
        try {
            return sqlValorarDAO.getValoracion(id);
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPoster(int id) {
        try {
            String url = sqlValorarDAO.getPoster(id);
            if (url == null) {
                return peliculaSerieRepository.getPoster(id);
            }
            return url;
        } catch (DatabaseErrorException e) {
            throw new RuntimeException(e);
        }
    }
}

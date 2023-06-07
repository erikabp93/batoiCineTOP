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

    /**
     * Constructor de la clase, obligatorio ponerlo. Posee 2 parametros,
     * el SQLValorarDao
     * el Repositorio de peliculas y serie
     */
    public ValoracionesRepository(SQLvalorarDAO sqlValorarDAO, PeliculaSerieRepository peliculaSerieRepository) {
        this.peliculaSerieRepository = peliculaSerieRepository;
        this.sqlValorarDAO = sqlValorarDAO;
    }

    /**
     * LLama al método findAll del atributo SQLvalorarDAO que encuentra todas las
     * producciones que han sido valoradas
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        return sqlValorarDAO.findAll();
    }

    /**
     * LLama al método save del atributo SQLvalorarDAO y guarda la produccion junto con el usuario, la valoración
     * que se le da y el comentario que añade el usuario
     *
     * Devuelve true en caso de que se pueda guardar
     * En caso contrario devuelve false
     *
     * @param produccion de tipo Produccion, es la produccion con la que se va a trabajar
     * @param usuario de tipo Usuario, es el usuario que a trabajar y valorar y comentar la produccion
     * @param valoracion de tipo Int, valorarción que se le asigna a la produccion
     * @param comentario de tipo String, comentario que se le da a la produccion
     * @return boolean
     * @throws DatabaseErrorException
     */
    public boolean save(Produccion produccion, Usuario usuario, int valoracion, String comentario) throws DatabaseErrorException, SQLException {
        return sqlValorarDAO.save(produccion, usuario, valoracion, comentario);
    }

    /**
     * Encuentra todas las producciones que sean de tipo MOVIE
     * y las añade al ArrayList de Produccion
     * Por último las devuelve
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAllPeliculas() throws DatabaseErrorException {
        ArrayList<Produccion> peliculas = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo() != null) {
                if (produccion.getTipo().toString().equals("MOVIE")) {
                    peliculas.add(produccion);
                }
            }
        }
        return peliculas;
    }

    /**
     * Encuentra todas las producciones que sean de tipo SERIE or tv_show
     * y las añade al ArrayList de Produccion
     * Por último las devuelve
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAllSeries() throws DatabaseErrorException {
        ArrayList<Produccion> series = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo() != null) {
                if (produccion.getTipo().toString().equals("SERIE") || produccion.getTipo().toString().equals("tv_show")) {
                    series.add(produccion);
                }
            }
        }
        return series;
    }

    /**
     * Obtiene la url del id pasado por parámetro.
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
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

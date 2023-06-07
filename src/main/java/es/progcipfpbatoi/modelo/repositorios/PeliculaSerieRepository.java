package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.TemporadaDAO;
import es.progcipfpbatoi.modelo.dao.PeliculaSerieDAO;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;

import java.util.ArrayList;

public class PeliculaSerieRepository {

    private PeliculaSerieDAO peliculaSerieDAO;

    /**
     * Constructor de la clase, obligatorio ponerlo. Posee 2 parametros
     * las peliculas y las series
     */
    public PeliculaSerieRepository(PeliculaSerieDAO peliculaSerieDAO, TemporadaDAO temporadaDAO) {
        this.peliculaSerieDAO = peliculaSerieDAO;
    }

    /**
     * LLama al método findAll(text) del atributo PeliculaSerieDAO que nos devolverá todas las
     * producciones que coincidan con el texto pasado por parámetro
     *
     * @param text
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAll(String text) throws DatabaseErrorException{
        return peliculaSerieDAO.findAll(text);
    }

    /**
     * LLama al método findAll(text) del atributo PeliculaSerieDAO que nos devolverá todas las
     * producciones que coincidan con el genero pasado por parámetro
     *
     * @param genero
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAll(Genero genero) throws DatabaseErrorException {
        return peliculaSerieDAO.findAll(genero);
    }

    /**
     * LLama al método findAll(text) del atributo PeliculaSerieDAO que nos devolverá todas las
     * producciones que coincidan en texto y género pasado por parámetro
     *
     * @param text
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAll(String text, Genero genero) throws DatabaseErrorException {
        return peliculaSerieDAO.findAll(text, genero);
    }

    /**
     * LLama al método findAll del atributo PeliculaSerieDAO que nos devolverá todas las
     * producciones
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        return peliculaSerieDAO.findAll();
    }

    /**
     * LLama al método findAll del atributo PeliculaSerieDAO que nos devolverá todas las
     * producciones, en este caso peliculas
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAllPeliculas() throws DatabaseErrorException {
        ArrayList<Produccion> peliculas = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo().toString().equals("MOVIE")) {
                peliculas.add(produccion);
            }
        }
        return peliculas;
    }

    /**
     * LLama al método findAll del atributo PeliculaSerieDAO que nos devolverá todas las
     * producciones, en este caso series o tv_show
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAllSeries() throws DatabaseErrorException {
        ArrayList<Produccion> series = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo().toString().equals("SERIE") || produccion.getTipo().toString().equals("tv_show")) {
                series.add(produccion);
            }
        }
        return series;
    }

    /**
     * LLama al método getById del atributo PeliculaSerieDAO, que nos devolverá la producción
     * que coincida con el id pasado por parámetro
     *
     * @param id de la produccion a buscar
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public Produccion getById(int id) throws DatabaseErrorException, NotFoundException {
        return peliculaSerieDAO.getById(id);
    }

    /**
     * LLama al método save del atributo PeliculaSerieDAO que guardará la produccion pasada por
     * parámetro
     *
     * @return void
     * @throws DatabaseErrorException
     */
    public void save(Produccion produccion) throws DatabaseErrorException {
        peliculaSerieDAO.save(produccion);
    }

    /**
     * LLama al método remove del atributo PeliculaSerieDAO que eliminará la produccion pasada por
     * parámetro
     *
     * @return void
     * @throws DatabaseErrorException
     * @throws NotFoundException
     */
    public void remove(Produccion produccion) throws NotFoundException, DatabaseErrorException {
        peliculaSerieDAO.remove(produccion);
    }

    /**
     * LLama al método getPoster del atributo PeliculaSerieDAO que nos devolverá el poster de la
     * del id que pasemos por parámetro.
     *
     * @return void
     * @throws DatabaseErrorException
     */
    public String getPoster(int id) throws DatabaseErrorException {
        return peliculaSerieDAO.getPoster(id);
    }

    /**
     *Ordena la lista de producciones de la base de datos filtrando por peliculas o series.
     * @param pelicula define si se desea ordenar por peliculas o series.
     * @param filtro el filtro que se aplicará. default muestra el filtro por valoración.
     * @param ascendente elige si se ordena de forma ascendente o descentente.
     * @return el arrayList de producciones ordenadas.
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> filtrar(boolean pelicula, String filtro, boolean ascendente) throws DatabaseErrorException {
        return peliculaSerieDAO.ordenar(pelicula, filtro, ascendente);
    }
}

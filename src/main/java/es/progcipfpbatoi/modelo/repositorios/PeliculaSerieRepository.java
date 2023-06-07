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

    public PeliculaSerieRepository(PeliculaSerieDAO peliculaSerieDAO, TemporadaDAO temporadaDAO) {
        this.peliculaSerieDAO = peliculaSerieDAO;
    }

    public ArrayList<Produccion> findAll(String text) throws DatabaseErrorException{
        return peliculaSerieDAO.findAll(text);
    }

    public ArrayList<Produccion> findAll(Genero genero) throws DatabaseErrorException {
        return peliculaSerieDAO.findAll(genero);
    }

    public ArrayList<Produccion> findAll(String text, Genero genero) throws DatabaseErrorException {
        return peliculaSerieDAO.findAll(text, genero);
    }

    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        return peliculaSerieDAO.findAll();
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

    public Produccion getById(int id) throws DatabaseErrorException, NotFoundException {
        return peliculaSerieDAO.getById(id);
    }

    public void save(Produccion produccion) throws DatabaseErrorException {
        peliculaSerieDAO.save(produccion);
    }

    public void remove(Produccion produccion) throws NotFoundException, DatabaseErrorException {
        peliculaSerieDAO.remove(produccion);
    }

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

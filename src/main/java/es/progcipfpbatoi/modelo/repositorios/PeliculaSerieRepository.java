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
            if (produccion.getTipo().toString().equals("PELICULA")) {
                peliculas.add(produccion);
            }
        }
        return peliculas;
    }

    public ArrayList<Produccion> findAllSeries() throws DatabaseErrorException {
        ArrayList<Produccion> series = new ArrayList<>();
        for (Produccion produccion : findAll()) {
            if (produccion.getTipo().toString().equals("SERIE")) {
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
}

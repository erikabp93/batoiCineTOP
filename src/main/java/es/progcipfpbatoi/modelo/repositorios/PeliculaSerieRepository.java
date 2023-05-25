package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.TemporadaDAO;
import es.progcipfpbatoi.modelo.dao.PeliculaSerieDAO;
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

    public Produccion getById(int id) throws DatabaseErrorException, NotFoundException {
        return peliculaSerieDAO.getById(id);
    }

    public void save(Produccion produccion) throws DatabaseErrorException {
        peliculaSerieDAO.save(produccion);
    }

    public void remove(Produccion produccion) throws NotFoundException, DatabaseErrorException {
        peliculaSerieDAO.remove(produccion);
    }
}

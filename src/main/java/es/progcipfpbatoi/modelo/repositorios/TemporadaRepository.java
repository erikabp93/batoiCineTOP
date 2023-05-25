package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.TemporadaDAO;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Temporada;

import java.util.ArrayList;

public class TemporadaRepository {

    private TemporadaDAO temporadaDAO;

    public TemporadaRepository(TemporadaDAO temporadaDAO) {
        this.temporadaDAO = temporadaDAO;
    }

    public ArrayList<Temporada> findAll() throws DatabaseErrorException {
        return temporadaDAO.findAll();
    }
    public Temporada findById(int id) throws DatabaseErrorException {
        return temporadaDAO.findById(id);
    }
}
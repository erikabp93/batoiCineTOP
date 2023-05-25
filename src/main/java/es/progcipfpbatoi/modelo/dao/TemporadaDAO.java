package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Temporada;

import java.util.ArrayList;

public interface TemporadaDAO {

    ArrayList<Temporada> findAll() throws DatabaseErrorException;
    Temporada findById(int id)  throws DatabaseErrorException;
    Temporada save(Temporada temporada) throws DatabaseErrorException;
}

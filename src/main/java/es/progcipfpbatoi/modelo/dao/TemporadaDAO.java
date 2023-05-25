package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Temporada;

public interface TemporadaDAO {
    Temporada findById(int id)  throws DatabaseErrorException;
}

package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;

public interface CategoriaDAO {
    Categoria findById(int id)  throws DatabaseErrorException;

    Categoria getByTypeAndPriority(Tipo tipo, Prioridad prioridad) throws NotFoundException, DatabaseErrorException;
}

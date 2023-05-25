package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.util.ArrayList;

public interface FavoritoDAO {

    ArrayList<Produccion> findAll(Usuario usuario) throws DatabaseErrorException;
    boolean save(Produccion produccion, Usuario usuario) throws DatabaseErrorException;
}

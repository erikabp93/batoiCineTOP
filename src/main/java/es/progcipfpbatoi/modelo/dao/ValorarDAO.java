package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.util.ArrayList;

public interface ValorarDAO {

    ArrayList<Produccion> findAll() throws DatabaseErrorException;
    boolean save(Produccion produccion, Usuario usuario, int valoracion, String comentario) throws DatabaseErrorException;

}

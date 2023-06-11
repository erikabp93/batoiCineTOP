package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.util.ArrayList;

/**
 * DAO de favorito
 */
public interface FavoritoDAO {

    /**
     * Devuelve todas las producciones del usuario
     *
     * @param usuario usuario que tiene las producciones
     * @return Arraylist de Producciones
     * @throws DatabaseErrorException lanza la exception
     */
    ArrayList<Produccion> findAll(Usuario usuario) throws DatabaseErrorException;

    /**
     * Si se puede guardar la produccion deuvelve true, si no false
     *
     * @param produccion produccion que queremos guardar
     * @param usuario usuario que guarda la produccion
     * @return devuelve booleano
     * @throws DatabaseErrorException lanza la exception
     */
    boolean save(Produccion produccion, Usuario usuario) throws DatabaseErrorException;

    /**
     * Si se puede eliminar la produccion deuvelve true, si no false
     *
     * @param produccion produccion que queremos eliminar
     * @param usuario usuario que elimina la produccion
     * @return devuelve booleano
     * @throws DatabaseErrorException lanza la exception
     */
    boolean delete(Produccion produccion, Usuario usuario) throws DatabaseErrorException;
}

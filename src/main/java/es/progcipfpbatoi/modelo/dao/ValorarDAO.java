package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Interfaz de Valorar con sus metodos
  */
public interface ValorarDAO {

    /**
     * devuelve todas las producciones que han sido valoradas
     *
     * @return Arraylist de Producciones
     * @throws DatabaseErrorException exception que lanza
     */
    ArrayList<Produccion> findAll() throws DatabaseErrorException;

    /**
     * Guarda la produccion que ha valorado el user junto con su comentario
     *
     * @param produccion produccion de la produccion
     * @param usuario usuario de la produccion
     * @param valoracion valoracion de la produccion
     * @param comentario comentario de la produccion
     * @return boolean devuelve true o false
     * @throws DatabaseErrorException exception que lanza
     * @throws SQLException exception que lanza
     */
    boolean save(Produccion produccion, Usuario usuario, int valoracion, String comentario) throws DatabaseErrorException, SQLException;

}

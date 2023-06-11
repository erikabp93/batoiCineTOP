package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Temporada;

import java.util.ArrayList;

/**
 * Interfaz de la temporada
 */
public interface TemporadaDAO {

    /**
     * devuelve todas las temporadas
     *
     * @return Arraylist de temporada
     * @throws DatabaseErrorException exception que lanza
     */
    ArrayList<Temporada> findAll() throws DatabaseErrorException;

    /**
     * busca la temporada por el id
     *
     * @param id id de la temporada a buscar
     * @return temporada que encuentre
     * @throws DatabaseErrorException exception que lanza
     */
    Temporada findById(int id)  throws DatabaseErrorException;

    /**
     * guarda la temporada pasada por parametro
     *
     * @param temporada temporada a guardar
     * @return temporada que ha sido guardado
     * @throws DatabaseErrorException exception que lanza
     */
    Temporada save(Temporada temporada) throws DatabaseErrorException;
}

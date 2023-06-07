package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;

import java.util.ArrayList;

public interface PeliculaSerieDAO {
    /**
     *  Obtiene todas las producciones
     */
    ArrayList<Produccion> findAll() throws DatabaseErrorException;

    /**
     * Obtiene todas las producciones que comiencen por @text
     * @param text
     * @return
     */
    ArrayList<Produccion> findAll(String text) throws DatabaseErrorException;

    ArrayList<Produccion> findAll(String text, Genero genero) throws DatabaseErrorException;

    ArrayList<Produccion> findAll(Genero genero) throws DatabaseErrorException;

    /**
     * Obtiene la produccion cuyo id sea @id
     * @param id
     * @return
     * @throws NotFoundException
     */
    Produccion getById(int id) throws NotFoundException, DatabaseErrorException;

    /**
     * Obtiene la produccion cuyo id sea @id
     * @param id
     * @return null si la produccion no ha sido encontrada
     */
    Produccion findById(int id) throws DatabaseErrorException;

    /**
     * Almacena la produccion o la actualiza en caso de existir
     * @param produccion
     * @return
     * @throws DatabaseErrorException
     */
    Produccion save(Produccion produccion) throws DatabaseErrorException;

    void remove(Produccion produccion) throws DatabaseErrorException, NotFoundException;

    String getPoster(int id) throws DatabaseErrorException;

    /**
     *Ordena la lista de producciones de la base de datos filtrando por peliculas o series.
     * @param pelicula define si se desea ordenar por peliculas o series.
     * @param filtro el filtro que se aplicará. default muestra el filtro por valoración.
     * @param ascendente elige si se ordena de forma ascendente o descentente.
     * @return el arrayList de producciones ordenadas.
     * @throws DatabaseErrorException
     */
    ArrayList<Produccion> ordenar(boolean pelicula, String filtro, boolean ascendente) throws DatabaseErrorException;
}

package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;

import java.util.ArrayList;

/**
 * Interfaz de DAO PeliculaSerie
 */
public interface PeliculaSerieDAO {
    /**
     *  Obtiene todas las producciones
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     * @return arraylist de producciones
     */
    ArrayList<Produccion> findAll() throws DatabaseErrorException;

    /**
     * Obtiene todas las producciones que coincidan con el texto pasado por parametro
     * @param text cadena por el cual queremos filtrar
     * @return arrayList de las producciones que coincidan en genero
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    ArrayList<Produccion> findAll(String text) throws DatabaseErrorException;

    /**
     * Obtiene todas las producciones que coincidan con el genero pasado por parametro
     * @param genero genero por el cual queremos filtrar
     * @param text texto a buscar
     * @return arrayList de las producciones que coincidan en genero
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    ArrayList<Produccion> findAll(String text, Genero genero) throws DatabaseErrorException;

    /**
     * Obtiene todas las producciones que coincidan con el genero pasado por parametro
     * @param genero genero por el cual queremos filtrar
     * @return arrayList de las producciones que coincidan en genero
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    ArrayList<Produccion> findAll(Genero genero) throws DatabaseErrorException;

    /**
     * Obtiene la produccion cuyo id sea @id
     * @param id id de la produccion que queremos obtener
     * @return Produccion produccion que obtegna
     * @throws NotFoundException al no encontrar la produccion por el id
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    Produccion getById(int id) throws NotFoundException, DatabaseErrorException;

    /**
     * Obtiene la produccion cuyo id sea @id
     * @param id id de la produccion a encontrar
     * @return Produccion produccion encontrada
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    Produccion findById(int id) throws DatabaseErrorException;

    /**
     * Almacena la produccion o la actualiza en caso de existir
     * @param produccion produccion con la vamos a trabajar
     * @return Produccion la produccion a guardar
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    Produccion save(Produccion produccion) throws DatabaseErrorException;

    /**
     * Elimina la produccion
     * @param produccion produccion con la vamos a trabajar
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     * @throws NotFoundException lanza la exception
     */
    void remove(Produccion produccion) throws DatabaseErrorException, NotFoundException;

    /**
     * Devuelve el poster del id de la produccion
     *
     * @param id id de la produccion
     * @return String devuelto
     * @throws DatabaseErrorException lanza la exception
     */
    String getPoster(int id) throws DatabaseErrorException;

    /**
     *Ordena la lista de producciones de la base de datos filtrando por peliculas o series.
     * @param pelicula define si se desea ordenar por peliculas o series.
     * @param filtro el filtro que se aplicará. default muestra el filtro por valoración.
     * @param ascendente elige si se ordena de forma ascendente o descentente.
     * @return el arrayList de producciones ordenadas.
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    ArrayList<Produccion> ordenar(boolean pelicula, String filtro, boolean ascendente) throws DatabaseErrorException;
}

package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Tarea;

import java.util.ArrayList;

public interface TareaDAO {
    /**
     *  Obtiene todas las tareas
     */
    ArrayList<Tarea> findAll() throws DatabaseErrorException;

    /**
     * Obtiene todas las tareas que comiencen por @text
     * @param text
     * @return
     */
    ArrayList<Tarea> findAll(String text) throws DatabaseErrorException;

    /**
     * Obtiene la tarea cuyo id sea @id
     * @param id
     * @return
     * @throws NotFoundException
     */
    Tarea getById(int id) throws NotFoundException, DatabaseErrorException;

    /**
     * Obtiene la tarea cuyo id sea @id
     * @param id
     * @return null si la tarea no ha sido encontrada
     */
    Tarea findById(int id) throws DatabaseErrorException;

    /**
     * Almacena la tarea o la actualiza en caso de existir
     * @param tarea
     * @return
     * @throws DatabaseErrorException
     */
    Tarea save(Tarea tarea) throws DatabaseErrorException;

    void remove(Tarea tarea) throws DatabaseErrorException, NotFoundException;
}

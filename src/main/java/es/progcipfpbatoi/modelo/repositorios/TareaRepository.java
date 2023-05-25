package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.CategoriaDAO;
import es.progcipfpbatoi.modelo.dao.TareaDAO;

import java.util.ArrayList;

public class TareaRepository {

    private TareaDAO tareaDAO;
    private CategoriaDAO categoriaDAO;

    public TareaRepository(TareaDAO tareaDAO, CategoriaDAO categoriaDAO) {
        this.tareaDAO = tareaDAO;
        this.categoriaDAO = categoriaDAO;
    }

    public ArrayList<Tarea> findAllWithCategories() throws DatabaseErrorException{
        ArrayList<Tarea> tareas = tareaDAO.findAll();
        for (Tarea tarea: tareas) {
            setCategorytoTask(tarea);
        }
        return tareas;
    }

    public ArrayList<Tarea> findAll(String text) throws DatabaseErrorException{
        return tareaDAO.findAll(text);
    }

    public Tarea getById(int id) throws DatabaseErrorException, NotFoundException {
        return tareaDAO.getById(id);
    }

    public Tarea getByIdWithCategory(int id) throws DatabaseErrorException, NotFoundException {
        Tarea tarea = tareaDAO.getById(id);
        setCategorytoTask(tarea);
        return tarea;
    }

    private void setCategorytoTask(Tarea tarea) throws DatabaseErrorException {
        Categoria categoria = categoriaDAO.findById(tarea.getCategoria().getId());
        tarea.setCategoria(categoria);
    }

    public void save(Tarea tarea) throws DatabaseErrorException {
        tareaDAO.save(tarea);
    }

    public void remove(Tarea tarea) throws NotFoundException, DatabaseErrorException {
        tareaDAO.remove(tarea);
    }

    public Categoria getCategoryByTypeAndPriority(Tipo tipo, Prioridad prioridad) throws NotFoundException, DatabaseErrorException {
        return categoriaDAO.getByTypeAndPriority(tipo, prioridad);
    }
}

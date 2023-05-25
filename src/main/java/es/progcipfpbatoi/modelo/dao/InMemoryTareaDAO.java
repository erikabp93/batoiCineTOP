package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;

import java.util.ArrayList;

public class InMemoryTareaDAO implements TareaDAO {

    private ArrayList<Tarea> tareas;

    public InMemoryTareaDAO() {
        this.tareas = new ArrayList<>();
        init();
    }

    private void init() {
        this.tareas.add(new Tarea(1, "Sacar al perro", new Categoria(1, Tipo.HOGAR, Prioridad.ALTA)));
        this.tareas.add(new Tarea(2, "Actividad final de programación", new Categoria(2, Tipo.CLASE, Prioridad.NORMAL)));
        this.tareas.add(new Tarea(3, "Hacer la comida", new Categoria(3, Tipo.HOGAR, Prioridad.BAJA)));
        this.tareas.add(new Tarea(4, "Ir al parque", new Categoria(4, Tipo.HOGAR, Prioridad.BAJA)));
    }

    @Override
    public ArrayList<Tarea> findAll() {
        return tareas;
    }

    public Tarea save(Tarea tarea) {
        int indiceTarea = tareas.indexOf(tarea);

        if (indiceTarea == -1) {
            this.tareas.add(tarea);
        }

        this.tareas.set(indiceTarea, tarea);
        return tarea;
    }

    @Override
    public void remove(Tarea tarea) throws DatabaseErrorException, NotFoundException {
        if (!this.tareas.remove(tarea)) {
            throw new NotFoundException("No se ha encontrado la tarea a eliminar");
        }
    }

    @Override
    public ArrayList<Tarea> findAll(String text) {
        ArrayList<Tarea>tareasFiltradas = new ArrayList<>();
        for (Tarea tarea: tareas) {
            if (tarea.empiezaPor(text)) {
                tareasFiltradas.add(tarea);
            }
        }

        return tareasFiltradas;
    }

    @Override
    public Tarea getById(int id) throws NotFoundException {
        int indiceTarea = tareas.indexOf(new Tarea(id));

        if (indiceTarea != -1) {
            return tareas.get(indiceTarea);
        }

        throw new NotFoundException("Tarea no encontrada");
    }

    @Override
    public Tarea findById(int id) {
        try {
            return getById(id);
        } catch (NotFoundException ex) {
            return null;
        }
    }
}

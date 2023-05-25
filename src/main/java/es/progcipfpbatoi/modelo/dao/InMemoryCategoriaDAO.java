package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Categoria;
import es.progcipfpbatoi.modelo.dto.Prioridad;
import es.progcipfpbatoi.modelo.dto.Tipo;

import java.util.ArrayList;

public class InMemoryCategoriaDAO implements CategoriaDAO{

    private ArrayList<Categoria> categorias;

    public InMemoryCategoriaDAO() {
        this.categorias = new ArrayList<>();
        init();
    }

    private void init() {
        this.categorias.add(new Categoria(1, Tipo.HOGAR, Prioridad.ALTA));
        this.categorias.add(new Categoria(2, Tipo.CLASE, Prioridad.NORMAL));
        this.categorias.add(new Categoria(3, Tipo.HOGAR, Prioridad.BAJA));
    }

    @Override
    public Categoria findById(int id) throws DatabaseErrorException {
        int indiceCategoria = categorias.indexOf(new Categoria(id));

        if (indiceCategoria != -1) {
            return categorias.get(indiceCategoria);
        }

        return null;
    }

    @Override
    public Categoria getByTypeAndPriority(Tipo tipo, Prioridad prioridad) throws NotFoundException, DatabaseErrorException {

        for (Categoria categoria: categorias) {
            if (categoria.tieneEsteTipoYPrioridad(tipo, prioridad)) {
                return categoria;
            }
        }
        throw new NotFoundException("No se ha encontrado ninguna categoría con esa prioridad registrada");
    }
}

package es.progcipfpbatoi.modelo.dto;

import java.util.Objects;

public class Categoria {
    private int id;
    private Tipo tipo;
    private Prioridad prioridad;

    public Categoria(int id) {
        this(id, null, null);
    }
    public Categoria(int id, Tipo tipo, Prioridad prioridad) {
        this.id = id;
        this.tipo = tipo;
        this.prioridad = prioridad;
    }

    public int getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean tieneEsteTipoYPrioridad(Tipo tipo, Prioridad prioridad) {
        return this.tipo == tipo && this.prioridad == prioridad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return id == categoria.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }
}

package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

public enum Prioridad {
    BAJA, NORMAL, ALTA, MUY_ALTA;

    public static Prioridad parse(String prioridadStr) throws CategoryTypeErrorException {

        for (Prioridad prioridad: Prioridad.values()) {
            if (prioridad.toString().equals(prioridadStr)) {
                return prioridad;
            }
        }

        throw new CategoryTypeErrorException("Prioridad de categoría desconocida: " + prioridadStr);
    }
}

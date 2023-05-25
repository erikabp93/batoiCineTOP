package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

public enum Tipo {

    HOGAR, CLASE, JUGAR;

    public static Tipo parse(String categoriaStr) throws CategoryTypeErrorException {

        for (Tipo categoria: Tipo.values()) {
            if (categoria.toString().equals(categoriaStr)) {
                return categoria;
            }
        }

        throw new CategoryTypeErrorException("Tipo de categoría desconocida: " + categoriaStr);
    }
}

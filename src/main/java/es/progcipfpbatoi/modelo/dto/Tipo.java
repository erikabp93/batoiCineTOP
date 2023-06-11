package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

/**
 * enum tipo
 */
public enum Tipo {

    /**
     * de tipo movie
     */
    MOVIE {
        @Override
        public String toString() {
            return "MOVIE";
        }
    },
    /**
     * de tipo serie
     */
    SERIE {
        @Override
        public String toString() {
            return "SERIE";
        }
    },
    /**
     * de tipo tv_show
     */
    tv_show {
        @Override
        public String toString() {
            return "tv_show";
        }
    };

    /**
     * Recorre todos los valores del Enum Tipo, comprobando uno por uno por si es igual al String pasado por
     * parámetro. En caso de que coincida, devuelve el Tipo con el que coincida
     * En caso de no encontrar ninguna coincidencia, devuelve una expcetion
     *
     * @param tipoStr cadena a parsear
     * @return Calificacion tipo enum que devuevle
     * @throws CategoryTypeErrorException exception al no encontrar categoria
     */
    public static Tipo parse(String tipoStr) throws CategoryTypeErrorException {

        for ( Tipo genero : Tipo.values() ) {
            if ( genero.toString().equalsIgnoreCase( tipoStr ) ) {
                return genero;
            }
        }

        throw new CategoryTypeErrorException( "Tipo de categoría desconocida: " + tipoStr );
    }

}

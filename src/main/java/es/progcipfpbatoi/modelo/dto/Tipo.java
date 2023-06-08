package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

public enum Tipo {
    MOVIE {
        @Override
        public String toString() {
            return "MOVIE";
        }
    },
    SERIE {
        @Override
        public String toString() {
            return "SERIE";
        }
    },
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
     * @param tipoStr
     * @return Calificacion
     * @throws CategoryTypeErrorException
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

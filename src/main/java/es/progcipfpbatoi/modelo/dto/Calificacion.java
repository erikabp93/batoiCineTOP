package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

public enum Calificacion {

    G {
        @Override
        public String toString() {
            return "G";
        }
    },
    PG {
        @Override
        public String toString() {
            return "PG";
        }
    },
    PG_13 {
        @Override
        public String toString() {
            return "PG-13";
        }
    },
    R {
        @Override
        public String toString() {
            return "R";
        }
    },
    X {
        @Override
        public String toString() {
            return "X";
        }
    },
    UNRATED {
        @Override
        public String toString() {
            return "UNRATED";
        }
    },
    APPROVED {
        @Override
        public String toString() {
            return "APPROVED";
        }
    };

    /**
     * Recorre todos los valores del Enum Calificacion, comprobando uno por uno por si es igual al String pasado por
     * parámetro. En caso de que coincida, devuelve la calificación con la que coincida
     * En caso de no encontrar ningúna coincidencia, devuelve una expcetion
     *
     * @param calificacionStr
     * @return Calificacion
     * @throws CategoryTypeErrorException
     */
    public static Calificacion parse(String calificacionStr) throws CategoryTypeErrorException {

        for ( Calificacion calificacion : Calificacion.values() ) {
            if ( calificacion.toString().equals( calificacionStr ) ) {
                return calificacion;
            }
        }

        throw new CategoryTypeErrorException( "Tipo de categoría desconocida: " + calificacionStr );

    }
}
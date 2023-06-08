package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

/**
 * Calicion de tipo enum
 */
public enum Calificacion {

    /**
     * tipo g
     */
    G {
        @Override
        public String toString() {
            return "G";
        }
    },
    /**
     * tipo PG
     */
    PG {
        @Override
        public String toString() {
            return "PG";
        }
    },
    /**
     * tipo PG_13
     */
    PG_13 {
        @Override
        public String toString() {
            return "PG-13";
        }
    },
    /**
     * tipo R
     */
    R {
        @Override
        public String toString() {
            return "R";
        }
    },
    /**
     * tipo X
     */
    X {
        @Override
        public String toString() {
            return "X";
        }
    },
    /**
     * tipo UNRATED
     */
    UNRATED {
        @Override
        public String toString() {
            return "UNRATED";
        }
    },
    /**
     * tipo APPROVED
     */
    APPROVED {
        @Override
        public String toString() {
            return "APPROVED";
        }
    };

    /**
     * Recorre todos los valores del Enum Calificacion, comprobando uno por uno por si es igual al String pasado por
     * parámetro. En caso de que coincida, devuelve la calificación con la que coincida
     * En caso de no encontrar ninguna coincidencia, devuelve una expcetion
     *
     * @param calificacionStr cadena con la que vamos a trabajar
     * @return Calificacion la coindicendia en forma de Calificacion
     * @throws CategoryTypeErrorException exception si no encuentra coindiciendias
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
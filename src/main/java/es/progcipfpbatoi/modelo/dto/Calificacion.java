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

    public static Calificacion parse(String calificacionStr) throws CategoryTypeErrorException {

        for ( Calificacion calificacion : Calificacion.values() ) {
            if ( calificacion.toString().equals( calificacionStr ) ) {
                return calificacion;
            }
        }

        throw new CategoryTypeErrorException( "Tipo de categoría desconocida: " + calificacionStr );

    }
}
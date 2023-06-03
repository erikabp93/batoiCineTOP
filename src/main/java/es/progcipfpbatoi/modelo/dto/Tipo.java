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

    public static Tipo parse(String tipoStr) throws CategoryTypeErrorException {

        for ( Tipo genero : Tipo.values() ) {
            if ( genero.toString().equalsIgnoreCase( tipoStr ) ) {
                return genero;
            }
        }

        throw new CategoryTypeErrorException( "Tipo de categoría desconocida: " + tipoStr );
    }

}

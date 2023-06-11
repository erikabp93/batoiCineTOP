package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

/**
 * enum de Genero
 */
public enum Genero {
    ACTION {
        /**
         * action
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Action";
        }
    },
    FANTASY {
        /**
         * fantasy
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Fantasy";
        }
    },
    MUSICAL {
        /**
         * musical
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Musical";
        }
    },
    HISTORY {
        /**
         * history
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "History";
        }
    },
    SPORT {
        /**
         * sport
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Sport";
        }
    },
    BIOGRAPHY {
        /**
         * biography
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Biography";
        }
    },
    COMEDY {
        /**
         * comedy
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Comedy";
        }
    },
    SCI_FI {
        /**
         * sci_fi
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Sci-Fi";
        }
    },
    MYSTERY {
        /**
         * mystery
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Mystery";
        }
    },
    WESTERN {
        /**
         * western
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Western";
        }
    },
    HORROR {
        /**
         * horror
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Horror";
        }
    },
    FAMILY {
        /**
         * family
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Family";
        }
    },
    ROMANCE {
        /**
         * romance
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Romance";
        }
    },
    THRILLER {
        /**
         * thiller
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Thriller";
        }
    },
    ADVENTURE {
        /**
         * adventure
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Adventure";
        }
    },
    CRIME {
        /**
         * crime
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Crime";
        }
    },
    DRAMA {
        /**
         * drama
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Drama";
        }
    },
    ANIMATION {
        /**
         * animation
         *
         * @return String cadena
         */
        @Override
        public String toString() {
            return "Animation";
        }
    };

    /**
     * Recorre todos los valores del Enum Genero, comprobando uno por uno por si es igual al String pasado por
     * parámetro. En caso de que coincida, devuelve el Genero con el que coincida
     * En caso de no encontrar ninguna coincidencia, devuelve una expcetion
     *
     * @param categoriaStr cadena con la que compara
     * @return Calificacion que devuelve
     * @throws CategoryTypeErrorException al no encontrar nigun genero
     */
    public static Genero parse(String categoriaStr) throws CategoryTypeErrorException {

        for ( Genero genero : Genero.values() ) {
            if ( genero.toString().equalsIgnoreCase( categoriaStr ) ) {
                return genero;
            }
        }

        throw new CategoryTypeErrorException( "Tipo de categoría desconocida: " + categoriaStr );
    }
}
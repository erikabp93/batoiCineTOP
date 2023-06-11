package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

/**
 * enum de Genero
 */
public enum Genero {
    /**
     * action
     */
    ACTION {
        @Override
        public String toString() {
            return "Action";
        }
    },
    /**
     * fantasy
     */
    FANTASY {
        @Override
        public String toString() {
            return "Fantasy";
        }
    },
    /**
     * musical
     */
    MUSICAL {
        @Override
        public String toString() {
            return "Musical";
        }
    },
    /**
     * history
     */
    HISTORY {
        @Override
        public String toString() {
            return "History";
        }
    },
    /**
     * sport
     */
    SPORT {
        @Override
        public String toString() {
            return "Sport";
        }
    },
    /**
     * biography
     */
    BIOGRAPHY {

        @Override
        public String toString() {
            return "Biography";
        }
    },
    /**
     * comedy
     */
    COMEDY {
        @Override
        public String toString() {
            return "Comedy";
        }
    },
    /**
     * sci_fi
     */
    SCI_FI {
        @Override
        public String toString() {
            return "Sci-Fi";
        }
    },
    /**
     * mystery
     */
    MYSTERY {
        @Override
        public String toString() {
            return "Mystery";
        }
    },
    /**
     * western
     */
    WESTERN {
        @Override
        public String toString() {
            return "Western";
        }
    },
    /**
     * horror
     */
    HORROR {
        @Override
        public String toString() {
            return "Horror";
        }
    },
    /**
     * family
     */
    FAMILY {
        @Override
        public String toString() {
            return "Family";
        }
    },
    /**
     * romance
     */
    ROMANCE {
        @Override
        public String toString() {
            return "Romance";
        }
    },
    /**
     * thiller
     */
    THRILLER {
        @Override
        public String toString() {
            return "Thriller";
        }
    },
    /**
     * adventure
     */
    ADVENTURE {
        @Override
        public String toString() {
            return "Adventure";
        }
    },
    /**
     * crime
     */
    CRIME {
        @Override
        public String toString() {
            return "Crime";
        }
    },
    /**
     * drama
     */
    DRAMA {
        @Override
        public String toString() {
            return "Drama";
        }
    },
    /**
     * animation
     */
    ANIMATION {
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
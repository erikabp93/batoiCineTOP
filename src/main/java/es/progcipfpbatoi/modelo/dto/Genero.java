package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

public enum Genero {
    ACTION {
        @Override
        public String toString() {
            return "Action";
        }
    },
    FANTASY {
        @Override
        public String toString() {
            return "Fantasy";
        }
    },
    MUSICAL {
        @Override
        public String toString() {
            return "Musical";
        }
    },
    HISTORY {
        @Override
        public String toString() {
            return "History";
        }
    },
    SPORT {
        @Override
        public String toString() {
            return "Sport";
        }
    },
    BIOGRAPHY {
        @Override
        public String toString() {
            return "Biography";
        }
    },
    COMEDY {
        @Override
        public String toString() {
            return "Comedy";
        }
    },
    SCI_FI {
        @Override
        public String toString() {
            return "Sci-Fi";
        }
    },
    MYSTERY {
        @Override
        public String toString() {
            return "Mystery";
        }
    },
    WESTERN {
        @Override
        public String toString() {
            return "Western";
        }
    },
    HORROR {
        @Override
        public String toString() {
            return "Horror";
        }
    },
    FAMILY {
        @Override
        public String toString() {
            return "Family";
        }
    },
    ROMANCE {
        @Override
        public String toString() {
            return "Romance";
        }
    },
    THRILLER {
        @Override
        public String toString() {
            return "Thriller";
        }
    },
    ADVENTURE {
        @Override
        public String toString() {
            return "Adventure";
        }
    },
    CRIME {
        @Override
        public String toString() {
            return "Crime";
        }
    },
    DRAMA {
        @Override
        public String toString() {
            return "Drama";
        }
    },
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
package es.progcipfpbatoi.modelo.dto;

public enum Tipo {

    PELICULA {
        @Override
        public String toString() {
            return "PELICULA";
        }
    },
    SERIE {
        @Override
        public String toString() {
            return "SERIE";
        }
    }
}
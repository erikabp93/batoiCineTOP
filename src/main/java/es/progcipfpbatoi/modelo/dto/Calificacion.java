package es.progcipfpbatoi.modelo.dto;

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
    }
}
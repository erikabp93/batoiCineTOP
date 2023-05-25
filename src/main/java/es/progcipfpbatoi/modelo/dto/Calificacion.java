package es.progcipfpbatoi.modelo.dto;

public enum Calificacion {

    G,
    PG,
    PG_13,
    R,
    X;

    public static Calificacion parse(String calificacionStr) throws CategoryCalificacionErrorException {

        for ( Calificacion calificacion : Calificacion.values() ) {
            if ( calificacion.toString().equals( calificacionStr ) ) {
                return calificacion;
            }
        }

        throw new CategoryCalificacionErrorException( "Tipo de categoría desconocida: " + calificacionStr );
    }
}
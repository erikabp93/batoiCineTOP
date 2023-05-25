package es.progcipfpbatoi.modelo.dto;

public enum Tipo {

    PELICULA,
    SERIE;

    public static Tipo parse(String tipoStr) throws CategoryGeneroErrorException {

        for ( Tipo genero : Tipo.values() ) {
            if ( genero.toString().equals( tipoStr ) ) {
                return genero;
            }
        }

        throw new CategoryGeneroErrorException( "Tipo de categoría desconocida: " + tipoStr );
    }
}
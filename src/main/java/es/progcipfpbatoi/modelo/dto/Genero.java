package es.progcipfpbatoi.modelo.dto;

public enum Genero {

    ACTION,
    FANTASY,
    MUSICAL,
    HISTORY,
    SPORT,
    BIOGRAPHY,
    COMEDY,
    SCI_FI,
    MYSTERY,
    WESTERN,
    HORROR,
    FAMILY,
    ROMANCE,
    THRILLER,
    ADVENTURE,
    CRIME,
    DRAMA,
    ANIMATION;



    public static Genero parse(String categoriaStr) throws CategoryGeneroErrorException {

        for ( Genero genero : Genero.values() ) {
            if ( genero.toString().equals( categoriaStr ) ) {
                return genero;
            }
        }

        throw new CategoryGeneroErrorException( "Tipo de categoría desconocida: " + categoriaStr );
    }
}
package es.progcipfpbatoi.util;

import es.progcipfpbatoi.exceptions.NotExistMonth;

/**
 * Clase que trabaja con el tipo de dato Month, ojo no es una instancia
 */
public class Month {

    /**
     * A partir de las 3 primeras letras o 4 en caso de septiembre obtengo el value Moth del mes
     *
     * @param month de tipo String
     * @return Devuelve el mes
     */
    public static java.time.Month getMonthByThreeInitials(String month) {
        return getMonth( month );
    }

    /**
     * Tengo dos posibilidades, que me pasen el String del mes en Ingles y
     * devuelvo el Month value en Ingles o que me el mes de tipo String en español y
     * devuelvo el Month value en Ingles
     *
     * @param month de tipo String
     * @return Devuelve el mes
     */
    private static java.time.Month getMonth(String month) {
        //Buscar en ingles
        java.time.Month monthItem = getMonthEnglish( month );
        if ( monthItem != null ) return monthItem;

        //Buscar en español
        return getMonthSpanish( month );
    }


    /**
     * Recorre todos los valores de valores del tip Month,
     * y substrae las 3 primeras letras, en caso de ser Septiembre,
     * sustrae las 4 primeras. Y si encuentra que dichas letras
     * coinciden con algun valor de Month, devuelve ese Month en Español
     * En caso de que no encuentre coindicencias devuelve null
     *
     * @param month de tipo String
     * @return Devuelve el mes en Español
     */
    private static java.time.Month getMonthSpanish(String month) {
        for ( java.time.Month monthItem : java.time.Month.values()
        ) {
            String monthSpanish;
            try {
                monthSpanish = monthFromEnglishToSpanish( monthItem.toString() );
            } catch ( NotExistMonth e ) {
                throw new RuntimeException( e );
            }
            String monthInitials = monthSpanish.substring( 0, 3 );
            if ( isSeptember( monthItem ) ) {
                monthInitials = monthItem.toString().substring( 0, 4 );
            }
            if ( month.equalsIgnoreCase( monthInitials ) ) {
                return monthItem;
            }
        }
        return null;
    }

    /**
     * Recorre todos los valores de valores del tip Month,
     * y substrae las 3 primeras letras, en caso de ser Septembre,
     * sustrae las 4 primeras. Y si encuentra que dichas letras
     * coinciden con algun valor de Month, devuelve ese Month en Ingles
     * En caso de que no encuentre coindicencias devuelve null
     *
     * @param month de tipo String
     * @return Devuelve el mes en Ingles
     */
    private static java.time.Month getMonthEnglish(String month) {
        for ( java.time.Month monthItem : java.time.Month.values()
        ) {
            String monthInitials = monthItem.toString().substring( 0, 3 );
            if ( isSeptember( monthItem ) ) {
                monthInitials = monthItem.toString().substring( 0, 4 );
            }
            if ( month.equalsIgnoreCase( monthInitials ) ) {
                return monthItem;
            }
        }
        return null;
    }


    /**
     * Devuevle true si el mes pasado como parametro es Septiembre
     * En caso contrario devuelve false
     *
     * @param monthItem
     * @return devulve un booleano, true or false
     */
    private static boolean isSeptember(java.time.Month monthItem) {
        return monthItem.toString().equalsIgnoreCase( "september" );
    }

    /**
     * Consiste en pasar un mes en inglés y te lo devuelve en español
     * En caso de que no exista el mes o no se ponga como el formato lo pide lanza exception
     *
     * @param month de tipo String
     * @return Devuevle el mes en español
     * @throws NotExistMonth En caso de que el mes no coincida lanza una expcetion
     */
    private static String monthFromEnglishToSpanish(String month) throws NotExistMonth {
        switch ( month ) {
            case "JANUARY" -> {
                return "Enero";
            }
            case "FEBRUARY" -> {
                return "Febrero";
            }
            case "MARCH" -> {
                return "Marzo";
            }
            case "APRIL" -> {
                return "Abril";
            }
            case "MAY" -> {
                return "Mayo";
            }
            case "JUNE" -> {
                return "Junio";
            }
            case "JULY" -> {
                return "Julio";
            }
            case "AUGUST" -> {
                return "Agosto";
            }
            case "SEPTEMBER" -> {
                return "Septiembre";
            }
            case "OCTOBER" -> {
                return "Octubre";
            }
            case "NOVEMBER" -> {
                return "Noviembre";
            }
            case "DECEMBER" -> {
                return "Diciembre";
            }
        }
        throw new NotExistMonth();
    }
}
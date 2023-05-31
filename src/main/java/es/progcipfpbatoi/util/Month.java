package es.progcipfpbatoi.util;

import es.progcipfpbatoi.exceptions.NotExistMonth;

public class Month {
    private static final int DIA  = 0;
    private static final int MES  = 1;
    private static final int ANYO = 2;

    public static java.time.Month getMonthByThreeInitials(String month) {
        return getMonth( month );
    }

    private static java.time.Month getMonth(String month) {
        //Buscar en ingles
        java.time.Month monthItem = getMonthEnglish( month );
        if ( monthItem != null ) return monthItem;

        //Buscar en español
        return getMonthSpanish( month );
    }

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

    private static boolean isSeptember(java.time.Month monthItem) {
        return monthItem.toString().equalsIgnoreCase( "september" );
    }

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
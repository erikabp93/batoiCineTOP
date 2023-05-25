package es.progcipfpbatoi.util;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;
import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class CsvToProducciones {

    private static final String DATABASE_FILE     = "resources/databases/peliculas_series.csv";
    private static final int    ID                = 0;
    private static final int    TITULO            = 1;
    private static final int    CALIFICACION      = 2;
    private static final int    FECHA_LANZAMIENTO = 3;
    private static final int    DURACION          = 4;
    private static final int    GENERO            = 5;
    private static final int    DIRECTOR          = 6;
    private static final int    ACTORES           = 7;
    private static final int    GUION             = 8;
    private static final int    PORTADA           = 9;
    private static final int    TIPO              = 10;
    private static final int    PRODUCTORA        = 11;
    private static final int    WEB               = 12;
    private static final int    PLATAFORMA        = 13;

    private static final String FIELD_SEPARATOR = ";";

    private File file;

    public CsvToProducciones() {
        this.file = new File( DATABASE_FILE );
    }

    private BufferedReader getReader() throws IOException {
        return new BufferedReader( new FileReader( file ) );
    }

    private Produccion getProduccionFromRegister(String register) {
        String[]        fields           = register.split( FIELD_SEPARATOR );
        int             id               = Integer.parseInt( fields[ ID ] );
        int             duracion         = Integer.parseInt( fields[ DURACION ].replaceAll( "[^0-9]", "" ) );
        String          actores          = fields[ ACTORES ];
        String          titulo           = fields[ TITULO ];
        HashSet<Genero> generos          = parseGeneros( fields );
        String          director         = fields[ DIRECTOR ];
        String          urlTrailer       = fields[ WEB ];
        String          productor        = fields[ PRODUCTORA ];
        Tipo            tipo             = Tipo.parse( fields[ TIPO ] );
        Calificacion    calificacion     = Calificacion.parse( fields[ CALIFICACION ] );
        String          poster           = fields[ PORTADA ];
        String          guion            = fields[ GUION ];
        String          plataforma       = fields[ PLATAFORMA ];
        LocalDate       fechaLanzamiento = LocalDate.parse( fields[ CALIFICACION ], DateTimeFormatter.ofPattern( "yyyy" ) );
        return new Produccion( id, duracion, actores, titulo, generos, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento );
    }

    public static String cleanFormat(String generStr) {
        return generStr.replaceAll( " ", "" );
    }

    public static HashSet<Genero> parseGeneros(String[] fields) throws CategoryTypeErrorException {
        HashSet<Genero> generoHashSet = new HashSet<>();
        for ( String generoItem :
                fields[ GENERO ].split( "," ) ) {
            generoItem = cleanFormat( generoItem );
            generoHashSet.add( Genero.parse( generoItem ) );
        }
        return generoHashSet;
    }


    private String getRegisterFromProduccion(Produccion produccion) {
        String[] fields = new String[ 14 ];
        fields[ ID ]                = String.valueOf( produccion.getId() );
        fields[ TITULO ]            = String.valueOf( produccion.getTitulo() );
        fields[ CALIFICACION ]      = String.valueOf( produccion.getCalificacion() );
        fields[ FECHA_LANZAMIENTO ] = produccion.getFechaLanzamiento().format( DateTimeFormatter.ofPattern( "dd-MM-yyyy" ) );
        fields[ DURACION ]          = String.valueOf( produccion.getDuracion() );
        fields[ GENERO ]            = String.valueOf( produccion.getGenero().toString() );
        fields[ DIRECTOR ]          = String.valueOf( produccion.getDirector() );
        fields[ ACTORES ]           = String.valueOf( produccion.getActores() );
        fields[ GUION ]             = String.valueOf( produccion.getGuion() );
        fields[ PORTADA ]           = String.valueOf( produccion.getPoster() );
        fields[ TIPO ]              = String.valueOf( produccion.getTipo().toString() );
        fields[ WEB ]               = String.valueOf( produccion.getUrlTrailer() );
        fields[ PLATAFORMA ]        = String.valueOf( produccion.getPlataforma() );
        return String.join( FIELD_SEPARATOR, fields );
    }

    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        try {
            ArrayList<Produccion> produccionArrayList = new ArrayList<>();
            try ( BufferedReader bufferedReader = getReader() ) {
                do {
                    String register = bufferedReader.readLine();
                    if ( register == null ) {
                        return produccionArrayList;
                    }
                    produccionArrayList.add( getProduccionFromRegister( register ) );
                } while ( true );
            }
        } catch ( IOException ex ) {
            System.out.println( ex.getMessage() );
            throw new DatabaseErrorException( "Error en el acceso a la base de datos de temporadas" );
        }
    }

    public Produccion getById(int id) throws NotFoundException, DatabaseErrorException {
        try ( FileReader fileReader = new FileReader( this.file );
              BufferedReader bufferedReader = new BufferedReader( fileReader ) ) {

            do {
                String register = bufferedReader.readLine();
                if ( register == null ) {
                    throw new NotFoundException( "Temporada no encontrada" );
                } else if ( !register.isBlank() ) {
                    Produccion produccion = getProduccionFromRegister( register );
                    if ( produccion.getId() == id ) {
                        return produccion;
                    }
                }
            } while ( true );
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ocurrió un error en el acceso a la base de datos" );
        }
    }

    public Produccion findById(int id) throws DatabaseErrorException {
        try {
            return getById( id );
        } catch ( NotFoundException ex ) {
            return null;
        }
    }

    public Produccion save(Produccion produccion) throws DatabaseErrorException {
        try {
            if ( findById( produccion.getId() ) == null ) {
                append( produccion );
            } else {
                update( produccion );
            }

            return produccion;
        } catch ( IOException ex ) {
            throw new DatabaseErrorException( ex.getMessage() );
        }
    }

    private void append(Produccion produccion) throws IOException {
        try ( BufferedWriter bufferedWriter = getWriter( true ) ) {
            bufferedWriter.write( getRegisterFromProduccion( produccion ) );
            bufferedWriter.newLine();
        }
    }

    private void update(Produccion produccion) throws DatabaseErrorException {
        updateOrRemove( produccion, true );
    }

    public void remove(Produccion produccion) throws DatabaseErrorException {
        updateOrRemove( produccion, false );
    }

    private void updateOrRemove(Produccion produccion, boolean update) throws DatabaseErrorException {
        ArrayList<Produccion> temporadaArrayList = findAll();
        try ( BufferedWriter bufferedWriter = getWriter( false ) ) {
            for ( Produccion produccionItem : temporadaArrayList ) {
                if ( !produccionItem.equals( produccion ) ) {
                    bufferedWriter.write( getRegisterFromProduccion( produccionItem ) );
                    bufferedWriter.newLine();
                } else if ( update ) {
                    bufferedWriter.write( getRegisterFromProduccion( produccion ) );
                    bufferedWriter.newLine();
                }
            }
        } catch ( IOException ex ) {
            ex.printStackTrace();
            throw new DatabaseErrorException( "Error en el acceso a la base de datos de temporadas" );
        }
    }

    private BufferedWriter getWriter(boolean append) throws IOException {
        return new BufferedWriter( new FileWriter( file, append ) );
    }
}

package es.progcipfpbatoi.util;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Temporada;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CsvToTemporadas {

    private static final String DATABASE_FILE     = "resources/databases/extra_series.csv";
    private static final int    ID_PELICULA       = 0;
    private static final int    ID_TEMPORADA      = 1;
    private static final int    FECHA_LANZAMIENTO = 2;
    private static final int    GUION             = 3;
    private static final int    CAPITULOS         = 4;

    private static final String FIELD_SEPARATOR = ";";

    private File file;

    public CsvToTemporadas() {
        this.file = new File( DATABASE_FILE );
    }

    private BufferedReader getReader() throws IOException {
        return new BufferedReader( new FileReader( file ) );
    }

    private Temporada getTemporadaFromRegister(String register) {
        String[]  fields           = register.split( FIELD_SEPARATOR );
        int       idPelicula       = Integer.parseInt( fields[ ID_PELICULA ] );
        int       idTemporada      = Integer.parseInt( fields[ ID_TEMPORADA ] );
        String    guion            = fields[ GUION ];
        LocalDate fechaLanzamiento = LocalDate.parse( fields[ FECHA_LANZAMIENTO ], DateTimeFormatter.ofPattern( "yyyy" ) );
        int       capitulos        = Integer.parseInt( fields[ CAPITULOS ] );
        return new Temporada( idPelicula, idTemporada, guion, fechaLanzamiento, capitulos );
    }

    private String getRegisterFromTemporada(Temporada temporada) {
        String[] fields = new String[ 5 ];
        fields[ ID_PELICULA ]       = String.valueOf( temporada.getId() );
        fields[ ID_TEMPORADA ]      = String.valueOf( temporada.getId_serie() );
        fields[ GUION ]             = String.valueOf( temporada.getPlot() );
        fields[ FECHA_LANZAMIENTO ] = temporada.getFechaLanzamiento().format( DateTimeFormatter.ofPattern( "yyyy" ) );
        fields[ CAPITULOS ]         = String.valueOf( temporada.getNumCapitulos() );
        return String.join( FIELD_SEPARATOR, fields );
    }

    public ArrayList<Temporada> findAll() throws DatabaseErrorException {
        try {
            ArrayList<Temporada> temporadaArrayList = new ArrayList<>();
            try ( BufferedReader bufferedReader = getReader() ) {
                do {
                    String register = bufferedReader.readLine();
                    if ( register == null ) {
                        return temporadaArrayList;
                    }
                    temporadaArrayList.add( getTemporadaFromRegister( register ) );
                } while ( true );
            }
        } catch ( IOException ex ) {
            System.out.println( ex.getMessage() );
            throw new DatabaseErrorException( "Error en el acceso a la base de datos de temporadas" );
        }
    }

    public Temporada getById(int id) throws NotFoundException, DatabaseErrorException {
        try ( FileReader fileReader = new FileReader( this.file );
              BufferedReader bufferedReader = new BufferedReader( fileReader ) ) {

            do {
                String register = bufferedReader.readLine();
                if ( register == null ) {
                    throw new NotFoundException( "Temporada no encontrada" );
                } else if ( !register.isBlank() ) {
                    Temporada temporada = getTemporadaFromRegister( register );
                    if ( temporada.getId() == id ) {
                        return temporada;
                    }
                }
            } while ( true );
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ocurrió un error en el acceso a la base de datos" );
        }
    }

    public Temporada findById(int id) throws DatabaseErrorException {
        try {
            return getById( id );
        } catch ( NotFoundException ex ) {
            return null;
        }
    }

    public Temporada save(Temporada temporada) throws DatabaseErrorException {
        try {
            if ( findById( temporada.getId() ) == null ) {
                append( temporada );
            } else {
                update( temporada );
            }

            return temporada;
        } catch ( IOException ex ) {
            throw new DatabaseErrorException( ex.getMessage() );
        }
    }

    private void append(Temporada temporada) throws IOException {
        try ( BufferedWriter bufferedWriter = getWriter( true ) ) {
            bufferedWriter.write( getRegisterFromTemporada( temporada ) );
            bufferedWriter.newLine();
        }
    }

    private void update(Temporada temporada) throws DatabaseErrorException {
        updateOrRemove( temporada, true );
    }

    public void remove(Temporada temporada) throws DatabaseErrorException {
        updateOrRemove( temporada, false );
    }

    private void updateOrRemove(Temporada temporada, boolean update) throws DatabaseErrorException {
        ArrayList<Temporada> temporadaArrayList = findAll();
        try ( BufferedWriter bufferedWriter = getWriter( false ) ) {
            for ( Temporada temporadaItem : temporadaArrayList ) {
                if ( !temporadaItem.equals( temporada ) ) {
                    bufferedWriter.write( getRegisterFromTemporada( temporadaItem ) );
                    bufferedWriter.newLine();
                } else if ( update ) {
                    bufferedWriter.write( getRegisterFromTemporada( temporada ) );
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

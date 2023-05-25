package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileSerieDAO implements TareaDAO {

    private static final String DATABASE_FILE     = "resources/databases/extra_series.csv";
    private static final int    ID_PELICULA       = 0;
    private static final int    ID_TEMPORADA      = 1;
    private static final int    FECHA_LANZAMIENTO = 2;
    private static final int    GUION             = 3;
    private static final int    CAPITULOS         = 4;

    private static final String FIELD_SEPARATOR = ";";

    private File file;

    public FileSerieDAO() {
        this.file = new File( DATABASE_FILE );
    }

    @Override
    public ArrayList<Tarea> findAll() throws DatabaseErrorException {
        try {
            ArrayList<Tarea> tareas = new ArrayList<>();
            try ( BufferedReader bufferedReader = getReader() ) {
                do {
                    String register = bufferedReader.readLine();
                    if ( register == null ) {
                        return tareas;
                    }
                    tareas.add( getTaskFromRegister( register ) );
                } while ( true );
            }
        } catch ( IOException ex ) {
            System.out.println( ex.getMessage() );
            throw new DatabaseErrorException( "Error en el acceso a la base de datos de tareas" );
        }

    }

    private BufferedReader getReader() throws IOException {
        return new BufferedReader( new FileReader( file ) );
    }

    private Tarea getTaskFromRegister(String register) {
        String[]      fields      = register.split( FIELD_SEPARATOR );
        int           codigo      = Integer.parseInt( fields[ ID_PELICULA ] );
        String        descripcion = fields[ ID_TEMPORADA ];
        LocalDateTime fecha       = LocalDateTime.parse( fields[ FECHA_LANZAMIENTO ], DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ) );
        boolean       finalizado  = Boolean.parseBoolean( fields[ GUION ] );
        int           idCategoria = Integer.parseInt( fields[ CAPITULOS ] );
        Categoria     categoria   = new Categoria( idCategoria );
        return new Tarea( codigo, descripcion, fecha, finalizado, categoria );
    }

    private String getRegisterFromTask(Tarea tarea) {
        String[] fields = new String[ 5 ];
        fields[ ID_PELICULA ]       = String.valueOf( tarea.getId() );
        fields[ ID_TEMPORADA ]      = String.valueOf( tarea.getDescripcion() );
        fields[ FECHA_LANZAMIENTO ] = tarea.getFechaAltaFormatted();
        fields[ GUION ]             = String.valueOf( tarea.isFinalizada() );
        fields[ CAPITULOS ]         = String.valueOf( tarea.getCategoria().getId() );
        return String.join( FIELD_SEPARATOR, fields );
    }


    @Override
    public ArrayList<Tarea> findAll(String text) throws DatabaseErrorException {
        ArrayList<Tarea> tareasFiltradas = new ArrayList<>();
        for ( Tarea tarea : findAll() ) {
            if ( tarea.empiezaPor( text ) ) {
                tareasFiltradas.add( tarea );
            }
        }

        return tareasFiltradas;
    }

    @Override
    public Tarea getById(int id) throws NotFoundException, DatabaseErrorException {
        try ( FileReader fileReader = new FileReader( this.file );
              BufferedReader bufferedReader = new BufferedReader( fileReader ) ) {

            do {
                String register = bufferedReader.readLine();
                if ( register == null ) {
                    throw new NotFoundException( "Tarea no encontrada" );
                } else if ( !register.isBlank() ) {
                    Tarea tarea = getTaskFromRegister( register );
                    if ( tarea.getId() == id ) {
                        return tarea;
                    }
                }
            } while ( true );
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new DatabaseErrorException( "Ocurrió un error en el acceso a la base de datos" );
        }
    }

    @Override
    public Tarea findById(int id) throws DatabaseErrorException {
        try {
            return getById( id );
        } catch ( NotFoundException ex ) {
            return null;
        }
    }

    @Override
    public Tarea save(Tarea tarea) throws DatabaseErrorException {
        try {
            if ( findById( tarea.getId() ) == null ) {
                append( tarea );
            } else {
                update( tarea );
            }

            return tarea;
        } catch ( IOException ex ) {
            throw new DatabaseErrorException( ex.getMessage() );
        }
    }

    private void append(Tarea tarea) throws IOException {
        try ( BufferedWriter bufferedWriter = getWriter( true ) ) {
            bufferedWriter.write( getRegisterFromTask( tarea ) );
            bufferedWriter.newLine();
        }
    }

    private void update(Tarea tarea) throws DatabaseErrorException {
        updateOrRemove( tarea, true );
    }

    public void remove(Tarea tarea) throws DatabaseErrorException {
        updateOrRemove( tarea, false );
    }

    private void updateOrRemove(Tarea tarea, boolean update) throws DatabaseErrorException {
        ArrayList<Tarea> tareas = findAll();
        try ( BufferedWriter bufferedWriter = getWriter( false ) ) {
            for ( Tarea tareaItem : tareas ) {
                if ( !tareaItem.equals( tarea ) ) {
                    bufferedWriter.write( getRegisterFromTask( tareaItem ) );
                    bufferedWriter.newLine();
                } else if ( update ) {
                    bufferedWriter.write( getRegisterFromTask( tarea ) );
                    bufferedWriter.newLine();
                }
            }
        } catch ( IOException ex ) {
            ex.printStackTrace();
            throw new DatabaseErrorException( "Error en el acceso a la base de datos de tareas" );
        }
    }

    private BufferedWriter getWriter(boolean append) throws IOException {
        return new BufferedWriter( new FileWriter( file, append ) );
    }


}

package es.progcipfpbatoi.util;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Temporada;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase que comvierte de txt a objeto
 */
public class CsvToTemporadas {

    private static final String DATABASE_FILE     = "resources/databases/extra_series.csv";
    private static final int    ID_PELICULA       = 0;
    private static final int    ID_TEMPORADA      = 1;
    private static final int    FECHA_LANZAMIENTO = 2;
    private static final int    GUION             = 3;
    private static final int    CAPITULOS         = 4;

    private static final String FIELD_SEPARATOR = ";";

    private File file;

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     */
    public CsvToTemporadas() {
        this.file = new File( DATABASE_FILE );
    }

    /**
     * Gracias al atributo file, creamos el FileReader y gracias al FileReader creamos el BufferReader y
     * lo retornamos
     *
     * @return BufferReader
     * @throws IOException
     */
    private BufferedReader getReader() throws IOException {
        return new BufferedReader( new FileReader( file ) );
    }

    /**
     * Gracias al String pasado como parámetro, lo desmenuzamos en un array de String. Lo desmenuzamos por el separador
     * del csv, en este caso ";".
     * Luego guardamos cada tipo de dato desmenuzado en su respecto tipo de dato.
     * Por ultimo creamos el objeto Temporada y lo retornamos
     *
     * @param register
     * @return Temporada
     */
    private Temporada getTemporadaFromRegister(String register) {
        String[] fields           = register.split( FIELD_SEPARATOR );
        int      idPelicula       = Integer.parseInt( fields[ID_PELICULA] );
        int      idTemporada      = Integer.parseInt( fields[ID_TEMPORADA] );
        String   guion            = fields[GUION];
        int      fechaLanzamiento = Integer.parseInt( fields[FECHA_LANZAMIENTO] );
        int      capitulos        = Integer.parseInt( fields[CAPITULOS] );
        return new Temporada( idPelicula, idTemporada, guion, fechaLanzamiento, capitulos );
    }


    /**
     * Gracias a la temporada pasada como parametro, lo que vamos a hacer es pasar de Temporada(objeto) a csv(String)
     * Para ello, creamos un array de size 5 y obtenemos gracias a la temporada por parametro y sus respectivos
     * getter y lo guardamos en cada posicion del array de String
     * Y por último, unimos todos los datos para devolverlos
     *
     * @param temporada
     * @return String
     */
    private String getRegisterFromTemporada(Temporada temporada) {
        String[] fields = new String[5];
        fields[ID_PELICULA]       = String.valueOf( temporada.getId() );
        fields[ID_TEMPORADA]      = String.valueOf( temporada.getId_serie() );
        fields[GUION]             = String.valueOf( temporada.getPlot() );
        fields[FECHA_LANZAMIENTO] = String.valueOf( temporada.getFechaLanzamiento() );
        fields[CAPITULOS]         = String.valueOf( temporada.getNumCapitulos() );
        return String.join( FIELD_SEPARATOR, fields );
    }


    /**
     * Creamos un ArrayList de temporadas, creamos un bufferReader para poder leer todas las Temporadas del fichero csv
     * Leemos la primera linea, ya que la primera linea es la cabecera de que tipo de dato es
     * La primera comprobacion es que comprueba si ya no hay más líneas por leer, en caso de que no, devuele el arrayList
     * con todas las temporadas
     * En caso de que no, lo que hace es pasar de linea de csv a objeto Temporada
     *
     * @return ArrayList de Temporda retorna todas las temporadas
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    public ArrayList<Temporada> findAll() throws DatabaseErrorException {
        try {
            ArrayList<Temporada> temporadaArrayList = new ArrayList<>();
            try ( BufferedReader bufferedReader = getReader() ) {
                bufferedReader.readLine();
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

    /**
     * Creamos un FileReader y un BufferReader para poder leer el .csv
     * Empezamos a leer linea por linea.
     * Primero comprobamos que la linea que leemos no sea null o que ya no hayan lineas por leer, en caso de
     * que no hayan lineas noz lanza la exception
     * Sugunda comprobación, nos aseguramos que la linea no este en blanco, y apartir de la linea creamos
     * la Temporada. De esa temporada extraemos el id y lo comparamos con el del parametro.
     * En caso de coincidir, nos devuelve la temporada, sino sigue iterando hasta que no hayan mas líneas
     *
     * @param id id de la temporada que queremos encontrar
     * @return Temporada que se ha añadio/actualizado
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     * @throws NotFoundException lanza la exception
     */
    public Temporada getById(int id) throws NotFoundException, DatabaseErrorException {
        try ( FileReader fileReader = new FileReader( this.file ) ;
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

    /**
     * Gracias al id como parametro busca la temporada gracias al metodo, en caso de que no exista,
     * lanza la exception y nos retorna null
     *
     * @param id id de la temporada que queremos encontrar
     * @return Temporada que se ha añadio/actualizado
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    public Temporada findById(int id) throws DatabaseErrorException {
        try {
            return getById( id );
        } catch ( NotFoundException ex ) {
            return null;
        }
    }

    /**
     * Guarda la temporada, para ello comprueba que la temporada no exista, en caso de no exsitir, lo que hace
     * es añadirla como una nueva.
     * En caso de que exista, lo que hace es actualizarla por la Temporada pasada por parámetro
     * @param temporada temporada que pasemos por parametro
     * @return Temporada que se ha añadio/actualizado
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
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

    /**
     * Añade la temporada pasada como parámetro al csv y crea una nueva línea
     * @param temporada temporada que pasemos por parametro
     * @return void
     * @throws IOException
     */
    private void append(Temporada temporada) throws IOException {
        try ( BufferedWriter bufferedWriter = getWriter( true ) ) {
            bufferedWriter.write( getRegisterFromTemporada( temporada ) );
            bufferedWriter.newLine();
        }
    }

    /**
     * Actualiza la Temporada pasa por parámetro
     * @param temporada temporada que pasemos por parametro
     * @return void
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    private void update(Temporada temporada) throws DatabaseErrorException {
        updateOrRemove( temporada, true );
    }


    /**
     * Elimina la Temporada pasada por parametro
     * @param temporada temporada que pasemos por parametro
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    public void remove(Temporada temporada) throws DatabaseErrorException {
        updateOrRemove( temporada, false );
    }

    /**
     * En caso de querer actualizar la Temporada pasa por parámetro, al update se le debería pasar un true,
     * y lo que hara es escribir tarea por tarea y cuando llegue a la tarea a actualizar,
     * simplemente escribirá la temporada pasada por parámetro. Luego seguirá con el resto de Temporadas
     *
     * En caso de querer eliminar, al update se le debería pasar un false, y lo que hara es escribir tarea
     * por tarea y cuando llegue a la tarea a eliminar, simplemente no la escribe y de esa forma la elimina
     *
     * @param temporada temporada que pasemos por parametro
     * @param update si queremos actualizar la temporada pasamos true, si queremos eliminarla pasamos false
     * @return void
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
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

    /**
     * Si le pasas por parametro un true, el bufferWriter lo que hace es crearte un buffer que añade
     * al fichero la nueva información
     * Si le pasas un false por parametro, lo que hace crearte un BufferWriter que borra los datos del fichero y escribirlo
     * con lo nuevo del bufferWriter
     *
     * @param append de tipo booleano
     * @return Devuelve el tipo de BufferWriter
     */
    private BufferedWriter getWriter(boolean append) throws IOException {
        return new BufferedWriter( new FileWriter( file, append ) );
    }
}

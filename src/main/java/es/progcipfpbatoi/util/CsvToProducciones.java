package es.progcipfpbatoi.util;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;
import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dto.Calificacion;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Tipo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private static final int    DIA               = 0;
    private static final int    MES               = 1;
    private static final int    ANYO              = 2;
    private static final String FIELD_SEPARATOR   = ";";

    private File file;

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     */
    public CsvToProducciones() {
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
     * Por ultimo creamos el objeto Produccion y lo retornamos
     *
     * @param register
     * @return Temporada
     */
    private Produccion getProduccionFromRegister(String register) {
        String[]     fields       = register.split( FIELD_SEPARATOR );
        int          id           = Integer.parseInt( fields[ ID ] );
        int          duracion     = Integer.parseInt( fields[ DURACION ].replaceAll( "[^0-9]", "" ) );
        String       actores      = fields[ ACTORES ];
        String       titulo       = fields[ TITULO ];
        Set<Genero>  generos      = parseGeneros( fields[ GENERO ] );
        String       director     = fields[ DIRECTOR ];
        String       urlTrailer   = fields[ WEB ];
        String       productor    = fields[ PRODUCTORA ];
        Tipo         tipo         = Tipo.parse( fields[ TIPO ] );
        Calificacion calificacion = Calificacion.parse( fields[ CALIFICACION ] );
        String       poster       = fields[ PORTADA ];
        String       guion        = fields[ GUION ];
        String       plataforma   = fields[ PLATAFORMA ];
        int anyo;
        fields[ FECHA_LANZAMIENTO ] = fields[ FECHA_LANZAMIENTO ].replaceAll( " ", "-" );
        String[]  fechaDesmenuzada  = fields[ FECHA_LANZAMIENTO ].split( "-" );
        if (fechaDesmenuzada[ANYO].length() <= 2) {
            anyo = anyo4digitos(Integer.parseInt(fechaDesmenuzada[ANYO]));
        } else {
            anyo = Integer.parseInt(fechaDesmenuzada[ANYO]);
        }
        LocalDate fechaLanzamiento  = LocalDate.of( anyo, Month.getMonthByThreeInitials( fechaDesmenuzada[ MES ] ), Integer.parseInt( fechaDesmenuzada[ DIA ] ) );
        return new Produccion( id, duracion, actores, titulo, generos, director, urlTrailer, productor, tipo, calificacion, poster, guion, plataforma, fechaLanzamiento );
    }

    /**
     * Se encarga de eliminar todos los espacios del String pasado por parámetro, y nos devuelve
     * este string limpiado de espacios en blanco
     *
     * @param generStr
     * @return String
     */
    public static String cleanWhiteSpaces(String generStr) {
        return generStr.replaceAll( " ", "" );
    }

    /**
     * Desmenuza el string pasado por parámetro, por el separador ";".
     * Recorre palabra por palabra y limpia los espacios en blanco de cada palabra.
     * Y convierte la palabra limpia al tipo Genero con el que coincida la palabra.
     * Y por último añade esa palabra al Set de Genero
     * Finalmente, al acabar el bucle devuelve el set de genero
     *
     * @param generosStr
     * @return Set<Genero>
     * @throws CategoryTypeErrorException
     */
    public static Set<Genero> parseGeneros(String generosStr) throws CategoryTypeErrorException {
        Set<Genero> generoHashSet = new HashSet<>();
        String[]      words         = generosStr.split( "," );
        for ( String generoStringItem :
                words ) {
            generoStringItem = cleanWhiteSpaces( generoStringItem );
            generoHashSet.add( Genero.parse( generoStringItem ) );
        }
        return generoHashSet;
    }

    /**
     * Pasamos un entero por parámetro. En este caso tenemos dos opciones:
     * Si el entero por parámetro lo juntamos con el 20 y si es superior o igual al año actual,
     * devolverá 19 junto con el anyo pasado por parametro
     * En caso contrario si el anyo pasado por parametro es inferior a 10, devolverá 200 junto
     * con el año pasado por parámetro
     *
     * Y si no, junta 20 con el año pasado por parámetro
     *
     * @param anyo
     * @return int
     */
    private int anyo4digitos(int anyo) {
        int anyoActual = LocalDate.now().getYear();
        if (Integer.parseInt("20"+anyo) >= anyoActual) {
            return Integer.parseInt("19"+anyo);
        } else {
            if (anyo < 10) {
                return Integer.parseInt("200"+anyo);
            }
            return Integer.parseInt("20"+anyo);
        }
    }

    /**
     * A partir de un objeto Produccion, lo que hacemos es desmenuzarlo en un Array de String de size 14.
     * Y con los respectivos getter del objeto produccion, vamos obteniendo sus datos y los almacenamos
     * en el Array de String, en cada posicion. La posicion es la que está en el csv.
     * Por último juntamos toda la información y ponemos el separador
     * Finalmente devolvemos toda esa cadena juntada
     *
     * @param produccion
     * @return String
     */
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

    /**
     * Encuentra todas las producciones del fichero .csv y las transforma a objeto Produccion
     * Cada objeto produccion es almacenado en un ArrayList de Produccion que cuando ya no hayan
     * mas producciones, será devuelto.
     *
     * @return ArrayList<Produccion>
     * @throws DatabaseErrorException
     */
    public ArrayList<Produccion> findAll() throws DatabaseErrorException {
        try {
            ArrayList<Produccion> produccionArrayList = new ArrayList<>();
            try ( BufferedReader bufferedReader = getReader() ) {
                bufferedReader.readLine();
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

    /**
     * Creamos un FileReader y un BufferReader para poder leer el .csv
     * Empezamos a leer linea por linea.
     * Primero comprobamos que la linea que leemos no sea null o que ya no hayan lineas por leer, en caso de
     * que no hayan lineas noz lanza la exception
     * Sugunda comprobación, nos aseguramos que la linea no este en blanco, y apartir de la linea creamos
     * la Temporada. De esa temporada extraemos el id y lo comparamos con el del parametro.
     * En caso de coincidir, nos devuelve la produccion, sino sigue iterando hasta que no hayan mas líneas
     *
     * @param id id de la produccion que queremos encontrar
     * @return Produccion que se ha encontrado
     * @throws DatabaseErrorException
     * @throws NotFoundException
     */
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

    /**
     * Gracias al id como parametro busca la produccion gracias al metodo, en caso de que no exista,
     * lanza la exception y nos retorna null
     *
     * @param id id de la produccion que queremos encontrar
     * @return Produccion que se ha encontrado
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    public Produccion findById(int id) throws DatabaseErrorException {
        try {
            return getById( id );
        } catch ( NotFoundException ex ) {
            return null;
        }
    }

    /**
     * Guarda la produccion, para ello comprueba que la temporada no exista, en caso de no exsitir, lo que hace
     * es añadirla como una nueva.
     * En caso de que exista, lo que hace es actualizarla por la produccion pasada por parámetro
     * @param produccion produccion que pasemos por parametro para guardar
     * @return Temporada que se ha añadio/actualizado
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
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

    /**
     * Añade la produccion pasada como parámetro al csv y crea una nueva línea
     * @param produccion produccion que pasemos por parametro
     * @return void
     * @throws IOException
     */
    private void append(Produccion produccion) throws IOException {
        try ( BufferedWriter bufferedWriter = getWriter( true ) ) {
            bufferedWriter.write( getRegisterFromProduccion( produccion ) );
            bufferedWriter.newLine();
        }
    }

    /**
     * Actualiza la Producion pasa por parámetro
     * @param produccion produccion que pasemos por parametro
     * @return void
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    private void update(Produccion produccion) throws DatabaseErrorException {
        updateOrRemove( produccion, true );
    }


    /**
     * Elimina la Produccion pasada por parametro
     * @param produccion produccion que pasemos por parametro
     * @return void
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
    public void remove(Produccion produccion) throws DatabaseErrorException {
        updateOrRemove( produccion, false );
    }

    /**
     * En caso de querer actualizar la Produccion pasa por parámetro, al update se le debería pasar un true,
     * y lo que hará es escribir tarea por tarea y cuando llegue a la tarea a actualizar,
     * simplemente escribirá la produccion pasada por parámetro. Luego seguirá con el resto de producciones
     *
     * En caso de querer eliminar, al update se le debería pasar un false, y lo que hara es escribir produccion
     * por produccion y cuando llegue a la produccion a eliminar, simplemente no la escribe y de esa forma la elimina
     *
     * @param produccion produccion que pasemos por parametro
     * @param update si queremos actualizar la produccion pasamos true, si queremos eliminarla pasamos false
     * @return void
     * @throws DatabaseErrorException en caso de no poder acceder a la base de datos
     */
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

package es.progcipfpbatoi.modelo.dto;

import es.progcipfpbatoi.exceptions.CategoryTypeErrorException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Produccion {

    private int id;

    private int duracion;

    private String actores;

    private String titulo;

    private Set<Genero> genero;

    private String director;

    private String urlTrailer;

    private String productor;

    private Tipo tipo;

    private Calificacion calificacion;

    private String poster;

    private String guion;

    private String plataforma;

    private LocalDate fechaLanzamiento;

    private int visualizaciones;

    private float valoracionTotal;

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Este constructor posee casi todos los atributos como parametros
     *
     * @param id
     * @param duracion
     * @param actores
     * @param titulo
     * @param genero
     * @param director
     * @param urlTrailer
     * @param productor
     * @param tipo
     * @param calificacion
     * @param poster
     * @param guion
     * @param plataforma
     * @param fechaLanzamiento
     * @param visualizaciones
     * @param valoracionTotal
     */
    public Produccion(int id, int duracion, String actores, String titulo, Set<Genero> genero, String director,
                      String urlTrailer, String productor, Tipo tipo, Calificacion calificacion, String poster,
                      String guion, String plataforma, LocalDate fechaLanzamiento, int visualizaciones, float valoracionTotal) {
        this.id               = id;
        this.duracion         = duracion;
        this.actores          = actores;
        this.titulo           = titulo;
        this.genero           = genero;
        this.director         = director;
        this.urlTrailer       = urlTrailer;
        this.productor        = productor;
        this.tipo             = tipo;
        this.calificacion     = calificacion;
        this.poster           = poster;
        this.guion            = guion;
        this.plataforma       = plataforma;
        this.fechaLanzamiento = fechaLanzamiento;
        this.visualizaciones  = visualizaciones;
        this.valoracionTotal  = valoracionTotal;
    }

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Este constructor posee casi todos los atributos como parametros, excepto las visualizaciones y la valoracion
     *
     * @param id
     * @param duracion
     * @param actores
     * @param titulo
     * @param genero
     * @param director
     * @param urlTrailer
     * @param productor
     * @param tipo
     * @param calificacion
     * @param poster
     * @param guion
     * @param plataforma
     * @param fechaLanzamiento
     */
    public Produccion(int id, int duracion, String actores, String titulo, Set<Genero> genero, String director,
                      String urlTrailer, String productor, Tipo tipo, Calificacion calificacion, String poster,
                      String guion, String plataforma, LocalDate fechaLanzamiento) {
        this.id               = id;
        this.duracion         = duracion;
        this.actores          = actores;
        this.titulo           = titulo;
        this.genero           = genero;
        this.director         = director;
        this.urlTrailer       = urlTrailer;
        this.productor        = productor;
        this.tipo             = tipo;
        this.calificacion     = calificacion;
        this.poster           = poster;
        this.guion            = guion;
        this.plataforma       = plataforma;
        this.fechaLanzamiento = fechaLanzamiento;
        this.visualizaciones  = 0;
        this.valoracionTotal  = 0;
    }

    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Este constructor posee un parametro el id, y dentro inicializa el HasSeht de genero
     *
     * @param id
     */
    public Produccion(int id) {
        this.id     = id;
        this.genero = new HashSet<>();
    }

    /**
     * Devuelve el id de la instacia
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la duracion de la instacia
     *
     * @return String
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Devuelve la valoracion de la instacia
     *
     * @return String
     */
    public float getValoracionTotal() {
        return valoracionTotal;
    }

    /**
     * Devuelve los actores de la instacia
     *
     * @return String
     */
    public String getActores() {
        return actores;
    }

    /**
     * Devuelve el titulo de la instacia
     *
     * @return String
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Devuelve un set de genero de los generos de la instacia
     *
     * @return Set<Genero>
     */
    public Set<Genero> getGenero() {
        return genero;
    }

    /**
     * Recorres genero por genero de la instancia. Y por cada genero, añades su .toString al StringBuilder junto con
     * una coma.
     * Luego del bucle guardas en un Sring el StringBuilder y eliminas todos los espacios del String.
     * Y eliminas la ultima coma del String
     * Finalmente retornas el String
     *
     * @return String
     */
    public String getGenerosWithDataBaseFormat() {
        StringBuilder generosDatabaseFormat = new StringBuilder();
        for ( Genero generoItem :
                genero ) {
            generosDatabaseFormat.append( generoItem.toString() ).append( ", " );
        }
        String allGeneros = generosDatabaseFormat.toString();
        allGeneros = deleteAllSpaces( allGeneros );
        allGeneros = deleteLastComma( allGeneros );
        return allGeneros;
    }

    /**
     * A partir del String pasado por parámetro, elimina todos los espacios de la cadena y retorna dicha cadena
     *
     * @param generosDatabaseFormat
     * @return String
     */
    private static String deleteAllSpaces(String generosDatabaseFormat) {
        return generosDatabaseFormat.replaceAll( " ", "" );
    }

    /**
     * A partir del String pasado por parámetro, elimina la ultima posicion del String
     * y luego retorna dicha cadena
     *
     * @param generosDatabaseFormat
     * @return String
     */
    private static String deleteLastComma(String generosDatabaseFormat) {
        return generosDatabaseFormat.substring( 0, generosDatabaseFormat.length() - 1 );
    }

    /**
     * Devuelve el director de la produccion
     *
     * @return String
     */
    public String getDirector() {
        return director;
    }

    /**
     * Devuelve la url de la produccion
     *
     * @return String
     */
    public String getUrlTrailer() {
        return urlTrailer;
    }

    /**
     * Devuelve el productor de la produccion
     *
     * @return String
     */
    public String getProductor() {
        return productor;
    }

    /**
     * Devuelve el tipo de la produccion
     *
     * @return Tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Devuelve la calificacion de la produccion
     *
     * @return int
     */
    public Calificacion getCalificacion() {
        return calificacion;
    }

    /**
     * Devuelve el poster de la produccion
     *
     * @return String
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Devuelve el guion de la produccion
     *
     * @return String
     */
    public String getGuion() {
        return guion;
    }

    /**
     * Devuelve la plataforma de la produccion
     *
     * @return String
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Devuelve la fecha de lanzamiento de la produccion
     *
     * @return LocalDate
     */
    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    /**
     * Devuelve las visualizaciones de la produccion
     *
     * @return int
     */
    public int getVisualizaciones() {
        return visualizaciones;
    }

    /**
     * Formato que queremos que nos muestre de la produccion al mostrarla.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Produccion{" +
                "id=" + id +
                ", duracion='" + duracion + '\'' +
                ", actores='" + actores + '\'' +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero.toString() +
                ", director='" + director + '\'' +
                ", urlTrailer='" + urlTrailer + '\'' +
                ", productor='" + productor + '\'' +
                ", tipo=" + tipo.toString() +
                ", calificacion=" + calificacion.toString() +
                ", poster='" + poster + '\'' +
                ", guion='" + guion + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", visualizaciones=" + visualizaciones +
                '}';
    }

    /**
     * Le pasas un obj por parámetro y comprueba que pertenece a produccion o que es propio de produccion,
     * En caso afirmativo devuelve si el id de la produccion por parametro es igual al id de la propia produccion
     * Y si este obj pasado por parámetro no pertenece a Produccion, directamente devuelve false.
     *
     * @param obj
     * @return boolen
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Produccion produccion) {
            return this.id == produccion.id;
        }
        return false;
    }

    /**
     * En caso de que el texto pasado por parámetro empiece por las mismas letras que el atributo
     * título del objeto, devuelve true. En caso contrario nos devuelve false
     *
     * @param text
     * @return boolena
     */
    public boolean empiezaPor(String text) {
        return this.titulo.startsWith( text );
    }

}

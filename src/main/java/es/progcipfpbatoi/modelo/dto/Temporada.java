package es.progcipfpbatoi.modelo.dto;

/**
 * Clase temporada
 */
public class Temporada {
    private int       id;
    private int       id_serie;
    private String    plot;
    private int fechaLanzamiento;
    private int       numCapitulos;


    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Este constructor posee todos atributos de la instancia como parametros
     *
     * @param id id de la temporada
     * @param id_serie id serie de la temporada
     * @param plot plot de la temporada
     * @param fechaLanzamiento fecha de la temporada
     * @param numCapitulos capitulos de la temporada
     */
    public Temporada(int id, int id_serie, String plot, int fechaLanzamiento, int numCapitulos) {
        this.id               = id;
        this.id_serie         = id_serie;
        this.plot             = plot;
        this.fechaLanzamiento = fechaLanzamiento;
        this.numCapitulos     = numCapitulos;
    }

    /**
     * Devuelve el id de la temporada
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el id de la serie
     *
     * @return int
     */
    public int getId_serie() {
        return id_serie;
    }

    /**
     * Devuelve el guion de la temporada
     *
     * @return String
     */
    public String getPlot() {
        return plot;
    }

    /**
     * Devuelve el numero de capitulos de la temporada
     *
     * @return int
     */
    public int getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    /**
     * Devuelve el numero de capitulos de la temporada
     *
     * @return int
     */
    public int getNumCapitulos() {
        return numCapitulos;
    }

    /**
     * Formato que queremos que nos muestre de la temporada al mostrarla.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Temporada{" +
                "id=" + id +
                ", id_serie='" + id_serie + '\'' +
                ", plot='" + plot + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", numCapitulos=" + numCapitulos +
                '}';
    }
}

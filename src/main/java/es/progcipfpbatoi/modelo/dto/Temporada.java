package es.progcipfpbatoi.modelo.dto;

public class Temporada {
    private int       id;
    private int       id_serie;
    private String    plot;
    private int fechaLanzamiento;
    private int       numCapitulos;

    public Temporada(int id, int id_serie, String plot, int fechaLanzamiento, int numCapitulos) {
        this.id               = id;
        this.id_serie         = id_serie;
        this.plot             = plot;
        this.fechaLanzamiento = fechaLanzamiento;
        this.numCapitulos     = numCapitulos;
    }

    public int getId() {
        return id;
    }

    public int getId_serie() {
        return id_serie;
    }

    public String getPlot() {
        return plot;
    }

    public int getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public int getNumCapitulos() {
        return numCapitulos;
    }

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

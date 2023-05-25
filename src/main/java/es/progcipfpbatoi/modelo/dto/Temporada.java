package es.progcipfpbatoi.modelo.dto;

import java.time.LocalDate;

public class Temporada {
    private int       id;
    private String    id_serie;
    private String    plot;
    private LocalDate fechaLanzamiento;
    private int       numCapitulos;

    public Temporada(int id, String id_serie, String plot, LocalDate fechaLanzamiento, int numCapitulos) {
        this.id               = id;
        this.id_serie         = id_serie;
        this.plot             = plot;
        this.fechaLanzamiento = fechaLanzamiento;
        this.numCapitulos     = numCapitulos;
    }

    public int getId() {
        return id;
    }

    public String getId_serie() {
        return id_serie;
    }

    public String getPlot() {
        return plot;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public int getNumCapitulos() {
        return numCapitulos;
    }
}

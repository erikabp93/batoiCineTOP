package es.progcipfpbatoi.modelo.dto;

import java.time.LocalDate;

public class Produccion {

    private int id;

    private int duracion;

    private String actores;

    private String titulo;

    private Genero genero;

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

    public Produccion(int id, int duracion, String actores, String titulo, Genero genero, String director,
                      String urlTrailer, String productor, Tipo tipo, Calificacion calificacion, String poster,
                      String guion, String plataforma, LocalDate fechaLanzamiento, int visualizaciones) {
        this.id = id;
        this.duracion = duracion;
        this.actores = actores;
        this.titulo = titulo;
        this.genero = genero;
        this.director = director;
        this.urlTrailer = urlTrailer;
        this.productor = productor;
        this.tipo = tipo;
        this.calificacion = calificacion;
        this.poster = poster;
        this.guion = guion;
        this.plataforma = plataforma;
        this.fechaLanzamiento = fechaLanzamiento;
        this.visualizaciones = visualizaciones;
    }

    public int getId() {
        return id;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getActores() {
        return actores;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getVisualizaciones() {
        return visualizaciones;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getDirector() {
        return director;
    }

    public String getGuion() {
        return guion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getPoster() {
        return poster;
    }

    public String getProductor() {
        return productor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public Tipo getTipo() {
        return tipo;
    }
}

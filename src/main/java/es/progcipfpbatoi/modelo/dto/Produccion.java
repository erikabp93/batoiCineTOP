package es.progcipfpbatoi.modelo.dto;

import java.time.LocalDate;
import java.util.HashSet;

public class Produccion {

    private int id;

    private int duracion;

    private String actores;

    private String titulo;

    private HashSet<Genero> genero;

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

    public Produccion(int id, int duracion, String actores, String titulo, HashSet<Genero> genero, String director,
                      String urlTrailer, String productor, Tipo tipo, Calificacion calificacion, String poster,
                      String guion, String plataforma, LocalDate fechaLanzamiento, int visualizaciones) {
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
    }

    public Produccion(int id, int duracion, String actores, String titulo, HashSet<Genero> genero, String director,
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
    }

    public Produccion(int id) {
        this.id = id;
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

    public String getTitulo() {
        return titulo;
    }

    public HashSet<Genero> getGenero() {
        return genero;
    }

    public String getDirector() {
        return director;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public String getProductor() {
        return productor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public String getPoster() {
        return poster;
    }

    public String getGuion() {
        return guion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public int getVisualizaciones() {
        return visualizaciones;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Produccion produccion) {
            return this.id == produccion.id;
        }
        return false;
    }

    public boolean empiezaPor(String text) {
        return this.titulo.startsWith(text);
    }


}

package es.progcipfpbatoi;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.*;
import es.progcipfpbatoi.modelo.dto.Genero;
import es.progcipfpbatoi.modelo.repositorios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AppTest {

    private UsuarioRepository       usuarioRepository;
    private PeliculaSerieRepository peliculaSerieRepository;
    private TemporadaRepository     temporadaRepository;
    private FavoritosRepository     favoritosRepository;
    private ValoracionesRepository  valoracionesRepository;

    @BeforeEach
    void setupRepositorios() {
        System.out.println( "@BeforeEach => setupRepositorios(): Se han ejecutado los repositorios ANTES DE CADA prueba" );
        usuarioRepository       = new UsuarioRepository( new SQLUsuarioDAO() );
        peliculaSerieRepository = new PeliculaSerieRepository( new SQLpeliculaSerieDAO(), new SQLtemporadaDAO() );
        temporadaRepository     = new TemporadaRepository( new SQLtemporadaDAO() );
        favoritosRepository     = new FavoritosRepository( new SQLfavoritoDAO() );
        valoracionesRepository  = new ValoracionesRepository( new SQLvalorarDAO(), new PeliculaSerieRepository( new SQLpeliculaSerieDAO(), new SQLtemporadaDAO() ) );
    }

    @Test
    void filtrarGenero() {
        int resultadoEsperado = 24;
        int resultadoActual;
        try {
            resultadoActual = peliculaSerieRepository.findAll( Genero.ROMANCE ).size();
        } catch ( DatabaseErrorException e ) {
            throw new RuntimeException( e );
        }
        System.out.println( resultadoActual );
        Assertions.assertEquals( resultadoEsperado, resultadoActual );
    }

    @Test
    void filtrarTodasLasPeliculas() {
        int resultadoEsperado = 151;
        int resultadoActual;
        try {
            resultadoActual = peliculaSerieRepository.findAll().size();
        } catch ( DatabaseErrorException e ) {
            throw new RuntimeException( e );
        }
        System.out.println( resultadoActual );
        Assertions.assertEquals( resultadoEsperado, resultadoActual );
    }

    @Test
    void filtrarTodasLasSeries() {
        int resultadoEsperado = 15;
        int resultadoActual;
        try {
            resultadoActual = temporadaRepository.findAll().size();
        } catch ( DatabaseErrorException e ) {
            throw new RuntimeException( e );
        }
        System.out.println( resultadoActual );
        Assertions.assertEquals( resultadoEsperado, resultadoActual );
    }
}

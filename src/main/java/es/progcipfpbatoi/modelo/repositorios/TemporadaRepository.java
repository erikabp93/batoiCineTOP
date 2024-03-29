package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.TemporadaDAO;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Temporada;

import java.util.ArrayList;

/**
 * Repositorio de las temporadas
 */
public class TemporadaRepository {

    private TemporadaDAO temporadaDAO;

    /**
     * Constructor de la clase, obligatorio ponerlo. Posee 1 parámetro
     * las temporadas
     * @param temporadaDAO parametro del dao de temporada
     */
    public TemporadaRepository(TemporadaDAO temporadaDAO) {
        this.temporadaDAO = temporadaDAO;
    }

    /**
     * LLama al método findAll del atributo TemporadaDAO que nos devolverá todas las
     * temporadas
     *
     * @return ArrayList Temporada
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    public ArrayList<Temporada> findAll() throws DatabaseErrorException {
        return temporadaDAO.findAll();
    }

    /**
     * LLama al método findById del atributo TemporadaDAO que nos devolverá la temporada que coincida
     * con el id pasado por parámetro
     *
     * @param id id de la Temporada a buscar
     * @return ArrayList de Temporada
     * @throws DatabaseErrorException al no poder acceder a la base de datos
     */
    public Temporada findById(int id) throws DatabaseErrorException {
        return temporadaDAO.findById(id);
    }
}
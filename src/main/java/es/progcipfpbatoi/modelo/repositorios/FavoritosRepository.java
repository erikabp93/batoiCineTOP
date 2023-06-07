package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.SQLfavoritoDAO;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.io.IOException;

public class FavoritosRepository {

    private SQLfavoritoDAO sqlFavoritoDAO;

    /**
     * Constructor de la clase, obligado ponerlo
     */
    public FavoritosRepository(SQLfavoritoDAO sqlFavoritoDAO) {
        this.sqlFavoritoDAO = sqlFavoritoDAO;
    }

    /**
     * Gracias a los parametros usuario y produccion, nos dice si el usuario ya tiene como favorito esa produccion
     * En caso afirmativo nos devuelve true
     * En caso negativo nos devuelve false
     * Tdo esto es gracias a que llama al método yaFavorito de la interfaz pasada como atributo
     *
     * @return boleean
     * @throws DatabaseErrorException
     */
    public boolean yaFavorito(Usuario usuario, Produccion produccion) throws DatabaseErrorException {
        return sqlFavoritoDAO.yaFavorito(usuario, produccion);
    }

    /**
     * Gracias al usuario y la produccion, nos permite que el usuario actual guarde en favoritos la producción que
     * qué quiera.
     * Nos devuelve true en caso de poder guardar la produccion como favorita y false en caso de no poder guardarla
     * Lanza exception cuando no pueda acceder
     *
     * @return boleean
     * @throws DatabaseErrorException
     */
    public boolean save(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        return sqlFavoritoDAO.save(produccion, usuario);
    }

    /**
     * Gracias a los parametros usuario y produccion, nos dice si el usuario ya puede eliminar de favoritos
     * la producción escogida
     * En caso afirmativo nos devuelve true
     * En caso negativo nos devuelve false
     *
     * Por ejemplo, no podrá eliminar de favoritos una producción que no se encuentre en favoritos
     *
     * @return boleean
     * @throws DatabaseErrorException
     */
    public boolean delete(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        return sqlFavoritoDAO.delete(produccion, usuario);
    }
}

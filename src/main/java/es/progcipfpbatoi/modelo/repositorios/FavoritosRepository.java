package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.SQLfavoritoDAO;
import es.progcipfpbatoi.modelo.dto.Produccion;
import es.progcipfpbatoi.modelo.dto.Usuario;

public class FavoritosRepository {

    private SQLfavoritoDAO sqlFavoritoDAO;

    public FavoritosRepository(SQLfavoritoDAO sqlFavoritoDAO) {
        this.sqlFavoritoDAO = sqlFavoritoDAO;
    }

    public boolean save(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        return sqlFavoritoDAO.save(produccion, usuario);
    }

    public boolean delete(Produccion produccion, Usuario usuario) throws DatabaseErrorException {
        return sqlFavoritoDAO.delete(produccion, usuario);
    }
}

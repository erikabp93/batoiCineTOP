package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.UsuarioDAO;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.sql.Connection;

/**
 * Repositorio del usuario
 */
public class UsuarioRepository {

    private UsuarioDAO usuarioDAO;

    /**
     * Constructor de la clase, obligatorio ponerlo. Posee 1 parametro
     * el UsuarioDAO
     * @param usuarioDAO dao del ususario
     */
    public UsuarioRepository(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * LLama al método save del atributo UsuarioDAO que guardara el usuario pasado por parámetro
     *
     * @param usuario que queremos que se guarde
     * @return Usuario que se guarde
     * @throws DatabaseErrorException lanza la exception
     */
    public Usuario save(Usuario usuario) throws DatabaseErrorException {
        return usuarioDAO.save(usuario);
    }

    /**
     * LLama al método existeuuario del atributo UsuarioDAO que nos dirá si el usuario pasado por parámetro
     * existe o no
     * En caso de exista devuelve true
     * En caso contrario devuelve false
     *
     * @param usuario que queremos saber si existe
     * @return boolean
     * @throws DatabaseErrorException lanza la exception
     */
    public boolean existeUsuario(Usuario usuario) throws DatabaseErrorException {
        return usuarioDAO.existeUsuario(usuario);
    }

    /**
     * LLama al método finByUsername del atributo UsuarioDAO que busca el nombre del usuario que coincida y
     * devuelve el usuario que haya coincidido con el nombre pasado por parámetro
     *
     * @param username nombre del usuario a encontrar
     * @return Usuario que se encuentre
     * @throws DatabaseErrorException lanza la exception
     */
    public Usuario findByUsername(String username) throws DatabaseErrorException {
        return usuarioDAO.findByUsername(username);
    }
}
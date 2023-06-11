package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Usuario;

/**
 * Interfaz de Usuario
 */
public interface UsuarioDAO {

    /**
     * Guardar el usuario
     *
     * @param usuario usuario por parametro
     * @return devuelve el usuario
     * @throws DatabaseErrorException lanza la exception
     */
    Usuario save(Usuario usuario) throws DatabaseErrorException;

    /**
     * Devuelve true en caso de que exista el usuario. En caso contrario devuelve false
     *
     * @param usuario usuario a guardar
     * @return boolean
     * @throws DatabaseErrorException lanza la exception
     */
    boolean existeUsuario(Usuario usuario) throws DatabaseErrorException;

    /**
     * Encuentra un usuario por nombre
     *
     * @param username nombre del usuario a encontrar
     * @return Usuario que buscamos
     */
    Usuario findByUsername(String username);
}
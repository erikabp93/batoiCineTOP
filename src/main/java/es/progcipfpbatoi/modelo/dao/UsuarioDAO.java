package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dto.Usuario;

public interface UsuarioDAO {

    Usuario save(Usuario usuario) throws DatabaseErrorException;
    boolean existeUsuario(Usuario usuario) throws DatabaseErrorException;
}
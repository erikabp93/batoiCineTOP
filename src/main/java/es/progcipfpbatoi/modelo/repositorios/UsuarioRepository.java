package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.modelo.dao.UsuarioDAO;
import es.progcipfpbatoi.modelo.dto.Usuario;

import java.sql.Connection;

public class UsuarioRepository {

    private UsuarioDAO usuarioDAO;

    public UsuarioRepository(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    private Usuario save(Usuario usuario) throws DatabaseErrorException {
        return usuarioDAO.save(usuario);
    }

    public boolean existeUsuario(Usuario usuario) throws DatabaseErrorException {
        return usuarioDAO.existeUsuario(usuario);
    }
}
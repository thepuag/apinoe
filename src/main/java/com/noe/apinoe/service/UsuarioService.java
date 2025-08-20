package com.noe.apinoe.service;

import com.noe.apinoe.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByGoogleId(String googleId);
    List<Usuario> findActivos();
    List<Usuario> findByNombreContaining(String nombre);
    Usuario save(Usuario usuario);
    Usuario update(Integer id, Usuario usuario);
    void deleteById(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
}


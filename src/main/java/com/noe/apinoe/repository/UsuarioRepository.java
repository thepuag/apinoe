package com.noe.apinoe.repository;

import com.noe.apinoe.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByGoogleId(String googleId);
    List<Usuario> findByActivoTrue();

    @Query("SELECT u FROM Usuario u WHERE u.activo = true AND u.nombre LIKE %:nombre%")
    List<Usuario> findByNombreContaining(String nombre);
}

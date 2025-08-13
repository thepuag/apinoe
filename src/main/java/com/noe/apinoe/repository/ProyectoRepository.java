package com.noe.apinoe.repository;

import com.noe.apinoe.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    List<Proyecto> findByActivoTrue();
    List<Proyecto> findByEmpresaAndActivoTrue(String empresa);

    @Query("SELECT p FROM Proyecto p WHERE p.activo = true AND p.titulo LIKE %:titulo%")
    List<Proyecto> findByTituloContaining(String titulo);
}

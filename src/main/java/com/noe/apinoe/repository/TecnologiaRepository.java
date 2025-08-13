package com.noe.apinoe.repository;

import com.noe.apinoe.model.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Integer> {
    List<Tecnologia> findByActivoTrue();

    @Query("SELECT t FROM Tecnologia t WHERE t.activo = true AND t.nombre LIKE %:nombre%")
    List<Tecnologia> findByNombreContaining(String nombre);
}

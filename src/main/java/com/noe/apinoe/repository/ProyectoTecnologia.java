
package com.noe.apinoe.repository;

import com.noe.apinoe.model.ProyectoTecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoTecnologiaRepository extends JpaRepository<ProyectoTecnologia, Integer> {
    List<ProyectoTecnologia> findByActivoTrue();
    List<ProyectoTecnologia> findByIdProyectoAndActivoTrue(Integer idProyecto);
    List<ProyectoTecnologia> findByIdTecnologiaAndActivoTrue(Integer idTecnologia);

    @Query("SELECT pt FROM ProyectoTecnologia pt WHERE pt.idProyecto = :idProyecto AND pt.idTecnologia = :idTecnologia AND pt.activo = true")
    ProyectoTecnologia findByProyectoAndTecnologia(Integer idProyecto, Integer idTecnologia);
}
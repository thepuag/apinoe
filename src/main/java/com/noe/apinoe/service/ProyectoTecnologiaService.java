package com.noe.apinoe.service;

import com.noe.apinoe.model.ProyectoTecnologia;
import java.util.List;
import java.util.Optional;

public interface ProyectoTecnologiaService {
    List<ProyectoTecnologia> findAll();
    Optional<ProyectoTecnologia> findById(Integer id);
    List<ProyectoTecnologia> findActivos();
    List<ProyectoTecnologia> findByProyecto(Integer idProyecto);
    List<ProyectoTecnologia> findByTecnologia(Integer idTecnologia);
    ProyectoTecnologia save(ProyectoTecnologia proyectoTecnologia);
    ProyectoTecnologia update(Integer id, ProyectoTecnologia proyectoTecnologia);
    void deleteById(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
    void asignarTecnologiaAProyecto(Integer idProyecto, Integer idTecnologia);
    void removerTecnologiaDeProyecto(Integer idProyecto, Integer idTecnologia);
}

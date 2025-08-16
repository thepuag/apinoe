package com.noe.apinoe.service.impl;

import com.noe.apinoe.model.ProyectoTecnologia;
import com.noe.apinoe.repository.ProyectoTecnologiaRepository;
import com.noe.apinoe.service.ProyectoTecnologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoTecnologiaServiceImpl implements ProyectoTecnologiaService {

    @Autowired
    private ProyectoTecnologiaRepository proyectoTecnologiaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoTecnologia> findAll() {
        return proyectoTecnologiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoTecnologia> findById(Integer id) {
        return proyectoTecnologiaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoTecnologia> findActivos() {
        return proyectoTecnologiaRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoTecnologia> findByProyecto(Integer idProyecto) {
        return proyectoTecnologiaRepository.findByIdProyectoAndActivoTrue(idProyecto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoTecnologia> findByTecnologia(Integer idTecnologia) {
        return proyectoTecnologiaRepository.findByIdTecnologiaAndActivoTrue(idTecnologia);
    }

    @Override
    public ProyectoTecnologia save(ProyectoTecnologia proyectoTecnologia) {
        proyectoTecnologia.setFechaCreacion(LocalDateTime.now());
        proyectoTecnologia.setFechaActualizacion(LocalDateTime.now());
        return proyectoTecnologiaRepository.save(proyectoTecnologia);
    }

    @Override
    public ProyectoTecnologia update(Integer id, ProyectoTecnologia proyectoTecnologia) {
        ProyectoTecnologia proyectoTecnologiaExistente = proyectoTecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProyectoTecnologia no encontrado con id: " + id));

        proyectoTecnologiaExistente.setIdProyecto(proyectoTecnologia.getIdProyecto());
        proyectoTecnologiaExistente.setIdTecnologia(proyectoTecnologia.getIdTecnologia());
        proyectoTecnologiaExistente.setFechaActualizacion(LocalDateTime.now());

        return proyectoTecnologiaRepository.save(proyectoTecnologiaExistente);
    }

    @Override
    public void deleteById(Integer id) {
        proyectoTecnologiaRepository.deleteById(id);
    }

    @Override
    public void activar(Integer id) {
        ProyectoTecnologia proyectoTecnologia = proyectoTecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProyectoTecnologia no encontrado con id: " + id));
        proyectoTecnologia.setActivo(true);
        proyectoTecnologia.setFechaActualizacion(LocalDateTime.now());
        proyectoTecnologiaRepository.save(proyectoTecnologia);
    }

    @Override
    public void desactivar(Integer id) {
        ProyectoTecnologia proyectoTecnologia = proyectoTecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProyectoTecnologia no encontrado con id: " + id));
        proyectoTecnologia.setActivo(false);
        proyectoTecnologia.setFechaActualizacion(LocalDateTime.now());
        proyectoTecnologiaRepository.save(proyectoTecnologia);
    }

    @Override
    public void asignarTecnologiaAProyecto(Integer idProyecto, Integer idTecnologia) {
        ProyectoTecnologia proyectoTecnologia = proyectoTecnologiaRepository
                .findByProyectoAndTecnologia(idProyecto, idTecnologia);
        if (proyectoTecnologia != null) {
            if (!proyectoTecnologia.getActivo()) {
                proyectoTecnologia.setActivo(true);
                proyectoTecnologia.setFechaActualizacion(LocalDateTime.now());
                proyectoTecnologiaRepository.save(proyectoTecnologia);
            }
        } else {
            proyectoTecnologia = new ProyectoTecnologia();
            proyectoTecnologia.setIdProyecto(idProyecto);
            proyectoTecnologia.setIdTecnologia(idTecnologia);
            proyectoTecnologia.setActivo(true);
            proyectoTecnologia.setFechaCreacion(LocalDateTime.now());
            proyectoTecnologia.setFechaActualizacion(LocalDateTime.now());
            proyectoTecnologiaRepository.save(proyectoTecnologia);
        }
    }

    @Override
    public void removerTecnologiaDeProyecto(Integer idProyecto, Integer idTecnologia) {
        ProyectoTecnologia proyectoTecnologia = proyectoTecnologiaRepository
                .findByProyectoAndTecnologia(idProyecto, idTecnologia);
        if (proyectoTecnologia == null) {
            throw new RuntimeException("Relación Proyecto-Tecnología no encontrada para idProyecto: " + idProyecto + " y idTecnologia: " + idTecnologia);
        }
        proyectoTecnologia.setActivo(false);
        proyectoTecnologia.setFechaActualizacion(LocalDateTime.now());
        proyectoTecnologiaRepository.save(proyectoTecnologia);
    }
}
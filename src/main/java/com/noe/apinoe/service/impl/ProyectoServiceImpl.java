package com.noe.apinoe.service.impl;

import com.noe.apinoe.model.Proyecto;
import com.noe.apinoe.repository.ProyectoRepository;
import com.noe.apinoe.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proyecto> findById(Integer id) {
        return proyectoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> findActivos() {
        return proyectoRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> findByEmpresa(String empresa) {
        return proyectoRepository.findByEmpresaAndActivoTrue(empresa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> findByTituloContaining(String titulo) {
        return proyectoRepository.findByTituloContaining(titulo);
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        proyecto.setFechaCreacion(LocalDateTime.now());
        proyecto.setFechaActualizacion(LocalDateTime.now());
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto update(Integer id, Proyecto proyecto) {
        Proyecto proyectoExistente = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));

        proyectoExistente.setTitulo(proyecto.getTitulo());
        proyectoExistente.setEmpresa(proyecto.getEmpresa());
        proyectoExistente.setFechaCreacion(proyecto.getFechaCreacion());
        proyectoExistente.setDescripcion(proyecto.getDescripcion());
        proyectoExistente.setFechaActualizacion(LocalDateTime.now());

        return proyectoRepository.save(proyectoExistente);
    }

    @Override
    public void deleteById(Integer id) {
        proyectoRepository.deleteById(id);
    }

    @Override
    public void activar(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
        proyecto.setActivo(true);
        proyecto.setFechaActualizacion(LocalDateTime.now());
        proyectoRepository.save(proyecto);
    }

    @Override
    public void desactivar(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
        proyecto.setActivo(false);
        proyecto.setFechaActualizacion(LocalDateTime.now());
        proyectoRepository.save(proyecto);
    }
}
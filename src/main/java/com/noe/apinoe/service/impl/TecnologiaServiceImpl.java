package com.noe.apinoe.service.impl;

import com.noe.apinoe.model.Tecnologia;
import com.noe.apinoe.repository.TecnologiaRepository;
import com.noe.apinoe.service.TecnologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TecnologiaServiceImpl implements TecnologiaService {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tecnologia> findAll() {
        return tecnologiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tecnologia> findById(Integer id) {
        return tecnologiaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tecnologia> findActivos() {
        return tecnologiaRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tecnologia> findByNombreContaining(String nombre) {
        return tecnologiaRepository.findByNombreContaining(nombre);
    }

    @Override
    public Tecnologia save(Tecnologia tecnologia) {
        return tecnologiaRepository.save(tecnologia);
    }

    @Override
    public Tecnologia update(Integer id, Tecnologia tecnologia) {
        Tecnologia tecnologiaExistente = tecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada con id: " + id));

        tecnologiaExistente.setNombre(tecnologia.getNombre());
        tecnologiaExistente.setDescripcion(tecnologia.getDescripcion());
        tecnologiaExistente.setIconoSvg(tecnologia.getIconoSvg());
        tecnologiaExistente.setFechaActualizacion(LocalDateTime.now());

        return tecnologiaRepository.save(tecnologiaExistente);
    }

    @Override
    public void deleteById(Integer id) {
        tecnologiaRepository.deleteById(id);
    }

    @Override
    public void activar(Integer id) {
        Tecnologia tecnologia = tecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada con id: " + id));
        tecnologia.setActivo(true);
        tecnologia.setFechaActualizacion(LocalDateTime.now());
        tecnologiaRepository.save(tecnologia);
    }

    @Override
    public void desactivar(Integer id) {
        Tecnologia tecnologia = tecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada con id: " + id));
        tecnologia.setActivo(false);
        tecnologia.setFechaActualizacion(LocalDateTime.now());
        tecnologiaRepository.save(tecnologia);
    }
}
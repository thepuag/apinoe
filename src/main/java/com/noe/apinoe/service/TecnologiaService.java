package com.noe.apinoe.service;

import com.noe.apinoe.model.Tecnologia;
import java.util.List;
import java.util.Optional;

public interface TecnologiaService {
    List<Tecnologia> findAll();
    Optional<Tecnologia> findById(Integer id);
    List<Tecnologia> findActivos();
    List<Tecnologia> findByNombreContaining(String nombre);
    Tecnologia save(Tecnologia tecnologia);
    Tecnologia update(Integer id, Tecnologia tecnologia);
    void deleteById(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
}

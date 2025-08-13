package com.noe.apinoe.service;

import com.noe.apinoe.model.Proyecto;
import java.util.List;
import java.util.Optional;

public interface ProyectoService {
    List<Proyecto> findAll();
    Optional<Proyecto> findById(Integer id);
    List<Proyecto> findActivos();
    List<Proyecto> findByEmpresa(String empresa);
    List<Proyecto> findByTituloContaining(String titulo);
    Proyecto save(Proyecto proyecto);
    Proyecto update(Integer id, Proyecto proyecto);
    void deleteById(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
}
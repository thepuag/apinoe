package com.noe.apinoe.service;

import com.noe.apinoe.model.Producto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> findAll();
    Optional<Producto> findById(Integer id);
    List<Producto> findActivos();
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    List<Producto> findByNombreContaining(String nombre);
    Producto save(Producto producto);
    Producto update(Integer id, Producto producto);
    void deleteById(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
}

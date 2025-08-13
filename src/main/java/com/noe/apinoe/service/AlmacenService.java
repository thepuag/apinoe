package com.noe.apinoe.service;

import com.noe.apinoe.model.Almacen;
import java.util.List;
import java.util.Optional;

public interface AlmacenService {
    List<Almacen> findAll();
    Optional<Almacen> findById(Integer id);
    List<Almacen> findActivos();
    Optional<Almacen> findByProducto(Integer idProducto);
    List<Almacen> findByStockBajo(Integer cantidadMinima);
    Almacen save(Almacen almacen);
    Almacen update(Integer id, Almacen almacen);
    void deleteById(Integer id);
    void activar(Integer id);
    void desactivar(Integer id);
    void actualizarStock(Integer idProducto, Integer nuevaCantidad);
    void incrementarStock(Integer idProducto, Integer cantidad);
    void decrementarStock(Integer idProducto, Integer cantidad);
}
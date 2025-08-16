package com.noe.apinoe.service.impl;

import com.noe.apinoe.model.Almacen;
import com.noe.apinoe.repository.AlmacenRepository;
import com.noe.apinoe.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Almacen> findById(Integer id) {
        return almacenRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Almacen> findActivos() {
        return almacenRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Almacen> findByProducto(Integer idProducto) {
        return almacenRepository.findByIdProductoAndActivoTrue(idProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Almacen> findByStockBajo(Integer cantidadMinima) {
        return almacenRepository.findByStockBajo(cantidadMinima);
    }

    @Override
    public Almacen save(Almacen almacen) {
        almacen.setFechaCreacion(LocalDateTime.now());
        almacen.setFechaActualizacion(LocalDateTime.now());
        return almacenRepository.save(almacen);
    }

    @Override
    public Almacen update(Integer id, Almacen almacen) {
        Almacen almacenExistente = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado con id: " + id));

        almacenExistente.setIdProducto(almacen.getIdProducto());
        almacenExistente.setCantidad(almacen.getCantidad());
        almacenExistente.setFechaCreacion(almacen.getFechaCreacion());
        almacenExistente.setFechaActualizacion(LocalDateTime.now());

        return almacenRepository.save(almacenExistente);
    }

    @Override
    public void deleteById(Integer id) {
        almacenRepository.deleteById(id);
    }

    @Override
    public void activar(Integer id) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado con id: " + id));
        almacen.setActivo(true);
        almacen.setFechaActualizacion(LocalDateTime.now());
        almacenRepository.save(almacen);
    }

    @Override
    public void desactivar(Integer id) {
        Almacen almacen = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado con id: " + id));
        almacen.setActivo(false);
        almacen.setFechaActualizacion(LocalDateTime.now());
        almacenRepository.save(almacen);
    }

    @Override
    public void actualizarStock(Integer idProducto, Integer nuevaCantidad) {
        Almacen almacen = almacenRepository.findByIdProductoAndActivoTrue(idProducto)
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado para el producto con id: " + idProducto));
        almacen.setCantidad(nuevaCantidad);
        almacen.setFechaActualizacion(LocalDateTime.now());
        almacenRepository.save(almacen);
    }

    @Override
    public void incrementarStock(Integer idProducto, Integer cantidad) {
        Almacen almacen = almacenRepository.findByIdProductoAndActivoTrue(idProducto)
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado para el producto con id: " + idProducto));
        almacen.setCantidad(almacen.getCantidad() + cantidad);
        almacen.setFechaActualizacion(LocalDateTime.now());
        almacenRepository.save(almacen);
    }

    @Override
    public void decrementarStock(Integer idProducto, Integer cantidad) {
        Almacen almacen = almacenRepository.findByIdProductoAndActivoTrue(idProducto)
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado para el producto con id: " + idProducto));
        int nuevoStock = almacen.getCantidad() - cantidad;
        if (nuevoStock < 0) {
            throw new RuntimeException("No hay suficiente stock para el producto con id: " + idProducto);
        }
        almacen.setCantidad(nuevoStock);
        almacen.setFechaActualizacion(LocalDateTime.now());
        almacenRepository.save(almacen);
    }
}
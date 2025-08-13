package com.noe.apinoe.repository;

import com.noe.apinoe.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByActivoTrue();

    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.precio BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);

    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.nombre LIKE %:nombre%")
    List<Producto> findByNombreContaining(String nombre);
}

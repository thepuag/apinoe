package com.noe.apinoe.repository;

import com.noe.apinoe.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {
    List<Almacen> findByActivoTrue();
    Optional<Almacen> findByIdProductoAndActivoTrue(Integer idProducto);

    @Query("SELECT a FROM Almacen a WHERE a.activo = true AND a.cantidad <= :cantidadMinima")
    List<Almacen> findByStockBajo(Integer cantidadMinima);
}

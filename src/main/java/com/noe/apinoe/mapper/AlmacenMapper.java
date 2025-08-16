package com.noe.apinoe.mapper;

import com.noe.apinoe.dto.AlmacenDto;
import com.noe.apinoe.model.Almacen;
import org.springframework.stereotype.Component;

@Component
public class AlmacenMapper {

    /**
     * Convierte Almacen (entidad) a AlmacenDto
     * Úsalo cuando devuelvas datos al cliente
     */
    public AlmacenDto toDto(Almacen almacen) {
        if (almacen == null) {
            return null;
        }
        
        AlmacenDto dto = new AlmacenDto();
        dto.setId(almacen.getId());
        dto.setIdProducto(almacen.getIdProducto());
        dto.setCantidad(almacen.getCantidad());
        dto.setActivo(almacen.getActivo());
        
        // NO incluimos: fechaCreacion, fechaActualizacion, producto
        // porque no queremos exponerlos en la API
        
        return dto;
    }

    /**
     * Convierte AlmacenDto a Almacen (entidad)
     * Úsalo cuando recibas datos del cliente
     */
    public Almacen toEntity(AlmacenDto dto) {
        if (dto == null) {
            return null;
        }
        
        Almacen almacen = new Almacen();
        almacen.setId(dto.getId());
        almacen.setIdProducto(dto.getIdProducto());
        almacen.setCantidad(dto.getCantidad());
        almacen.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        
        // Las fechas se setean automáticamente con @PrePersist/@PreUpdate
        // La relación con Producto se maneja en otros flujos
        
        return almacen;
    }

    /**
     * Actualiza una entidad Almacen existente con datos del DTO
     * Úsalo para operaciones de UPDATE
     */
    public void updateEntityFromDto(Almacen almacen, AlmacenDto dto) {
        if (almacen == null || dto == null) {
            return;
        }
        
        almacen.setIdProducto(dto.getIdProducto());
        almacen.setCantidad(dto.getCantidad());
        
        if (dto.getActivo() != null) {
            almacen.setActivo(dto.getActivo());
        }
        
        // ID no se actualiza
        // fechaActualizacion se maneja automáticamente con @PreUpdate
        // La relación con Producto no se actualiza desde el DTO
    }
}
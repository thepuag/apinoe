package com.noe.apinoe.mapper;

import com.noe.apinoe.dto.UsuarioDto;
import com.noe.apinoe.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    /**
     * Convierte Usuario (entidad) a UsuarioDto
     * Úsalo cuando devuelvas datos al cliente
     */
    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setImagenUrl(usuario.getImagenUrl());
        dto.setActivo(usuario.getActivo());
        
        // NO incluimos: googleId, fechaCreacion, fechaActualizacion
        // porque no queremos exponerlos en la API
        
        return dto;
    }

    /**
     * Convierte UsuarioDto a Usuario (entidad)
     * Úsalo cuando recibas datos del cliente
     */
    public Usuario toEntity(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setImagenUrl(dto.getImagenUrl());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        
        // Las fechas se setean automáticamente con @PrePersist/@PreUpdate
        // googleId se maneja en otros flujos (OAuth)
        
        return usuario;
    }

    /**
     * Actualiza una entidad Usuario existente con datos del DTO
     * Úsalo para operaciones de UPDATE
     */
    public void updateEntityFromDto(Usuario usuario, UsuarioDto dto) {
        if (usuario == null || dto == null) {
            return;
        }
        
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setImagenUrl(dto.getImagenUrl());
        
        if (dto.getActivo() != null) {
            usuario.setActivo(dto.getActivo());
        }
        
        // ID no se actualiza
        // fechaActualizacion se maneja automáticamente con @PreUpdate
    }
}

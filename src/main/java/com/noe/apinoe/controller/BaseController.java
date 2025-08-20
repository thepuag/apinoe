package com.noe.apinoe.controller;

import com.noe.apinoe.dto.ApiResponse;
import com.noe.apinoe.mapper.BaseMapper;
import com.noe.apinoe.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador base genérico que proporciona operaciones CRUD estándar
 * @param <E> Tipo de la entidad
 * @param <D> Tipo del DTO
 * @param <ID> Tipo del identificador
 */
public abstract class BaseController<E, D, ID> {
    
    protected final BaseService<E, ID> service;
    protected final BaseMapper<E, D> mapper;
    
    public BaseController(BaseService<E, ID> service, BaseMapper<E, D> mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    
    // Métodos que pueden ser sobrescritos si se necesita lógica específica
    protected String getEntityName() {
        return "Entidad";
    }
    
    // =============== OPERACIONES CRUD BÁSICAS ===============
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<D>>> getAll() {
        List<E> entities = service.findAll();
        List<D> dtos = entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<D>> getById(@PathVariable ID id) {
        Optional<E> entity = service.findById(id);
        
        if (entity.isPresent()) {
            D dto = mapper.toDto(entity.get());
            return ResponseEntity.ok(ApiResponse.success(dto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(getEntityName() + " no encontrado con id: " + id));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<D>> create(@Valid @RequestBody D dto) {
        try {
            // Validaciones personalizadas antes de crear (puede ser sobrescrito)
            validateBeforeCreate(dto);
            
            // Convierte DTO a entidad
            E entity = mapper.toEntity(dto);
            
            // Guarda en base de datos
            E savedEntity = service.save(entity);
            
            // Convierte la entidad guardada de vuelta a DTO
            D savedDto = mapper.toDto(savedEntity);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(getEntityName() + " creado exitosamente", savedDto));
                    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Error al crear " + getEntityName().toLowerCase() + ": " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<D>> update(@PathVariable ID id, @Valid @RequestBody D dto) {
        try {
            // Validaciones personalizadas antes de actualizar
            validateBeforeUpdate(id, dto);
            
            // Busca la entidad existente
            Optional<E> existingEntity = service.findById(id);
            
            if (existingEntity.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(getEntityName() + " no encontrado con id: " + id));
            }
            
            // Actualiza la entidad existente con datos del DTO
            E entity = existingEntity.get();
            mapper.updateEntityFromDto(entity, dto);
            
            // Guarda los cambios
            E updatedEntity = service.update(id, entity);
            
            // Convierte de vuelta a DTO
            D updatedDto = mapper.toDto(updatedEntity);
            
            return ResponseEntity.ok(ApiResponse.success(getEntityName() + " actualizado exitosamente", updatedDto));
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(getEntityName() + " no encontrado con id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Error al actualizar " + getEntityName().toLowerCase() + ": " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable ID id) {
        try {
            // Validaciones antes de eliminar
            validateBeforeDelete(id);
            
            service.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success(getEntityName() + " eliminado exitosamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(getEntityName() + " no encontrado con id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar " + getEntityName().toLowerCase() + ": " + e.getMessage()));
        }
    }
    
    // =============== MÉTODOS HOOK PARA VALIDACIONES PERSONALIZADAS ===============
    
    /**
     * Validaciones personalizadas antes de crear una entidad
     * Puede ser sobrescrito en los controladores específicos
     */
    protected void validateBeforeCreate(D dto) {
        // Implementación por defecto vacía
    }
    
    /**
     * Validaciones personalizadas antes de actualizar una entidad
     */
    protected void validateBeforeUpdate(ID id, D dto) {
        // Implementación por defecto vacía
    }
    
    /**
     * Validaciones personalizadas antes de eliminar una entidad
     */
    protected void validateBeforeDelete(ID id) {
        // Implementación por defecto vacía
    }
    
    // =============== OPERACIONES ADICIONALES COMUNES ===============
    
    /**
     * Endpoint para activar/desactivar entidades (si aplica)
     * Solo disponible si el servicio implementa estos métodos
     */
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleStatus(@PathVariable ID id, 
                                                           @RequestParam boolean active) {
        try {
            // Esto requeriría que BaseService tenga métodos para activar/desactivar
            if (active) {
                activateEntity(id);
                return ResponseEntity.ok(ApiResponse.success(getEntityName() + " activado exitosamente", null));
            } else {
                deactivateEntity(id);
                return ResponseEntity.ok(ApiResponse.success(getEntityName() + " desactivado exitosamente", null));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(getEntityName() + " no encontrado con id: " + id));
        }
    }
    
    // Métodos que pueden ser implementados en controladores específicos
    protected void activateEntity(ID id) {
        throw new UnsupportedOperationException("Operación de activación no implementada");
    }
    
    protected void deactivateEntity(ID id) {
        throw new UnsupportedOperationException("Operación de desactivación no implementada");
    }
}

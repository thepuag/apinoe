package com.noe.apinoe.mapper;

/**
 * Interface base para mappers que convierten entre entidades y DTOs
 * @param <E> Tipo de la entidad
 * @param <D> Tipo del DTO
 */
public interface BaseMapper<E, D> {
    
    /**
     * Convierte una entidad a DTO
     * @param entity La entidad a convertir
     * @return El DTO correspondiente
     */
    D toDto(E entity);
    
    /**
     * Convierte un DTO a entidad
     * @param dto El DTO a convertir
     * @return La entidad correspondiente
     */
    E toEntity(D dto);
    
    /**
     * Actualiza una entidad existente con los datos de un DTO
     * @param entity La entidad a actualizar (se modifica directamente)
     * @param dto El DTO con los nuevos datos
     */
    void updateEntityFromDto(E entity, D dto);
}

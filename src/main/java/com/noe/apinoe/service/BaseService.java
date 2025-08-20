package com.noe.apinoe.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface base para servicios que proporciona operaciones CRUD básicas
 * @param <E> Tipo de la entidad
 * @param <ID> Tipo del identificador
 */
public interface BaseService<E, ID> {
    
    /**
     * Obtiene todas las entidades
     */
    List<E> findAll();
    
    /**
     * Busca una entidad por su ID
     */
    Optional<E> findById(ID id);
    
    /**
     * Guarda una nueva entidad
     */
    E save(E entity);
    
    /**
     * Actualiza una entidad existente
     */
    E update(ID id, E entity);
    
    /**
     * Elimina una entidad por su ID
     */
    void deleteById(ID id);
    
    /**
     * Verifica si existe una entidad con el ID dado
     */
    boolean existsById(ID id);
}

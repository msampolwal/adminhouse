package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.GrupoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Grupo.
 */
public interface GrupoService {

    /**
     * Save a grupo.
     *
     * @param grupoDTO the entity to save
     * @return the persisted entity
     */
    GrupoDTO save(GrupoDTO grupoDTO);

    /**
     * Get all the grupos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GrupoDTO> findAll(Pageable pageable);

    /**
     * Get all the Grupo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<GrupoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" grupo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GrupoDTO> findOne(Long id);

    /**
     * Delete the "id" grupo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

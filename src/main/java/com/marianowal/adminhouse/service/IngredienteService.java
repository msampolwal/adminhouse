package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.IngredienteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ingrediente.
 */
public interface IngredienteService {

    /**
     * Save a ingrediente.
     *
     * @param ingredienteDTO the entity to save
     * @return the persisted entity
     */
    IngredienteDTO save(IngredienteDTO ingredienteDTO);

    /**
     * Get all the ingredientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IngredienteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ingrediente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IngredienteDTO> findOne(Long id);

    /**
     * Delete the "id" ingrediente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

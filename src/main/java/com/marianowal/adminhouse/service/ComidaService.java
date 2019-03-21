package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.ComidaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Comida.
 */
public interface ComidaService {

    /**
     * Save a comida.
     *
     * @param comidaDTO the entity to save
     * @return the persisted entity
     */
    ComidaDTO save(ComidaDTO comidaDTO);

    /**
     * Get all the comidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ComidaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" comida.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ComidaDTO> findOne(Long id);

    /**
     * Delete the "id" comida.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

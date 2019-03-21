package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.DiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dia.
 */
public interface DiaService {

    /**
     * Save a dia.
     *
     * @param diaDTO the entity to save
     * @return the persisted entity
     */
    DiaDTO save(DiaDTO diaDTO);

    /**
     * Get all the dias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiaDTO> findOne(Long id);

    /**
     * Delete the "id" dia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.CalendarioComidaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CalendarioComida.
 */
public interface CalendarioComidaService {

    /**
     * Save a calendarioComida.
     *
     * @param calendarioComidaDTO the entity to save
     * @return the persisted entity
     */
    CalendarioComidaDTO save(CalendarioComidaDTO calendarioComidaDTO);

    /**
     * Get all the calendarioComidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CalendarioComidaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" calendarioComida.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CalendarioComidaDTO> findOne(Long id);

    /**
     * Delete the "id" calendarioComida.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

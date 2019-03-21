package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.UnidadMedidaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UnidadMedida.
 */
public interface UnidadMedidaService {

    /**
     * Save a unidadMedida.
     *
     * @param unidadMedidaDTO the entity to save
     * @return the persisted entity
     */
    UnidadMedidaDTO save(UnidadMedidaDTO unidadMedidaDTO);

    /**
     * Get all the unidadMedidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UnidadMedidaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" unidadMedida.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UnidadMedidaDTO> findOne(Long id);

    /**
     * Delete the "id" unidadMedida.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

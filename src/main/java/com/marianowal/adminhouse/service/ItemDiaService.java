package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.ItemDiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemDia.
 */
public interface ItemDiaService {

    /**
     * Save a itemDia.
     *
     * @param itemDiaDTO the entity to save
     * @return the persisted entity
     */
    ItemDiaDTO save(ItemDiaDTO itemDiaDTO);

    /**
     * Get all the itemDias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemDiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemDia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemDiaDTO> findOne(Long id);

    /**
     * Delete the "id" itemDia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.marianowal.adminhouse.service;

import com.marianowal.adminhouse.service.dto.PrecioProductoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PrecioProducto.
 */
public interface PrecioProductoService {

    /**
     * Save a precioProducto.
     *
     * @param precioProductoDTO the entity to save
     * @return the persisted entity
     */
    PrecioProductoDTO save(PrecioProductoDTO precioProductoDTO);

    /**
     * Get all the precioProductos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PrecioProductoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" precioProducto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PrecioProductoDTO> findOne(Long id);

    /**
     * Delete the "id" precioProducto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

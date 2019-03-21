package com.marianowal.adminhouse.service.impl;

import com.marianowal.adminhouse.service.PrecioProductoService;
import com.marianowal.adminhouse.domain.PrecioProducto;
import com.marianowal.adminhouse.repository.PrecioProductoRepository;
import com.marianowal.adminhouse.service.dto.PrecioProductoDTO;
import com.marianowal.adminhouse.service.mapper.PrecioProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing PrecioProducto.
 */
@Service
@Transactional
public class PrecioProductoServiceImpl implements PrecioProductoService {

    private final Logger log = LoggerFactory.getLogger(PrecioProductoServiceImpl.class);

    private final PrecioProductoRepository precioProductoRepository;

    private final PrecioProductoMapper precioProductoMapper;

    public PrecioProductoServiceImpl(PrecioProductoRepository precioProductoRepository, PrecioProductoMapper precioProductoMapper) {
        this.precioProductoRepository = precioProductoRepository;
        this.precioProductoMapper = precioProductoMapper;
    }

    /**
     * Save a precioProducto.
     *
     * @param precioProductoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PrecioProductoDTO save(PrecioProductoDTO precioProductoDTO) {
        log.debug("Request to save PrecioProducto : {}", precioProductoDTO);
        PrecioProducto precioProducto = precioProductoMapper.toEntity(precioProductoDTO);
        precioProducto = precioProductoRepository.save(precioProducto);
        return precioProductoMapper.toDto(precioProducto);
    }

    /**
     * Get all the precioProductos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrecioProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrecioProductos");
        return precioProductoRepository.findAll(pageable)
            .map(precioProductoMapper::toDto);
    }


    /**
     * Get one precioProducto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrecioProductoDTO> findOne(Long id) {
        log.debug("Request to get PrecioProducto : {}", id);
        return precioProductoRepository.findById(id)
            .map(precioProductoMapper::toDto);
    }

    /**
     * Delete the precioProducto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrecioProducto : {}", id);
        precioProductoRepository.deleteById(id);
    }
}

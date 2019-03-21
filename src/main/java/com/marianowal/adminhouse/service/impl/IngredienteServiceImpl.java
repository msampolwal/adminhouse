package com.marianowal.adminhouse.service.impl;

import com.marianowal.adminhouse.service.IngredienteService;
import com.marianowal.adminhouse.domain.Ingrediente;
import com.marianowal.adminhouse.repository.IngredienteRepository;
import com.marianowal.adminhouse.service.dto.IngredienteDTO;
import com.marianowal.adminhouse.service.mapper.IngredienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Ingrediente.
 */
@Service
@Transactional
public class IngredienteServiceImpl implements IngredienteService {

    private final Logger log = LoggerFactory.getLogger(IngredienteServiceImpl.class);

    private final IngredienteRepository ingredienteRepository;

    private final IngredienteMapper ingredienteMapper;

    public IngredienteServiceImpl(IngredienteRepository ingredienteRepository, IngredienteMapper ingredienteMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.ingredienteMapper = ingredienteMapper;
    }

    /**
     * Save a ingrediente.
     *
     * @param ingredienteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IngredienteDTO save(IngredienteDTO ingredienteDTO) {
        log.debug("Request to save Ingrediente : {}", ingredienteDTO);
        Ingrediente ingrediente = ingredienteMapper.toEntity(ingredienteDTO);
        ingrediente = ingredienteRepository.save(ingrediente);
        return ingredienteMapper.toDto(ingrediente);
    }

    /**
     * Get all the ingredientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ingredientes");
        return ingredienteRepository.findAll(pageable)
            .map(ingredienteMapper::toDto);
    }


    /**
     * Get one ingrediente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IngredienteDTO> findOne(Long id) {
        log.debug("Request to get Ingrediente : {}", id);
        return ingredienteRepository.findById(id)
            .map(ingredienteMapper::toDto);
    }

    /**
     * Delete the ingrediente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ingrediente : {}", id);
        ingredienteRepository.deleteById(id);
    }
}

package com.marianowal.adminhouse.service.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marianowal.adminhouse.domain.Comida;
import com.marianowal.adminhouse.domain.Ingrediente;
import com.marianowal.adminhouse.repository.ComidaRepository;
import com.marianowal.adminhouse.service.ComidaService;
import com.marianowal.adminhouse.service.dto.ComidaDTO;
import com.marianowal.adminhouse.service.dto.IngredienteDTO;
import com.marianowal.adminhouse.service.mapper.ComidaMapper;
import com.marianowal.adminhouse.service.mapper.IngredienteMapper;
/**
 * Service Implementation for managing Comida.
 */
@Service
@Transactional
public class ComidaServiceImpl implements ComidaService {

    private final Logger log = LoggerFactory.getLogger(ComidaServiceImpl.class);

    private final ComidaRepository comidaRepository;

    private final ComidaMapper comidaMapper;
    
    private final IngredienteMapper ingredienteMapper;

    public ComidaServiceImpl(ComidaRepository comidaRepository, ComidaMapper comidaMapper, IngredienteMapper ingredienteMapper) {
        this.comidaRepository = comidaRepository;
        this.comidaMapper = comidaMapper;
        this.ingredienteMapper = ingredienteMapper;
    }

    /**
     * Save a comida.
     *
     * @param comidaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ComidaDTO save(ComidaDTO comidaDTO) {
        log.debug("Request to save Comida : {}", comidaDTO);
        Set<IngredienteDTO> ingredientes = comidaDTO.getIngredientes();
        Comida comida = comidaMapper.toEntity(comidaDTO);
        for (IngredienteDTO ingredienteDTO : ingredientes) {
			Ingrediente ingrediente = ingredienteMapper.toEntity(ingredienteDTO);
			comida.addIngredientes(ingrediente);
		}
        comida = comidaRepository.save(comida);
        return comidaMapper.toDto(comida);
    }

    /**
     * Get all the comidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ComidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comidas");
        return comidaRepository.findAll(pageable)
            .map(comidaMapper::toDto);
    }


    /**
     * Get one comida by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ComidaDTO> findOne(Long id) {
        log.debug("Request to get Comida : {}", id);
        return comidaRepository.findById(id)
            .map(comidaMapper::toDto);
    }

    /**
     * Delete the comida by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comida : {}", id);
        comidaRepository.deleteById(id);
    }
}

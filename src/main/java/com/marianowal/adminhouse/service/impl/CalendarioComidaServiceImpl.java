package com.marianowal.adminhouse.service.impl;

import com.marianowal.adminhouse.service.CalendarioComidaService;
import com.marianowal.adminhouse.domain.CalendarioComida;
import com.marianowal.adminhouse.repository.CalendarioComidaRepository;
import com.marianowal.adminhouse.service.dto.CalendarioComidaDTO;
import com.marianowal.adminhouse.service.mapper.CalendarioComidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing CalendarioComida.
 */
@Service
@Transactional
public class CalendarioComidaServiceImpl implements CalendarioComidaService {

    private final Logger log = LoggerFactory.getLogger(CalendarioComidaServiceImpl.class);

    private final CalendarioComidaRepository calendarioComidaRepository;

    private final CalendarioComidaMapper calendarioComidaMapper;

    public CalendarioComidaServiceImpl(CalendarioComidaRepository calendarioComidaRepository, CalendarioComidaMapper calendarioComidaMapper) {
        this.calendarioComidaRepository = calendarioComidaRepository;
        this.calendarioComidaMapper = calendarioComidaMapper;
    }

    /**
     * Save a calendarioComida.
     *
     * @param calendarioComidaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CalendarioComidaDTO save(CalendarioComidaDTO calendarioComidaDTO) {
        log.debug("Request to save CalendarioComida : {}", calendarioComidaDTO);
        CalendarioComida calendarioComida = calendarioComidaMapper.toEntity(calendarioComidaDTO);
        calendarioComida = calendarioComidaRepository.save(calendarioComida);
        return calendarioComidaMapper.toDto(calendarioComida);
    }

    /**
     * Get all the calendarioComidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CalendarioComidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CalendarioComidas");
        return calendarioComidaRepository.findAll(pageable)
            .map(calendarioComidaMapper::toDto);
    }


    /**
     * Get one calendarioComida by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CalendarioComidaDTO> findOne(Long id) {
        log.debug("Request to get CalendarioComida : {}", id);
        return calendarioComidaRepository.findById(id)
            .map(calendarioComidaMapper::toDto);
    }

    /**
     * Delete the calendarioComida by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CalendarioComida : {}", id);
        calendarioComidaRepository.deleteById(id);
    }
}

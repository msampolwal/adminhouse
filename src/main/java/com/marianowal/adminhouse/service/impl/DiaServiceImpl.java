package com.marianowal.adminhouse.service.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marianowal.adminhouse.domain.Dia;
import com.marianowal.adminhouse.domain.ItemDia;
import com.marianowal.adminhouse.repository.DiaRepository;
import com.marianowal.adminhouse.service.DiaService;
import com.marianowal.adminhouse.service.dto.DiaDTO;
import com.marianowal.adminhouse.service.dto.ItemDiaDTO;
import com.marianowal.adminhouse.service.mapper.DiaMapper;
import com.marianowal.adminhouse.service.mapper.ItemDiaMapper;
/**
 * Service Implementation for managing Dia.
 */
@Service
@Transactional
public class DiaServiceImpl implements DiaService {

    private final Logger log = LoggerFactory.getLogger(DiaServiceImpl.class);

    private final DiaRepository diaRepository;

    private final DiaMapper diaMapper;
    
    private final ItemDiaMapper itemDiaMapper;

    public DiaServiceImpl(DiaRepository diaRepository, DiaMapper diaMapper, ItemDiaMapper itemDiaMapper) {
        this.diaRepository = diaRepository;
        this.diaMapper = diaMapper;
        this.itemDiaMapper = itemDiaMapper;
    }

    /**
     * Save a dia.
     *
     * @param diaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiaDTO save(DiaDTO diaDTO) {
        log.debug("Request to save Dia : {}", diaDTO);
        Set<ItemDiaDTO> itemsDia = diaDTO.getItems();
        Dia dia = diaMapper.toEntity(diaDTO);
        for (ItemDiaDTO itemDTO : itemsDia) {
			ItemDia itemDia = itemDiaMapper.toEntity(itemDTO);
			dia.addItems(itemDia);
		}
        dia = diaRepository.save(dia);
        return diaMapper.toDto(dia);
    }

    /**
     * Get all the dias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dias");
        return diaRepository.findAll(pageable)
            .map(diaMapper::toDto);
    }


    /**
     * Get one dia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiaDTO> findOne(Long id) {
        log.debug("Request to get Dia : {}", id);
        return diaRepository.findById(id)
            .map(diaMapper::toDto);
    }

    /**
     * Delete the dia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dia : {}", id);
        diaRepository.deleteById(id);
    }
}

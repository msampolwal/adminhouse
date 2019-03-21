package com.marianowal.adminhouse.service.impl;

import com.marianowal.adminhouse.service.ItemDiaService;
import com.marianowal.adminhouse.domain.ItemDia;
import com.marianowal.adminhouse.repository.ItemDiaRepository;
import com.marianowal.adminhouse.service.dto.ItemDiaDTO;
import com.marianowal.adminhouse.service.mapper.ItemDiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ItemDia.
 */
@Service
@Transactional
public class ItemDiaServiceImpl implements ItemDiaService {

    private final Logger log = LoggerFactory.getLogger(ItemDiaServiceImpl.class);

    private final ItemDiaRepository itemDiaRepository;

    private final ItemDiaMapper itemDiaMapper;

    public ItemDiaServiceImpl(ItemDiaRepository itemDiaRepository, ItemDiaMapper itemDiaMapper) {
        this.itemDiaRepository = itemDiaRepository;
        this.itemDiaMapper = itemDiaMapper;
    }

    /**
     * Save a itemDia.
     *
     * @param itemDiaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemDiaDTO save(ItemDiaDTO itemDiaDTO) {
        log.debug("Request to save ItemDia : {}", itemDiaDTO);
        ItemDia itemDia = itemDiaMapper.toEntity(itemDiaDTO);
        itemDia = itemDiaRepository.save(itemDia);
        return itemDiaMapper.toDto(itemDia);
    }

    /**
     * Get all the itemDias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemDiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemDias");
        return itemDiaRepository.findAll(pageable)
            .map(itemDiaMapper::toDto);
    }


    /**
     * Get one itemDia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemDiaDTO> findOne(Long id) {
        log.debug("Request to get ItemDia : {}", id);
        return itemDiaRepository.findById(id)
            .map(itemDiaMapper::toDto);
    }

    /**
     * Delete the itemDia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemDia : {}", id);
        itemDiaRepository.deleteById(id);
    }
}

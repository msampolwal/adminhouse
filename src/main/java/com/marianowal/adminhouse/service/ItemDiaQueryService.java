package com.marianowal.adminhouse.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.marianowal.adminhouse.domain.ItemDia;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.ItemDiaRepository;
import com.marianowal.adminhouse.service.dto.ItemDiaCriteria;

import com.marianowal.adminhouse.service.dto.ItemDiaDTO;
import com.marianowal.adminhouse.service.mapper.ItemDiaMapper;

/**
 * Service for executing complex queries for ItemDia entities in the database.
 * The main input is a {@link ItemDiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemDiaDTO} or a {@link Page} of {@link ItemDiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemDiaQueryService extends QueryService<ItemDia> {

    private final Logger log = LoggerFactory.getLogger(ItemDiaQueryService.class);

    private final ItemDiaRepository itemDiaRepository;

    private final ItemDiaMapper itemDiaMapper;

    public ItemDiaQueryService(ItemDiaRepository itemDiaRepository, ItemDiaMapper itemDiaMapper) {
        this.itemDiaRepository = itemDiaRepository;
        this.itemDiaMapper = itemDiaMapper;
    }

    /**
     * Return a {@link List} of {@link ItemDiaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemDiaDTO> findByCriteria(ItemDiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemDia> specification = createSpecification(criteria);
        return itemDiaMapper.toDto(itemDiaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemDiaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemDiaDTO> findByCriteria(ItemDiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemDia> specification = createSpecification(criteria);
        return itemDiaRepository.findAll(specification, page)
            .map(itemDiaMapper::toDto);
    }

    /**
     * Function to convert ItemDiaCriteria to a {@link Specification}
     */
    private Specification<ItemDia> createSpecification(ItemDiaCriteria criteria) {
        Specification<ItemDia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ItemDia_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), ItemDia_.tipo));
            }
            if (criteria.getDiaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDiaId(), ItemDia_.dia, Dia_.id));
            }
            if (criteria.getComidaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getComidaId(), ItemDia_.comida, Comida_.id));
            }
        }
        return specification;
    }

}

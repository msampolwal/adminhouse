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

import com.marianowal.adminhouse.domain.Comida;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.ComidaRepository;
import com.marianowal.adminhouse.service.dto.ComidaCriteria;

import com.marianowal.adminhouse.service.dto.ComidaDTO;
import com.marianowal.adminhouse.service.mapper.ComidaMapper;

/**
 * Service for executing complex queries for Comida entities in the database.
 * The main input is a {@link ComidaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComidaDTO} or a {@link Page} of {@link ComidaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComidaQueryService extends QueryService<Comida> {

    private final Logger log = LoggerFactory.getLogger(ComidaQueryService.class);

    private final ComidaRepository comidaRepository;

    private final ComidaMapper comidaMapper;

    public ComidaQueryService(ComidaRepository comidaRepository, ComidaMapper comidaMapper) {
        this.comidaRepository = comidaRepository;
        this.comidaMapper = comidaMapper;
    }

    /**
     * Return a {@link List} of {@link ComidaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComidaDTO> findByCriteria(ComidaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Comida> specification = createSpecification(criteria);
        return comidaMapper.toDto(comidaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComidaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComidaDTO> findByCriteria(ComidaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Comida> specification = createSpecification(criteria);
        return comidaRepository.findAll(specification, page)
            .map(comidaMapper::toDto);
    }

    /**
     * Function to convert ComidaCriteria to a {@link Specification}
     */
    private Specification<Comida> createSpecification(ComidaCriteria criteria) {
        Specification<Comida> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Comida_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Comida_.nombre));
            }
            if (criteria.getIngredientesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getIngredientesId(), Comida_.ingredientes, Ingrediente_.id));
            }
        }
        return specification;
    }

}

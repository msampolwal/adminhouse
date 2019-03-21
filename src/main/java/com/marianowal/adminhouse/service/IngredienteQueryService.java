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

import com.marianowal.adminhouse.domain.Ingrediente;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.IngredienteRepository;
import com.marianowal.adminhouse.service.dto.IngredienteCriteria;

import com.marianowal.adminhouse.service.dto.IngredienteDTO;
import com.marianowal.adminhouse.service.mapper.IngredienteMapper;

/**
 * Service for executing complex queries for Ingrediente entities in the database.
 * The main input is a {@link IngredienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IngredienteDTO} or a {@link Page} of {@link IngredienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IngredienteQueryService extends QueryService<Ingrediente> {

    private final Logger log = LoggerFactory.getLogger(IngredienteQueryService.class);

    private final IngredienteRepository ingredienteRepository;

    private final IngredienteMapper ingredienteMapper;

    public IngredienteQueryService(IngredienteRepository ingredienteRepository, IngredienteMapper ingredienteMapper) {
        this.ingredienteRepository = ingredienteRepository;
        this.ingredienteMapper = ingredienteMapper;
    }

    /**
     * Return a {@link List} of {@link IngredienteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IngredienteDTO> findByCriteria(IngredienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ingrediente> specification = createSpecification(criteria);
        return ingredienteMapper.toDto(ingredienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IngredienteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IngredienteDTO> findByCriteria(IngredienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ingrediente> specification = createSpecification(criteria);
        return ingredienteRepository.findAll(specification, page)
            .map(ingredienteMapper::toDto);
    }

    /**
     * Function to convert IngredienteCriteria to a {@link Specification}
     */
    private Specification<Ingrediente> createSpecification(IngredienteCriteria criteria) {
        Specification<Ingrediente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ingrediente_.id));
            }
            if (criteria.getCantidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCantidad(), Ingrediente_.cantidad));
            }
            if (criteria.getComidaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getComidaId(), Ingrediente_.comida, Comida_.id));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProductoId(), Ingrediente_.producto, Producto_.id));
            }
        }
        return specification;
    }

}

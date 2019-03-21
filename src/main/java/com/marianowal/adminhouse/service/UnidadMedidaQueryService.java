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

import com.marianowal.adminhouse.domain.UnidadMedida;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.UnidadMedidaRepository;
import com.marianowal.adminhouse.service.dto.UnidadMedidaCriteria;

import com.marianowal.adminhouse.service.dto.UnidadMedidaDTO;
import com.marianowal.adminhouse.service.mapper.UnidadMedidaMapper;

/**
 * Service for executing complex queries for UnidadMedida entities in the database.
 * The main input is a {@link UnidadMedidaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UnidadMedidaDTO} or a {@link Page} of {@link UnidadMedidaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnidadMedidaQueryService extends QueryService<UnidadMedida> {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaQueryService.class);

    private final UnidadMedidaRepository unidadMedidaRepository;

    private final UnidadMedidaMapper unidadMedidaMapper;

    public UnidadMedidaQueryService(UnidadMedidaRepository unidadMedidaRepository, UnidadMedidaMapper unidadMedidaMapper) {
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.unidadMedidaMapper = unidadMedidaMapper;
    }

    /**
     * Return a {@link List} of {@link UnidadMedidaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UnidadMedidaDTO> findByCriteria(UnidadMedidaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UnidadMedida> specification = createSpecification(criteria);
        return unidadMedidaMapper.toDto(unidadMedidaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UnidadMedidaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadMedidaDTO> findByCriteria(UnidadMedidaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UnidadMedida> specification = createSpecification(criteria);
        return unidadMedidaRepository.findAll(specification, page)
            .map(unidadMedidaMapper::toDto);
    }

    /**
     * Function to convert UnidadMedidaCriteria to a {@link Specification}
     */
    private Specification<UnidadMedida> createSpecification(UnidadMedidaCriteria criteria) {
        Specification<UnidadMedida> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UnidadMedida_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), UnidadMedida_.nombre));
            }
        }
        return specification;
    }

}

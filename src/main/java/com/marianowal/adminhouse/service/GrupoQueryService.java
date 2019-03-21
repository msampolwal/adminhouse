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

import com.marianowal.adminhouse.domain.Grupo;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.GrupoRepository;
import com.marianowal.adminhouse.service.dto.GrupoCriteria;

import com.marianowal.adminhouse.service.dto.GrupoDTO;
import com.marianowal.adminhouse.service.mapper.GrupoMapper;

/**
 * Service for executing complex queries for Grupo entities in the database.
 * The main input is a {@link GrupoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GrupoDTO} or a {@link Page} of {@link GrupoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GrupoQueryService extends QueryService<Grupo> {

    private final Logger log = LoggerFactory.getLogger(GrupoQueryService.class);

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;

    public GrupoQueryService(GrupoRepository grupoRepository, GrupoMapper grupoMapper) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
    }

    /**
     * Return a {@link List} of {@link GrupoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoDTO> findByCriteria(GrupoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Grupo> specification = createSpecification(criteria);
        return grupoMapper.toDto(grupoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GrupoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoDTO> findByCriteria(GrupoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Grupo> specification = createSpecification(criteria);
        return grupoRepository.findAll(specification, page)
            .map(grupoMapper::toDto);
    }

    /**
     * Function to convert GrupoCriteria to a {@link Specification}
     */
    private Specification<Grupo> createSpecification(GrupoCriteria criteria) {
        Specification<Grupo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Grupo_.id));
            }
            if (criteria.getCalendarioComidasId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCalendarioComidasId(), Grupo_.calendarioComidas, CalendarioComida_.id));
            }
            if (criteria.getDiasId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDiasId(), Grupo_.dias, Dia_.id));
            }
            if (criteria.getUsersId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUsersId(), Grupo_.users, User_.id));
            }
        }
        return specification;
    }

}

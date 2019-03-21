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

import com.marianowal.adminhouse.domain.CalendarioComida;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.CalendarioComidaRepository;
import com.marianowal.adminhouse.service.dto.CalendarioComidaCriteria;

import com.marianowal.adminhouse.service.dto.CalendarioComidaDTO;
import com.marianowal.adminhouse.service.mapper.CalendarioComidaMapper;

/**
 * Service for executing complex queries for CalendarioComida entities in the database.
 * The main input is a {@link CalendarioComidaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CalendarioComidaDTO} or a {@link Page} of {@link CalendarioComidaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CalendarioComidaQueryService extends QueryService<CalendarioComida> {

    private final Logger log = LoggerFactory.getLogger(CalendarioComidaQueryService.class);

    private final CalendarioComidaRepository calendarioComidaRepository;

    private final CalendarioComidaMapper calendarioComidaMapper;

    public CalendarioComidaQueryService(CalendarioComidaRepository calendarioComidaRepository, CalendarioComidaMapper calendarioComidaMapper) {
        this.calendarioComidaRepository = calendarioComidaRepository;
        this.calendarioComidaMapper = calendarioComidaMapper;
    }

    /**
     * Return a {@link List} of {@link CalendarioComidaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CalendarioComidaDTO> findByCriteria(CalendarioComidaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CalendarioComida> specification = createSpecification(criteria);
        return calendarioComidaMapper.toDto(calendarioComidaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CalendarioComidaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CalendarioComidaDTO> findByCriteria(CalendarioComidaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CalendarioComida> specification = createSpecification(criteria);
        return calendarioComidaRepository.findAll(specification, page)
            .map(calendarioComidaMapper::toDto);
    }

    /**
     * Function to convert CalendarioComidaCriteria to a {@link Specification}
     */
    private Specification<CalendarioComida> createSpecification(CalendarioComidaCriteria criteria) {
        Specification<CalendarioComida> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CalendarioComida_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), CalendarioComida_.fecha));
            }
            if (criteria.getGrupoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGrupoId(), CalendarioComida_.grupo, Grupo_.id));
            }
            if (criteria.getDiaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDiaId(), CalendarioComida_.dia, Dia_.id));
            }
        }
        return specification;
    }

}

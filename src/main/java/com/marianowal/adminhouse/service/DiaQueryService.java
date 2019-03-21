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

import com.marianowal.adminhouse.domain.Dia;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.DiaRepository;
import com.marianowal.adminhouse.service.dto.DiaCriteria;

import com.marianowal.adminhouse.service.dto.DiaDTO;
import com.marianowal.adminhouse.service.mapper.DiaMapper;

/**
 * Service for executing complex queries for Dia entities in the database.
 * The main input is a {@link DiaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiaDTO} or a {@link Page} of {@link DiaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiaQueryService extends QueryService<Dia> {

    private final Logger log = LoggerFactory.getLogger(DiaQueryService.class);

    private final DiaRepository diaRepository;

    private final DiaMapper diaMapper;

    public DiaQueryService(DiaRepository diaRepository, DiaMapper diaMapper) {
        this.diaRepository = diaRepository;
        this.diaMapper = diaMapper;
    }

    /**
     * Return a {@link List} of {@link DiaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiaDTO> findByCriteria(DiaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dia> specification = createSpecification(criteria);
        return diaMapper.toDto(diaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiaDTO> findByCriteria(DiaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dia> specification = createSpecification(criteria);
        return diaRepository.findAll(specification, page)
            .map(diaMapper::toDto);
    }

    /**
     * Function to convert DiaCriteria to a {@link Specification}
     */
    private Specification<Dia> createSpecification(DiaCriteria criteria) {
        Specification<Dia> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Dia_.id));
            }
            if (criteria.getDiaSemana() != null) {
                specification = specification.and(buildSpecification(criteria.getDiaSemana(), Dia_.diaSemana));
            }
            if (criteria.getGrupoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getGrupoId(), Dia_.grupo, Grupo_.id));
            }
            if (criteria.getItemsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getItemsId(), Dia_.items, ItemDia_.id));
            }
        }
        return specification;
    }

}

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

import com.marianowal.adminhouse.domain.PrecioProducto;
import com.marianowal.adminhouse.domain.*; // for static metamodels
import com.marianowal.adminhouse.repository.PrecioProductoRepository;
import com.marianowal.adminhouse.service.dto.PrecioProductoCriteria;

import com.marianowal.adminhouse.service.dto.PrecioProductoDTO;
import com.marianowal.adminhouse.service.mapper.PrecioProductoMapper;

/**
 * Service for executing complex queries for PrecioProducto entities in the database.
 * The main input is a {@link PrecioProductoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrecioProductoDTO} or a {@link Page} of {@link PrecioProductoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrecioProductoQueryService extends QueryService<PrecioProducto> {

    private final Logger log = LoggerFactory.getLogger(PrecioProductoQueryService.class);

    private final PrecioProductoRepository precioProductoRepository;

    private final PrecioProductoMapper precioProductoMapper;

    public PrecioProductoQueryService(PrecioProductoRepository precioProductoRepository, PrecioProductoMapper precioProductoMapper) {
        this.precioProductoRepository = precioProductoRepository;
        this.precioProductoMapper = precioProductoMapper;
    }

    /**
     * Return a {@link List} of {@link PrecioProductoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrecioProductoDTO> findByCriteria(PrecioProductoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrecioProducto> specification = createSpecification(criteria);
        return precioProductoMapper.toDto(precioProductoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrecioProductoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrecioProductoDTO> findByCriteria(PrecioProductoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrecioProducto> specification = createSpecification(criteria);
        return precioProductoRepository.findAll(specification, page)
            .map(precioProductoMapper::toDto);
    }

    /**
     * Function to convert PrecioProductoCriteria to a {@link Specification}
     */
    private Specification<PrecioProducto> createSpecification(PrecioProductoCriteria criteria) {
        Specification<PrecioProducto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrecioProducto_.id));
            }
            if (criteria.getPrecio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrecio(), PrecioProducto_.precio));
            }
            if (criteria.getCantidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCantidad(), PrecioProducto_.cantidad));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProductoId(), PrecioProducto_.producto, Producto_.id));
            }
            if (criteria.getUnidadMedidaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUnidadMedidaId(), PrecioProducto_.unidadMedida, UnidadMedida_.id));
            }
        }
        return specification;
    }

}

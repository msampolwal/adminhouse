package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.PrecioProductoService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.PrecioProductoDTO;
import com.marianowal.adminhouse.service.dto.PrecioProductoCriteria;
import com.marianowal.adminhouse.service.PrecioProductoQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PrecioProducto.
 */
@RestController
@RequestMapping("/api")
public class PrecioProductoResource {

    private final Logger log = LoggerFactory.getLogger(PrecioProductoResource.class);

    private static final String ENTITY_NAME = "precioProducto";

    private final PrecioProductoService precioProductoService;

    private final PrecioProductoQueryService precioProductoQueryService;

    public PrecioProductoResource(PrecioProductoService precioProductoService, PrecioProductoQueryService precioProductoQueryService) {
        this.precioProductoService = precioProductoService;
        this.precioProductoQueryService = precioProductoQueryService;
    }

    /**
     * POST  /precio-productos : Create a new precioProducto.
     *
     * @param precioProductoDTO the precioProductoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new precioProductoDTO, or with status 400 (Bad Request) if the precioProducto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/precio-productos")
    @Timed
    public ResponseEntity<PrecioProductoDTO> createPrecioProducto(@Valid @RequestBody PrecioProductoDTO precioProductoDTO) throws URISyntaxException {
        log.debug("REST request to save PrecioProducto : {}", precioProductoDTO);
        if (precioProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new precioProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecioProductoDTO result = precioProductoService.save(precioProductoDTO);
        return ResponseEntity.created(new URI("/api/precio-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /precio-productos : Updates an existing precioProducto.
     *
     * @param precioProductoDTO the precioProductoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated precioProductoDTO,
     * or with status 400 (Bad Request) if the precioProductoDTO is not valid,
     * or with status 500 (Internal Server Error) if the precioProductoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/precio-productos")
    @Timed
    public ResponseEntity<PrecioProductoDTO> updatePrecioProducto(@Valid @RequestBody PrecioProductoDTO precioProductoDTO) throws URISyntaxException {
        log.debug("REST request to update PrecioProducto : {}", precioProductoDTO);
        if (precioProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrecioProductoDTO result = precioProductoService.save(precioProductoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, precioProductoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /precio-productos : get all the precioProductos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of precioProductos in body
     */
    @GetMapping("/precio-productos")
    @Timed
    public ResponseEntity<List<PrecioProductoDTO>> getAllPrecioProductos(PrecioProductoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PrecioProductos by criteria: {}", criteria);
        Page<PrecioProductoDTO> page = precioProductoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/precio-productos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /precio-productos/:id : get the "id" precioProducto.
     *
     * @param id the id of the precioProductoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the precioProductoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/precio-productos/{id}")
    @Timed
    public ResponseEntity<PrecioProductoDTO> getPrecioProducto(@PathVariable Long id) {
        log.debug("REST request to get PrecioProducto : {}", id);
        Optional<PrecioProductoDTO> precioProductoDTO = precioProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(precioProductoDTO);
    }

    /**
     * DELETE  /precio-productos/:id : delete the "id" precioProducto.
     *
     * @param id the id of the precioProductoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/precio-productos/{id}")
    @Timed
    public ResponseEntity<Void> deletePrecioProducto(@PathVariable Long id) {
        log.debug("REST request to delete PrecioProducto : {}", id);
        precioProductoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

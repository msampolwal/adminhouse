package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.UnidadMedidaService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.UnidadMedidaDTO;
import com.marianowal.adminhouse.service.dto.UnidadMedidaCriteria;
import com.marianowal.adminhouse.service.UnidadMedidaQueryService;
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
 * REST controller for managing UnidadMedida.
 */
@RestController
@RequestMapping("/api")
public class UnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaResource.class);

    private static final String ENTITY_NAME = "unidadMedida";

    private final UnidadMedidaService unidadMedidaService;

    private final UnidadMedidaQueryService unidadMedidaQueryService;

    public UnidadMedidaResource(UnidadMedidaService unidadMedidaService, UnidadMedidaQueryService unidadMedidaQueryService) {
        this.unidadMedidaService = unidadMedidaService;
        this.unidadMedidaQueryService = unidadMedidaQueryService;
    }

    /**
     * POST  /unidad-medidas : Create a new unidadMedida.
     *
     * @param unidadMedidaDTO the unidadMedidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidadMedidaDTO, or with status 400 (Bad Request) if the unidadMedida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidad-medidas")
    @Timed
    public ResponseEntity<UnidadMedidaDTO> createUnidadMedida(@Valid @RequestBody UnidadMedidaDTO unidadMedidaDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadMedida : {}", unidadMedidaDTO);
        if (unidadMedidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadMedidaDTO result = unidadMedidaService.save(unidadMedidaDTO);
        return ResponseEntity.created(new URI("/api/unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidad-medidas : Updates an existing unidadMedida.
     *
     * @param unidadMedidaDTO the unidadMedidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidadMedidaDTO,
     * or with status 400 (Bad Request) if the unidadMedidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the unidadMedidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidad-medidas")
    @Timed
    public ResponseEntity<UnidadMedidaDTO> updateUnidadMedida(@Valid @RequestBody UnidadMedidaDTO unidadMedidaDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadMedida : {}", unidadMedidaDTO);
        if (unidadMedidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadMedidaDTO result = unidadMedidaService.save(unidadMedidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidadMedidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidad-medidas : get all the unidadMedidas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of unidadMedidas in body
     */
    @GetMapping("/unidad-medidas")
    @Timed
    public ResponseEntity<List<UnidadMedidaDTO>> getAllUnidadMedidas(UnidadMedidaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UnidadMedidas by criteria: {}", criteria);
        Page<UnidadMedidaDTO> page = unidadMedidaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unidad-medidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unidad-medidas/:id : get the "id" unidadMedida.
     *
     * @param id the id of the unidadMedidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidadMedidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/unidad-medidas/{id}")
    @Timed
    public ResponseEntity<UnidadMedidaDTO> getUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadMedida : {}", id);
        Optional<UnidadMedidaDTO> unidadMedidaDTO = unidadMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadMedidaDTO);
    }

    /**
     * DELETE  /unidad-medidas/:id : delete the "id" unidadMedida.
     *
     * @param id the id of the unidadMedidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unidad-medidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadMedida : {}", id);
        unidadMedidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

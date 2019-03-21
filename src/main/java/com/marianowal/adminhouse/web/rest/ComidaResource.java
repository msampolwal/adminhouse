package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.ComidaService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.ComidaDTO;
import com.marianowal.adminhouse.service.dto.ComidaCriteria;
import com.marianowal.adminhouse.service.ComidaQueryService;
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
 * REST controller for managing Comida.
 */
@RestController
@RequestMapping("/api")
public class ComidaResource {

    private final Logger log = LoggerFactory.getLogger(ComidaResource.class);

    private static final String ENTITY_NAME = "comida";

    private final ComidaService comidaService;

    private final ComidaQueryService comidaQueryService;

    public ComidaResource(ComidaService comidaService, ComidaQueryService comidaQueryService) {
        this.comidaService = comidaService;
        this.comidaQueryService = comidaQueryService;
    }

    /**
     * POST  /comidas : Create a new comida.
     *
     * @param comidaDTO the comidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comidaDTO, or with status 400 (Bad Request) if the comida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comidas")
    @Timed
    public ResponseEntity<ComidaDTO> createComida(@Valid @RequestBody ComidaDTO comidaDTO) throws URISyntaxException {
        log.debug("REST request to save Comida : {}", comidaDTO);
        if (comidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new comida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComidaDTO result = comidaService.save(comidaDTO);
        return ResponseEntity.created(new URI("/api/comidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comidas : Updates an existing comida.
     *
     * @param comidaDTO the comidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comidaDTO,
     * or with status 400 (Bad Request) if the comidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the comidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comidas")
    @Timed
    public ResponseEntity<ComidaDTO> updateComida(@Valid @RequestBody ComidaDTO comidaDTO) throws URISyntaxException {
        log.debug("REST request to update Comida : {}", comidaDTO);
        if (comidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComidaDTO result = comidaService.save(comidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comidas : get all the comidas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of comidas in body
     */
    @GetMapping("/comidas")
    @Timed
    public ResponseEntity<List<ComidaDTO>> getAllComidas(ComidaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Comidas by criteria: {}", criteria);
        Page<ComidaDTO> page = comidaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comidas/:id : get the "id" comida.
     *
     * @param id the id of the comidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comidas/{id}")
    @Timed
    public ResponseEntity<ComidaDTO> getComida(@PathVariable Long id) {
        log.debug("REST request to get Comida : {}", id);
        Optional<ComidaDTO> comidaDTO = comidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comidaDTO);
    }

    /**
     * DELETE  /comidas/:id : delete the "id" comida.
     *
     * @param id the id of the comidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteComida(@PathVariable Long id) {
        log.debug("REST request to delete Comida : {}", id);
        comidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

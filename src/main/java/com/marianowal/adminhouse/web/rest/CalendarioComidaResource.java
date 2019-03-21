package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.CalendarioComidaService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.CalendarioComidaDTO;
import com.marianowal.adminhouse.service.dto.CalendarioComidaCriteria;
import com.marianowal.adminhouse.service.CalendarioComidaQueryService;
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
 * REST controller for managing CalendarioComida.
 */
@RestController
@RequestMapping("/api")
public class CalendarioComidaResource {

    private final Logger log = LoggerFactory.getLogger(CalendarioComidaResource.class);

    private static final String ENTITY_NAME = "calendarioComida";

    private final CalendarioComidaService calendarioComidaService;

    private final CalendarioComidaQueryService calendarioComidaQueryService;

    public CalendarioComidaResource(CalendarioComidaService calendarioComidaService, CalendarioComidaQueryService calendarioComidaQueryService) {
        this.calendarioComidaService = calendarioComidaService;
        this.calendarioComidaQueryService = calendarioComidaQueryService;
    }

    /**
     * POST  /calendario-comidas : Create a new calendarioComida.
     *
     * @param calendarioComidaDTO the calendarioComidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendarioComidaDTO, or with status 400 (Bad Request) if the calendarioComida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendario-comidas")
    @Timed
    public ResponseEntity<CalendarioComidaDTO> createCalendarioComida(@Valid @RequestBody CalendarioComidaDTO calendarioComidaDTO) throws URISyntaxException {
        log.debug("REST request to save CalendarioComida : {}", calendarioComidaDTO);
        if (calendarioComidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new calendarioComida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalendarioComidaDTO result = calendarioComidaService.save(calendarioComidaDTO);
        return ResponseEntity.created(new URI("/api/calendario-comidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendario-comidas : Updates an existing calendarioComida.
     *
     * @param calendarioComidaDTO the calendarioComidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendarioComidaDTO,
     * or with status 400 (Bad Request) if the calendarioComidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the calendarioComidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendario-comidas")
    @Timed
    public ResponseEntity<CalendarioComidaDTO> updateCalendarioComida(@Valid @RequestBody CalendarioComidaDTO calendarioComidaDTO) throws URISyntaxException {
        log.debug("REST request to update CalendarioComida : {}", calendarioComidaDTO);
        if (calendarioComidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalendarioComidaDTO result = calendarioComidaService.save(calendarioComidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calendarioComidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendario-comidas : get all the calendarioComidas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of calendarioComidas in body
     */
    @GetMapping("/calendario-comidas")
    @Timed
    public ResponseEntity<List<CalendarioComidaDTO>> getAllCalendarioComidas(CalendarioComidaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CalendarioComidas by criteria: {}", criteria);
        Page<CalendarioComidaDTO> page = calendarioComidaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/calendario-comidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /calendario-comidas/:id : get the "id" calendarioComida.
     *
     * @param id the id of the calendarioComidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendarioComidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/calendario-comidas/{id}")
    @Timed
    public ResponseEntity<CalendarioComidaDTO> getCalendarioComida(@PathVariable Long id) {
        log.debug("REST request to get CalendarioComida : {}", id);
        Optional<CalendarioComidaDTO> calendarioComidaDTO = calendarioComidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarioComidaDTO);
    }

    /**
     * DELETE  /calendario-comidas/:id : delete the "id" calendarioComida.
     *
     * @param id the id of the calendarioComidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendario-comidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendarioComida(@PathVariable Long id) {
        log.debug("REST request to delete CalendarioComida : {}", id);
        calendarioComidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

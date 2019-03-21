package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.DiaService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.DiaDTO;
import com.marianowal.adminhouse.service.dto.DiaCriteria;
import com.marianowal.adminhouse.service.DiaQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dia.
 */
@RestController
@RequestMapping("/api")
public class DiaResource {

    private final Logger log = LoggerFactory.getLogger(DiaResource.class);

    private static final String ENTITY_NAME = "dia";

    private final DiaService diaService;

    private final DiaQueryService diaQueryService;

    public DiaResource(DiaService diaService, DiaQueryService diaQueryService) {
        this.diaService = diaService;
        this.diaQueryService = diaQueryService;
    }

    /**
     * POST  /dias : Create a new dia.
     *
     * @param diaDTO the diaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diaDTO, or with status 400 (Bad Request) if the dia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dias")
    @Timed
    public ResponseEntity<DiaDTO> createDia(@RequestBody DiaDTO diaDTO) throws URISyntaxException {
        log.debug("REST request to save Dia : {}", diaDTO);
        if (diaDTO.getId() != null) {
            throw new BadRequestAlertException("A new dia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaDTO result = diaService.save(diaDTO);
        return ResponseEntity.created(new URI("/api/dias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dias : Updates an existing dia.
     *
     * @param diaDTO the diaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diaDTO,
     * or with status 400 (Bad Request) if the diaDTO is not valid,
     * or with status 500 (Internal Server Error) if the diaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dias")
    @Timed
    public ResponseEntity<DiaDTO> updateDia(@RequestBody DiaDTO diaDTO) throws URISyntaxException {
        log.debug("REST request to update Dia : {}", diaDTO);
        if (diaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiaDTO result = diaService.save(diaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dias : get all the dias.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dias in body
     */
    @GetMapping("/dias")
    @Timed
    public ResponseEntity<List<DiaDTO>> getAllDias(DiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Dias by criteria: {}", criteria);
        Page<DiaDTO> page = diaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dias/:id : get the "id" dia.
     *
     * @param id the id of the diaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dias/{id}")
    @Timed
    public ResponseEntity<DiaDTO> getDia(@PathVariable Long id) {
        log.debug("REST request to get Dia : {}", id);
        Optional<DiaDTO> diaDTO = diaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diaDTO);
    }

    /**
     * DELETE  /dias/:id : delete the "id" dia.
     *
     * @param id the id of the diaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dias/{id}")
    @Timed
    public ResponseEntity<Void> deleteDia(@PathVariable Long id) {
        log.debug("REST request to delete Dia : {}", id);
        diaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.GrupoService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.GrupoDTO;
import com.marianowal.adminhouse.service.dto.GrupoCriteria;
import com.marianowal.adminhouse.service.GrupoQueryService;
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
 * REST controller for managing Grupo.
 */
@RestController
@RequestMapping("/api")
public class GrupoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoResource.class);

    private static final String ENTITY_NAME = "grupo";

    private final GrupoService grupoService;

    private final GrupoQueryService grupoQueryService;

    public GrupoResource(GrupoService grupoService, GrupoQueryService grupoQueryService) {
        this.grupoService = grupoService;
        this.grupoQueryService = grupoQueryService;
    }

    /**
     * POST  /grupos : Create a new grupo.
     *
     * @param grupoDTO the grupoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grupoDTO, or with status 400 (Bad Request) if the grupo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grupos")
    @Timed
    public ResponseEntity<GrupoDTO> createGrupo(@RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        log.debug("REST request to save Grupo : {}", grupoDTO);
        if (grupoDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoDTO result = grupoService.save(grupoDTO);
        return ResponseEntity.created(new URI("/api/grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grupos : Updates an existing grupo.
     *
     * @param grupoDTO the grupoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grupoDTO,
     * or with status 400 (Bad Request) if the grupoDTO is not valid,
     * or with status 500 (Internal Server Error) if the grupoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grupos")
    @Timed
    public ResponseEntity<GrupoDTO> updateGrupo(@RequestBody GrupoDTO grupoDTO) throws URISyntaxException {
        log.debug("REST request to update Grupo : {}", grupoDTO);
        if (grupoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoDTO result = grupoService.save(grupoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grupoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grupos : get all the grupos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of grupos in body
     */
    @GetMapping("/grupos")
    @Timed
    public ResponseEntity<List<GrupoDTO>> getAllGrupos(GrupoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Grupos by criteria: {}", criteria);
        Page<GrupoDTO> page = grupoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grupos/:id : get the "id" grupo.
     *
     * @param id the id of the grupoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grupoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/grupos/{id}")
    @Timed
    public ResponseEntity<GrupoDTO> getGrupo(@PathVariable Long id) {
        log.debug("REST request to get Grupo : {}", id);
        Optional<GrupoDTO> grupoDTO = grupoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoDTO);
    }

    /**
     * DELETE  /grupos/:id : delete the "id" grupo.
     *
     * @param id the id of the grupoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grupos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        log.debug("REST request to delete Grupo : {}", id);
        grupoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

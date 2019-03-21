package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.IngredienteService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.IngredienteDTO;
import com.marianowal.adminhouse.service.dto.IngredienteCriteria;
import com.marianowal.adminhouse.service.IngredienteQueryService;
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
 * REST controller for managing Ingrediente.
 */
@RestController
@RequestMapping("/api")
public class IngredienteResource {

    private final Logger log = LoggerFactory.getLogger(IngredienteResource.class);

    private static final String ENTITY_NAME = "ingrediente";

    private final IngredienteService ingredienteService;

    private final IngredienteQueryService ingredienteQueryService;

    public IngredienteResource(IngredienteService ingredienteService, IngredienteQueryService ingredienteQueryService) {
        this.ingredienteService = ingredienteService;
        this.ingredienteQueryService = ingredienteQueryService;
    }

    /**
     * POST  /ingredientes : Create a new ingrediente.
     *
     * @param ingredienteDTO the ingredienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredienteDTO, or with status 400 (Bad Request) if the ingrediente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredientes")
    @Timed
    public ResponseEntity<IngredienteDTO> createIngrediente(@Valid @RequestBody IngredienteDTO ingredienteDTO) throws URISyntaxException {
        log.debug("REST request to save Ingrediente : {}", ingredienteDTO);
        if (ingredienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingrediente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredienteDTO result = ingredienteService.save(ingredienteDTO);
        return ResponseEntity.created(new URI("/api/ingredientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredientes : Updates an existing ingrediente.
     *
     * @param ingredienteDTO the ingredienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredienteDTO,
     * or with status 400 (Bad Request) if the ingredienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the ingredienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredientes")
    @Timed
    public ResponseEntity<IngredienteDTO> updateIngrediente(@Valid @RequestBody IngredienteDTO ingredienteDTO) throws URISyntaxException {
        log.debug("REST request to update Ingrediente : {}", ingredienteDTO);
        if (ingredienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredienteDTO result = ingredienteService.save(ingredienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredientes : get all the ingredientes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientes in body
     */
    @GetMapping("/ingredientes")
    @Timed
    public ResponseEntity<List<IngredienteDTO>> getAllIngredientes(IngredienteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ingredientes by criteria: {}", criteria);
        Page<IngredienteDTO> page = ingredienteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ingredientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ingredientes/:id : get the "id" ingrediente.
     *
     * @param id the id of the ingredienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ingredientes/{id}")
    @Timed
    public ResponseEntity<IngredienteDTO> getIngrediente(@PathVariable Long id) {
        log.debug("REST request to get Ingrediente : {}", id);
        Optional<IngredienteDTO> ingredienteDTO = ingredienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredienteDTO);
    }

    /**
     * DELETE  /ingredientes/:id : delete the "id" ingrediente.
     *
     * @param id the id of the ingredienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngrediente(@PathVariable Long id) {
        log.debug("REST request to delete Ingrediente : {}", id);
        ingredienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

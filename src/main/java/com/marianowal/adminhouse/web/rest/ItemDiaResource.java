package com.marianowal.adminhouse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.marianowal.adminhouse.service.ItemDiaService;
import com.marianowal.adminhouse.web.rest.errors.BadRequestAlertException;
import com.marianowal.adminhouse.web.rest.util.HeaderUtil;
import com.marianowal.adminhouse.web.rest.util.PaginationUtil;
import com.marianowal.adminhouse.service.dto.ItemDiaDTO;
import com.marianowal.adminhouse.service.dto.ItemDiaCriteria;
import com.marianowal.adminhouse.service.ItemDiaQueryService;
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
 * REST controller for managing ItemDia.
 */
@RestController
@RequestMapping("/api")
public class ItemDiaResource {

    private final Logger log = LoggerFactory.getLogger(ItemDiaResource.class);

    private static final String ENTITY_NAME = "itemDia";

    private final ItemDiaService itemDiaService;

    private final ItemDiaQueryService itemDiaQueryService;

    public ItemDiaResource(ItemDiaService itemDiaService, ItemDiaQueryService itemDiaQueryService) {
        this.itemDiaService = itemDiaService;
        this.itemDiaQueryService = itemDiaQueryService;
    }

    /**
     * POST  /item-dias : Create a new itemDia.
     *
     * @param itemDiaDTO the itemDiaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemDiaDTO, or with status 400 (Bad Request) if the itemDia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-dias")
    @Timed
    public ResponseEntity<ItemDiaDTO> createItemDia(@RequestBody ItemDiaDTO itemDiaDTO) throws URISyntaxException {
        log.debug("REST request to save ItemDia : {}", itemDiaDTO);
        if (itemDiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemDia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemDiaDTO result = itemDiaService.save(itemDiaDTO);
        return ResponseEntity.created(new URI("/api/item-dias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-dias : Updates an existing itemDia.
     *
     * @param itemDiaDTO the itemDiaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemDiaDTO,
     * or with status 400 (Bad Request) if the itemDiaDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemDiaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-dias")
    @Timed
    public ResponseEntity<ItemDiaDTO> updateItemDia(@RequestBody ItemDiaDTO itemDiaDTO) throws URISyntaxException {
        log.debug("REST request to update ItemDia : {}", itemDiaDTO);
        if (itemDiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemDiaDTO result = itemDiaService.save(itemDiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemDiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-dias : get all the itemDias.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemDias in body
     */
    @GetMapping("/item-dias")
    @Timed
    public ResponseEntity<List<ItemDiaDTO>> getAllItemDias(ItemDiaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemDias by criteria: {}", criteria);
        Page<ItemDiaDTO> page = itemDiaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-dias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /item-dias/:id : get the "id" itemDia.
     *
     * @param id the id of the itemDiaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemDiaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-dias/{id}")
    @Timed
    public ResponseEntity<ItemDiaDTO> getItemDia(@PathVariable Long id) {
        log.debug("REST request to get ItemDia : {}", id);
        Optional<ItemDiaDTO> itemDiaDTO = itemDiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemDiaDTO);
    }

    /**
     * DELETE  /item-dias/:id : delete the "id" itemDia.
     *
     * @param id the id of the itemDiaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-dias/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemDia(@PathVariable Long id) {
        log.debug("REST request to delete ItemDia : {}", id);
        itemDiaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

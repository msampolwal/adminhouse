package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.ItemDia;
import com.marianowal.adminhouse.domain.Dia;
import com.marianowal.adminhouse.domain.Comida;
import com.marianowal.adminhouse.repository.ItemDiaRepository;
import com.marianowal.adminhouse.service.ItemDiaService;
import com.marianowal.adminhouse.service.dto.ItemDiaDTO;
import com.marianowal.adminhouse.service.mapper.ItemDiaMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.ItemDiaCriteria;
import com.marianowal.adminhouse.service.ItemDiaQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.marianowal.adminhouse.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.marianowal.adminhouse.domain.enumeration.TipoComida;
/**
 * Test class for the ItemDiaResource REST controller.
 *
 * @see ItemDiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class ItemDiaResourceIntTest {

    private static final TipoComida DEFAULT_TIPO = TipoComida.DESAYUNO;
    private static final TipoComida UPDATED_TIPO = TipoComida.ALMUERZO;

    @Autowired
    private ItemDiaRepository itemDiaRepository;


    @Autowired
    private ItemDiaMapper itemDiaMapper;
    

    @Autowired
    private ItemDiaService itemDiaService;

    @Autowired
    private ItemDiaQueryService itemDiaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restItemDiaMockMvc;

    private ItemDia itemDia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemDiaResource itemDiaResource = new ItemDiaResource(itemDiaService, itemDiaQueryService);
        this.restItemDiaMockMvc = MockMvcBuilders.standaloneSetup(itemDiaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemDia createEntity(EntityManager em) {
        ItemDia itemDia = new ItemDia()
            .tipo(DEFAULT_TIPO);
        return itemDia;
    }

    @Before
    public void initTest() {
        itemDia = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemDia() throws Exception {
        int databaseSizeBeforeCreate = itemDiaRepository.findAll().size();

        // Create the ItemDia
        ItemDiaDTO itemDiaDTO = itemDiaMapper.toDto(itemDia);
        restItemDiaMockMvc.perform(post("/api/item-dias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDiaDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemDia in the database
        List<ItemDia> itemDiaList = itemDiaRepository.findAll();
        assertThat(itemDiaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemDia testItemDia = itemDiaList.get(itemDiaList.size() - 1);
        assertThat(testItemDia.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createItemDiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemDiaRepository.findAll().size();

        // Create the ItemDia with an existing ID
        itemDia.setId(1L);
        ItemDiaDTO itemDiaDTO = itemDiaMapper.toDto(itemDia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemDiaMockMvc.perform(post("/api/item-dias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemDia in the database
        List<ItemDia> itemDiaList = itemDiaRepository.findAll();
        assertThat(itemDiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllItemDias() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        // Get all the itemDiaList
        restItemDiaMockMvc.perform(get("/api/item-dias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemDia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    

    @Test
    @Transactional
    public void getItemDia() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        // Get the itemDia
        restItemDiaMockMvc.perform(get("/api/item-dias/{id}", itemDia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemDia.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getAllItemDiasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        // Get all the itemDiaList where tipo equals to DEFAULT_TIPO
        defaultItemDiaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the itemDiaList where tipo equals to UPDATED_TIPO
        defaultItemDiaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllItemDiasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        // Get all the itemDiaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultItemDiaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the itemDiaList where tipo equals to UPDATED_TIPO
        defaultItemDiaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllItemDiasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        // Get all the itemDiaList where tipo is not null
        defaultItemDiaShouldBeFound("tipo.specified=true");

        // Get all the itemDiaList where tipo is null
        defaultItemDiaShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemDiasByDiaIsEqualToSomething() throws Exception {
        // Initialize the database
        Dia dia = DiaResourceIntTest.createEntity(em);
        em.persist(dia);
        em.flush();
        itemDia.setDia(dia);
        itemDiaRepository.saveAndFlush(itemDia);
        Long diaId = dia.getId();

        // Get all the itemDiaList where dia equals to diaId
        defaultItemDiaShouldBeFound("diaId.equals=" + diaId);

        // Get all the itemDiaList where dia equals to diaId + 1
        defaultItemDiaShouldNotBeFound("diaId.equals=" + (diaId + 1));
    }


    @Test
    @Transactional
    public void getAllItemDiasByComidaIsEqualToSomething() throws Exception {
        // Initialize the database
        Comida comida = ComidaResourceIntTest.createEntity(em);
        em.persist(comida);
        em.flush();
        itemDia.setComida(comida);
        itemDiaRepository.saveAndFlush(itemDia);
        Long comidaId = comida.getId();

        // Get all the itemDiaList where comida equals to comidaId
        defaultItemDiaShouldBeFound("comidaId.equals=" + comidaId);

        // Get all the itemDiaList where comida equals to comidaId + 1
        defaultItemDiaShouldNotBeFound("comidaId.equals=" + (comidaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemDiaShouldBeFound(String filter) throws Exception {
        restItemDiaMockMvc.perform(get("/api/item-dias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemDia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemDiaShouldNotBeFound(String filter) throws Exception {
        restItemDiaMockMvc.perform(get("/api/item-dias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingItemDia() throws Exception {
        // Get the itemDia
        restItemDiaMockMvc.perform(get("/api/item-dias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemDia() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        int databaseSizeBeforeUpdate = itemDiaRepository.findAll().size();

        // Update the itemDia
        ItemDia updatedItemDia = itemDiaRepository.findById(itemDia.getId()).get();
        // Disconnect from session so that the updates on updatedItemDia are not directly saved in db
        em.detach(updatedItemDia);
        updatedItemDia
            .tipo(UPDATED_TIPO);
        ItemDiaDTO itemDiaDTO = itemDiaMapper.toDto(updatedItemDia);

        restItemDiaMockMvc.perform(put("/api/item-dias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDiaDTO)))
            .andExpect(status().isOk());

        // Validate the ItemDia in the database
        List<ItemDia> itemDiaList = itemDiaRepository.findAll();
        assertThat(itemDiaList).hasSize(databaseSizeBeforeUpdate);
        ItemDia testItemDia = itemDiaList.get(itemDiaList.size() - 1);
        assertThat(testItemDia.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingItemDia() throws Exception {
        int databaseSizeBeforeUpdate = itemDiaRepository.findAll().size();

        // Create the ItemDia
        ItemDiaDTO itemDiaDTO = itemDiaMapper.toDto(itemDia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItemDiaMockMvc.perform(put("/api/item-dias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemDiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemDia in the database
        List<ItemDia> itemDiaList = itemDiaRepository.findAll();
        assertThat(itemDiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemDia() throws Exception {
        // Initialize the database
        itemDiaRepository.saveAndFlush(itemDia);

        int databaseSizeBeforeDelete = itemDiaRepository.findAll().size();

        // Get the itemDia
        restItemDiaMockMvc.perform(delete("/api/item-dias/{id}", itemDia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemDia> itemDiaList = itemDiaRepository.findAll();
        assertThat(itemDiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemDia.class);
        ItemDia itemDia1 = new ItemDia();
        itemDia1.setId(1L);
        ItemDia itemDia2 = new ItemDia();
        itemDia2.setId(itemDia1.getId());
        assertThat(itemDia1).isEqualTo(itemDia2);
        itemDia2.setId(2L);
        assertThat(itemDia1).isNotEqualTo(itemDia2);
        itemDia1.setId(null);
        assertThat(itemDia1).isNotEqualTo(itemDia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemDiaDTO.class);
        ItemDiaDTO itemDiaDTO1 = new ItemDiaDTO();
        itemDiaDTO1.setId(1L);
        ItemDiaDTO itemDiaDTO2 = new ItemDiaDTO();
        assertThat(itemDiaDTO1).isNotEqualTo(itemDiaDTO2);
        itemDiaDTO2.setId(itemDiaDTO1.getId());
        assertThat(itemDiaDTO1).isEqualTo(itemDiaDTO2);
        itemDiaDTO2.setId(2L);
        assertThat(itemDiaDTO1).isNotEqualTo(itemDiaDTO2);
        itemDiaDTO1.setId(null);
        assertThat(itemDiaDTO1).isNotEqualTo(itemDiaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemDiaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemDiaMapper.fromId(null)).isNull();
    }
}

package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.Comida;
import com.marianowal.adminhouse.domain.Ingrediente;
import com.marianowal.adminhouse.repository.ComidaRepository;
import com.marianowal.adminhouse.service.ComidaService;
import com.marianowal.adminhouse.service.dto.ComidaDTO;
import com.marianowal.adminhouse.service.mapper.ComidaMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.ComidaCriteria;
import com.marianowal.adminhouse.service.ComidaQueryService;

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

/**
 * Test class for the ComidaResource REST controller.
 *
 * @see ComidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class ComidaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ComidaRepository comidaRepository;


    @Autowired
    private ComidaMapper comidaMapper;
    

    @Autowired
    private ComidaService comidaService;

    @Autowired
    private ComidaQueryService comidaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComidaMockMvc;

    private Comida comida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComidaResource comidaResource = new ComidaResource(comidaService, comidaQueryService);
        this.restComidaMockMvc = MockMvcBuilders.standaloneSetup(comidaResource)
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
    public static Comida createEntity(EntityManager em) {
        Comida comida = new Comida()
            .nombre(DEFAULT_NOMBRE);
        return comida;
    }

    @Before
    public void initTest() {
        comida = createEntity(em);
    }

    @Test
    @Transactional
    public void createComida() throws Exception {
        int databaseSizeBeforeCreate = comidaRepository.findAll().size();

        // Create the Comida
        ComidaDTO comidaDTO = comidaMapper.toDto(comida);
        restComidaMockMvc.perform(post("/api/comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Comida in the database
        List<Comida> comidaList = comidaRepository.findAll();
        assertThat(comidaList).hasSize(databaseSizeBeforeCreate + 1);
        Comida testComida = comidaList.get(comidaList.size() - 1);
        assertThat(testComida.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createComidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comidaRepository.findAll().size();

        // Create the Comida with an existing ID
        comida.setId(1L);
        ComidaDTO comidaDTO = comidaMapper.toDto(comida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComidaMockMvc.perform(post("/api/comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comida in the database
        List<Comida> comidaList = comidaRepository.findAll();
        assertThat(comidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = comidaRepository.findAll().size();
        // set the field null
        comida.setNombre(null);

        // Create the Comida, which fails.
        ComidaDTO comidaDTO = comidaMapper.toDto(comida);

        restComidaMockMvc.perform(post("/api/comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comidaDTO)))
            .andExpect(status().isBadRequest());

        List<Comida> comidaList = comidaRepository.findAll();
        assertThat(comidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComidas() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        // Get all the comidaList
        restComidaMockMvc.perform(get("/api/comidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    

    @Test
    @Transactional
    public void getComida() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        // Get the comida
        restComidaMockMvc.perform(get("/api/comidas/{id}", comida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comida.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getAllComidasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        // Get all the comidaList where nombre equals to DEFAULT_NOMBRE
        defaultComidaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the comidaList where nombre equals to UPDATED_NOMBRE
        defaultComidaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllComidasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        // Get all the comidaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultComidaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the comidaList where nombre equals to UPDATED_NOMBRE
        defaultComidaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllComidasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        // Get all the comidaList where nombre is not null
        defaultComidaShouldBeFound("nombre.specified=true");

        // Get all the comidaList where nombre is null
        defaultComidaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllComidasByIngredientesIsEqualToSomething() throws Exception {
        // Initialize the database
        Ingrediente ingredientes = IngredienteResourceIntTest.createEntity(em);
        em.persist(ingredientes);
        em.flush();
        comida.addIngredientes(ingredientes);
        comidaRepository.saveAndFlush(comida);
        Long ingredientesId = ingredientes.getId();

        // Get all the comidaList where ingredientes equals to ingredientesId
        defaultComidaShouldBeFound("ingredientesId.equals=" + ingredientesId);

        // Get all the comidaList where ingredientes equals to ingredientesId + 1
        defaultComidaShouldNotBeFound("ingredientesId.equals=" + (ingredientesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultComidaShouldBeFound(String filter) throws Exception {
        restComidaMockMvc.perform(get("/api/comidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultComidaShouldNotBeFound(String filter) throws Exception {
        restComidaMockMvc.perform(get("/api/comidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingComida() throws Exception {
        // Get the comida
        restComidaMockMvc.perform(get("/api/comidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComida() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        int databaseSizeBeforeUpdate = comidaRepository.findAll().size();

        // Update the comida
        Comida updatedComida = comidaRepository.findById(comida.getId()).get();
        // Disconnect from session so that the updates on updatedComida are not directly saved in db
        em.detach(updatedComida);
        updatedComida
            .nombre(UPDATED_NOMBRE);
        ComidaDTO comidaDTO = comidaMapper.toDto(updatedComida);

        restComidaMockMvc.perform(put("/api/comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comidaDTO)))
            .andExpect(status().isOk());

        // Validate the Comida in the database
        List<Comida> comidaList = comidaRepository.findAll();
        assertThat(comidaList).hasSize(databaseSizeBeforeUpdate);
        Comida testComida = comidaList.get(comidaList.size() - 1);
        assertThat(testComida.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingComida() throws Exception {
        int databaseSizeBeforeUpdate = comidaRepository.findAll().size();

        // Create the Comida
        ComidaDTO comidaDTO = comidaMapper.toDto(comida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComidaMockMvc.perform(put("/api/comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comida in the database
        List<Comida> comidaList = comidaRepository.findAll();
        assertThat(comidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComida() throws Exception {
        // Initialize the database
        comidaRepository.saveAndFlush(comida);

        int databaseSizeBeforeDelete = comidaRepository.findAll().size();

        // Get the comida
        restComidaMockMvc.perform(delete("/api/comidas/{id}", comida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comida> comidaList = comidaRepository.findAll();
        assertThat(comidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comida.class);
        Comida comida1 = new Comida();
        comida1.setId(1L);
        Comida comida2 = new Comida();
        comida2.setId(comida1.getId());
        assertThat(comida1).isEqualTo(comida2);
        comida2.setId(2L);
        assertThat(comida1).isNotEqualTo(comida2);
        comida1.setId(null);
        assertThat(comida1).isNotEqualTo(comida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComidaDTO.class);
        ComidaDTO comidaDTO1 = new ComidaDTO();
        comidaDTO1.setId(1L);
        ComidaDTO comidaDTO2 = new ComidaDTO();
        assertThat(comidaDTO1).isNotEqualTo(comidaDTO2);
        comidaDTO2.setId(comidaDTO1.getId());
        assertThat(comidaDTO1).isEqualTo(comidaDTO2);
        comidaDTO2.setId(2L);
        assertThat(comidaDTO1).isNotEqualTo(comidaDTO2);
        comidaDTO1.setId(null);
        assertThat(comidaDTO1).isNotEqualTo(comidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(comidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(comidaMapper.fromId(null)).isNull();
    }
}

package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.UnidadMedida;
import com.marianowal.adminhouse.repository.UnidadMedidaRepository;
import com.marianowal.adminhouse.service.UnidadMedidaService;
import com.marianowal.adminhouse.service.dto.UnidadMedidaDTO;
import com.marianowal.adminhouse.service.mapper.UnidadMedidaMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.UnidadMedidaCriteria;
import com.marianowal.adminhouse.service.UnidadMedidaQueryService;

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
 * Test class for the UnidadMedidaResource REST controller.
 *
 * @see UnidadMedidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class UnidadMedidaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;


    @Autowired
    private UnidadMedidaMapper unidadMedidaMapper;
    

    @Autowired
    private UnidadMedidaService unidadMedidaService;

    @Autowired
    private UnidadMedidaQueryService unidadMedidaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnidadMedidaMockMvc;

    private UnidadMedida unidadMedida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadMedidaResource unidadMedidaResource = new UnidadMedidaResource(unidadMedidaService, unidadMedidaQueryService);
        this.restUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadMedidaResource)
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
    public static UnidadMedida createEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida()
            .nombre(DEFAULT_NOMBRE);
        return unidadMedida;
    }

    @Before
    public void initTest() {
        unidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida with an existing ID
        unidadMedida.setId(1L);
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadMedidaRepository.findAll().size();
        // set the field null
        unidadMedida.setNombre(null);

        // Create the UnidadMedida, which fails.
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadMedidas() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    

    @Test
    @Transactional
    public void getUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", unidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getAllUnidadMedidasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where nombre equals to DEFAULT_NOMBRE
        defaultUnidadMedidaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the unidadMedidaList where nombre equals to UPDATED_NOMBRE
        defaultUnidadMedidaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllUnidadMedidasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultUnidadMedidaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the unidadMedidaList where nombre equals to UPDATED_NOMBRE
        defaultUnidadMedidaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllUnidadMedidasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList where nombre is not null
        defaultUnidadMedidaShouldBeFound("nombre.specified=true");

        // Get all the unidadMedidaList where nombre is null
        defaultUnidadMedidaShouldNotBeFound("nombre.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUnidadMedidaShouldBeFound(String filter) throws Exception {
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUnidadMedidaShouldNotBeFound(String filter) throws Exception {
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingUnidadMedida() throws Exception {
        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida
        UnidadMedida updatedUnidadMedida = unidadMedidaRepository.findById(unidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadMedida are not directly saved in db
        em.detach(updatedUnidadMedida);
        updatedUnidadMedida
            .nombre(UPDATED_NOMBRE);
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(updatedUnidadMedida);

        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida
        UnidadMedidaDTO unidadMedidaDTO = unidadMedidaMapper.toDto(unidadMedida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        int databaseSizeBeforeDelete = unidadMedidaRepository.findAll().size();

        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(delete("/api/unidad-medidas/{id}", unidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedida.class);
        UnidadMedida unidadMedida1 = new UnidadMedida();
        unidadMedida1.setId(1L);
        UnidadMedida unidadMedida2 = new UnidadMedida();
        unidadMedida2.setId(unidadMedida1.getId());
        assertThat(unidadMedida1).isEqualTo(unidadMedida2);
        unidadMedida2.setId(2L);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
        unidadMedida1.setId(null);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedidaDTO.class);
        UnidadMedidaDTO unidadMedidaDTO1 = new UnidadMedidaDTO();
        unidadMedidaDTO1.setId(1L);
        UnidadMedidaDTO unidadMedidaDTO2 = new UnidadMedidaDTO();
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO2.setId(unidadMedidaDTO1.getId());
        assertThat(unidadMedidaDTO1).isEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO2.setId(2L);
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
        unidadMedidaDTO1.setId(null);
        assertThat(unidadMedidaDTO1).isNotEqualTo(unidadMedidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadMedidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadMedidaMapper.fromId(null)).isNull();
    }
}

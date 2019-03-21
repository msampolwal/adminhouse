package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.CalendarioComida;
import com.marianowal.adminhouse.domain.Grupo;
import com.marianowal.adminhouse.domain.Dia;
import com.marianowal.adminhouse.repository.CalendarioComidaRepository;
import com.marianowal.adminhouse.service.CalendarioComidaService;
import com.marianowal.adminhouse.service.dto.CalendarioComidaDTO;
import com.marianowal.adminhouse.service.mapper.CalendarioComidaMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.CalendarioComidaCriteria;
import com.marianowal.adminhouse.service.CalendarioComidaQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.marianowal.adminhouse.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CalendarioComidaResource REST controller.
 *
 * @see CalendarioComidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class CalendarioComidaResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CalendarioComidaRepository calendarioComidaRepository;


    @Autowired
    private CalendarioComidaMapper calendarioComidaMapper;
    

    @Autowired
    private CalendarioComidaService calendarioComidaService;

    @Autowired
    private CalendarioComidaQueryService calendarioComidaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCalendarioComidaMockMvc;

    private CalendarioComida calendarioComida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendarioComidaResource calendarioComidaResource = new CalendarioComidaResource(calendarioComidaService, calendarioComidaQueryService);
        this.restCalendarioComidaMockMvc = MockMvcBuilders.standaloneSetup(calendarioComidaResource)
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
    public static CalendarioComida createEntity(EntityManager em) {
        CalendarioComida calendarioComida = new CalendarioComida()
            .fecha(DEFAULT_FECHA);
        return calendarioComida;
    }

    @Before
    public void initTest() {
        calendarioComida = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarioComida() throws Exception {
        int databaseSizeBeforeCreate = calendarioComidaRepository.findAll().size();

        // Create the CalendarioComida
        CalendarioComidaDTO calendarioComidaDTO = calendarioComidaMapper.toDto(calendarioComida);
        restCalendarioComidaMockMvc.perform(post("/api/calendario-comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarioComidaDTO)))
            .andExpect(status().isCreated());

        // Validate the CalendarioComida in the database
        List<CalendarioComida> calendarioComidaList = calendarioComidaRepository.findAll();
        assertThat(calendarioComidaList).hasSize(databaseSizeBeforeCreate + 1);
        CalendarioComida testCalendarioComida = calendarioComidaList.get(calendarioComidaList.size() - 1);
        assertThat(testCalendarioComida.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createCalendarioComidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarioComidaRepository.findAll().size();

        // Create the CalendarioComida with an existing ID
        calendarioComida.setId(1L);
        CalendarioComidaDTO calendarioComidaDTO = calendarioComidaMapper.toDto(calendarioComida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarioComidaMockMvc.perform(post("/api/calendario-comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarioComidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarioComida in the database
        List<CalendarioComida> calendarioComidaList = calendarioComidaRepository.findAll();
        assertThat(calendarioComidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarioComidaRepository.findAll().size();
        // set the field null
        calendarioComida.setFecha(null);

        // Create the CalendarioComida, which fails.
        CalendarioComidaDTO calendarioComidaDTO = calendarioComidaMapper.toDto(calendarioComida);

        restCalendarioComidaMockMvc.perform(post("/api/calendario-comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarioComidaDTO)))
            .andExpect(status().isBadRequest());

        List<CalendarioComida> calendarioComidaList = calendarioComidaRepository.findAll();
        assertThat(calendarioComidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalendarioComidas() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get all the calendarioComidaList
        restCalendarioComidaMockMvc.perform(get("/api/calendario-comidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarioComida.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    

    @Test
    @Transactional
    public void getCalendarioComida() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get the calendarioComida
        restCalendarioComidaMockMvc.perform(get("/api/calendario-comidas/{id}", calendarioComida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendarioComida.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getAllCalendarioComidasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get all the calendarioComidaList where fecha equals to DEFAULT_FECHA
        defaultCalendarioComidaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the calendarioComidaList where fecha equals to UPDATED_FECHA
        defaultCalendarioComidaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCalendarioComidasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get all the calendarioComidaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCalendarioComidaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the calendarioComidaList where fecha equals to UPDATED_FECHA
        defaultCalendarioComidaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCalendarioComidasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get all the calendarioComidaList where fecha is not null
        defaultCalendarioComidaShouldBeFound("fecha.specified=true");

        // Get all the calendarioComidaList where fecha is null
        defaultCalendarioComidaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarioComidasByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get all the calendarioComidaList where fecha greater than or equals to DEFAULT_FECHA
        defaultCalendarioComidaShouldBeFound("fecha.greaterOrEqualThan=" + DEFAULT_FECHA);

        // Get all the calendarioComidaList where fecha greater than or equals to UPDATED_FECHA
        defaultCalendarioComidaShouldNotBeFound("fecha.greaterOrEqualThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCalendarioComidasByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        // Get all the calendarioComidaList where fecha less than or equals to DEFAULT_FECHA
        defaultCalendarioComidaShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the calendarioComidaList where fecha less than or equals to UPDATED_FECHA
        defaultCalendarioComidaShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }


    @Test
    @Transactional
    public void getAllCalendarioComidasByGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        Grupo grupo = GrupoResourceIntTest.createEntity(em);
        em.persist(grupo);
        em.flush();
        calendarioComida.setGrupo(grupo);
        calendarioComidaRepository.saveAndFlush(calendarioComida);
        Long grupoId = grupo.getId();

        // Get all the calendarioComidaList where grupo equals to grupoId
        defaultCalendarioComidaShouldBeFound("grupoId.equals=" + grupoId);

        // Get all the calendarioComidaList where grupo equals to grupoId + 1
        defaultCalendarioComidaShouldNotBeFound("grupoId.equals=" + (grupoId + 1));
    }


    @Test
    @Transactional
    public void getAllCalendarioComidasByDiaIsEqualToSomething() throws Exception {
        // Initialize the database
        Dia dia = DiaResourceIntTest.createEntity(em);
        em.persist(dia);
        em.flush();
        calendarioComida.setDia(dia);
        calendarioComidaRepository.saveAndFlush(calendarioComida);
        Long diaId = dia.getId();

        // Get all the calendarioComidaList where dia equals to diaId
        defaultCalendarioComidaShouldBeFound("diaId.equals=" + diaId);

        // Get all the calendarioComidaList where dia equals to diaId + 1
        defaultCalendarioComidaShouldNotBeFound("diaId.equals=" + (diaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCalendarioComidaShouldBeFound(String filter) throws Exception {
        restCalendarioComidaMockMvc.perform(get("/api/calendario-comidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarioComida.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCalendarioComidaShouldNotBeFound(String filter) throws Exception {
        restCalendarioComidaMockMvc.perform(get("/api/calendario-comidas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCalendarioComida() throws Exception {
        // Get the calendarioComida
        restCalendarioComidaMockMvc.perform(get("/api/calendario-comidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarioComida() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        int databaseSizeBeforeUpdate = calendarioComidaRepository.findAll().size();

        // Update the calendarioComida
        CalendarioComida updatedCalendarioComida = calendarioComidaRepository.findById(calendarioComida.getId()).get();
        // Disconnect from session so that the updates on updatedCalendarioComida are not directly saved in db
        em.detach(updatedCalendarioComida);
        updatedCalendarioComida
            .fecha(UPDATED_FECHA);
        CalendarioComidaDTO calendarioComidaDTO = calendarioComidaMapper.toDto(updatedCalendarioComida);

        restCalendarioComidaMockMvc.perform(put("/api/calendario-comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarioComidaDTO)))
            .andExpect(status().isOk());

        // Validate the CalendarioComida in the database
        List<CalendarioComida> calendarioComidaList = calendarioComidaRepository.findAll();
        assertThat(calendarioComidaList).hasSize(databaseSizeBeforeUpdate);
        CalendarioComida testCalendarioComida = calendarioComidaList.get(calendarioComidaList.size() - 1);
        assertThat(testCalendarioComida.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendarioComida() throws Exception {
        int databaseSizeBeforeUpdate = calendarioComidaRepository.findAll().size();

        // Create the CalendarioComida
        CalendarioComidaDTO calendarioComidaDTO = calendarioComidaMapper.toDto(calendarioComida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalendarioComidaMockMvc.perform(put("/api/calendario-comidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarioComidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarioComida in the database
        List<CalendarioComida> calendarioComidaList = calendarioComidaRepository.findAll();
        assertThat(calendarioComidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendarioComida() throws Exception {
        // Initialize the database
        calendarioComidaRepository.saveAndFlush(calendarioComida);

        int databaseSizeBeforeDelete = calendarioComidaRepository.findAll().size();

        // Get the calendarioComida
        restCalendarioComidaMockMvc.perform(delete("/api/calendario-comidas/{id}", calendarioComida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CalendarioComida> calendarioComidaList = calendarioComidaRepository.findAll();
        assertThat(calendarioComidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarioComida.class);
        CalendarioComida calendarioComida1 = new CalendarioComida();
        calendarioComida1.setId(1L);
        CalendarioComida calendarioComida2 = new CalendarioComida();
        calendarioComida2.setId(calendarioComida1.getId());
        assertThat(calendarioComida1).isEqualTo(calendarioComida2);
        calendarioComida2.setId(2L);
        assertThat(calendarioComida1).isNotEqualTo(calendarioComida2);
        calendarioComida1.setId(null);
        assertThat(calendarioComida1).isNotEqualTo(calendarioComida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarioComidaDTO.class);
        CalendarioComidaDTO calendarioComidaDTO1 = new CalendarioComidaDTO();
        calendarioComidaDTO1.setId(1L);
        CalendarioComidaDTO calendarioComidaDTO2 = new CalendarioComidaDTO();
        assertThat(calendarioComidaDTO1).isNotEqualTo(calendarioComidaDTO2);
        calendarioComidaDTO2.setId(calendarioComidaDTO1.getId());
        assertThat(calendarioComidaDTO1).isEqualTo(calendarioComidaDTO2);
        calendarioComidaDTO2.setId(2L);
        assertThat(calendarioComidaDTO1).isNotEqualTo(calendarioComidaDTO2);
        calendarioComidaDTO1.setId(null);
        assertThat(calendarioComidaDTO1).isNotEqualTo(calendarioComidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(calendarioComidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(calendarioComidaMapper.fromId(null)).isNull();
    }
}

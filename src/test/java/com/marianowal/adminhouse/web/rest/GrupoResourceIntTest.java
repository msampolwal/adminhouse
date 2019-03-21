package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.Grupo;
import com.marianowal.adminhouse.domain.CalendarioComida;
import com.marianowal.adminhouse.domain.Dia;
import com.marianowal.adminhouse.domain.User;
import com.marianowal.adminhouse.repository.GrupoRepository;
import com.marianowal.adminhouse.service.GrupoService;
import com.marianowal.adminhouse.service.dto.GrupoDTO;
import com.marianowal.adminhouse.service.mapper.GrupoMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.GrupoCriteria;
import com.marianowal.adminhouse.service.GrupoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.marianowal.adminhouse.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GrupoResource REST controller.
 *
 * @see GrupoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class GrupoResourceIntTest {

    @Autowired
    private GrupoRepository grupoRepository;
    @Mock
    private GrupoRepository grupoRepositoryMock;

    @Autowired
    private GrupoMapper grupoMapper;
    
    @Mock
    private GrupoService grupoServiceMock;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoQueryService grupoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrupoMockMvc;

    private Grupo grupo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupoResource grupoResource = new GrupoResource(grupoService, grupoQueryService);
        this.restGrupoMockMvc = MockMvcBuilders.standaloneSetup(grupoResource)
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
    public static Grupo createEntity(EntityManager em) {
        Grupo grupo = new Grupo();
        return grupo;
    }

    @Before
    public void initTest() {
        grupo = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupo() throws Exception {
        int databaseSizeBeforeCreate = grupoRepository.findAll().size();

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);
        restGrupoMockMvc.perform(post("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
            .andExpect(status().isCreated());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeCreate + 1);
        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
    }

    @Test
    @Transactional
    public void createGrupoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoRepository.findAll().size();

        // Create the Grupo with an existing ID
        grupo.setId(1L);
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoMockMvc.perform(post("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGrupos() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        // Get all the grupoList
        restGrupoMockMvc.perform(get("/api/grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupo.getId().intValue())));
    }
    
    public void getAllGruposWithEagerRelationshipsIsEnabled() throws Exception {
        GrupoResource grupoResource = new GrupoResource(grupoServiceMock, grupoQueryService);
        when(grupoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGrupoMockMvc = MockMvcBuilders.standaloneSetup(grupoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGrupoMockMvc.perform(get("/api/grupos?eagerload=true"))
        .andExpect(status().isOk());

        verify(grupoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGruposWithEagerRelationshipsIsNotEnabled() throws Exception {
        GrupoResource grupoResource = new GrupoResource(grupoServiceMock, grupoQueryService);
            when(grupoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGrupoMockMvc = MockMvcBuilders.standaloneSetup(grupoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGrupoMockMvc.perform(get("/api/grupos?eagerload=true"))
        .andExpect(status().isOk());

            verify(grupoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        // Get the grupo
        restGrupoMockMvc.perform(get("/api/grupos/{id}", grupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupo.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllGruposByCalendarioComidasIsEqualToSomething() throws Exception {
        // Initialize the database
        CalendarioComida calendarioComidas = CalendarioComidaResourceIntTest.createEntity(em);
        em.persist(calendarioComidas);
        em.flush();
        grupo.addCalendarioComidas(calendarioComidas);
        grupoRepository.saveAndFlush(grupo);
        Long calendarioComidasId = calendarioComidas.getId();

        // Get all the grupoList where calendarioComidas equals to calendarioComidasId
        defaultGrupoShouldBeFound("calendarioComidasId.equals=" + calendarioComidasId);

        // Get all the grupoList where calendarioComidas equals to calendarioComidasId + 1
        defaultGrupoShouldNotBeFound("calendarioComidasId.equals=" + (calendarioComidasId + 1));
    }


    @Test
    @Transactional
    public void getAllGruposByDiasIsEqualToSomething() throws Exception {
        // Initialize the database
        Dia dias = DiaResourceIntTest.createEntity(em);
        em.persist(dias);
        em.flush();
        grupo.addDias(dias);
        grupoRepository.saveAndFlush(grupo);
        Long diasId = dias.getId();

        // Get all the grupoList where dias equals to diasId
        defaultGrupoShouldBeFound("diasId.equals=" + diasId);

        // Get all the grupoList where dias equals to diasId + 1
        defaultGrupoShouldNotBeFound("diasId.equals=" + (diasId + 1));
    }


    @Test
    @Transactional
    public void getAllGruposByUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        User users = UserResourceIntTest.createEntity(em);
        em.persist(users);
        em.flush();
        grupo.addUsers(users);
        grupoRepository.saveAndFlush(grupo);
        Long usersId = users.getId();

        // Get all the grupoList where users equals to usersId
        defaultGrupoShouldBeFound("usersId.equals=" + usersId);

        // Get all the grupoList where users equals to usersId + 1
        defaultGrupoShouldNotBeFound("usersId.equals=" + (usersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGrupoShouldBeFound(String filter) throws Exception {
        restGrupoMockMvc.perform(get("/api/grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupo.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGrupoShouldNotBeFound(String filter) throws Exception {
        restGrupoMockMvc.perform(get("/api/grupos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingGrupo() throws Exception {
        // Get the grupo
        restGrupoMockMvc.perform(get("/api/grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();

        // Update the grupo
        Grupo updatedGrupo = grupoRepository.findById(grupo.getId()).get();
        // Disconnect from session so that the updates on updatedGrupo are not directly saved in db
        em.detach(updatedGrupo);
        GrupoDTO grupoDTO = grupoMapper.toDto(updatedGrupo);

        restGrupoMockMvc.perform(put("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
            .andExpect(status().isOk());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();

        // Create the Grupo
        GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrupoMockMvc.perform(put("/api/grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        int databaseSizeBeforeDelete = grupoRepository.findAll().size();

        // Get the grupo
        restGrupoMockMvc.perform(delete("/api/grupos/{id}", grupo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grupo.class);
        Grupo grupo1 = new Grupo();
        grupo1.setId(1L);
        Grupo grupo2 = new Grupo();
        grupo2.setId(grupo1.getId());
        assertThat(grupo1).isEqualTo(grupo2);
        grupo2.setId(2L);
        assertThat(grupo1).isNotEqualTo(grupo2);
        grupo1.setId(null);
        assertThat(grupo1).isNotEqualTo(grupo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoDTO.class);
        GrupoDTO grupoDTO1 = new GrupoDTO();
        grupoDTO1.setId(1L);
        GrupoDTO grupoDTO2 = new GrupoDTO();
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
        grupoDTO2.setId(grupoDTO1.getId());
        assertThat(grupoDTO1).isEqualTo(grupoDTO2);
        grupoDTO2.setId(2L);
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
        grupoDTO1.setId(null);
        assertThat(grupoDTO1).isNotEqualTo(grupoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grupoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grupoMapper.fromId(null)).isNull();
    }
}

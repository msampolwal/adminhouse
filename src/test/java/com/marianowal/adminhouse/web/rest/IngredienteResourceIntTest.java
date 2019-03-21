package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.Ingrediente;
import com.marianowal.adminhouse.domain.Comida;
import com.marianowal.adminhouse.domain.Producto;
import com.marianowal.adminhouse.repository.IngredienteRepository;
import com.marianowal.adminhouse.service.IngredienteService;
import com.marianowal.adminhouse.service.dto.IngredienteDTO;
import com.marianowal.adminhouse.service.mapper.IngredienteMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.IngredienteCriteria;
import com.marianowal.adminhouse.service.IngredienteQueryService;

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
 * Test class for the IngredienteResource REST controller.
 *
 * @see IngredienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class IngredienteResourceIntTest {

    private static final Float DEFAULT_CANTIDAD = 1F;
    private static final Float UPDATED_CANTIDAD = 2F;

    @Autowired
    private IngredienteRepository ingredienteRepository;


    @Autowired
    private IngredienteMapper ingredienteMapper;
    

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private IngredienteQueryService ingredienteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredienteMockMvc;

    private Ingrediente ingrediente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngredienteResource ingredienteResource = new IngredienteResource(ingredienteService, ingredienteQueryService);
        this.restIngredienteMockMvc = MockMvcBuilders.standaloneSetup(ingredienteResource)
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
    public static Ingrediente createEntity(EntityManager em) {
        Ingrediente ingrediente = new Ingrediente()
            .cantidad(DEFAULT_CANTIDAD);
        return ingrediente;
    }

    @Before
    public void initTest() {
        ingrediente = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngrediente() throws Exception {
        int databaseSizeBeforeCreate = ingredienteRepository.findAll().size();

        // Create the Ingrediente
        IngredienteDTO ingredienteDTO = ingredienteMapper.toDto(ingrediente);
        restIngredienteMockMvc.perform(post("/api/ingredientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Ingrediente in the database
        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        assertThat(ingredienteList).hasSize(databaseSizeBeforeCreate + 1);
        Ingrediente testIngrediente = ingredienteList.get(ingredienteList.size() - 1);
        assertThat(testIngrediente.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createIngredienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredienteRepository.findAll().size();

        // Create the Ingrediente with an existing ID
        ingrediente.setId(1L);
        IngredienteDTO ingredienteDTO = ingredienteMapper.toDto(ingrediente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredienteMockMvc.perform(post("/api/ingredientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ingrediente in the database
        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        assertThat(ingredienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredienteRepository.findAll().size();
        // set the field null
        ingrediente.setCantidad(null);

        // Create the Ingrediente, which fails.
        IngredienteDTO ingredienteDTO = ingredienteMapper.toDto(ingrediente);

        restIngredienteMockMvc.perform(post("/api/ingredientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredienteDTO)))
            .andExpect(status().isBadRequest());

        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        assertThat(ingredienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredientes() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        // Get all the ingredienteList
        restIngredienteMockMvc.perform(get("/api/ingredientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingrediente.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getIngrediente() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        // Get the ingrediente
        restIngredienteMockMvc.perform(get("/api/ingredientes/{id}", ingrediente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingrediente.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllIngredientesByCantidadIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        // Get all the ingredienteList where cantidad equals to DEFAULT_CANTIDAD
        defaultIngredienteShouldBeFound("cantidad.equals=" + DEFAULT_CANTIDAD);

        // Get all the ingredienteList where cantidad equals to UPDATED_CANTIDAD
        defaultIngredienteShouldNotBeFound("cantidad.equals=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllIngredientesByCantidadIsInShouldWork() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        // Get all the ingredienteList where cantidad in DEFAULT_CANTIDAD or UPDATED_CANTIDAD
        defaultIngredienteShouldBeFound("cantidad.in=" + DEFAULT_CANTIDAD + "," + UPDATED_CANTIDAD);

        // Get all the ingredienteList where cantidad equals to UPDATED_CANTIDAD
        defaultIngredienteShouldNotBeFound("cantidad.in=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllIngredientesByCantidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        // Get all the ingredienteList where cantidad is not null
        defaultIngredienteShouldBeFound("cantidad.specified=true");

        // Get all the ingredienteList where cantidad is null
        defaultIngredienteShouldNotBeFound("cantidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllIngredientesByComidaIsEqualToSomething() throws Exception {
        // Initialize the database
        Comida comida = ComidaResourceIntTest.createEntity(em);
        em.persist(comida);
        em.flush();
        ingrediente.setComida(comida);
        ingredienteRepository.saveAndFlush(ingrediente);
        Long comidaId = comida.getId();

        // Get all the ingredienteList where comida equals to comidaId
        defaultIngredienteShouldBeFound("comidaId.equals=" + comidaId);

        // Get all the ingredienteList where comida equals to comidaId + 1
        defaultIngredienteShouldNotBeFound("comidaId.equals=" + (comidaId + 1));
    }


    @Test
    @Transactional
    public void getAllIngredientesByProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        Producto producto = ProductoResourceIntTest.createEntity(em);
        em.persist(producto);
        em.flush();
        ingrediente.setProducto(producto);
        ingredienteRepository.saveAndFlush(ingrediente);
        Long productoId = producto.getId();

        // Get all the ingredienteList where producto equals to productoId
        defaultIngredienteShouldBeFound("productoId.equals=" + productoId);

        // Get all the ingredienteList where producto equals to productoId + 1
        defaultIngredienteShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultIngredienteShouldBeFound(String filter) throws Exception {
        restIngredienteMockMvc.perform(get("/api/ingredientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingrediente.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultIngredienteShouldNotBeFound(String filter) throws Exception {
        restIngredienteMockMvc.perform(get("/api/ingredientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingIngrediente() throws Exception {
        // Get the ingrediente
        restIngredienteMockMvc.perform(get("/api/ingredientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngrediente() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        int databaseSizeBeforeUpdate = ingredienteRepository.findAll().size();

        // Update the ingrediente
        Ingrediente updatedIngrediente = ingredienteRepository.findById(ingrediente.getId()).get();
        // Disconnect from session so that the updates on updatedIngrediente are not directly saved in db
        em.detach(updatedIngrediente);
        updatedIngrediente
            .cantidad(UPDATED_CANTIDAD);
        IngredienteDTO ingredienteDTO = ingredienteMapper.toDto(updatedIngrediente);

        restIngredienteMockMvc.perform(put("/api/ingredientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredienteDTO)))
            .andExpect(status().isOk());

        // Validate the Ingrediente in the database
        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        assertThat(ingredienteList).hasSize(databaseSizeBeforeUpdate);
        Ingrediente testIngrediente = ingredienteList.get(ingredienteList.size() - 1);
        assertThat(testIngrediente.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingIngrediente() throws Exception {
        int databaseSizeBeforeUpdate = ingredienteRepository.findAll().size();

        // Create the Ingrediente
        IngredienteDTO ingredienteDTO = ingredienteMapper.toDto(ingrediente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredienteMockMvc.perform(put("/api/ingredientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ingrediente in the database
        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        assertThat(ingredienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngrediente() throws Exception {
        // Initialize the database
        ingredienteRepository.saveAndFlush(ingrediente);

        int databaseSizeBeforeDelete = ingredienteRepository.findAll().size();

        // Get the ingrediente
        restIngredienteMockMvc.perform(delete("/api/ingredientes/{id}", ingrediente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        assertThat(ingredienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingrediente.class);
        Ingrediente ingrediente1 = new Ingrediente();
        ingrediente1.setId(1L);
        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setId(ingrediente1.getId());
        assertThat(ingrediente1).isEqualTo(ingrediente2);
        ingrediente2.setId(2L);
        assertThat(ingrediente1).isNotEqualTo(ingrediente2);
        ingrediente1.setId(null);
        assertThat(ingrediente1).isNotEqualTo(ingrediente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredienteDTO.class);
        IngredienteDTO ingredienteDTO1 = new IngredienteDTO();
        ingredienteDTO1.setId(1L);
        IngredienteDTO ingredienteDTO2 = new IngredienteDTO();
        assertThat(ingredienteDTO1).isNotEqualTo(ingredienteDTO2);
        ingredienteDTO2.setId(ingredienteDTO1.getId());
        assertThat(ingredienteDTO1).isEqualTo(ingredienteDTO2);
        ingredienteDTO2.setId(2L);
        assertThat(ingredienteDTO1).isNotEqualTo(ingredienteDTO2);
        ingredienteDTO1.setId(null);
        assertThat(ingredienteDTO1).isNotEqualTo(ingredienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ingredienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ingredienteMapper.fromId(null)).isNull();
    }
}

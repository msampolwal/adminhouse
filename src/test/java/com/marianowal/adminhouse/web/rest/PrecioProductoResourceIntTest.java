package com.marianowal.adminhouse.web.rest;

import com.marianowal.adminhouse.AdminhouseApp;

import com.marianowal.adminhouse.domain.PrecioProducto;
import com.marianowal.adminhouse.domain.Producto;
import com.marianowal.adminhouse.domain.UnidadMedida;
import com.marianowal.adminhouse.repository.PrecioProductoRepository;
import com.marianowal.adminhouse.service.PrecioProductoService;
import com.marianowal.adminhouse.service.dto.PrecioProductoDTO;
import com.marianowal.adminhouse.service.mapper.PrecioProductoMapper;
import com.marianowal.adminhouse.web.rest.errors.ExceptionTranslator;
import com.marianowal.adminhouse.service.dto.PrecioProductoCriteria;
import com.marianowal.adminhouse.service.PrecioProductoQueryService;

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
 * Test class for the PrecioProductoResource REST controller.
 *
 * @see PrecioProductoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminhouseApp.class)
public class PrecioProductoResourceIntTest {

    private static final Float DEFAULT_PRECIO = 1F;
    private static final Float UPDATED_PRECIO = 2F;

    private static final Float DEFAULT_CANTIDAD = 1F;
    private static final Float UPDATED_CANTIDAD = 2F;

    @Autowired
    private PrecioProductoRepository precioProductoRepository;


    @Autowired
    private PrecioProductoMapper precioProductoMapper;
    

    @Autowired
    private PrecioProductoService precioProductoService;

    @Autowired
    private PrecioProductoQueryService precioProductoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrecioProductoMockMvc;

    private PrecioProducto precioProducto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrecioProductoResource precioProductoResource = new PrecioProductoResource(precioProductoService, precioProductoQueryService);
        this.restPrecioProductoMockMvc = MockMvcBuilders.standaloneSetup(precioProductoResource)
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
    public static PrecioProducto createEntity(EntityManager em) {
        PrecioProducto precioProducto = new PrecioProducto()
            .precio(DEFAULT_PRECIO)
            .cantidad(DEFAULT_CANTIDAD);
        return precioProducto;
    }

    @Before
    public void initTest() {
        precioProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrecioProducto() throws Exception {
        int databaseSizeBeforeCreate = precioProductoRepository.findAll().size();

        // Create the PrecioProducto
        PrecioProductoDTO precioProductoDTO = precioProductoMapper.toDto(precioProducto);
        restPrecioProductoMockMvc.perform(post("/api/precio-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrecioProducto in the database
        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeCreate + 1);
        PrecioProducto testPrecioProducto = precioProductoList.get(precioProductoList.size() - 1);
        assertThat(testPrecioProducto.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testPrecioProducto.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createPrecioProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = precioProductoRepository.findAll().size();

        // Create the PrecioProducto with an existing ID
        precioProducto.setId(1L);
        PrecioProductoDTO precioProductoDTO = precioProductoMapper.toDto(precioProducto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecioProductoMockMvc.perform(post("/api/precio-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrecioProducto in the database
        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = precioProductoRepository.findAll().size();
        // set the field null
        precioProducto.setPrecio(null);

        // Create the PrecioProducto, which fails.
        PrecioProductoDTO precioProductoDTO = precioProductoMapper.toDto(precioProducto);

        restPrecioProductoMockMvc.perform(post("/api/precio-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioProductoDTO)))
            .andExpect(status().isBadRequest());

        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = precioProductoRepository.findAll().size();
        // set the field null
        precioProducto.setCantidad(null);

        // Create the PrecioProducto, which fails.
        PrecioProductoDTO precioProductoDTO = precioProductoMapper.toDto(precioProducto);

        restPrecioProductoMockMvc.perform(post("/api/precio-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioProductoDTO)))
            .andExpect(status().isBadRequest());

        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrecioProductos() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList
        restPrecioProductoMockMvc.perform(get("/api/precio-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precioProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getPrecioProducto() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get the precioProducto
        restPrecioProductoMockMvc.perform(get("/api/precio-productos/{id}", precioProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(precioProducto.getId().intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByPrecioIsEqualToSomething() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList where precio equals to DEFAULT_PRECIO
        defaultPrecioProductoShouldBeFound("precio.equals=" + DEFAULT_PRECIO);

        // Get all the precioProductoList where precio equals to UPDATED_PRECIO
        defaultPrecioProductoShouldNotBeFound("precio.equals=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByPrecioIsInShouldWork() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList where precio in DEFAULT_PRECIO or UPDATED_PRECIO
        defaultPrecioProductoShouldBeFound("precio.in=" + DEFAULT_PRECIO + "," + UPDATED_PRECIO);

        // Get all the precioProductoList where precio equals to UPDATED_PRECIO
        defaultPrecioProductoShouldNotBeFound("precio.in=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByPrecioIsNullOrNotNull() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList where precio is not null
        defaultPrecioProductoShouldBeFound("precio.specified=true");

        // Get all the precioProductoList where precio is null
        defaultPrecioProductoShouldNotBeFound("precio.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByCantidadIsEqualToSomething() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList where cantidad equals to DEFAULT_CANTIDAD
        defaultPrecioProductoShouldBeFound("cantidad.equals=" + DEFAULT_CANTIDAD);

        // Get all the precioProductoList where cantidad equals to UPDATED_CANTIDAD
        defaultPrecioProductoShouldNotBeFound("cantidad.equals=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByCantidadIsInShouldWork() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList where cantidad in DEFAULT_CANTIDAD or UPDATED_CANTIDAD
        defaultPrecioProductoShouldBeFound("cantidad.in=" + DEFAULT_CANTIDAD + "," + UPDATED_CANTIDAD);

        // Get all the precioProductoList where cantidad equals to UPDATED_CANTIDAD
        defaultPrecioProductoShouldNotBeFound("cantidad.in=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByCantidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        // Get all the precioProductoList where cantidad is not null
        defaultPrecioProductoShouldBeFound("cantidad.specified=true");

        // Get all the precioProductoList where cantidad is null
        defaultPrecioProductoShouldNotBeFound("cantidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrecioProductosByProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        Producto producto = ProductoResourceIntTest.createEntity(em);
        em.persist(producto);
        em.flush();
        precioProducto.setProducto(producto);
        precioProductoRepository.saveAndFlush(precioProducto);
        Long productoId = producto.getId();

        // Get all the precioProductoList where producto equals to productoId
        defaultPrecioProductoShouldBeFound("productoId.equals=" + productoId);

        // Get all the precioProductoList where producto equals to productoId + 1
        defaultPrecioProductoShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }


    @Test
    @Transactional
    public void getAllPrecioProductosByUnidadMedidaIsEqualToSomething() throws Exception {
        // Initialize the database
        UnidadMedida unidadMedida = UnidadMedidaResourceIntTest.createEntity(em);
        em.persist(unidadMedida);
        em.flush();
        precioProducto.setUnidadMedida(unidadMedida);
        precioProductoRepository.saveAndFlush(precioProducto);
        Long unidadMedidaId = unidadMedida.getId();

        // Get all the precioProductoList where unidadMedida equals to unidadMedidaId
        defaultPrecioProductoShouldBeFound("unidadMedidaId.equals=" + unidadMedidaId);

        // Get all the precioProductoList where unidadMedida equals to unidadMedidaId + 1
        defaultPrecioProductoShouldNotBeFound("unidadMedidaId.equals=" + (unidadMedidaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrecioProductoShouldBeFound(String filter) throws Exception {
        restPrecioProductoMockMvc.perform(get("/api/precio-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precioProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrecioProductoShouldNotBeFound(String filter) throws Exception {
        restPrecioProductoMockMvc.perform(get("/api/precio-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingPrecioProducto() throws Exception {
        // Get the precioProducto
        restPrecioProductoMockMvc.perform(get("/api/precio-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrecioProducto() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        int databaseSizeBeforeUpdate = precioProductoRepository.findAll().size();

        // Update the precioProducto
        PrecioProducto updatedPrecioProducto = precioProductoRepository.findById(precioProducto.getId()).get();
        // Disconnect from session so that the updates on updatedPrecioProducto are not directly saved in db
        em.detach(updatedPrecioProducto);
        updatedPrecioProducto
            .precio(UPDATED_PRECIO)
            .cantidad(UPDATED_CANTIDAD);
        PrecioProductoDTO precioProductoDTO = precioProductoMapper.toDto(updatedPrecioProducto);

        restPrecioProductoMockMvc.perform(put("/api/precio-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioProductoDTO)))
            .andExpect(status().isOk());

        // Validate the PrecioProducto in the database
        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeUpdate);
        PrecioProducto testPrecioProducto = precioProductoList.get(precioProductoList.size() - 1);
        assertThat(testPrecioProducto.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testPrecioProducto.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingPrecioProducto() throws Exception {
        int databaseSizeBeforeUpdate = precioProductoRepository.findAll().size();

        // Create the PrecioProducto
        PrecioProductoDTO precioProductoDTO = precioProductoMapper.toDto(precioProducto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrecioProductoMockMvc.perform(put("/api/precio-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrecioProducto in the database
        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrecioProducto() throws Exception {
        // Initialize the database
        precioProductoRepository.saveAndFlush(precioProducto);

        int databaseSizeBeforeDelete = precioProductoRepository.findAll().size();

        // Get the precioProducto
        restPrecioProductoMockMvc.perform(delete("/api/precio-productos/{id}", precioProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrecioProducto> precioProductoList = precioProductoRepository.findAll();
        assertThat(precioProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioProducto.class);
        PrecioProducto precioProducto1 = new PrecioProducto();
        precioProducto1.setId(1L);
        PrecioProducto precioProducto2 = new PrecioProducto();
        precioProducto2.setId(precioProducto1.getId());
        assertThat(precioProducto1).isEqualTo(precioProducto2);
        precioProducto2.setId(2L);
        assertThat(precioProducto1).isNotEqualTo(precioProducto2);
        precioProducto1.setId(null);
        assertThat(precioProducto1).isNotEqualTo(precioProducto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioProductoDTO.class);
        PrecioProductoDTO precioProductoDTO1 = new PrecioProductoDTO();
        precioProductoDTO1.setId(1L);
        PrecioProductoDTO precioProductoDTO2 = new PrecioProductoDTO();
        assertThat(precioProductoDTO1).isNotEqualTo(precioProductoDTO2);
        precioProductoDTO2.setId(precioProductoDTO1.getId());
        assertThat(precioProductoDTO1).isEqualTo(precioProductoDTO2);
        precioProductoDTO2.setId(2L);
        assertThat(precioProductoDTO1).isNotEqualTo(precioProductoDTO2);
        precioProductoDTO1.setId(null);
        assertThat(precioProductoDTO1).isNotEqualTo(precioProductoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(precioProductoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(precioProductoMapper.fromId(null)).isNull();
    }
}

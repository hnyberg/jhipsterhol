package com.mycompany.demo.jhipsterhol.web.rest;

import com.mycompany.demo.jhipsterhol.JhipsterholApp;
import com.mycompany.demo.jhipsterhol.domain.Type;
import com.mycompany.demo.jhipsterhol.repository.TypeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TypeResource REST controller.
 *
 * @see TypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterholApp.class)
@WebAppConfiguration
@IntegrationTest
public class TypeResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    @Inject
    private TypeRepository typeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTypeMockMvc;

    private Type type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TypeResource typeResource = new TypeResource();
        ReflectionTestUtils.setField(typeResource, "typeRepository", typeRepository);
        this.restTypeMockMvc = MockMvcBuilders.standaloneSetup(typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        type = new Type();
        type.setTitle(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createType() throws Exception {
        int databaseSizeBeforeCreate = typeRepository.findAll().size();

        // Create the Type

        restTypeMockMvc.perform(post("/api/types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type)))
                .andExpect(status().isCreated());

        // Validate the Type in the database
        List<Type> types = typeRepository.findAll();
        assertThat(types).hasSize(databaseSizeBeforeCreate + 1);
        Type testType = types.get(types.size() - 1);
        assertThat(testType.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeRepository.findAll().size();
        // set the field null
        type.setTitle(null);

        // Create the Type, which fails.

        restTypeMockMvc.perform(post("/api/types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type)))
                .andExpect(status().isBadRequest());

        List<Type> types = typeRepository.findAll();
        assertThat(types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypes() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        // Get all the types
        restTypeMockMvc.perform(get("/api/types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);

        // Get the type
        restTypeMockMvc.perform(get("/api/types/{id}", type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingType() throws Exception {
        // Get the type
        restTypeMockMvc.perform(get("/api/types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);
        int databaseSizeBeforeUpdate = typeRepository.findAll().size();

        // Update the type
        Type updatedType = new Type();
        updatedType.setId(type.getId());
        updatedType.setTitle(UPDATED_TITLE);

        restTypeMockMvc.perform(put("/api/types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedType)))
                .andExpect(status().isOk());

        // Validate the Type in the database
        List<Type> types = typeRepository.findAll();
        assertThat(types).hasSize(databaseSizeBeforeUpdate);
        Type testType = types.get(types.size() - 1);
        assertThat(testType.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void deleteType() throws Exception {
        // Initialize the database
        typeRepository.saveAndFlush(type);
        int databaseSizeBeforeDelete = typeRepository.findAll().size();

        // Get the type
        restTypeMockMvc.perform(delete("/api/types/{id}", type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type> types = typeRepository.findAll();
        assertThat(types).hasSize(databaseSizeBeforeDelete - 1);
    }
}

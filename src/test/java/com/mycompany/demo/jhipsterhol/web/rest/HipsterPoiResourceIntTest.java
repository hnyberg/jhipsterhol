package com.mycompany.demo.jhipsterhol.web.rest;

import com.mycompany.demo.jhipsterhol.Application;
import com.mycompany.demo.jhipsterhol.domain.HipsterPoi;
import com.mycompany.demo.jhipsterhol.repository.HipsterPoiRepository;

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
 * Test class for the HipsterPoiResource REST controller.
 *
 * @see HipsterPoiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HipsterPoiResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_ADRESS = "AAAAA";
    private static final String UPDATED_ADRESS = "BBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Inject
    private HipsterPoiRepository hipsterPoiRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHipsterPoiMockMvc;

    private HipsterPoi hipsterPoi;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HipsterPoiResource hipsterPoiResource = new HipsterPoiResource();
        ReflectionTestUtils.setField(hipsterPoiResource, "hipsterPoiRepository", hipsterPoiRepository);
        this.restHipsterPoiMockMvc = MockMvcBuilders.standaloneSetup(hipsterPoiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hipsterPoi = new HipsterPoi();
        hipsterPoi.setTitle(DEFAULT_TITLE);
        hipsterPoi.setAdress(DEFAULT_ADRESS);
        hipsterPoi.setLatitude(DEFAULT_LATITUDE);
        hipsterPoi.setLongitude(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createHipsterPoi() throws Exception {
        int databaseSizeBeforeCreate = hipsterPoiRepository.findAll().size();

        // Create the HipsterPoi

        restHipsterPoiMockMvc.perform(post("/api/hipsterPois")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipsterPoi)))
                .andExpect(status().isCreated());

        // Validate the HipsterPoi in the database
        List<HipsterPoi> hipsterPois = hipsterPoiRepository.findAll();
        assertThat(hipsterPois).hasSize(databaseSizeBeforeCreate + 1);
        HipsterPoi testHipsterPoi = hipsterPois.get(hipsterPois.size() - 1);
        assertThat(testHipsterPoi.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testHipsterPoi.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testHipsterPoi.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testHipsterPoi.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = hipsterPoiRepository.findAll().size();
        // set the field null
        hipsterPoi.setTitle(null);

        // Create the HipsterPoi, which fails.

        restHipsterPoiMockMvc.perform(post("/api/hipsterPois")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipsterPoi)))
                .andExpect(status().isBadRequest());

        List<HipsterPoi> hipsterPois = hipsterPoiRepository.findAll();
        assertThat(hipsterPois).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHipsterPois() throws Exception {
        // Initialize the database
        hipsterPoiRepository.saveAndFlush(hipsterPoi);

        // Get all the hipsterPois
        restHipsterPoiMockMvc.perform(get("/api/hipsterPois?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hipsterPoi.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getHipsterPoi() throws Exception {
        // Initialize the database
        hipsterPoiRepository.saveAndFlush(hipsterPoi);

        // Get the hipsterPoi
        restHipsterPoiMockMvc.perform(get("/api/hipsterPois/{id}", hipsterPoi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hipsterPoi.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHipsterPoi() throws Exception {
        // Get the hipsterPoi
        restHipsterPoiMockMvc.perform(get("/api/hipsterPois/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHipsterPoi() throws Exception {
        // Initialize the database
        hipsterPoiRepository.saveAndFlush(hipsterPoi);

		int databaseSizeBeforeUpdate = hipsterPoiRepository.findAll().size();

        // Update the hipsterPoi
        hipsterPoi.setTitle(UPDATED_TITLE);
        hipsterPoi.setAdress(UPDATED_ADRESS);
        hipsterPoi.setLatitude(UPDATED_LATITUDE);
        hipsterPoi.setLongitude(UPDATED_LONGITUDE);

        restHipsterPoiMockMvc.perform(put("/api/hipsterPois")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipsterPoi)))
                .andExpect(status().isOk());

        // Validate the HipsterPoi in the database
        List<HipsterPoi> hipsterPois = hipsterPoiRepository.findAll();
        assertThat(hipsterPois).hasSize(databaseSizeBeforeUpdate);
        HipsterPoi testHipsterPoi = hipsterPois.get(hipsterPois.size() - 1);
        assertThat(testHipsterPoi.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHipsterPoi.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testHipsterPoi.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testHipsterPoi.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void deleteHipsterPoi() throws Exception {
        // Initialize the database
        hipsterPoiRepository.saveAndFlush(hipsterPoi);

		int databaseSizeBeforeDelete = hipsterPoiRepository.findAll().size();

        // Get the hipsterPoi
        restHipsterPoiMockMvc.perform(delete("/api/hipsterPois/{id}", hipsterPoi.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HipsterPoi> hipsterPois = hipsterPoiRepository.findAll();
        assertThat(hipsterPois).hasSize(databaseSizeBeforeDelete - 1);
    }
}

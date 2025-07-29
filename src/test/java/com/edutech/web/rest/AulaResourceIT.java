package com.edutech.web.rest;

import static com.edutech.domain.AulaAsserts.*;
import static com.edutech.web.rest.TestUtil.createUpdateProxyForBean;
import static com.edutech.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edutech.IntegrationTest;
import com.edutech.domain.Aula;
import com.edutech.repository.AulaRepository;
import com.edutech.service.dto.AulaDTO;
import com.edutech.service.mapper.AulaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AulaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AulaResourceIT {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aulas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AulaMapper aulaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAulaMockMvc;

    private Aula aula;

    private Aula insertedAula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aula createEntity() {
        return new Aula().data(DEFAULT_DATA).titulo(DEFAULT_TITULO).conteudo(DEFAULT_CONTEUDO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aula createUpdatedEntity() {
        return new Aula().data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);
    }

    @BeforeEach
    void initTest() {
        aula = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAula != null) {
            aulaRepository.delete(insertedAula);
            insertedAula = null;
        }
    }

    @Test
    @Transactional
    void createAula() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);
        var returnedAulaDTO = om.readValue(
            restAulaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AulaDTO.class
        );

        // Validate the Aula in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAula = aulaMapper.toEntity(returnedAulaDTO);
        assertAulaUpdatableFieldsEquals(returnedAula, getPersistedAula(returnedAula));

        insertedAula = returnedAula;
    }

    @Test
    @Transactional
    void createAulaWithExistingId() throws Exception {
        // Create the Aula with an existing ID
        aula.setId(1L);
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aula.setData(null);

        // Create the Aula, which fails.
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        restAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aula.setTitulo(null);

        // Create the Aula, which fails.
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        restAulaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAulas() throws Exception {
        // Initialize the database
        insertedAula = aulaRepository.saveAndFlush(aula);

        // Get all the aulaList
        restAulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aula.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)));
    }

    @Test
    @Transactional
    void getAula() throws Exception {
        // Initialize the database
        insertedAula = aulaRepository.saveAndFlush(aula);

        // Get the aula
        restAulaMockMvc
            .perform(get(ENTITY_API_URL_ID, aula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aula.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO));
    }

    @Test
    @Transactional
    void getNonExistingAula() throws Exception {
        // Get the aula
        restAulaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAula() throws Exception {
        // Initialize the database
        insertedAula = aulaRepository.saveAndFlush(aula);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aula
        Aula updatedAula = aulaRepository.findById(aula.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAula are not directly saved in db
        em.detach(updatedAula);
        updatedAula.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);
        AulaDTO aulaDTO = aulaMapper.toDto(updatedAula);

        restAulaMockMvc
            .perform(put(ENTITY_API_URL_ID, aulaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isOk());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAulaToMatchAllProperties(updatedAula);
    }

    @Test
    @Transactional
    void putNonExistingAula() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aula.setId(longCount.incrementAndGet());

        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAulaMockMvc
            .perform(put(ENTITY_API_URL_ID, aulaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAula() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aula.setId(longCount.incrementAndGet());

        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAula() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aula.setId(longCount.incrementAndGet());

        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAulaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAulaWithPatch() throws Exception {
        // Initialize the database
        insertedAula = aulaRepository.saveAndFlush(aula);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aula using partial update
        Aula partialUpdatedAula = new Aula();
        partialUpdatedAula.setId(aula.getId());

        partialUpdatedAula.data(UPDATED_DATA);

        restAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAula.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAula))
            )
            .andExpect(status().isOk());

        // Validate the Aula in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAulaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAula, aula), getPersistedAula(aula));
    }

    @Test
    @Transactional
    void fullUpdateAulaWithPatch() throws Exception {
        // Initialize the database
        insertedAula = aulaRepository.saveAndFlush(aula);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aula using partial update
        Aula partialUpdatedAula = new Aula();
        partialUpdatedAula.setId(aula.getId());

        partialUpdatedAula.data(UPDATED_DATA).titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO);

        restAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAula.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAula))
            )
            .andExpect(status().isOk());

        // Validate the Aula in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAulaUpdatableFieldsEquals(partialUpdatedAula, getPersistedAula(partialUpdatedAula));
    }

    @Test
    @Transactional
    void patchNonExistingAula() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aula.setId(longCount.incrementAndGet());

        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aulaDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAula() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aula.setId(longCount.incrementAndGet());

        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAula() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aula.setId(longCount.incrementAndGet());

        // Create the Aula
        AulaDTO aulaDTO = aulaMapper.toDto(aula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAulaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aulaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aula in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAula() throws Exception {
        // Initialize the database
        insertedAula = aulaRepository.saveAndFlush(aula);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aula
        restAulaMockMvc
            .perform(delete(ENTITY_API_URL_ID, aula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aulaRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Aula getPersistedAula(Aula aula) {
        return aulaRepository.findById(aula.getId()).orElseThrow();
    }

    protected void assertPersistedAulaToMatchAllProperties(Aula expectedAula) {
        assertAulaAllPropertiesEquals(expectedAula, getPersistedAula(expectedAula));
    }

    protected void assertPersistedAulaToMatchUpdatableProperties(Aula expectedAula) {
        assertAulaAllUpdatablePropertiesEquals(expectedAula, getPersistedAula(expectedAula));
    }
}

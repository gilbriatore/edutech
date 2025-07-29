package com.edutech.web.rest;

import static com.edutech.domain.InscricaoAsserts.*;
import static com.edutech.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edutech.IntegrationTest;
import com.edutech.domain.Inscricao;
import com.edutech.repository.InscricaoRepository;
import com.edutech.service.dto.InscricaoDTO;
import com.edutech.service.mapper.InscricaoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link InscricaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InscricaoResourceIT {

    private static final Instant DEFAULT_DATA_INSCRICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INSCRICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/inscricaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private InscricaoMapper inscricaoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInscricaoMockMvc;

    private Inscricao inscricao;

    private Inscricao insertedInscricao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscricao createEntity() {
        return new Inscricao().dataInscricao(DEFAULT_DATA_INSCRICAO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscricao createUpdatedEntity() {
        return new Inscricao().dataInscricao(UPDATED_DATA_INSCRICAO);
    }

    @BeforeEach
    void initTest() {
        inscricao = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInscricao != null) {
            inscricaoRepository.delete(insertedInscricao);
            insertedInscricao = null;
        }
    }

    @Test
    @Transactional
    void createInscricao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);
        var returnedInscricaoDTO = om.readValue(
            restInscricaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscricaoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InscricaoDTO.class
        );

        // Validate the Inscricao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInscricao = inscricaoMapper.toEntity(returnedInscricaoDTO);
        assertInscricaoUpdatableFieldsEquals(returnedInscricao, getPersistedInscricao(returnedInscricao));

        insertedInscricao = returnedInscricao;
    }

    @Test
    @Transactional
    void createInscricaoWithExistingId() throws Exception {
        // Create the Inscricao with an existing ID
        inscricao.setId(1L);
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscricaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscricaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataInscricaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inscricao.setDataInscricao(null);

        // Create the Inscricao, which fails.
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        restInscricaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscricaoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInscricaos() throws Exception {
        // Initialize the database
        insertedInscricao = inscricaoRepository.saveAndFlush(inscricao);

        // Get all the inscricaoList
        restInscricaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscricao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInscricao").value(hasItem(DEFAULT_DATA_INSCRICAO.toString())));
    }

    @Test
    @Transactional
    void getInscricao() throws Exception {
        // Initialize the database
        insertedInscricao = inscricaoRepository.saveAndFlush(inscricao);

        // Get the inscricao
        restInscricaoMockMvc
            .perform(get(ENTITY_API_URL_ID, inscricao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscricao.getId().intValue()))
            .andExpect(jsonPath("$.dataInscricao").value(DEFAULT_DATA_INSCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInscricao() throws Exception {
        // Get the inscricao
        restInscricaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInscricao() throws Exception {
        // Initialize the database
        insertedInscricao = inscricaoRepository.saveAndFlush(inscricao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inscricao
        Inscricao updatedInscricao = inscricaoRepository.findById(inscricao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInscricao are not directly saved in db
        em.detach(updatedInscricao);
        updatedInscricao.dataInscricao(UPDATED_DATA_INSCRICAO);
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(updatedInscricao);

        restInscricaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscricaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inscricaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInscricaoToMatchAllProperties(updatedInscricao);
    }

    @Test
    @Transactional
    void putNonExistingInscricao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscricao.setId(longCount.incrementAndGet());

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscricaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscricaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inscricaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInscricao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscricao.setId(longCount.incrementAndGet());

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inscricaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInscricao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscricao.setId(longCount.incrementAndGet());

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inscricaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInscricaoWithPatch() throws Exception {
        // Initialize the database
        insertedInscricao = inscricaoRepository.saveAndFlush(inscricao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inscricao using partial update
        Inscricao partialUpdatedInscricao = new Inscricao();
        partialUpdatedInscricao.setId(inscricao.getId());

        partialUpdatedInscricao.dataInscricao(UPDATED_DATA_INSCRICAO);

        restInscricaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscricao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInscricao))
            )
            .andExpect(status().isOk());

        // Validate the Inscricao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInscricaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInscricao, inscricao),
            getPersistedInscricao(inscricao)
        );
    }

    @Test
    @Transactional
    void fullUpdateInscricaoWithPatch() throws Exception {
        // Initialize the database
        insertedInscricao = inscricaoRepository.saveAndFlush(inscricao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inscricao using partial update
        Inscricao partialUpdatedInscricao = new Inscricao();
        partialUpdatedInscricao.setId(inscricao.getId());

        partialUpdatedInscricao.dataInscricao(UPDATED_DATA_INSCRICAO);

        restInscricaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscricao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInscricao))
            )
            .andExpect(status().isOk());

        // Validate the Inscricao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInscricaoUpdatableFieldsEquals(partialUpdatedInscricao, getPersistedInscricao(partialUpdatedInscricao));
    }

    @Test
    @Transactional
    void patchNonExistingInscricao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscricao.setId(longCount.incrementAndGet());

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscricaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inscricaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inscricaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInscricao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscricao.setId(longCount.incrementAndGet());

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inscricaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInscricao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inscricao.setId(longCount.incrementAndGet());

        // Create the Inscricao
        InscricaoDTO inscricaoDTO = inscricaoMapper.toDto(inscricao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscricaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inscricaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscricao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInscricao() throws Exception {
        // Initialize the database
        insertedInscricao = inscricaoRepository.saveAndFlush(inscricao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inscricao
        restInscricaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, inscricao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inscricaoRepository.count();
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

    protected Inscricao getPersistedInscricao(Inscricao inscricao) {
        return inscricaoRepository.findById(inscricao.getId()).orElseThrow();
    }

    protected void assertPersistedInscricaoToMatchAllProperties(Inscricao expectedInscricao) {
        assertInscricaoAllPropertiesEquals(expectedInscricao, getPersistedInscricao(expectedInscricao));
    }

    protected void assertPersistedInscricaoToMatchUpdatableProperties(Inscricao expectedInscricao) {
        assertInscricaoAllUpdatablePropertiesEquals(expectedInscricao, getPersistedInscricao(expectedInscricao));
    }
}

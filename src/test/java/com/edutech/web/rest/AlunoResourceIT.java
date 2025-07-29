package com.edutech.web.rest;

import static com.edutech.domain.AlunoAsserts.*;
import static com.edutech.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edutech.IntegrationTest;
import com.edutech.domain.Aluno;
import com.edutech.repository.AlunoRepository;
import com.edutech.service.dto.AlunoDTO;
import com.edutech.service.mapper.AlunoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AlunoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlunoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/alunos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    private Aluno insertedAluno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createEntity() {
        return new Aluno().nome(DEFAULT_NOME).email(DEFAULT_EMAIL).matricula(DEFAULT_MATRICULA).dataNascimento(DEFAULT_DATA_NASCIMENTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createUpdatedEntity() {
        return new Aluno().nome(UPDATED_NOME).email(UPDATED_EMAIL).matricula(UPDATED_MATRICULA).dataNascimento(UPDATED_DATA_NASCIMENTO);
    }

    @BeforeEach
    void initTest() {
        aluno = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAluno != null) {
            alunoRepository.delete(insertedAluno);
            insertedAluno = null;
        }
    }

    @Test
    @Transactional
    void createAluno() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);
        var returnedAlunoDTO = om.readValue(
            restAlunoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AlunoDTO.class
        );

        // Validate the Aluno in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAluno = alunoMapper.toEntity(returnedAlunoDTO);
        assertAlunoUpdatableFieldsEquals(returnedAluno, getPersistedAluno(returnedAluno));

        insertedAluno = returnedAluno;
    }

    @Test
    @Transactional
    void createAlunoWithExistingId() throws Exception {
        // Create the Aluno with an existing ID
        aluno.setId(1L);
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aluno.setNome(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aluno.setEmail(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatriculaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        aluno.setMatricula(null);

        // Create the Aluno, which fails.
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        restAlunoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlunos() throws Exception {
        // Initialize the database
        insertedAluno = alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList
        restAlunoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())));
    }

    @Test
    @Transactional
    void getAluno() throws Exception {
        // Initialize the database
        insertedAluno = alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc
            .perform(get(ENTITY_API_URL_ID, aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAluno() throws Exception {
        // Initialize the database
        insertedAluno = alunoRepository.saveAndFlush(aluno);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findById(aluno.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAluno are not directly saved in db
        em.detach(updatedAluno);
        updatedAluno.nome(UPDATED_NOME).email(UPDATED_EMAIL).matricula(UPDATED_MATRICULA).dataNascimento(UPDATED_DATA_NASCIMENTO);
        AlunoDTO alunoDTO = alunoMapper.toDto(updatedAluno);

        restAlunoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alunoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAlunoToMatchAllProperties(updatedAluno);
    }

    @Test
    @Transactional
    void putNonExistingAluno() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aluno.setId(longCount.incrementAndGet());

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alunoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAluno() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aluno.setId(longCount.incrementAndGet());

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alunoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAluno() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aluno.setId(longCount.incrementAndGet());

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alunoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlunoWithPatch() throws Exception {
        // Initialize the database
        insertedAluno = alunoRepository.saveAndFlush(aluno);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aluno using partial update
        Aluno partialUpdatedAluno = new Aluno();
        partialUpdatedAluno.setId(aluno.getId());

        partialUpdatedAluno.nome(UPDATED_NOME).email(UPDATED_EMAIL);

        restAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAluno.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAluno))
            )
            .andExpect(status().isOk());

        // Validate the Aluno in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlunoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAluno, aluno), getPersistedAluno(aluno));
    }

    @Test
    @Transactional
    void fullUpdateAlunoWithPatch() throws Exception {
        // Initialize the database
        insertedAluno = alunoRepository.saveAndFlush(aluno);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aluno using partial update
        Aluno partialUpdatedAluno = new Aluno();
        partialUpdatedAluno.setId(aluno.getId());

        partialUpdatedAluno.nome(UPDATED_NOME).email(UPDATED_EMAIL).matricula(UPDATED_MATRICULA).dataNascimento(UPDATED_DATA_NASCIMENTO);

        restAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAluno.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAluno))
            )
            .andExpect(status().isOk());

        // Validate the Aluno in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlunoUpdatableFieldsEquals(partialUpdatedAluno, getPersistedAluno(partialUpdatedAluno));
    }

    @Test
    @Transactional
    void patchNonExistingAluno() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aluno.setId(longCount.incrementAndGet());

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alunoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alunoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAluno() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aluno.setId(longCount.incrementAndGet());

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alunoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAluno() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aluno.setId(longCount.incrementAndGet());

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlunoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(alunoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aluno in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAluno() throws Exception {
        // Initialize the database
        insertedAluno = alunoRepository.saveAndFlush(aluno);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aluno
        restAlunoMockMvc
            .perform(delete(ENTITY_API_URL_ID, aluno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return alunoRepository.count();
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

    protected Aluno getPersistedAluno(Aluno aluno) {
        return alunoRepository.findById(aluno.getId()).orElseThrow();
    }

    protected void assertPersistedAlunoToMatchAllProperties(Aluno expectedAluno) {
        assertAlunoAllPropertiesEquals(expectedAluno, getPersistedAluno(expectedAluno));
    }

    protected void assertPersistedAlunoToMatchUpdatableProperties(Aluno expectedAluno) {
        assertAlunoAllUpdatablePropertiesEquals(expectedAluno, getPersistedAluno(expectedAluno));
    }
}

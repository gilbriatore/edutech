package com.edutech.web.rest;

import com.edutech.repository.AlunoRepository;
import com.edutech.service.AlunoService;
import com.edutech.service.dto.AlunoDTO;
import com.edutech.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.edutech.domain.Aluno}.
 */
@RestController
@RequestMapping("/api/alunos")
public class AlunoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AlunoResource.class);

    private static final String ENTITY_NAME = "aluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlunoService alunoService;

    private final AlunoRepository alunoRepository;

    public AlunoResource(AlunoService alunoService, AlunoRepository alunoRepository) {
        this.alunoService = alunoService;
        this.alunoRepository = alunoRepository;
    }

    /**
     * {@code POST  /alunos} : Create a new aluno.
     *
     * @param alunoDTO the alunoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alunoDTO, or with status {@code 400 (Bad Request)} if the aluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AlunoDTO> createAluno(@Valid @RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Aluno : {}", alunoDTO);
        if (alunoDTO.getId() != null) {
            throw new BadRequestAlertException("A new aluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        alunoDTO = alunoService.save(alunoDTO);
        return ResponseEntity.created(new URI("/api/alunos/" + alunoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, alunoDTO.getId().toString()))
            .body(alunoDTO);
    }

    /**
     * {@code PUT  /alunos/:id} : Updates an existing aluno.
     *
     * @param id the id of the alunoDTO to save.
     * @param alunoDTO the alunoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunoDTO,
     * or with status {@code 400 (Bad Request)} if the alunoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alunoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateAluno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlunoDTO alunoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Aluno : {}, {}", id, alunoDTO);
        if (alunoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alunoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alunoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        alunoDTO = alunoService.update(alunoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alunoDTO.getId().toString()))
            .body(alunoDTO);
    }

    /**
     * {@code PATCH  /alunos/:id} : Partial updates given fields of an existing aluno, field will ignore if it is null
     *
     * @param id the id of the alunoDTO to save.
     * @param alunoDTO the alunoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunoDTO,
     * or with status {@code 400 (Bad Request)} if the alunoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alunoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alunoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlunoDTO> partialUpdateAluno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlunoDTO alunoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Aluno partially : {}, {}", id, alunoDTO);
        if (alunoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alunoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alunoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlunoDTO> result = alunoService.partialUpdate(alunoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alunoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alunos} : get all the alunos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alunos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AlunoDTO>> getAllAlunos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Alunos");
        Page<AlunoDTO> page = alunoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alunos/:id} : get the "id" aluno.
     *
     * @param id the id of the alunoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alunoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Aluno : {}", id);
        Optional<AlunoDTO> alunoDTO = alunoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alunoDTO);
    }

    /**
     * {@code DELETE  /alunos/:id} : delete the "id" aluno.
     *
     * @param id the id of the alunoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Aluno : {}", id);
        alunoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

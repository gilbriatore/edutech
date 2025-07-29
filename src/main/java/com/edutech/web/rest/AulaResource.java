package com.edutech.web.rest;

import com.edutech.repository.AulaRepository;
import com.edutech.service.AulaService;
import com.edutech.service.dto.AulaDTO;
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
 * REST controller for managing {@link com.edutech.domain.Aula}.
 */
@RestController
@RequestMapping("/api/aulas")
public class AulaResource {

    private static final Logger LOG = LoggerFactory.getLogger(AulaResource.class);

    private static final String ENTITY_NAME = "aula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AulaService aulaService;

    private final AulaRepository aulaRepository;

    public AulaResource(AulaService aulaService, AulaRepository aulaRepository) {
        this.aulaService = aulaService;
        this.aulaRepository = aulaRepository;
    }

    /**
     * {@code POST  /aulas} : Create a new aula.
     *
     * @param aulaDTO the aulaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aulaDTO, or with status {@code 400 (Bad Request)} if the aula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AulaDTO> createAula(@Valid @RequestBody AulaDTO aulaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Aula : {}", aulaDTO);
        if (aulaDTO.getId() != null) {
            throw new BadRequestAlertException("A new aula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aulaDTO = aulaService.save(aulaDTO);
        return ResponseEntity.created(new URI("/api/aulas/" + aulaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, aulaDTO.getId().toString()))
            .body(aulaDTO);
    }

    /**
     * {@code PUT  /aulas/:id} : Updates an existing aula.
     *
     * @param id the id of the aulaDTO to save.
     * @param aulaDTO the aulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aulaDTO,
     * or with status {@code 400 (Bad Request)} if the aulaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AulaDTO> updateAula(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AulaDTO aulaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Aula : {}, {}", id, aulaDTO);
        if (aulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aulaDTO = aulaService.update(aulaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aulaDTO.getId().toString()))
            .body(aulaDTO);
    }

    /**
     * {@code PATCH  /aulas/:id} : Partial updates given fields of an existing aula, field will ignore if it is null
     *
     * @param id the id of the aulaDTO to save.
     * @param aulaDTO the aulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aulaDTO,
     * or with status {@code 400 (Bad Request)} if the aulaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the aulaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AulaDTO> partialUpdateAula(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AulaDTO aulaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Aula partially : {}, {}", id, aulaDTO);
        if (aulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AulaDTO> result = aulaService.partialUpdate(aulaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aulaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /aulas} : get all the aulas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aulas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AulaDTO>> getAllAulas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Aulas");
        Page<AulaDTO> page = aulaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aulas/:id} : get the "id" aula.
     *
     * @param id the id of the aulaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aulaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> getAula(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Aula : {}", id);
        Optional<AulaDTO> aulaDTO = aulaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aulaDTO);
    }

    /**
     * {@code DELETE  /aulas/:id} : delete the "id" aula.
     *
     * @param id the id of the aulaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Aula : {}", id);
        aulaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

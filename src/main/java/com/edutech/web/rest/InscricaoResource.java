package com.edutech.web.rest;

import com.edutech.repository.InscricaoRepository;
import com.edutech.service.InscricaoService;
import com.edutech.service.dto.InscricaoDTO;
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
 * REST controller for managing {@link com.edutech.domain.Inscricao}.
 */
@RestController
@RequestMapping("/api/inscricaos")
public class InscricaoResource {

    private static final Logger LOG = LoggerFactory.getLogger(InscricaoResource.class);

    private static final String ENTITY_NAME = "inscricao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InscricaoService inscricaoService;

    private final InscricaoRepository inscricaoRepository;

    public InscricaoResource(InscricaoService inscricaoService, InscricaoRepository inscricaoRepository) {
        this.inscricaoService = inscricaoService;
        this.inscricaoRepository = inscricaoRepository;
    }

    /**
     * {@code POST  /inscricaos} : Create a new inscricao.
     *
     * @param inscricaoDTO the inscricaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inscricaoDTO, or with status {@code 400 (Bad Request)} if the inscricao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InscricaoDTO> createInscricao(@Valid @RequestBody InscricaoDTO inscricaoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Inscricao : {}", inscricaoDTO);
        if (inscricaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new inscricao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inscricaoDTO = inscricaoService.save(inscricaoDTO);
        return ResponseEntity.created(new URI("/api/inscricaos/" + inscricaoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inscricaoDTO.getId().toString()))
            .body(inscricaoDTO);
    }

    /**
     * {@code PUT  /inscricaos/:id} : Updates an existing inscricao.
     *
     * @param id the id of the inscricaoDTO to save.
     * @param inscricaoDTO the inscricaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscricaoDTO,
     * or with status {@code 400 (Bad Request)} if the inscricaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inscricaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InscricaoDTO> updateInscricao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InscricaoDTO inscricaoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Inscricao : {}, {}", id, inscricaoDTO);
        if (inscricaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscricaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscricaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inscricaoDTO = inscricaoService.update(inscricaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inscricaoDTO.getId().toString()))
            .body(inscricaoDTO);
    }

    /**
     * {@code PATCH  /inscricaos/:id} : Partial updates given fields of an existing inscricao, field will ignore if it is null
     *
     * @param id the id of the inscricaoDTO to save.
     * @param inscricaoDTO the inscricaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscricaoDTO,
     * or with status {@code 400 (Bad Request)} if the inscricaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inscricaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inscricaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InscricaoDTO> partialUpdateInscricao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InscricaoDTO inscricaoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Inscricao partially : {}, {}", id, inscricaoDTO);
        if (inscricaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscricaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscricaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InscricaoDTO> result = inscricaoService.partialUpdate(inscricaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inscricaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inscricaos} : get all the inscricaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inscricaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InscricaoDTO>> getAllInscricaos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Inscricaos");
        Page<InscricaoDTO> page = inscricaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inscricaos/:id} : get the "id" inscricao.
     *
     * @param id the id of the inscricaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inscricaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InscricaoDTO> getInscricao(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Inscricao : {}", id);
        Optional<InscricaoDTO> inscricaoDTO = inscricaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscricaoDTO);
    }

    /**
     * {@code DELETE  /inscricaos/:id} : delete the "id" inscricao.
     *
     * @param id the id of the inscricaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscricao(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Inscricao : {}", id);
        inscricaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

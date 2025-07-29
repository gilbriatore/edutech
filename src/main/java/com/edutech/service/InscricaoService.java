package com.edutech.service;

import com.edutech.domain.Inscricao;
import com.edutech.repository.InscricaoRepository;
import com.edutech.service.dto.InscricaoDTO;
import com.edutech.service.mapper.InscricaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.edutech.domain.Inscricao}.
 */
@Service
@Transactional
public class InscricaoService {

    private static final Logger LOG = LoggerFactory.getLogger(InscricaoService.class);

    private final InscricaoRepository inscricaoRepository;

    private final InscricaoMapper inscricaoMapper;

    public InscricaoService(InscricaoRepository inscricaoRepository, InscricaoMapper inscricaoMapper) {
        this.inscricaoRepository = inscricaoRepository;
        this.inscricaoMapper = inscricaoMapper;
    }

    /**
     * Save a inscricao.
     *
     * @param inscricaoDTO the entity to save.
     * @return the persisted entity.
     */
    public InscricaoDTO save(InscricaoDTO inscricaoDTO) {
        LOG.debug("Request to save Inscricao : {}", inscricaoDTO);
        Inscricao inscricao = inscricaoMapper.toEntity(inscricaoDTO);
        inscricao = inscricaoRepository.save(inscricao);
        return inscricaoMapper.toDto(inscricao);
    }

    /**
     * Update a inscricao.
     *
     * @param inscricaoDTO the entity to save.
     * @return the persisted entity.
     */
    public InscricaoDTO update(InscricaoDTO inscricaoDTO) {
        LOG.debug("Request to update Inscricao : {}", inscricaoDTO);
        Inscricao inscricao = inscricaoMapper.toEntity(inscricaoDTO);
        inscricao = inscricaoRepository.save(inscricao);
        return inscricaoMapper.toDto(inscricao);
    }

    /**
     * Partially update a inscricao.
     *
     * @param inscricaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InscricaoDTO> partialUpdate(InscricaoDTO inscricaoDTO) {
        LOG.debug("Request to partially update Inscricao : {}", inscricaoDTO);

        return inscricaoRepository
            .findById(inscricaoDTO.getId())
            .map(existingInscricao -> {
                inscricaoMapper.partialUpdate(existingInscricao, inscricaoDTO);

                return existingInscricao;
            })
            .map(inscricaoRepository::save)
            .map(inscricaoMapper::toDto);
    }

    /**
     * Get all the inscricaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InscricaoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Inscricaos");
        return inscricaoRepository.findAll(pageable).map(inscricaoMapper::toDto);
    }

    /**
     * Get one inscricao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InscricaoDTO> findOne(Long id) {
        LOG.debug("Request to get Inscricao : {}", id);
        return inscricaoRepository.findById(id).map(inscricaoMapper::toDto);
    }

    /**
     * Delete the inscricao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Inscricao : {}", id);
        inscricaoRepository.deleteById(id);
    }
}

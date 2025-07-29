package com.edutech.service;

import com.edutech.domain.Aula;
import com.edutech.repository.AulaRepository;
import com.edutech.service.dto.AulaDTO;
import com.edutech.service.mapper.AulaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.edutech.domain.Aula}.
 */
@Service
@Transactional
public class AulaService {

    private static final Logger LOG = LoggerFactory.getLogger(AulaService.class);

    private final AulaRepository aulaRepository;

    private final AulaMapper aulaMapper;

    public AulaService(AulaRepository aulaRepository, AulaMapper aulaMapper) {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
    }

    /**
     * Save a aula.
     *
     * @param aulaDTO the entity to save.
     * @return the persisted entity.
     */
    public AulaDTO save(AulaDTO aulaDTO) {
        LOG.debug("Request to save Aula : {}", aulaDTO);
        Aula aula = aulaMapper.toEntity(aulaDTO);
        aula = aulaRepository.save(aula);
        return aulaMapper.toDto(aula);
    }

    /**
     * Update a aula.
     *
     * @param aulaDTO the entity to save.
     * @return the persisted entity.
     */
    public AulaDTO update(AulaDTO aulaDTO) {
        LOG.debug("Request to update Aula : {}", aulaDTO);
        Aula aula = aulaMapper.toEntity(aulaDTO);
        aula = aulaRepository.save(aula);
        return aulaMapper.toDto(aula);
    }

    /**
     * Partially update a aula.
     *
     * @param aulaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AulaDTO> partialUpdate(AulaDTO aulaDTO) {
        LOG.debug("Request to partially update Aula : {}", aulaDTO);

        return aulaRepository
            .findById(aulaDTO.getId())
            .map(existingAula -> {
                aulaMapper.partialUpdate(existingAula, aulaDTO);

                return existingAula;
            })
            .map(aulaRepository::save)
            .map(aulaMapper::toDto);
    }

    /**
     * Get all the aulas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AulaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Aulas");
        return aulaRepository.findAll(pageable).map(aulaMapper::toDto);
    }

    /**
     * Get one aula by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AulaDTO> findOne(Long id) {
        LOG.debug("Request to get Aula : {}", id);
        return aulaRepository.findById(id).map(aulaMapper::toDto);
    }

    /**
     * Delete the aula by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Aula : {}", id);
        aulaRepository.deleteById(id);
    }
}

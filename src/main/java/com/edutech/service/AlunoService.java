package com.edutech.service;

import com.edutech.domain.Aluno;
import com.edutech.repository.AlunoRepository;
import com.edutech.service.dto.AlunoDTO;
import com.edutech.service.mapper.AlunoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.edutech.domain.Aluno}.
 */
@Service
@Transactional
public class AlunoService {

    private static final Logger LOG = LoggerFactory.getLogger(AlunoService.class);

    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save.
     * @return the persisted entity.
     */
    public AlunoDTO save(AlunoDTO alunoDTO) {
        LOG.debug("Request to save Aluno : {}", alunoDTO);
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDto(aluno);
    }

    /**
     * Update a aluno.
     *
     * @param alunoDTO the entity to save.
     * @return the persisted entity.
     */
    public AlunoDTO update(AlunoDTO alunoDTO) {
        LOG.debug("Request to update Aluno : {}", alunoDTO);
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDto(aluno);
    }

    /**
     * Partially update a aluno.
     *
     * @param alunoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AlunoDTO> partialUpdate(AlunoDTO alunoDTO) {
        LOG.debug("Request to partially update Aluno : {}", alunoDTO);

        return alunoRepository
            .findById(alunoDTO.getId())
            .map(existingAluno -> {
                alunoMapper.partialUpdate(existingAluno, alunoDTO);

                return existingAluno;
            })
            .map(alunoRepository::save)
            .map(alunoMapper::toDto);
    }

    /**
     * Get all the alunos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlunoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Alunos");
        return alunoRepository.findAll(pageable).map(alunoMapper::toDto);
    }

    /**
     * Get one aluno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlunoDTO> findOne(Long id) {
        LOG.debug("Request to get Aluno : {}", id);
        return alunoRepository.findById(id).map(alunoMapper::toDto);
    }

    /**
     * Delete the aluno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Aluno : {}", id);
        alunoRepository.deleteById(id);
    }
}

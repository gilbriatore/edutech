package com.edutech.service;

import com.edutech.domain.Curso;
import com.edutech.repository.CursoRepository;
import com.edutech.service.dto.CursoDTO;
import com.edutech.service.mapper.CursoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.edutech.domain.Curso}.
 */
@Service
@Transactional
public class CursoService {

    private static final Logger LOG = LoggerFactory.getLogger(CursoService.class);

    private final CursoRepository cursoRepository;

    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    /**
     * Save a curso.
     *
     * @param cursoDTO the entity to save.
     * @return the persisted entity.
     */
    public CursoDTO save(CursoDTO cursoDTO) {
        LOG.debug("Request to save Curso : {}", cursoDTO);
        Curso curso = cursoMapper.toEntity(cursoDTO);
        curso = cursoRepository.save(curso);
        return cursoMapper.toDto(curso);
    }

    /**
     * Update a curso.
     *
     * @param cursoDTO the entity to save.
     * @return the persisted entity.
     */
    public CursoDTO update(CursoDTO cursoDTO) {
        LOG.debug("Request to update Curso : {}", cursoDTO);
        Curso curso = cursoMapper.toEntity(cursoDTO);
        curso = cursoRepository.save(curso);
        return cursoMapper.toDto(curso);
    }

    /**
     * Partially update a curso.
     *
     * @param cursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CursoDTO> partialUpdate(CursoDTO cursoDTO) {
        LOG.debug("Request to partially update Curso : {}", cursoDTO);

        return cursoRepository
            .findById(cursoDTO.getId())
            .map(existingCurso -> {
                cursoMapper.partialUpdate(existingCurso, cursoDTO);

                return existingCurso;
            })
            .map(cursoRepository::save)
            .map(cursoMapper::toDto);
    }

    /**
     * Get all the cursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CursoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Cursos");
        return cursoRepository.findAll(pageable).map(cursoMapper::toDto);
    }

    /**
     * Get one curso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CursoDTO> findOne(Long id) {
        LOG.debug("Request to get Curso : {}", id);
        return cursoRepository.findById(id).map(cursoMapper::toDto);
    }

    /**
     * Delete the curso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Curso : {}", id);
        cursoRepository.deleteById(id);
    }
}

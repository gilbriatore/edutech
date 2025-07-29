package com.edutech.service.mapper;

import com.edutech.domain.Curso;
import com.edutech.domain.Professor;
import com.edutech.service.dto.CursoDTO;
import com.edutech.service.dto.ProfessorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {
    @Mapping(target = "professor", source = "professor", qualifiedByName = "professorId")
    CursoDTO toDto(Curso s);

    @Named("professorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfessorDTO toDtoProfessorId(Professor professor);
}

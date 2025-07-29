package com.edutech.service.mapper;

import com.edutech.domain.Aula;
import com.edutech.domain.Curso;
import com.edutech.service.dto.AulaDTO;
import com.edutech.service.dto.CursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aula} and its DTO {@link AulaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AulaMapper extends EntityMapper<AulaDTO, Aula> {
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoId")
    AulaDTO toDto(Aula s);

    @Named("cursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CursoDTO toDtoCursoId(Curso curso);
}

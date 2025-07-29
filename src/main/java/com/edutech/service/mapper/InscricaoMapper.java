package com.edutech.service.mapper;

import com.edutech.domain.Aluno;
import com.edutech.domain.Curso;
import com.edutech.domain.Inscricao;
import com.edutech.service.dto.AlunoDTO;
import com.edutech.service.dto.CursoDTO;
import com.edutech.service.dto.InscricaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inscricao} and its DTO {@link InscricaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface InscricaoMapper extends EntityMapper<InscricaoDTO, Inscricao> {
    @Mapping(target = "aluno", source = "aluno", qualifiedByName = "alunoId")
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoId")
    InscricaoDTO toDto(Inscricao s);

    @Named("alunoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlunoDTO toDtoAlunoId(Aluno aluno);

    @Named("cursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CursoDTO toDtoCursoId(Curso curso);
}

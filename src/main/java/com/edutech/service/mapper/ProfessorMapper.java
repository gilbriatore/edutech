package com.edutech.service.mapper;

import com.edutech.domain.Professor;
import com.edutech.service.dto.ProfessorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professor} and its DTO {@link ProfessorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfessorMapper extends EntityMapper<ProfessorDTO, Professor> {}

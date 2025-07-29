package com.edutech.service.mapper;

import static com.edutech.domain.CursoAsserts.*;
import static com.edutech.domain.CursoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CursoMapperTest {

    private CursoMapper cursoMapper;

    @BeforeEach
    void setUp() {
        cursoMapper = new CursoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCursoSample1();
        var actual = cursoMapper.toEntity(cursoMapper.toDto(expected));
        assertCursoAllPropertiesEquals(expected, actual);
    }
}

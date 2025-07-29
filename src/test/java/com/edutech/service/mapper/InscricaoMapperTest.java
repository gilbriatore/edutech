package com.edutech.service.mapper;

import static com.edutech.domain.InscricaoAsserts.*;
import static com.edutech.domain.InscricaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InscricaoMapperTest {

    private InscricaoMapper inscricaoMapper;

    @BeforeEach
    void setUp() {
        inscricaoMapper = new InscricaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInscricaoSample1();
        var actual = inscricaoMapper.toEntity(inscricaoMapper.toDto(expected));
        assertInscricaoAllPropertiesEquals(expected, actual);
    }
}

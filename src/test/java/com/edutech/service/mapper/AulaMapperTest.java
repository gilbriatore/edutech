package com.edutech.service.mapper;

import static com.edutech.domain.AulaAsserts.*;
import static com.edutech.domain.AulaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AulaMapperTest {

    private AulaMapper aulaMapper;

    @BeforeEach
    void setUp() {
        aulaMapper = new AulaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAulaSample1();
        var actual = aulaMapper.toEntity(aulaMapper.toDto(expected));
        assertAulaAllPropertiesEquals(expected, actual);
    }
}

package com.edutech.domain;

import static com.edutech.domain.AulaTestSamples.*;
import static com.edutech.domain.CursoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AulaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aula.class);
        Aula aula1 = getAulaSample1();
        Aula aula2 = new Aula();
        assertThat(aula1).isNotEqualTo(aula2);

        aula2.setId(aula1.getId());
        assertThat(aula1).isEqualTo(aula2);

        aula2 = getAulaSample2();
        assertThat(aula1).isNotEqualTo(aula2);
    }

    @Test
    void cursoTest() {
        Aula aula = getAulaRandomSampleGenerator();
        Curso cursoBack = getCursoRandomSampleGenerator();

        aula.setCurso(cursoBack);
        assertThat(aula.getCurso()).isEqualTo(cursoBack);

        aula.curso(null);
        assertThat(aula.getCurso()).isNull();
    }
}

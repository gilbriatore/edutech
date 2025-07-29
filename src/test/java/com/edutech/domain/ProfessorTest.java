package com.edutech.domain;

import static com.edutech.domain.CursoTestSamples.*;
import static com.edutech.domain.ProfessorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfessorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professor.class);
        Professor professor1 = getProfessorSample1();
        Professor professor2 = new Professor();
        assertThat(professor1).isNotEqualTo(professor2);

        professor2.setId(professor1.getId());
        assertThat(professor1).isEqualTo(professor2);

        professor2 = getProfessorSample2();
        assertThat(professor1).isNotEqualTo(professor2);
    }

    @Test
    void cursosMinistradosTest() {
        Professor professor = getProfessorRandomSampleGenerator();
        Curso cursoBack = getCursoRandomSampleGenerator();

        professor.addCursosMinistrados(cursoBack);
        assertThat(professor.getCursosMinistrados()).containsOnly(cursoBack);
        assertThat(cursoBack.getProfessor()).isEqualTo(professor);

        professor.removeCursosMinistrados(cursoBack);
        assertThat(professor.getCursosMinistrados()).doesNotContain(cursoBack);
        assertThat(cursoBack.getProfessor()).isNull();

        professor.cursosMinistrados(new HashSet<>(Set.of(cursoBack)));
        assertThat(professor.getCursosMinistrados()).containsOnly(cursoBack);
        assertThat(cursoBack.getProfessor()).isEqualTo(professor);

        professor.setCursosMinistrados(new HashSet<>());
        assertThat(professor.getCursosMinistrados()).doesNotContain(cursoBack);
        assertThat(cursoBack.getProfessor()).isNull();
    }
}

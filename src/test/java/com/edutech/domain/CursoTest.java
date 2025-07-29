package com.edutech.domain;

import static com.edutech.domain.AulaTestSamples.*;
import static com.edutech.domain.CursoTestSamples.*;
import static com.edutech.domain.InscricaoTestSamples.*;
import static com.edutech.domain.ProfessorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Curso.class);
        Curso curso1 = getCursoSample1();
        Curso curso2 = new Curso();
        assertThat(curso1).isNotEqualTo(curso2);

        curso2.setId(curso1.getId());
        assertThat(curso1).isEqualTo(curso2);

        curso2 = getCursoSample2();
        assertThat(curso1).isNotEqualTo(curso2);
    }

    @Test
    void aulasTest() {
        Curso curso = getCursoRandomSampleGenerator();
        Aula aulaBack = getAulaRandomSampleGenerator();

        curso.addAulas(aulaBack);
        assertThat(curso.getAulas()).containsOnly(aulaBack);
        assertThat(aulaBack.getCurso()).isEqualTo(curso);

        curso.removeAulas(aulaBack);
        assertThat(curso.getAulas()).doesNotContain(aulaBack);
        assertThat(aulaBack.getCurso()).isNull();

        curso.aulas(new HashSet<>(Set.of(aulaBack)));
        assertThat(curso.getAulas()).containsOnly(aulaBack);
        assertThat(aulaBack.getCurso()).isEqualTo(curso);

        curso.setAulas(new HashSet<>());
        assertThat(curso.getAulas()).doesNotContain(aulaBack);
        assertThat(aulaBack.getCurso()).isNull();
    }

    @Test
    void inscricoesTest() {
        Curso curso = getCursoRandomSampleGenerator();
        Inscricao inscricaoBack = getInscricaoRandomSampleGenerator();

        curso.addInscricoes(inscricaoBack);
        assertThat(curso.getInscricoes()).containsOnly(inscricaoBack);
        assertThat(inscricaoBack.getCurso()).isEqualTo(curso);

        curso.removeInscricoes(inscricaoBack);
        assertThat(curso.getInscricoes()).doesNotContain(inscricaoBack);
        assertThat(inscricaoBack.getCurso()).isNull();

        curso.inscricoes(new HashSet<>(Set.of(inscricaoBack)));
        assertThat(curso.getInscricoes()).containsOnly(inscricaoBack);
        assertThat(inscricaoBack.getCurso()).isEqualTo(curso);

        curso.setInscricoes(new HashSet<>());
        assertThat(curso.getInscricoes()).doesNotContain(inscricaoBack);
        assertThat(inscricaoBack.getCurso()).isNull();
    }

    @Test
    void professorTest() {
        Curso curso = getCursoRandomSampleGenerator();
        Professor professorBack = getProfessorRandomSampleGenerator();

        curso.setProfessor(professorBack);
        assertThat(curso.getProfessor()).isEqualTo(professorBack);

        curso.professor(null);
        assertThat(curso.getProfessor()).isNull();
    }
}

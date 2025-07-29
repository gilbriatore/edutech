package com.edutech.domain;

import static com.edutech.domain.AlunoTestSamples.*;
import static com.edutech.domain.CursoTestSamples.*;
import static com.edutech.domain.InscricaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscricaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscricao.class);
        Inscricao inscricao1 = getInscricaoSample1();
        Inscricao inscricao2 = new Inscricao();
        assertThat(inscricao1).isNotEqualTo(inscricao2);

        inscricao2.setId(inscricao1.getId());
        assertThat(inscricao1).isEqualTo(inscricao2);

        inscricao2 = getInscricaoSample2();
        assertThat(inscricao1).isNotEqualTo(inscricao2);
    }

    @Test
    void alunoTest() {
        Inscricao inscricao = getInscricaoRandomSampleGenerator();
        Aluno alunoBack = getAlunoRandomSampleGenerator();

        inscricao.setAluno(alunoBack);
        assertThat(inscricao.getAluno()).isEqualTo(alunoBack);

        inscricao.aluno(null);
        assertThat(inscricao.getAluno()).isNull();
    }

    @Test
    void cursoTest() {
        Inscricao inscricao = getInscricaoRandomSampleGenerator();
        Curso cursoBack = getCursoRandomSampleGenerator();

        inscricao.setCurso(cursoBack);
        assertThat(inscricao.getCurso()).isEqualTo(cursoBack);

        inscricao.curso(null);
        assertThat(inscricao.getCurso()).isNull();
    }
}

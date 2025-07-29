package com.edutech.domain;

import static com.edutech.domain.AlunoTestSamples.*;
import static com.edutech.domain.InscricaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AlunoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aluno.class);
        Aluno aluno1 = getAlunoSample1();
        Aluno aluno2 = new Aluno();
        assertThat(aluno1).isNotEqualTo(aluno2);

        aluno2.setId(aluno1.getId());
        assertThat(aluno1).isEqualTo(aluno2);

        aluno2 = getAlunoSample2();
        assertThat(aluno1).isNotEqualTo(aluno2);
    }

    @Test
    void inscricoesTest() {
        Aluno aluno = getAlunoRandomSampleGenerator();
        Inscricao inscricaoBack = getInscricaoRandomSampleGenerator();

        aluno.addInscricoes(inscricaoBack);
        assertThat(aluno.getInscricoes()).containsOnly(inscricaoBack);
        assertThat(inscricaoBack.getAluno()).isEqualTo(aluno);

        aluno.removeInscricoes(inscricaoBack);
        assertThat(aluno.getInscricoes()).doesNotContain(inscricaoBack);
        assertThat(inscricaoBack.getAluno()).isNull();

        aluno.inscricoes(new HashSet<>(Set.of(inscricaoBack)));
        assertThat(aluno.getInscricoes()).containsOnly(inscricaoBack);
        assertThat(inscricaoBack.getAluno()).isEqualTo(aluno);

        aluno.setInscricoes(new HashSet<>());
        assertThat(aluno.getInscricoes()).doesNotContain(inscricaoBack);
        assertThat(inscricaoBack.getAluno()).isNull();
    }
}

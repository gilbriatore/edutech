package com.edutech.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.edutech.domain.Inscricao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InscricaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dataInscricao;

    private AlunoDTO aluno;

    private CursoDTO curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Instant dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscricaoDTO)) {
            return false;
        }

        InscricaoDTO inscricaoDTO = (InscricaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inscricaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscricaoDTO{" +
            "id=" + getId() +
            ", dataInscricao='" + getDataInscricao() + "'" +
            ", aluno=" + getAluno() +
            ", curso=" + getCurso() +
            "}";
    }
}

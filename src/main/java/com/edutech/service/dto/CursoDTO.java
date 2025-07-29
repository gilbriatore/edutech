package com.edutech.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edutech.domain.Curso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CursoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String descricao;

    @NotNull
    private Integer cargaHoraria;

    private ProfessorDTO professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CursoDTO)) {
            return false;
        }

        CursoDTO cursoDTO = (CursoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cursoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CursoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cargaHoraria=" + getCargaHoraria() +
            ", professor=" + getProfessor() +
            "}";
    }
}

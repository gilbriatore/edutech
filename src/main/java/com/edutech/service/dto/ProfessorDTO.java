package com.edutech.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edutech.domain.Professor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfessorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String email;

    private String especialidade;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfessorDTO)) {
            return false;
        }

        ProfessorDTO professorDTO = (ProfessorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, professorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfessorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            "}";
    }
}

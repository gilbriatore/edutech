package com.edutech.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.edutech.domain.Aula} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AulaDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    private String titulo;

    @Lob
    private String conteudo;

    private CursoDTO curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
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
        if (!(o instanceof AulaDTO)) {
            return false;
        }

        AulaDTO aulaDTO = (AulaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aulaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AulaDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", curso=" + getCurso() +
            "}";
    }
}

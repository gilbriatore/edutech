package com.edutech.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Inscricao.
 */
@Entity
@Table(name = "inscricao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_inscricao", nullable = false)
    private Instant dataInscricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inscricoes" }, allowSetters = true)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "aulas", "inscricoes", "professor" }, allowSetters = true)
    private Curso curso;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inscricao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataInscricao() {
        return this.dataInscricao;
    }

    public Inscricao dataInscricao(Instant dataInscricao) {
        this.setDataInscricao(dataInscricao);
        return this;
    }

    public void setDataInscricao(Instant dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Inscricao aluno(Aluno aluno) {
        this.setAluno(aluno);
        return this;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Inscricao curso(Curso curso) {
        this.setCurso(curso);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inscricao)) {
            return false;
        }
        return getId() != null && getId().equals(((Inscricao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inscricao{" +
            "id=" + getId() +
            ", dataInscricao='" + getDataInscricao() + "'" +
            "}";
    }
}

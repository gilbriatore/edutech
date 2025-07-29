package com.edutech.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "curso" }, allowSetters = true)
    private Set<Aula> aulas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aluno", "curso" }, allowSetters = true)
    private Set<Inscricao> inscricoes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cursosMinistrados" }, allowSetters = true)
    private Professor professor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Curso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Curso nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Curso descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return this.cargaHoraria;
    }

    public Curso cargaHoraria(Integer cargaHoraria) {
        this.setCargaHoraria(cargaHoraria);
        return this;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Set<Aula> getAulas() {
        return this.aulas;
    }

    public void setAulas(Set<Aula> aulas) {
        if (this.aulas != null) {
            this.aulas.forEach(i -> i.setCurso(null));
        }
        if (aulas != null) {
            aulas.forEach(i -> i.setCurso(this));
        }
        this.aulas = aulas;
    }

    public Curso aulas(Set<Aula> aulas) {
        this.setAulas(aulas);
        return this;
    }

    public Curso addAulas(Aula aula) {
        this.aulas.add(aula);
        aula.setCurso(this);
        return this;
    }

    public Curso removeAulas(Aula aula) {
        this.aulas.remove(aula);
        aula.setCurso(null);
        return this;
    }

    public Set<Inscricao> getInscricoes() {
        return this.inscricoes;
    }

    public void setInscricoes(Set<Inscricao> inscricaos) {
        if (this.inscricoes != null) {
            this.inscricoes.forEach(i -> i.setCurso(null));
        }
        if (inscricaos != null) {
            inscricaos.forEach(i -> i.setCurso(this));
        }
        this.inscricoes = inscricaos;
    }

    public Curso inscricoes(Set<Inscricao> inscricaos) {
        this.setInscricoes(inscricaos);
        return this;
    }

    public Curso addInscricoes(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
        inscricao.setCurso(this);
        return this;
    }

    public Curso removeInscricoes(Inscricao inscricao) {
        this.inscricoes.remove(inscricao);
        inscricao.setCurso(null);
        return this;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Curso professor(Professor professor) {
        this.setProfessor(professor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curso)) {
            return false;
        }
        return getId() != null && getId().equals(((Curso) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", cargaHoraria=" + getCargaHoraria() +
            "}";
    }
}

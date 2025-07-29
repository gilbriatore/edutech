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
 * A Professor.
 */
@Entity
@Table(name = "professor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "especialidade")
    private String especialidade;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aulas", "inscricoes", "professor" }, allowSetters = true)
    private Set<Curso> cursosMinistrados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Professor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Professor nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public Professor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return this.especialidade;
    }

    public Professor especialidade(String especialidade) {
        this.setEspecialidade(especialidade);
        return this;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Set<Curso> getCursosMinistrados() {
        return this.cursosMinistrados;
    }

    public void setCursosMinistrados(Set<Curso> cursos) {
        if (this.cursosMinistrados != null) {
            this.cursosMinistrados.forEach(i -> i.setProfessor(null));
        }
        if (cursos != null) {
            cursos.forEach(i -> i.setProfessor(this));
        }
        this.cursosMinistrados = cursos;
    }

    public Professor cursosMinistrados(Set<Curso> cursos) {
        this.setCursosMinistrados(cursos);
        return this;
    }

    public Professor addCursosMinistrados(Curso curso) {
        this.cursosMinistrados.add(curso);
        curso.setProfessor(this);
        return this;
    }

    public Professor removeCursosMinistrados(Curso curso) {
        this.cursosMinistrados.remove(curso);
        curso.setProfessor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professor)) {
            return false;
        }
        return getId() != null && getId().equals(((Professor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            "}";
    }
}

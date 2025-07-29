package com.edutech.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Aluno.
 */
@Entity
@Table(name = "aluno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aluno implements Serializable {

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

    @NotNull
    @Column(name = "matricula", nullable = false)
    private String matricula;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aluno", "curso" }, allowSetters = true)
    private Set<Inscricao> inscricoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aluno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Aluno nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public Aluno email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public Aluno matricula(String matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Aluno dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Set<Inscricao> getInscricoes() {
        return this.inscricoes;
    }

    public void setInscricoes(Set<Inscricao> inscricaos) {
        if (this.inscricoes != null) {
            this.inscricoes.forEach(i -> i.setAluno(null));
        }
        if (inscricaos != null) {
            inscricaos.forEach(i -> i.setAluno(this));
        }
        this.inscricoes = inscricaos;
    }

    public Aluno inscricoes(Set<Inscricao> inscricaos) {
        this.setInscricoes(inscricaos);
        return this;
    }

    public Aluno addInscricoes(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
        inscricao.setAluno(this);
        return this;
    }

    public Aluno removeInscricoes(Inscricao inscricao) {
        this.inscricoes.remove(inscricao);
        inscricao.setAluno(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aluno)) {
            return false;
        }
        return getId() != null && getId().equals(((Aluno) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", matricula='" + getMatricula() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            "}";
    }
}

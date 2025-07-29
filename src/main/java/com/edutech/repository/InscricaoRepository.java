package com.edutech.repository;

import com.edutech.domain.Inscricao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inscricao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {}

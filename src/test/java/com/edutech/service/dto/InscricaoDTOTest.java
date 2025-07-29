package com.edutech.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscricaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscricaoDTO.class);
        InscricaoDTO inscricaoDTO1 = new InscricaoDTO();
        inscricaoDTO1.setId(1L);
        InscricaoDTO inscricaoDTO2 = new InscricaoDTO();
        assertThat(inscricaoDTO1).isNotEqualTo(inscricaoDTO2);
        inscricaoDTO2.setId(inscricaoDTO1.getId());
        assertThat(inscricaoDTO1).isEqualTo(inscricaoDTO2);
        inscricaoDTO2.setId(2L);
        assertThat(inscricaoDTO1).isNotEqualTo(inscricaoDTO2);
        inscricaoDTO1.setId(null);
        assertThat(inscricaoDTO1).isNotEqualTo(inscricaoDTO2);
    }
}

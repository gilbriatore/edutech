package com.edutech.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlunoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlunoDTO.class);
        AlunoDTO alunoDTO1 = new AlunoDTO();
        alunoDTO1.setId(1L);
        AlunoDTO alunoDTO2 = new AlunoDTO();
        assertThat(alunoDTO1).isNotEqualTo(alunoDTO2);
        alunoDTO2.setId(alunoDTO1.getId());
        assertThat(alunoDTO1).isEqualTo(alunoDTO2);
        alunoDTO2.setId(2L);
        assertThat(alunoDTO1).isNotEqualTo(alunoDTO2);
        alunoDTO1.setId(null);
        assertThat(alunoDTO1).isNotEqualTo(alunoDTO2);
    }
}

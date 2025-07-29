package com.edutech.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfessorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessorDTO.class);
        ProfessorDTO professorDTO1 = new ProfessorDTO();
        professorDTO1.setId(1L);
        ProfessorDTO professorDTO2 = new ProfessorDTO();
        assertThat(professorDTO1).isNotEqualTo(professorDTO2);
        professorDTO2.setId(professorDTO1.getId());
        assertThat(professorDTO1).isEqualTo(professorDTO2);
        professorDTO2.setId(2L);
        assertThat(professorDTO1).isNotEqualTo(professorDTO2);
        professorDTO1.setId(null);
        assertThat(professorDTO1).isNotEqualTo(professorDTO2);
    }
}

package com.edutech.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edutech.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CursoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CursoDTO.class);
        CursoDTO cursoDTO1 = new CursoDTO();
        cursoDTO1.setId(1L);
        CursoDTO cursoDTO2 = new CursoDTO();
        assertThat(cursoDTO1).isNotEqualTo(cursoDTO2);
        cursoDTO2.setId(cursoDTO1.getId());
        assertThat(cursoDTO1).isEqualTo(cursoDTO2);
        cursoDTO2.setId(2L);
        assertThat(cursoDTO1).isNotEqualTo(cursoDTO2);
        cursoDTO1.setId(null);
        assertThat(cursoDTO1).isNotEqualTo(cursoDTO2);
    }
}

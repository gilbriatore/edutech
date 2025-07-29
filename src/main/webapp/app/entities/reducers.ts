import aluno from 'app/entities/aluno/aluno.reducer';
import professor from 'app/entities/professor/professor.reducer';
import curso from 'app/entities/curso/curso.reducer';
import aula from 'app/entities/aula/aula.reducer';
import inscricao from 'app/entities/inscricao/inscricao.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  aluno,
  professor,
  curso,
  aula,
  inscricao,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

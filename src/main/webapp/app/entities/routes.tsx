import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aluno from './aluno';
import Professor from './professor';
import Curso from './curso';
import Aula from './aula';
import Inscricao from './inscricao';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="aluno/*" element={<Aluno />} />
        <Route path="professor/*" element={<Professor />} />
        <Route path="curso/*" element={<Curso />} />
        <Route path="aula/*" element={<Aula />} />
        <Route path="inscricao/*" element={<Inscricao />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

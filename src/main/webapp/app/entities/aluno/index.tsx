import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aluno from './aluno';
import AlunoDetail from './aluno-detail';
import AlunoUpdate from './aluno-update';
import AlunoDeleteDialog from './aluno-delete-dialog';

const AlunoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aluno />} />
    <Route path="new" element={<AlunoUpdate />} />
    <Route path=":id">
      <Route index element={<AlunoDetail />} />
      <Route path="edit" element={<AlunoUpdate />} />
      <Route path="delete" element={<AlunoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlunoRoutes;

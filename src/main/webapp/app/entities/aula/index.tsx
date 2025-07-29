import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aula from './aula';
import AulaDetail from './aula-detail';
import AulaUpdate from './aula-update';
import AulaDeleteDialog from './aula-delete-dialog';

const AulaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aula />} />
    <Route path="new" element={<AulaUpdate />} />
    <Route path=":id">
      <Route index element={<AulaDetail />} />
      <Route path="edit" element={<AulaUpdate />} />
      <Route path="delete" element={<AulaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AulaRoutes;

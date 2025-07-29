import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Curso from './curso';
import CursoDetail from './curso-detail';
import CursoUpdate from './curso-update';
import CursoDeleteDialog from './curso-delete-dialog';

const CursoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Curso />} />
    <Route path="new" element={<CursoUpdate />} />
    <Route path=":id">
      <Route index element={<CursoDetail />} />
      <Route path="edit" element={<CursoUpdate />} />
      <Route path="delete" element={<CursoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CursoRoutes;

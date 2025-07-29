import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Professor from './professor';
import ProfessorDetail from './professor-detail';
import ProfessorUpdate from './professor-update';
import ProfessorDeleteDialog from './professor-delete-dialog';

const ProfessorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Professor />} />
    <Route path="new" element={<ProfessorUpdate />} />
    <Route path=":id">
      <Route index element={<ProfessorDetail />} />
      <Route path="edit" element={<ProfessorUpdate />} />
      <Route path="delete" element={<ProfessorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProfessorRoutes;

import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inscricao from './inscricao';
import InscricaoDetail from './inscricao-detail';
import InscricaoUpdate from './inscricao-update';
import InscricaoDeleteDialog from './inscricao-delete-dialog';

const InscricaoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inscricao />} />
    <Route path="new" element={<InscricaoUpdate />} />
    <Route path=":id">
      <Route index element={<InscricaoDetail />} />
      <Route path="edit" element={<InscricaoUpdate />} />
      <Route path="delete" element={<InscricaoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InscricaoRoutes;

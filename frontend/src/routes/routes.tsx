import React, { Suspense } from 'react';
import ROUTES, { RenderRoutes } from './RenderRoutes';
import CircularProgress from '@mui/material/CircularProgress';

export const routes: React.FunctionComponent = () => {
  return (
    <Suspense fallback={<CircularProgress />}>
      <RenderRoutes routes={ROUTES} />
    </Suspense>
  );
};

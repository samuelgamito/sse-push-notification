import React, { ReactElement, lazy } from 'react';
import { Route, Routes } from 'react-router-dom';
import { Wrapper } from '../layouts/Wrapper/Wrapper';
const Notifications = lazy(() => import('../pages/Notifications'));
const NotificationsHistory = lazy(
  () => import('../pages/NotificationsHistory')
);
const NotificationListen = lazy(() => import('../pages/NotificationListen'));
const Users = lazy(() => import('../pages/Users'));

type routeItem = {
  path: string;
  key: string;
  element: ReactElement<any, any>;
};

type routes = routeItem & {
  routes?: routeItem[];
};

const ROUTES: routes[] = [
  {
    path: '/',
    key: 'ROOT',
    element: <Wrapper title='Home' />,
    routes: [],
  },
  {
    path: '/users',
    key: 'USERS',
    element: <Users />,
    routes: [],
  },
  {
    path: '/notifications',
    key: 'NOTIFICATIONS',
    element: <Notifications />,
    routes: [],
  },
  {
    path: '/notifications/history',
    key: 'NOTIFICATIONS_HISTORY',
    element: <NotificationsHistory />,
    routes: [],
  },
  {
    path: '/notifications/listen',
    key: 'NOTIFICATIONS_LISTEN',
    element: <NotificationListen />,
    routes: [],
  },
];

export default ROUTES;

export function RenderRoutes({ routes }: { routes: routes[] }) {
  return (
    <Routes>
      {routes.map((route, i) => {
        return (
          <Route key={route.key} path={route.path} element={route.element} />
        );
      })}
    </Routes>
  );
}

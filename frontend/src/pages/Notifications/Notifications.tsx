import * as React from 'react';
import { Wrapper } from '../../layouts/Wrapper/Wrapper';

interface NotificationsProps {}

export const Notifications: React.FunctionComponent<
  NotificationsProps
> = () => {
  return <Wrapper title='Manage notifications' />;
};

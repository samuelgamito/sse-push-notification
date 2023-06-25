import * as React from 'react';
import Wrapper from '../../layouts/Wrapper';
import { ContentCard } from '../../components/ContentCard/ContentCard';

interface NotificationListenProps {}

export const NotificationListen: React.FunctionComponent<
  NotificationListenProps
> = () => {
  return (
    <Wrapper title='Notification Listener'>
      <ContentCard>asd</ContentCard>
    </Wrapper>
  );
};

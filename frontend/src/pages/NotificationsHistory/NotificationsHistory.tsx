import * as React from 'react';
import Wrapper from '../../layouts/Wrapper';
import EventsHistoryService from '../../services/EventsHistoryService';
import ContentCard from '../../components/ContentCard';
import { Grid } from '@mui/material';
import SimpleTable from '../../components/SimpleTable';
import LoadingCard from '../../components/LoadingCard';
import { EventHistory } from '../../schemas/Events';

interface NotificationsHistoryProps {}

export const NotificationsHistory: React.FunctionComponent<
  NotificationsHistoryProps
> = () => {
  const [loadSpinner, setLoadSpinner] = React.useState(true);
  const [eventList, setEventList] = React.useState<EventHistory[]>([]);

  const eventsHistory = new EventsHistoryService();

  const fetchDataTable = async () => {
    const eventsHistoryList: EventHistory[] =
      await eventsHistory.getEventHistory();
    setEventList(eventsHistoryList);
    setLoadSpinner(false);
  };

  React.useEffect(() => {
    fetchDataTable();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Wrapper title='Notification History'>
      {eventList.length > 0 && (
        <ContentCard>
          <Grid container rowSpacing={5}>
            <Grid container item xs={12}>
              <SimpleTable
                tableData={eventList}
                tableHeader={['id', 'message', 'alias', 'publishedAt']}
              />
            </Grid>
          </Grid>
        </ContentCard>
      )}
      {loadSpinner && <LoadingCard />}
    </Wrapper>
  );
};

import * as React from 'react';
import { Wrapper } from '../../layouts/Wrapper/Wrapper';
import { EventsSettingsResponse } from '../../schemas/Events';
import LoadingCard from '../../components/LoadingCard';
import ContentCard from '../../components/ContentCard';
import { Button, Grid } from '@mui/material';
import SimpleTable from '../../components/SimpleTable';
import EventsSettingsService from '../../services/EventsSettingsService';
import DialogDesktop from '../../components/DialogDesktop';
import { NotificationsSettingsForm } from '../../components/NotificationsSettingsForm/NotificationsSettingsForm';

interface NotificationsProps {}

export const Notifications: React.FunctionComponent<
  NotificationsProps
> = () => {
  const [loadSpinner, setLoadSpinner] = React.useState(true);
  const [eventSettingsList, setEventSettingsList] = React.useState<
    EventsSettingsResponse[]
  >([]);

  const [openDialog, setOpenDialog] = React.useState(false);

  const handleOpenDialog = async () => {
    setOpenDialog(true);
  };

  const onSubmit = async (eventSettings: EventsSettingsResponse) => {
    setOpenDialog(false);
    setLoadSpinner(true);
    const eventsSettingsService = new EventsSettingsService();
    await eventsSettingsService.createEventsSettings(eventSettings);
    await fetchDataTable();
  };

  const eventsSettingsService = new EventsSettingsService();

  const fetchDataTable = async () => {
    setLoadSpinner(true);
    const eventSettingsList: EventsSettingsResponse[] =
      await eventsSettingsService.getEventsSettings();
    setEventSettingsList(eventSettingsList);
    setLoadSpinner(false);
  };

  const handleCloseDialog = async () => {
    setOpenDialog(false);
  };

  React.useEffect(() => {
    fetchDataTable();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div>
      <DialogDesktop
        openState={openDialog}
        title='Notifications Settings Form'
        handleClose={handleCloseDialog}>
        <NotificationsSettingsForm
          onCancel={handleCloseDialog}
          onSubmit={onSubmit}
        />
      </DialogDesktop>
      <Wrapper title='Manage notifications'>
        {eventSettingsList.length > 0 && (
          <ContentCard>
            <Grid container rowSpacing={5}>
              <Grid container item xs={12} justifyContent={'right'}>
                <Button onClick={handleOpenDialog} variant='contained'>
                  Add Notification Settings
                </Button>
              </Grid>
              <Grid container item xs={12}>
                <SimpleTable
                  tableData={eventSettingsList}
                  tableHeader={[
                    'alias',
                    'routingKey',
                    'description',
                    'createdAt',
                  ]}
                />
              </Grid>
            </Grid>
          </ContentCard>
        )}
        {loadSpinner && <LoadingCard />}
      </Wrapper>
    </div>
  );
};

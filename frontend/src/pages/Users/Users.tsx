import * as React from 'react';
import { Wrapper } from '../../layouts/Wrapper/Wrapper';
import { ContentCard } from '../../components/ContentCard/ContentCard';
import LoadingCard from '../../components/LoadingCard';
import UsersService from '../../services/UsersService';
import { SimpleTable } from '../../components/SimpleTable/SimpleTable';
import { UserResponse } from '../../schemas/User';
import { Button, Grid } from '@mui/material';
import { DialogDesktop } from '../../components/DialogDesktop/DialogDesktop';
import { UserForm } from '../../components/UserForm/UserForm';
import { EventsSettingsResponse } from '../../schemas/Events';
import EventsSettingsService from '../../services/EventsSettingsService';
interface UsersProps {}

export const Users: React.FunctionComponent<UsersProps> = () => {
  const [loadSpinner, setLoadSpinner] = React.useState(false);
  const [usersList, setUsersList] = React.useState<UserResponse[]>([]);
  const [open, setOpen] = React.useState(false);
  const [aliasList, setAliasList] = React.useState<string[]>([]);
  const [selectedUser, setSelectedUser] = React.useState<UserResponse>();

  const userService = new UsersService();
  const eventsSettingsService = new EventsSettingsService();

  const retrieveEventsSettings = async () => {
    const eventsSettings: EventsSettingsResponse[] =
      await eventsSettingsService.getEventsSettings();
    setAliasList(eventsSettings.map((event) => event.alias));
  };

  const handleOpenDialog = async () => {
    await retrieveEventsSettings();
    setOpen(true);
  };

  const handleOpenDialogTale = async (row: any) => {
    await retrieveEventsSettings();

    setSelectedUser(row);

    setOpen(true);
  };

  const handleCloseDialog = () => {
    setSelectedUser(undefined);
    setOpen(false);
  };
  const handleSubmit = async (user: UserResponse) => {
    console.log(user);
    setSelectedUser(undefined);
    setOpen(false);

    setLoadSpinner(true);
    await userService.upsertUser(user);

    fetchDataTable();
  };

  const fetchDataTable = async () => {
    setLoadSpinner(true);
    const users: UserResponse[] = await userService.getUsers();

    setUsersList(users);
    setLoadSpinner(false);
  };

  React.useEffect(() => {
    fetchDataTable();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div>
      <DialogDesktop
        openState={open}
        title='User Form'
        handleClose={handleCloseDialog}>
        <UserForm
          onCancel={handleCloseDialog}
          onSubmit={handleSubmit}
          selectedUser={selectedUser}
          eventsSettings={aliasList}
        />
      </DialogDesktop>
      <Wrapper title='Manage Users'>
        {usersList.length > 0 && (
          <ContentCard>
            <Grid container rowSpacing={5}>
              <Grid container item xs={12} justifyContent={'right'}>
                <Button onClick={handleOpenDialog} variant='contained'>
                  Add User
                </Button>
              </Grid>
              <Grid container item xs={12}>
                <SimpleTable
                  onClickLineEvent={handleOpenDialogTale}
                  tableData={usersList}
                  tableHeader={['username', 'alias', 'createdAt']}
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

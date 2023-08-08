import * as React from 'react';
import Wrapper from '../../layouts/Wrapper';
import { ContentCard } from '../../components/ContentCard/ContentCard';
import { UserResponse } from '../../schemas/User';
import UsersService from '../../services/UsersService';
import LoadingCard from '../../components/LoadingCard';
import {
  Stack,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Paper,
  Typography,
} from '@mui/material';

interface NotificationListenProps {}

export const NotificationListen: React.FunctionComponent<
  NotificationListenProps
> = () => {
  const [usersList, setUsersList] = React.useState<UserResponse[]>([]);
  const [eventsList, setEventsList] = React.useState<any[]>([]);
  const [newEventsList, setNewEventsList] = React.useState<any[]>([]);
  const [loadSpinner, setLoadSpinner] = React.useState(false);
  const [selectedUser, setSelectedUser] = React.useState<string>('');

  const userService = new UsersService();
  var eventSource: EventSource;

  const handleUsernameChange = (e: any) => {
    const username = e.target.value as string;
    eventListener(username);
    setSelectedUser(username);
  };

  const fetchDataTable = async () => {
    setLoadSpinner(true);
    const users: UserResponse[] = await userService.getUsers();

    setUsersList(users);
    setLoadSpinner(false);
  };

  const handleEvents = (e: any) => {
    if (e.type === 'INITIAL') {
      console.log(e.eventResponses);
      setEventsList(e.eventResponses);
    } else {
      console.log(e);
    }
  };

  const eventListener = (username: string) => {
    if (eventSource !== undefined) {
      eventSource.close();
    }
    const url = `http://localhost:8080/stream/${username}`;
    eventSource = new EventSource(url);
    eventSource.onmessage = (e) => handleEvents(JSON.parse(e.data));
  };

  React.useEffect(() => {
    fetchDataTable();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Wrapper title='Notification Listener'>
      <Stack spacing={5}>
        <ContentCard>
          <Grid container rowSpacing={5}>
            <Grid container item xs={12} justifyContent={'right'}>
              <FormControl fullWidth>
                <InputLabel id='username-select-label'>Username</InputLabel>
                <Select
                  labelId='username-select-label'
                  id='username-select'
                  value={selectedUser}
                  onChange={(e) => handleUsernameChange(e)}
                  label='Username'>
                  {usersList.map((user, i) => (
                    <MenuItem
                      key={`${user.username}+${i}`}
                      value={user.username}>
                      {user.username}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          </Grid>
        </ContentCard>
        <ContentCard>
          <Grid container>
            <Grid item xs={12} md={6}>
              <Typography variant='h5'>New Events</Typography>
              <Paper
                variant='outlined'
                sx={{ width: '90%', height: '40rem', overflow: 'auto' }}>
                <Stack>
                  {newEventsList.map((event, i) => (
                    <Paper
                      key={`${event.username}+${i}`}
                      elevation={2}
                      sx={{ width: '95%', margin: 'auto', p: 2, my: 1 }}>
                      {event.message}
                    </Paper>
                  ))}
                </Stack>
              </Paper>
            </Grid>
            <Grid item xs={12} md={6}>
              <Typography variant='h5'>Events History</Typography>
              <Paper
                variant='outlined'
                sx={{ width: '90%', height: '40rem', overflow: 'auto' }}>
                <Stack>
                  {eventsList.map((event, i) => (
                    <Paper
                      key={`${event.username}+${i}`}
                      elevation={2}
                      sx={{ width: '95%', margin: 'auto', p: 2, my: 1 }}>
                      {event.message}
                      <br />
                      {event.alias}
                      <br />
                      {event.publishedAt}
                      <br />
                      {event.metadata?.action}
                      <br />
                    </Paper>
                  ))}
                </Stack>
              </Paper>
            </Grid>
          </Grid>
        </ContentCard>
      </Stack>

      {loadSpinner && <LoadingCard />}
    </Wrapper>
  );
};

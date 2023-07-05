import {
  Box,
  Button,
  DialogActions,
  FormControl,
  TextField,
} from '@mui/material';
import * as React from 'react';
import { EventsSettingsResponse } from '../../schemas/Events';

interface NotificationsSettingsFormProps {
  onSubmit: (eventSettings: EventsSettingsResponse) => void;
  onCancel: () => void;
}

export const NotificationsSettingsForm: React.FunctionComponent<
  NotificationsSettingsFormProps
> = ({ onCancel, onSubmit }) => {
  const [aliasValue, setAliasValue] = React.useState('');
  const [descriptionValue, setDescriptionValue] = React.useState('');

  const handleSubmitForm = () => {
    onSubmit({
      alias: aliasValue,
      description: descriptionValue,
    });
  };
  return (
    <Box component='form'>
      <FormControl fullWidth sx={{ m: 1 }}>
        <TextField
          id='alias'
          onChange={(e) => {
            setAliasValue(e.target.value);
          }}
          value={aliasValue}
          label='Alias'
        />
      </FormControl>

      <FormControl fullWidth sx={{ m: 1 }}>
        <TextField
          id='description'
          onChange={(e) => {
            setDescriptionValue(e.target.value);
          }}
          value={descriptionValue}
          label='Description'
        />
      </FormControl>
      <DialogActions>
        <Button onClick={onCancel}>Cancel</Button>
        <Button onClick={handleSubmitForm}>Submit</Button>
      </DialogActions>
    </Box>
  );
};

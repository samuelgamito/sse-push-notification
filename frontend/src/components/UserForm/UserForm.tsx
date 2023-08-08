import * as React from 'react';
import Box from '@mui/material/Box';
import {
  FormControl,
  TextField,
  InputLabel,
  Select,
  MenuItem,
  Checkbox,
  ListItemText,
  OutlinedInput,
} from '@mui/material';
import { SelectChangeEvent } from '@mui/material/Select';
import { UserResponse } from '../../schemas/User';
import { Button } from '@mui/material';

import DialogActions from '@mui/material/DialogActions';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;

const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

interface UserFormProps {
  eventsSettings: Array<string>;
  selectedUser?: UserResponse;
  onSubmit: (user: UserResponse) => void;
  onCancel: () => void;
}

export const UserForm: React.FunctionComponent<UserFormProps> = ({
  eventsSettings,
  selectedUser,
  onCancel,
  onSubmit,
}) => {
  const [usernameValue, setUsernameValue] = React.useState('');
  const [aliasValue, setAliasValue] = React.useState<string[]>([]);
  const [disableUsername, setDisableUsername] = React.useState<boolean>(false);

  React.useEffect(() => {}, [eventsSettings]);
  React.useEffect(() => {
    if (selectedUser) {
      setUsernameValue(selectedUser.username);
      setAliasValue(selectedUser.alias);
      setDisableUsername(true);
    } else {
      setUsernameValue('');
      setAliasValue([]);
      setDisableUsername(false);
    }
  }, [selectedUser]);

  const handleSubmitForm = () => {
    if (selectedUser) {
      selectedUser.username = usernameValue;
      selectedUser.alias = aliasValue;
      onSubmit(selectedUser);
    } else {
      const user: UserResponse = {
        username: usernameValue,
        alias: aliasValue,
      };
      onSubmit(user);
    }
  };

  const handleChange = (event: SelectChangeEvent<typeof aliasValue>) => {
    const {
      target: { value },
    } = event;
    setAliasValue(
      // On autofill we get a stringified value.
      typeof value === 'string' ? value.split(',') : value
    );
  };

  return (
    <div>
      <Box component='form'>
        <FormControl fullWidth sx={{ m: 1 }}>
          <TextField
            disabled={disableUsername}
            id='username'
            onChange={(e) => {
              setUsernameValue(e.target.value);
            }}
            value={usernameValue}
            label='Username'
          />
        </FormControl>

        <FormControl sx={{ m: 1, width: 300 }}>
          <InputLabel id='demo-multiple-checkbox-label'>Alias</InputLabel>
          <Select
            labelId='demo-multiple-checkbox-label'
            id='demo-multiple-checkbox'
            multiple
            value={aliasValue}
            onChange={handleChange}
            input={<OutlinedInput label='Tag' />}
            renderValue={(selected) => selected.join(', ')}
            MenuProps={MenuProps}>
            {eventsSettings.map((name) => (
              <MenuItem key={name} value={name}>
                <Checkbox checked={aliasValue.indexOf(name) > -1} />
                <ListItemText primary={name} />
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>
      <DialogActions>
        <Button onClick={onCancel}>Cancel</Button>
        <Button onClick={handleSubmitForm}>Submit</Button>
      </DialogActions>
    </div>
  );
};

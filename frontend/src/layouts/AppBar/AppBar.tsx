import * as React from 'react';

import AppBarMUI from '@mui/material/AppBar';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

interface AppBarProps {
  handleDrawerToggle: any;
  mobileOpen: boolean;
  setMobileOpen: any;
  title: string;
  drawerWidth: number;
}

export const AppBar: React.FunctionComponent<AppBarProps> = ({
  handleDrawerToggle,
  mobileOpen,
  setMobileOpen,
  title,
  drawerWidth,
}) => {
  return (
    <AppBarMUI
      position='fixed'
      sx={{
        width: { sm: `calc(100% - ${drawerWidth}px)` },
        ml: { sm: `${drawerWidth}px` },
      }}>
      <Toolbar>
        <IconButton
          color='inherit'
          aria-label='open drawer'
          edge='start'
          onClick={handleDrawerToggle}
          sx={{ mr: 2, display: { sm: 'none' } }}>
          <MenuIcon />
        </IconButton>
        <Typography variant='h6' noWrap component='div'>
          {title}
        </Typography>
      </Toolbar>
    </AppBarMUI>
  );
};

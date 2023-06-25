import * as React from 'react';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';

import Toolbar from '@mui/material/Toolbar';
import AppBar from './../AppBar';
import NavBar from './../NavBar';

interface WrapperProps {
  title: string;
  children?: React.ReactNode;
}

export const Wrapper: React.FunctionComponent<WrapperProps> = ({
  title,
  children,
}) => {
  const drawerWidth = 240;

  const [mobileOpen, setMobileOpen] = React.useState(false);
  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        handleDrawerToggle={handleDrawerToggle}
        mobileOpen={mobileOpen}
        setMobileOpen={setMobileOpen}
        drawerWidth={drawerWidth}
        title={title}
      />

      <NavBar
        mobileOpen={mobileOpen}
        handleDrawerToggle={handleDrawerToggle}
        setMobileOpen={setMobileOpen}
        drawerWidth={drawerWidth}
      />

      <Box
        component='main'
        sx={{
          flexGrow: 1,
          p: 3,
          width: { sm: `calc(100% - ${drawerWidth}px)` },
        }}>
        <Toolbar />

        {children}
      </Box>
    </Box>
  );
};

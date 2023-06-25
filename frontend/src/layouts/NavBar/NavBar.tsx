import * as React from 'react';

import Drawer from '@mui/material/Drawer';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import EditNotificationsIcon from '@mui/icons-material/EditNotifications';
import HistoryIcon from '@mui/icons-material/History';
import NotificationsIcon from '@mui/icons-material/Notifications';
import Toolbar from '@mui/material/Toolbar';
import Box from '@mui/material/Box';

interface NavBarProps {
  handleDrawerToggle: any;
  mobileOpen: boolean;
  setMobileOpen: any;
  drawerWidth: number;
}
export const NavBar: React.FunctionComponent<NavBarProps> = ({
  handleDrawerToggle,
  mobileOpen,
  setMobileOpen,
  drawerWidth,
}) => {
  const { pathname } = window.location;

  const links = [
    {
      title: 'Manage Users',
      icon: <ManageAccountsIcon />,
      path: '/users',
    },
    {
      title: 'Edit Notifications',
      icon: <EditNotificationsIcon />,
      path: '/notifications',
    },
    {
      title: 'Notification History',
      icon: <HistoryIcon />,
      path: '/notifications/history',
    },
    {
      title: 'Listen to Notifications',
      icon: <NotificationsIcon />,
      path: '/notifications/listen',
    },
  ];

  const drawer = (
    <div>
      <Toolbar />
      <Divider />
      <List>
        {links.map((linkValue, index) => (
          <ListItem key={linkValue.title} disablePadding>
            <ListItemButton
              selected={pathname === linkValue.path}
              href={linkValue.path}>
              <ListItemIcon>{linkValue.icon}</ListItemIcon>
              <ListItemText primary={linkValue.title} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </div>
  );
  return (
    <Box
      component='nav'
      sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
      aria-label='mailbox folders'>
      {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
      <Drawer
        variant='temporary'
        open={mobileOpen}
        onClose={handleDrawerToggle}
        ModalProps={{
          keepMounted: true, // Better open performance on mobile.
        }}
        sx={{
          display: { xs: 'block', sm: 'none' },
          '& .MuiDrawer-paper': {
            boxSizing: 'border-box',
            width: drawerWidth,
          },
        }}>
        {drawer}
      </Drawer>
      <Drawer
        variant='permanent'
        sx={{
          display: { xs: 'none', sm: 'block' },
          '& .MuiDrawer-paper': {
            boxSizing: 'border-box',
            width: drawerWidth,
          },
        }}
        open>
        {drawer}
      </Drawer>
    </Box>
  );
};

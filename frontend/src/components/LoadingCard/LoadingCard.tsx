import * as React from 'react';

import Card from '@mui/material/Card';
import Paper from '@mui/material/Paper';
import CardContent from '@mui/material/CardContent';
import CircularProgress from '@mui/material/CircularProgress';
import Grid from '@mui/material/Grid';

interface LoadingCardProps {}

export const LoadingCard: React.FunctionComponent<LoadingCardProps> = () => {
  return (
    <Grid
      sx={{
        marginTop: 0,
        position: 'absolute',
        left: '0',
        top: '50%',
        transform: 'translateY(-50%)',
        msTransform: 'translateY(-50%)',
      }}
      container
      spacing={1}
      justifyContent='center'
      alignItems='center'>
      <Paper>
        <Card sx={{ width: 80, height: 80 }} elevation={10}>
          <CardContent>
            <CircularProgress size={50} />
          </CardContent>
        </Card>
      </Paper>
    </Grid>
  );
};

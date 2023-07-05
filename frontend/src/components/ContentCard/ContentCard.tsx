import * as React from 'react';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Grid } from '@mui/material';

interface ContentCardProps {
  children?: React.ReactNode;
}

export const ContentCard: React.FunctionComponent<ContentCardProps> = ({
  children,
}: ContentCardProps) => {
  return (
    <Grid>
      <Card>
        <CardContent>{children}</CardContent>
      </Card>
    </Grid>
  );
};

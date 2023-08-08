import * as React from 'react';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Grid } from '@mui/material';

interface ContentCardProps {
  rowSpacing?: number | 0;
  children?: React.ReactNode;
}

export const ContentCard: React.FunctionComponent<ContentCardProps> = ({
  children,
  rowSpacing,
}: ContentCardProps) => {
  return (
    <Grid rowSpacing={rowSpacing}>
      <Card>
        <CardContent>{children}</CardContent>
      </Card>
    </Grid>
  );
};

import * as React from 'react';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';

interface ContentCardProps {
  children?: React.ReactNode;
}

export const ContentCard: React.FunctionComponent<ContentCardProps> = ({
  children,
}: ContentCardProps) => {
  return (
    <Card sx={{ minWidth: 275 }}>
      <CardContent>{children}</CardContent>
    </Card>
  );
};

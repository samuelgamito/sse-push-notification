import * as React from 'react';

import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';

import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';

interface DialogDesktopProps {
  openState: any;
  handleClose: any;
  children?: React.ReactNode;
  title: string;
}

const Transition = React.forwardRef(function Transition(
  props: TransitionProps & {
    children: React.ReactElement<any, any>;
  },
  ref: React.Ref<unknown>
) {
  return <Slide direction='up' ref={ref} {...props} />;
});

export const DialogDesktop: React.FunctionComponent<DialogDesktopProps> = ({
  openState,
  handleClose,
  children,
  title,
}) => {
  return (
    <>
      <Dialog
        open={openState}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby='alert-dialog-slide-description'>
        <DialogTitle>{title}</DialogTitle>
        <DialogContent>{children}</DialogContent>
      </Dialog>
    </>
  );
};

import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

interface SimpleTableProps {
  tableData: any[];
  tableHeader: string[];
  onClickLineEvent?: (row: string) => void;
}

export const SimpleTable: React.FunctionComponent<SimpleTableProps> = ({
  tableData,
  tableHeader,
  onClickLineEvent,
}) => {
  const getFormattedText = (text: any) => {
    if (text === undefined || text === null || text === '') {
      return 'N/A';
    } else if (Array.isArray(text)) {
      return text.join(', ');
    } else if (typeof text === 'object' && text.constructor === Date) {
      return 'date';
    } else if (typeof text === 'boolean') {
      return text ? 'Yes' : 'No';
    }
    return text;
  };
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label='simple table'>
        <TableHead>
          <TableRow key='header'>
            {tableHeader.map((row, i) => (
              <TableCell key={i}>{row}</TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {tableData.map((row) => (
            <TableRow
              hover
              onClick={
                onClickLineEvent ? () => onClickLineEvent(row) : undefined
              }
              key={row.id}
              sx={{
                '&:last-child td, &:last-child th': { border: 0 },
                cursor: 'pointer',
              }}>
              {tableHeader.map((column, i) => (
                <TableCell key={i}>{getFormattedText(row[column])}</TableCell>
              ))}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

import Routes from './routes';
import { BrowserRouter } from 'react-router-dom';
interface Props {}

export default function App(props: Props) {
  return (
    <BrowserRouter>
      <Routes />
    </BrowserRouter>
  );
}

import 'bootstrap/dist/css/bootstrap.css';
import { useEffect } from 'react';
import './css/main.scss';

// Pages
import HomePage from './pages/home/home';
import { setDarkTheme, setLightTheme } from './state/setTheme';

// TODO: create a router for each page
const App = () => {

  useEffect(() => {
    const theme = localStorage.getItem('theme');

    switch (theme) {
      case 'dark':
        setDarkTheme();
        break;
      case 'light':
        setLightTheme();
        break;
    }

  }, [])
  

  return (
    <HomePage />
  )
}

export default App;
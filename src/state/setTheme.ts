import { setGlobalState } from ".";
import { CssColors as c } from '../colors';

export const setLightTheme = () => {
    setGlobalState('theme', 'light');
    localStorage.setItem('theme', 'light');

    document.body.style.backgroundColor = c.bgColorLight;
}

export const setDarkTheme = () => {
    setGlobalState('theme', 'dark');
    localStorage.setItem('theme', 'dark');

    document.body.style.backgroundColor = c.bgColorDark;
};
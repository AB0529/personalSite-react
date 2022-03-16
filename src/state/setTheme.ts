import { setGlobalState } from ".";
import { CssColors as c } from '../colors';

export const setLightTheme = () => {
    setGlobalState('theme', 'light');
    localStorage.setItem('theme', 'light');
    setGlobalState('textColor', c.textColorWhite);
    setGlobalState('bgColor', c.bgColorLight);
    setGlobalState('bgColor2', c.bgColor2Light);
    setGlobalState('hlColor', c.hlColorLight);
    setGlobalState('hlColor2', c.hlColor2Light);
    
    document.body.style.backgroundColor = c.bgColorLight;
}

export const setDarkTheme = () => {
    setGlobalState('theme', 'dark');
    localStorage.setItem('theme', 'dark');
    setGlobalState('textColor', c.textColorDark);
    setGlobalState('bgColor', c.bgColorDark);
    setGlobalState('bgColor2', c.bgColor2Dark);
    setGlobalState('hlColor', c.hlColorDark);
    setGlobalState('hlColor2', c.hlColor2Dark);

    document.body.style.backgroundColor = c.bgColorDark;
};
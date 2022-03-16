import { createGlobalState } from "react-hooks-global-state";
import { CssColors as c } from '../colors';

export interface ISiteTheme {
    theme: string;
    textColor: string;
    bgColor: string;
    bgColor2: string;
    hlColor: string;
    h1Color2: string;
}

const themeState = {
    theme: '',
    textColor: c.textColorDark,
    bgColor: c.bgColorDark,
    bgColor2: c.bgColor2Dark,
    hlColor: c.hlColorDark,
    hlColor2: c.hlColor2Dark,
};
const { setGlobalState, useGlobalState } = createGlobalState(themeState);

export { setGlobalState, useGlobalState };
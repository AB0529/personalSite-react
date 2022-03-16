import c from './css/colors.scss';

export interface CssColors {
    textColorWhite: string;
    textColorDark: string;
    bgColorDark: string;
    bgColor2Dark: string;
    hlColorDark: string;
    h1Color2Dark: string;
    bgColorLight: string;
    bgColor2Light: string;
    hlColorLight: string;
    hlColor2Light: string;
}

export const CssColors = {
    textColorWhite: c.textColorWhite,
    textColorDark: c.textColorDark,
    bgColorDark: c.bgColorDark,
    bgColor2Dark: c.bgColor2Dark,
    hlColorDark: c.hlColorDark,
    hlColor2Dark: c.h1Color2Dark,
    bgColorLight: c.bgColorLight,
    bgColor2Light: c.bgColorLight,
    hlColorLight: c.hlColorLight,
    hlColor2Light: c.hlColor2Light
} 
import { createGlobalState } from "react-hooks-global-state";

const themeState = {
    theme: 'dark',
}

const { setGlobalState, useGlobalState } = createGlobalState(themeState);

export { setGlobalState, useGlobalState };
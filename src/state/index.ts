import { createGlobalState } from "react-hooks-global-state";

const themeState = {
    theme: '',
}

const { setGlobalState, useGlobalState } = createGlobalState(themeState);

export { setGlobalState, useGlobalState };
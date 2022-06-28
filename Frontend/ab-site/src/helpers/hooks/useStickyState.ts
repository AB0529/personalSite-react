import { useEffect, useState } from "react";

export const useStickyState = (key: string, useSessionStorage?: boolean) => {
	const [value, setValue] = useState(() => {
		const stickyValue = useSessionStorage ? window.sessionStorage.getItem(key) : window.localStorage.getItem(key);
		return stickyValue !== null
			? JSON.parse(stickyValue)
			: null;
	});
	useEffect(() => {
		useSessionStorage ? window.sessionStorage.setItem(key, JSON.stringify(value)) : window.localStorage.setItem(key, JSON.stringify(value));
	}, [key, value]);
	return [value, setValue];
}
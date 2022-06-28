import { useEffect } from "react";
import { useGlobalState } from "../../state";

export const NotFound = () => {
  const theme = useGlobalState("theme")[0];

  useEffect(() => {
    if (theme === "dark") document.body.classList.add("dark-theme");

    return () => {
      document.body.classList.remove("dark-theme");
    };
  });

  return (
    <>
      <h1>Not found</h1>
    </>
  );
};

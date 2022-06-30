import { useEffect } from "react";
import { useOutletContext } from "react-router-dom";
import { IUser } from "../../helpers/typings";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";

export const Profile = () => {
  const user = useOutletContext<IUser>();
  const NAV_ITEMS = [
    {
      name: "Home",
      href: "/",
    },
    {
      name: "Files",
      href: "/profile/files",
    },
  ];
  const theme = useGlobalState("theme")[0];

  useEffect(() => {
    if (theme === "dark") document.body.classList.add("dark-theme");

    return () => {
      document.body.classList.remove("dark-theme");
    };
  });

  return (
    <>
      <MainNavbar items={NAV_ITEMS} />
      <h1>{user?.firstName}</h1>
      <h1>{user?.email}</h1>
    </>
  );
};

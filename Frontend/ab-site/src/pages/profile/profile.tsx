import { useOutletContext } from "react-router-dom";
import { IUser } from "../../helpers/typings";
import { MainNavbar } from "../home/components/nav/MainNavbar";

export const Profile = () => {
  const user = useOutletContext<IUser>();
  const NAV_ITEMS = [
    {
      name: "Home",
      href: "/",
    },
  ];

  return (
    <>
      <MainNavbar items={NAV_ITEMS} />
      <h1>{user?.firstName}</h1>
      <h1>{user?.email}</h1>
    </>
  );
};

import { AxiosError } from "axios";
import { useEffect } from "react";
import { Container } from "react-bootstrap";
import { useQuery } from "react-query";
import getUser from "../../helpers/api/user/getUser";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { useGlobalState } from "../../state";
import LoadingPage from "../auth/components/LoadingPage";
import { MainNavbar } from "../home/components/nav/MainNavbar";
import SectionTitle from "../home/components/sections/sectionTitle";

export const AdminHome = () => {
  const NAV_ITEMS = [
    {
      name: "Home",
      href: "/",
    },
    {
      name: "Users",
      href: "/admin/users",
    },
    {
      name: "Files",
      href: "/admin/files",
    },
  ];
  const [token] = useStickyState("token");
  const { isError, isLoading, data, error } = useQuery(
    "currentUser",
    async () => {
      return await getUser(token);
    }
  );
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
      <Container fluid>
        {isLoading && <LoadingPage />}
        {isError && <h1>{(error as AxiosError).message}</h1>}
        <SectionTitle title="Admin Panel" color="#bf1194" />
      </Container>
    </>
  );
};

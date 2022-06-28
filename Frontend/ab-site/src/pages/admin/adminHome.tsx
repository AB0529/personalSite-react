import { AxiosError } from "axios";
import { useEffect } from "react";
import { Container } from "react-bootstrap";
import { useQuery } from "react-query";
import getUser from "../../helpers/api/user/getUser";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";

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
        {isLoading && <h1>...</h1>}
        {isError && <h1>{(error as AxiosError).message}</h1>}
        {data && (
          <h1>
            Welcome {data?.result.firstName} {data?.result.lastName}
          </h1>
        )}
      </Container>
    </>
  );
};

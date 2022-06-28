import { AxiosError } from "axios";
import { Container } from "react-bootstrap";
import { useQuery } from "react-query";
import getUser from "../../helpers/api/user/getUser";
import { useStickyState } from "../../helpers/hooks/useStickyState";
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

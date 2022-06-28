import { AxiosError } from "axios";
import { useEffect } from "react";
import { Container, Table } from "react-bootstrap";
import { useQuery } from "react-query";
import { useNavigate } from "react-router-dom";
import getAllUsersLimited from "../../helpers/api/user/getAllUsersLimited";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { IDBUser, IUser } from "../../helpers/typings";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";

export const AdminUsers = () => {
  const navigate = useNavigate();
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

  // Get all users
  const { isLoading, isError, data, error } = useQuery(
    "allUsers",
    async () => {
      return await getAllUsersLimited(token, 10);
    },
    {
      retry: 1,
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
        {isLoading && <h5>...</h5>}
        {isError && <h5>Error: {(error as AxiosError).message}</h5>}
        {data && (
          <Table striped bordered hover variant={theme}>
            <thead>
              <tr>
                <th>#</th>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Roles</th>
                {/* <th>Password</th> */}
              </tr>
            </thead>
            <tbody>
              {(data?.result as Array<IDBUser>).map((user) => {
                return (
                  <tr>
                    <td>{user.id}</td>
                    <td>{user.username}</td>
                    <td>{user.firstName}</td>
                    <td>{user.lastName}</td>
                    <td>{user.email}</td>
                    <td>{user.roles.flatMap((a) => a.name + " ")}</td>
                    {/* <td>{user.password}</td> */}
                  </tr>
                );
              })}
            </tbody>
          </Table>
        )}
      </Container>
    </>
  );
};

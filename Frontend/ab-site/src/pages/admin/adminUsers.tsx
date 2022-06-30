import { AxiosError } from "axios";
import { useEffect } from "react";
import { Button, Container, Table } from "react-bootstrap";
import { HiPencilAlt } from "react-icons/hi";
import { useQuery } from "react-query";
import { Link } from "react-router-dom";
import config from "../../config/config";
import userDelete from "../../helpers/api/auth/userDelete";
import getAllUsersLimited from "../../helpers/api/user/getAllUsersLimited";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { IDBUser } from "../../helpers/typings";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";
import SectionTitle from "../home/components/sections/sectionTitle";

export const AdminUsers = () => {
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

  // Get all users
  const { isLoading, isError, data, error } = useQuery(
    "allUsers",
    async () => {
      return await getAllUsersLimited(token, config.LIMIT_MAX);
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

  const deleteOnClick = (id: number) => {
    userDelete(token, id)
      .then((r) => {
        alert(r.message);
      })
      .catch((e: AxiosError) => alert(e.message));
  };

  return (
    <>
      <MainNavbar items={NAV_ITEMS} />
      <SectionTitle title="Users" color="#fff" />
      <Container fluid>
        {isLoading && <h5>...</h5>}
        {isError && <h5>Error: {(error as AxiosError).message}</h5>}
        {data && (
          <Table striped bordered hover variant={theme}>
            <thead>
              <tr>
                <th></th>
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
                    <td width={50}>
                      <Container>
                        <Button
                          variant="danger"
                          onClick={() => deleteOnClick(user.id)}
                        >
                          X
                        </Button>
                      </Container>
                    </td>
                    <td>{user.id}</td>
                    <td>
                      {user.username}{" "}
                      <Link
                        to={{
                          pathname: "/admin/users/edit",
                        }}
                        state={user}
                      >
                        <HiPencilAlt />
                      </Link>
                    </td>
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

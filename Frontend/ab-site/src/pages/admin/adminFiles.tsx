import { AxiosError } from "axios";
import { useEffect } from "react";
import { Button, Container, Table } from "react-bootstrap";
import { useQuery } from "react-query";
import config from "../../config/config";
import fileDelete from "../../helpers/api/file/fileDelete";
import getAllFilesLimited from "../../helpers/api/file/getAllFilesLimited";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { IFile } from "../../helpers/typings";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";

export const AdminFiles = () => {
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
    "allAdminFiles",
    async () => {
      return await getAllFilesLimited(token, config.LIMIT_MAX);
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

  const deleteOnClick = (id: string) => {
    fileDelete(token, id)
      .then((r) => {
        alert(r.message);
      })
      .catch((e: AxiosError) => alert(e.message));
  };

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
                <th></th>
                <th>#</th>
                <th>Name</th>
                <th>Owner</th>
                <th>Content Type</th>
                <th>Content</th>
              </tr>
            </thead>
            <tbody>
              {(data?.result as Array<IFile>).map((file) => {
                return (
                  <tr>
                    <td>
                      <Button
                        variant="danger"
                        onClick={() => deleteOnClick(file[0])}
                      >
                        X
                      </Button>
                    </td>
                    <td>{file[0]}</td>
                    {
                      <td>
                        <a
                          href={
                            file[3].startsWith("image/")
                              ? `${config.API_URL}/file/v/${file[0]}`
                              : `${config.API_URL}/file/d/${file[0]}`
                          }
                        >
                          {file[1]}
                        </a>
                      </td>
                    }
                    <td>{file[2]}</td>
                    <td>{file[3]}</td>
                    <td>{file[4]}</td>
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

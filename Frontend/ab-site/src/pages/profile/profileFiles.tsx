import { AxiosError } from "axios";
import { useEffect, useRef, useState } from "react";
import { Button, Container, Table } from "react-bootstrap";
import { useQuery } from "react-query";
import { Link } from "react-router-dom";
import config from "../../config/config";
import fileDelete from "../../helpers/api/file/fileDelete";
import fileUpload from "../../helpers/api/file/fileUpload";
import getAllFilesLimited from "../../helpers/api/file/getAllFilesLimited";
import getAllUserFilesLimited from "../../helpers/api/file/getAllUserFilesLimited";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { IFile } from "../../helpers/typings";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";
import SectionTitle from "../home/components/sections/sectionTitle";

import "./profile.scss";

export const ProfileFiles = () => {
  const NAV_ITEMS = [
    {
      name: "Home",
      href: "/",
    },
    {
      name: "Files",
      href: "/admin/files",
    },
  ];

  const [token] = useStickyState("token");

  // Get all files
  const { isLoading, isError, data, error, refetch } = useQuery(
    "allUserFiles",
    async () => {
      return await getAllUserFilesLimited(token, config.LIMIT_MAX);
    },
    {
      // enabled: false,
      staleTime: Infinity,
      retry: 1,
    }
  );
  const deleteOnClick = (id: string) => {
    fileDelete(token, id)
      .then(() => {
        refetch();
      })
      .catch((e: AxiosError) => alert(e.message));
  };
  const theme = useGlobalState("theme")[0];
  const inputRef = useRef<HTMLInputElement>(null);
  const [disableBtn, setDisableBtn] = useState(false);
  const [inputKey, setInputKey] = useState(0);

  const handleUpload = () => {
    if (!inputRef.current?.files) return;
    else if (inputRef.current.files?.length <= 0) return;

    let formData = new FormData();
    formData.append("multipartFile", inputRef.current.files[0]);
    setDisableBtn(true);
    fileUpload(token, formData)
      .then(() => {
        refetch();
        // Clear files
        setInputKey(Date.now());
        setDisableBtn(false);
      })
      .catch((err) => {
        alert(err.message);
        setDisableBtn(false);
      });
  };

  const downloadFile = (id: string, type: string, name: string) => {
    fetch(`${config.API_URL}/file/d/${id}`, {
      method: "GET",
      headers: {
        Authentication: token,
      },
    })
      .then((resp) =>
        resp.blob().then(async (resp) => {
          const link = document.createElement("a");
          const url = window.URL.createObjectURL(new Blob([resp], { type }));

          // Firefox requires link to be in body
          document.body.appendChild(link);
          link.target = "_blank";
          link.href = url;
          link.setAttribute("download", name);
          link.click();
          link.parentNode!.removeChild(link);
        })
      )
      .catch((err) => console.error(`DownloadFileErr: ${err}`));
  };

  useEffect(() => {
    if (theme === "dark") document.body.classList.add("dark-theme");

    return () => {
      document.body.classList.remove("dark-theme");
    };
  });

  return (
    <>
      <MainNavbar items={NAV_ITEMS} />
      <SectionTitle title="Files" color="#438aba" />

      <Container className="d-flex justify-content-center text-align-center">
        <h5>
          It is best practice to{" "}
          <a href="https://github.com/AB0529/file-protect" target="_blank">
            encrypt
          </a>{" "}
          your files first!
        </h5>
      </Container>

      <Container
        fluid
        className="d-flex justify-content-center text-align-center"
      >
        {isLoading && <h5>...</h5>}
        {isError && (error as AxiosError).code === "ERR_BAD_REQUEST" && (
          <div>
            <h1>Uh oh.. you have no files!</h1>
            <div>
              <Button
                onClick={handleUpload}
                disabled={disableBtn}
                style={{ width: "100%" }}
              >
                Upload
              </Button>
              <input ref={inputRef} type="file" key={inputKey} />
            </div>
          </div>
        )}
        {isError && (error as AxiosError).code !== "ERR_BAD_REQUEST" && (
          <h1>Error: {(error as AxiosError).message}</h1>
        )}
        {data && (
          <div>
            <div className="d-flex justify-content-center text-align-center">
              <input ref={inputRef} type="file" key={inputKey} />
              <br></br>
              <Button
                onClick={handleUpload}
                disabled={disableBtn}
                style={{ width: "25%" }}
              >
                Upload
              </Button>
            </div>

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
                          {file[3].startsWith("image/") ? (
                            <a href={`${config.API_URL}/file/v/${file[0]}`}>
                              {file[1]}
                            </a>
                          ) : (
                            <button
                              className="btn-link"
                              onClick={() =>
                                downloadFile(file[0], file[3], file[1])
                              }
                            >
                              {file[1]}
                            </button>
                          )}
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
          </div>
        )}
      </Container>
    </>
  );
};

import { useEffect } from "react";
import { Container } from "react-bootstrap";
import { useGlobalState } from "../../../state";

export default () => {
  const theme = useGlobalState("theme")[0];

  useEffect(() => {
    if (theme === "dark") document.body.classList.add("dark-theme");

    return () => {
      document.body.classList.remove("dark-theme");
    };
  });

  return (
    <>
      <Container
        fluid
        className="d-flex justify-content-center align-items-center text-center"
        style={{ padding: "40vh" }}
      >
        <h1>Loading...</h1>
      </Container>
    </>
  );
};

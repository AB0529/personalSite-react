import { Container, Nav, Navbar } from "react-bootstrap";
import { AiFillGithub, AiFillLinkedin } from "react-icons/ai";
import getUser from "../../../../helpers/api/user/getUser";
import { useQuery } from "react-query";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import { ADMIN_ROLE } from "../../../../helpers/typings";
import { useGlobalState } from "../../../../state";
import { LoggedInUser } from "./loggedInUser";

import "./nav.scss";

import NavBrand from "./navBrand";
import ThemeIcon from "./themeIcon";

export default () => {
  const socials = [
    {
      name: "GitHub",
      icon: <AiFillGithub size={30} />,
      link: "https://github.com/AB0529/",
    },
    {
      name: "LinkedIn",
      icon: <AiFillLinkedin size={30} />,
      link: "https://www.linkedin.com/in/anish-bastola/",
    },
  ];

  const theme = useGlobalState("theme")[0];
  const [token] = useStickyState("token");
  const { isLoading, isError, data } = useQuery(
    "currentUser",
    async () => {
      return await getUser(token);
    },
    {
      retry: 1,
    }
  );
  const isAdmin =
    data?.result.authorities.filter((a: any) => a.authority === ADMIN_ROLE)
      .length > 0;

  return (
    <Navbar
      sticky="top"
      collapseOnSelect
      expand="lg"
      variant={theme === "dark" ? "dark" : "light"}
    >
      <Container>
        {/* Logo */}
        <Navbar.Brand href="#home">
          {" "}
          <NavBrand />{" "}
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />

        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="#about">
              {" "}
              <strong>About</strong>{" "}
            </Nav.Link>
            <Nav.Link href="#projects">
              <strong>Projects</strong>
            </Nav.Link>
            <Nav.Link href="#contact">
              <strong>Contact</strong>
            </Nav.Link>
          </Nav>
          <Nav>
            <Nav.Link>
              <ThemeIcon />
            </Nav.Link>
            {/* Socials */}
            {socials.map((s) => (
              <Nav.Link href={s.link} target="_blank">
                <strong>
                  <a>
                    {s.icon} {s.name}
                  </a>{" "}
                </strong>
              </Nav.Link>
            ))}
            {/* Load admin item */}
            {data && isAdmin && (
              <Nav.Link href="/admin">
                <strong>Admin</strong>
              </Nav.Link>
            )}
            <strong>
              <LoggedInUser
                isLoading={isLoading}
                isError={isError}
                data={data}
              />
            </strong>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

import { Container, Nav, Navbar } from "react-bootstrap";
import { useQuery } from "react-query";
import getUser from "../../../../helpers/api/user/getUser";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import { ADMIN_ROLE } from "../../../../helpers/typings";
import { useGlobalState } from "../../../../state";
import { LoggedInUser } from "./loggedInUser";
import NavBrand from "./navBrand";
import ThemeIcon from "./themeIcon";
interface IProps {
  items: Array<{
    name: string;
    href: string;
  }>;
  rightItems?: Array<{
    name: string;
    href: string;
  }>;
}

export const MainNavbar = ({ items }: IProps) => {
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

  const t = theme === "dark" ? "dark" : "light";

  return (
    <Navbar
      variant={t}
      bg={t}
      /*expand="lg"*/
      sticky="top"
      collapseOnSelect
    >
      <Container fluid>
        <Navbar.Brand href="#home">
          <NavBrand />
        </Navbar.Brand>{" "}
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto"
            // style={{ maxHeight: "100px" }}
            navbarScroll
          >
            {/* Load items from NAV_ITEMS */}
            {items &&
              items.map((item) => (
                <Nav.Link key={item.name} href={item.href}>
                  {item.name}
                </Nav.Link>
              ))}
          </Nav>
          {/* Rigtht aligned item */}
          <Nav className="justify-content-end">
            {/* Load admin item */}
            {data && isAdmin && <Nav.Link href="/admin">Admin</Nav.Link>}
            <LoggedInUser isLoading={isLoading} isError={isError} data={data} />
            <Nav.Link>
              <ThemeIcon />
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

import { Container, Nav, Navbar } from "react-bootstrap";
import { useQuery } from "react-query";
import getUser from "../../../../helpers/api/user/getUser";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import { ADMIN_ROLE } from "../../../../helpers/typings";
import { LoggedInUser } from "./loggedInUser";
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
  const NAV_TITLE = "Anish B.";
  const NAV_LOGO = "/assets/ocotpus-ico-128x128.png";

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
      variant="dark"
      bg="dark"
      /*expand="lg"*/
      sticky="top"
      collapseOnSelect
    >
      <Container fluid>
        <Navbar.Brand href="#home">
          <img
            src={NAV_LOGO}
            width="30"
            height="30"
            className="d-inline-block align-top"
          />{" "}
          {NAV_TITLE}
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
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

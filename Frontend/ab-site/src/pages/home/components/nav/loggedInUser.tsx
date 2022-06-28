import { Nav, NavDropdown } from "react-bootstrap";
import { AiOutlineUser } from "react-icons/ai";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import { IApiResponse } from "../../../../helpers/typings";
import { LoadingAnimation } from "../loadingAnimation";
interface IProps {
  isLoading: boolean;
  isError: boolean;
  data: IApiResponse | undefined;
}

export const LoggedInUser = ({ isLoading, isError, data }: IProps) => {
  const [token, setToken] = useStickyState("token");
  const handleLogOut = () => {
    setToken(null);
    window.location.reload();
  };

  const notLoggedIn = (
    <div>
      <Nav.Link href="/login">
        <AiOutlineUser fontSize={20} /> Login
      </Nav.Link>
    </div>
  );

  if (isLoading) return <LoadingAnimation size={8} />;
  else if (isError) return notLoggedIn;

  return (
    <>
      <NavDropdown
        title={
          <span>
            <AiOutlineUser fontSize={20} /> {data?.result.firstName}
          </span>
        }
        id="collasible-nav-dropdown"
      >
        <NavDropdown.Item href="/profile" key="loggedprof">
          Profile
        </NavDropdown.Item>
        <NavDropdown.Divider key="loggeddiv" />
        <NavDropdown.Item
          onClick={handleLogOut}
          key="loggedlogout"
          style={{ color: "#d9073f" }}
        >
          Log Out
        </NavDropdown.Item>
      </NavDropdown>
    </>
  );
};

import { useQuery } from "react-query";
import { Navigate, Outlet } from "react-router-dom";
import getUser from "../../../../helpers/api/user/getUser";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import LoadingPage from "../LoadingPage";

export const LoggedInRoute = () => {
  const [token] = useStickyState("token");
  const { isError, data, isLoading } = useQuery("currentUser", async () => {
    return await getUser(token);
  });

  if (isLoading) return <LoadingPage />;
  else if (isError) return <Navigate to="/login" />;

  return <Outlet context={data?.result} />;
};

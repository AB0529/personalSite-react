import { useQuery } from "react-query";
import { Navigate, Outlet } from "react-router-dom";
import getUser from "../../../../helpers/api/user/getUser";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import { LoadingAnimation } from "../../../home/components/loadingAnimation";

export const LoggedInRoute = () => {
  const [token] = useStickyState("token");
  const { isError, data, isLoading } = useQuery("currentUser", async () => {
    return await getUser(token);
  });

  if (isLoading) return <LoadingAnimation size={8} />;
  else if (isError) return <Navigate to="/login" />;

  return <Outlet context={data?.result} />;
};

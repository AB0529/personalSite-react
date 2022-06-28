import { useQuery } from "react-query";
import { Navigate, Outlet } from "react-router-dom";
import getUser from "../../../../helpers/api/user/getUser";
import { useStickyState } from "../../../../helpers/hooks/useStickyState";
import { ADMIN_ROLE, IUser } from "../../../../helpers/typings";
import { LoadingAnimation } from "../../../home/components/loadingAnimation";

export const AdminRoute = () => {
  const [token] = useStickyState("token");

  const { isError, data, isLoading } = useQuery(
    "currentUser",
    async () => {
      return await getUser(token);
    },
    {
      retry: 1,
    }
  );

  if (isLoading) return <LoadingAnimation size={8} />;
  else if (isError) return <Navigate to="/login" />;

  const isAdmin =
    (data?.result as IUser).authorities.findIndex(
      (a) => a.authority === ADMIN_ROLE
    ) !== -1;

  // Not authorized
  if (!isAdmin) return <Navigate to="/login" />;

  return <Outlet context={data?.result} />;
};

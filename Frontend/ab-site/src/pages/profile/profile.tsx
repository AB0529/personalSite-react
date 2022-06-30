import { useContext, useEffect } from "react";
import { useLocation, useNavigate, useOutletContext } from "react-router-dom";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { IApiResponse, IDBUser, IUser } from "../../helpers/typings";
import { useGlobalState } from "../../state";
import { MainNavbar } from "../home/components/nav/MainNavbar";

import SectionTitle from "../home/components/sections/sectionTitle";
import * as FormikForm from "../auth/components/form/FormikForm";
import * as Yup from "yup";
import UserEditForm from "../auth/components/form/AdminUserEditForm";
import userAdminUpdate from "../../helpers/api/user/userAdminUpdate";
import { AxiosError } from "axios";
import handleAxiosError from "../../helpers/api/handleAxiosError";
import userUpdate from "../../helpers/api/user/userUpdate";

export const Profile = () => {
  const navigate = useNavigate();
  const user = useOutletContext<IUser>();

  const [token, setToken] = useStickyState("token");
  const theme = useGlobalState("theme")[0];

  if (!user) navigate(-1);

  const NAV_ITEMS = [
    {
      name: "Home",
      href: "/",
    },
    {
      name: "Files",
      href: "/profile/files",
    },
  ];

  const fields: FormikForm.IFormikFormField[] = [
    {
      name: "username",
      initalValue: user.username,
      schema: Yup.string().required("Username cannot be empty"),
    },
    {
      name: "firstName",
      initalValue: user.firstName,
      schema: Yup.string().required("First name cannot be empty"),
    },
    {
      name: "lastName",
      initalValue: user.lastName,
      schema: Yup.string().required("Last name cannot be empty"),
    },
    {
      name: "email",
      initalValue: user.email,
      schema: Yup.string()
        .email("Email is invalid")
        .required("Email cannot be empty"),
    },
    {
      name: "password",
      initalValue: "",
      schema: Yup.string().optional(),
    },
    {
      name: "confirmPassword",
      initalValue: "",
      schema: Yup.string().oneOf(
        [Yup.ref("password"), null],
        "Passwords must match"
      ),
    },
  ];

  useEffect(() => {
    if (theme === "dark") document.body.classList.add("dark-theme");

    return () => {
      document.body.classList.remove("dark-theme");
    };
  });
  const formOnSubmit: FormikForm.TFormikFormSubmit = (
    { username, password, email, firstName, lastName },
    { setStatus, setSubmitting }
  ) => {
    setSubmitting(true);

    userUpdate(
      token,
      username,
      user.username,
      password,
      email,
      user.email,
      firstName,
      lastName
    )
      .then((resp: IApiResponse) => {
        setSubmitting(false);
        alert(resp.message);
        window.location.reload();
      })
      .catch((err: AxiosError) => {
        setSubmitting(false);
        setStatus(handleAxiosError(err));
      });
  };

  const formRender = (values: any) => <UserEditForm {...values} />;

  return (
    <>
      <MainNavbar items={NAV_ITEMS} />
      <SectionTitle title={user.username.toUpperCase()} />
      <FormikForm.FormikForm
        fields={fields}
        onSubmit={formOnSubmit}
        render={formRender}
      />
    </>
  );
};

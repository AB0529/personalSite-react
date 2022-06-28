import * as Yup from "yup";
import { useNavigate } from "react-router-dom";
import * as FormikForm from "./components/form/FormikForm";
import userRegister from "../../helpers/api/auth/userRegister";
import { AxiosError } from "axios";
import handleAxiosError from "../../helpers/api/handleAxiosError";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import RegisterCardForm from "./components/form/RegisterCardForm";
import { MainNavbar } from "../home/components/nav/MainNavbar";

const fields: FormikForm.IFormikFormField[] = [
  {
    name: "username",
    initalValue: "",
    schema: Yup.string().required("Username cannot be empty"),
  },
  {
    name: "firstName",
    initalValue: "",
    schema: Yup.string().required("First name cannot be empty"),
  },
  {
    name: "lastName",
    initalValue: "",
    schema: Yup.string().required("Last name cannot be empty"),
  },
  {
    name: "email",
    initalValue: "",
    schema: Yup.string()
      .email("Email is invalid")
      .required("Email cannot be empty"),
  },
  {
    name: "password",
    initalValue: "",
    schema: Yup.string().required("Password cannot be empty"),
  },
];

export const Register = () => {
  const navigate = useNavigate();
  const [token, setToken] = useStickyState("token");

  const formOnSubmit: FormikForm.TFormikFormSubmit = (
    { username, password, firstName, lastName, email },
    { setStatus, setSubmitting }
  ) => {
    setSubmitting(true);

    userRegister(username, password, email, firstName, lastName)
      .then((resp) => {
        // Store user token
        setToken(`AB0529 ${resp.result.token}`);
        setStatus({
          error: false,
          msg: "Success, you will now be redirected...",
        });
        setSubmitting(false);
        setTimeout(() => navigate(-1), 3e3);
      })
      .catch((err: AxiosError) => {
        setSubmitting(false);
        setStatus(handleAxiosError(err));
      });
  };

  const formRender = (values: any) => <RegisterCardForm {...values} />;
  const NAV_ITEMS = [
    {
      name: "Home",
      href: "/",
    },
  ];

  return (
    <>
      <MainNavbar items={NAV_ITEMS} />
      <FormikForm.FormikForm
        fields={fields}
        onSubmit={formOnSubmit}
        render={formRender}
      />
    </>
  );
};

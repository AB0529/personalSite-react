import * as Yup from "yup";
import userLogin from "../../helpers/api/auth/userLogin";
import { useStickyState } from "../../helpers/hooks/useStickyState";
import { useNavigate } from "react-router-dom";
import * as FormikForm from "./components/form/FormikForm";
import { AxiosError } from "axios";
import handleAxiosError from "../../helpers/api/handleAxiosError";
import LoginCardForm from "./components/form/LoginCardForm";

// TODO: Add incoming from prop for navigation
const fields: FormikForm.IFormikFormField[] = [
  {
    name: "username",
    initalValue: "",
    schema: Yup.string().required("Username cannot be empty"),
  },
  {
    name: "password",
    initalValue: "",
    schema: Yup.string().required("Password cannot be empty"),
  },
];

export const Login = () => {
  const [token, setToken] = useStickyState("token");
  const navigate = useNavigate();

  const formOnSubmit: FormikForm.TFormikFormSubmit = (
    { username, password },
    { setStatus, setSubmitting }
  ) => {
    setSubmitting(true);

    userLogin(username, password)
      .then((resp) => {
        // Store user token
        setToken(`AB0529 ${resp.result.token}`);
        setSubmitting(false);
        navigate(-1);
      })
      .catch((err: AxiosError) => {
        setSubmitting(false);
        setStatus(handleAxiosError(err));
      });
  };

  const formRender = (values: any) => <LoginCardForm {...values} />;

  return (
    <>
      <FormikForm.FormikForm
        fields={fields}
        onSubmit={formOnSubmit}
        render={formRender}
      />
    </>
  );
};

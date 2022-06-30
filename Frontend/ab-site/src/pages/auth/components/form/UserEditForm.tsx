import {
  ErrorMessage,
  Field,
  Form,
  FormikErrors,
  FormikTouched,
  FormikValues,
} from "formik";
import { useEffect } from "react";
import { Button, Card } from "react-bootstrap";
import { AiOutlineMail } from "react-icons/ai";
import { BiLock, BiUserCircle } from "react-icons/bi";
import { useGlobalState } from "../../../../state";

import "../../auth.scss";

interface IProps {
  errors: FormikErrors<FormikValues>;
  status: any;
  touched: FormikTouched<FormikValues>;
  isSubmitting: boolean;
}

export default ({ errors, status, touched, isSubmitting }: IProps) => {
  const theme = useGlobalState("theme")[0];

  useEffect(() => {
    if (theme === "dark") document.body.classList.add("dark-theme");

    return () => {
      document.body.classList.remove("dark-theme");
    };
  });

  return (
    <Card
      className={
        theme === "dark"
          ? "dark-theme shadow-lg rounded ab-center-card"
          : "shadow-lg rounded ab-center-card"
      }
    >
      <Card.Body>
        <Form>
          {status && status.error && (
            <div className={"alert alert-danger"}>{status.msg}</div>
          )}
          {status && !status.error && (
            <div className={"alert alert-success"}>{status.msg}</div>
          )}

          <div className="form-group">
            <label htmlFor="username">
              {" "}
              <BiUserCircle /> <strong>Username</strong>
            </label>
            <Field
              name="username"
              type="text"
              className={
                "form-control" +
                (errors.username && touched.username ? " is-invalid" : "")
              }
            />
            <ErrorMessage
              name="username"
              component="div"
              className="invalid-feedback"
            />
          </div>
          <div className="form-group">
            <label htmlFor="username">
              <strong>First name</strong>
            </label>
            <Field
              name="firstName"
              type="text"
              className={
                "form-control" +
                (errors.firstName && touched.firstName ? " is-invalid" : "")
              }
            />
            <ErrorMessage
              name="firstName"
              component="div"
              className="invalid-feedback"
            />
          </div>
          <div className="form-group">
            <label htmlFor="lastName">
              <strong>Last name</strong>
            </label>
            <Field
              name="lastName"
              type="text"
              className={
                "form-control" +
                (errors.lastName && touched.lastName ? " is-invalid" : "")
              }
            />
            <ErrorMessage
              name="lastName"
              component="div"
              className="invalid-feedback"
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">
              {" "}
              <AiOutlineMail /> <strong>Email</strong>
            </label>
            <Field
              name="email"
              type="text"
              className={
                "form-control" +
                (errors.email && touched.email ? " is-invalid" : "")
              }
            />
            <ErrorMessage
              name="email"
              component="div"
              className="invalid-feedback"
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">
              {" "}
              <BiLock /> <strong>New Password</strong>
            </label>
            <Field
              name="password"
              type="password"
              className={
                "form-control" +
                (errors.password && touched.password ? " is-invalid" : "")
              }
            />
            <ErrorMessage
              name="password"
              component="div"
              className="invalid-feedback"
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">
              {" "}
              <BiLock /> <strong>Confirm Password</strong>
            </label>
            <Field
              name="confirmPassword"
              type="password"
              className={
                "form-control" +
                (errors.confirmPassword && touched.confirmPassword
                  ? " is-invalid"
                  : "")
              }
            />
            <ErrorMessage
              name="confirmPassword"
              component="div"
              className="invalid-feedback"
            />
          </div>

          <div className="form-group">
            <br />
            <Button size="sm" type="submit" disabled={isSubmitting}>
              Update
            </Button>
            {isSubmitting && (
              <img src="assets/loading.gif" alt="loading" className="lr-l-g" />
            )}
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

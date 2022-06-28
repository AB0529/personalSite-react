import {
  ErrorMessage,
  Field,
  Form,
  FormikErrors,
  FormikTouched,
  FormikValues,
} from "formik";
import { Button, Card } from "react-bootstrap";
import { BiLock, BiUserCircle } from "react-icons/bi";
import { AiOutlineMail } from "react-icons/ai";

import "../../auth.scss";

interface IProps {
  errors: FormikErrors<FormikValues>;
  status: any;
  touched: FormikTouched<FormikValues>;
  isSubmitting: boolean;
}

export default ({ errors, status, touched, isSubmitting }: IProps) => {
  return (
    <Card className="shadow-lg rounded ab-center-card">
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
              <BiLock /> <strong>Password</strong>
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
            <br />
            <Button
              size="sm"
              variant="success"
              type="submit"
              disabled={isSubmitting}
            >
              Register
            </Button>
            {isSubmitting && (
              <img src="assets/loading.gif" alt="loading" className="lr-l-g" />
            )}
            <br /> <a href="/login">Already have an account?</a>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

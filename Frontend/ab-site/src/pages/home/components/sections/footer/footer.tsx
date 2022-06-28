import { Col, Container, Row } from "react-bootstrap";
import { useGlobalState } from "../../../../../state";
import { AiFillGithub, AiFillLinkedin, AiOutlineMail } from "react-icons/ai";

import "./footer.scss";
import SectionTitle from "../sectionTitle";

export default () => {
  const variant = useGlobalState("theme")[0];
  const email = "contact@anishb.net";
  const year = new Date().getFullYear();

  const socials = [
    {
      name: "Email",
      icon: <AiOutlineMail size={30} />,
      link: "mailto: contact@anishb.net",
    },
    {
      name: "GitHub",
      icon: <AiFillGithub size={30} />,
      link: "https://github.com/AB0529/",
    },
    {
      name: "LinkedIn",
      icon: <AiFillLinkedin size={30} />,
      link: "https://www.linkedin.com/in/anish-bastola/",
    },
  ];

  return (
    <footer
      id="contact"
      className={`footer-section  ${
        variant === "dark" ? "footer-section-dark" : "footer-section-light"
      }`}
    >
      <div style={{ paddingTop: "20px" }}></div>
      <Container>
        <Col className="d-flex justify-content-center align-items-center">
          {socials.map((item) => {
            return (
              <div>
                {" "}
                {item.icon}{" "}
                <a className="footer-socials" href={item.link}>
                  <strong>{item.name}</strong>
                </a>
              </div>
            );
          })}
        </Col>
      </Container>
      <div
        className="text-center p-4"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.09)" }}
      >
        © {year} Copyright:
        <a className="text-reset fw-bold" href="https://anishb.net">
          All rights reserved
        </a>
      </div>
    </footer>
  );
};

import { Col, Container, Row } from "react-bootstrap"
import { useGlobalState } from "../../../../../state";
import { AiOutlineMail } from "react-icons/ai"

import "./footer.scss"

export default () => {
    const variant = useGlobalState("theme")[0] ? useGlobalState("theme")[0] : "dark";
    const email = "contact@anishb.net";
    const year = new Date().getFullYear();

    return (
        <footer id="contact" className={`footer-section  ${variant === "dark" ? "footer-section-dark" : "footer-section-light"}`}>
            <Container fluid>
                <Row>
                    <Col l={6} s={12}>
                        <h5><strong>Contact</strong></h5>
                        <a href="mailto: contact@anishb.net"> <AiOutlineMail /> {email}</a>
                    </Col>
                </Row>
            </Container>
            <div className="text-center p-4" style={{backgroundColor: "rgba(0, 0, 0, 0.09)"}}>
                © {year} Copyright:
                <a className="text-reset fw-bold" href="https://anishb.net">All rights reserved</a>
            </div>
        </footer>
    )
}
import { Col, Container, Image } from 'react-bootstrap'
import SectionTitle from '../sectionTitle'

import '../sections.scss'
import './about.scss'
import { useGlobalState } from '../../../../../state'


export default () => {
    const variant = useGlobalState("theme")[0];

    const img = (
        <Image width={256}
            fluid={true}
            roundedCircle={true}
            src="selfie-min.webp" />
    )

    return (
        <Container id="about" fluid className={variant === "dark" ? "about-section-dark" : "about-section-light"}>
            <SectionTitle title="About Me" />

            <div className="d-flex about-text text-center">
                <Col ms={2}>
                    {img}
                    <p>
                        I am a full stack web developer who enjoys problem solving. I have strong technical skills and a background developing websites, automation scripts, and other applications.
                        <br />
                        I am always open and interested in new learning opportunities. Please feel free to get in touch via email at <a href="mailto://contact@anishb.net" style={{color: "rgba(107, 181, 201, 1)"}}>contact@anishb.net</a>.
                        <br />
                        <strong>Skills</strong>:
                        <ul style={{listStylePosition: "inside"}}>
                            <li>JavaScript (TypeScript, Node.JS, React)</li>
                            <li>Java (Spingboot, Gradle, Maven)</li>
                            <li>Linux (Arch, Gentoo, Debian)</li>
                            <li>Python (Django, Flask)</li>
                            <li>Databases (MySQL, MongoDB)</li>
                            <li>Go</li>
                            and much more!
                        </ul>
                    </p>
                </Col>
            </div>
        </Container>
    )
}

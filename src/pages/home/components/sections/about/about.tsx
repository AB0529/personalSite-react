import { Col, Container, Image } from 'react-bootstrap'
import SectionTitle from '../sectionTitle'

import '../sections.scss'
import './about.scss'
import { useGlobalState } from '../../../../../state'


export default () => {
    const variant = useGlobalState("theme")[0];

    const img = (
        <Image className="profile-pic"
            height={128}
            width={128}
            fluid={true}
            roundedCircle={true}
            src="selfie-min.jpg" />
    )

    return (
        <Container id="about" fluid className={variant === "dark" ? "about-section-dark" : "about-section-light"}>
            <SectionTitle title="About Me" />

            <div className="d-flex about-text text-center">
                <Col ms={2}>
                    {img}
                    <p className="about-text text-center">
                        I am a full stack web developer who enjoys problem solving. I have strong technical skills and a background developing websites, automation scripts, and other applications.
                        <br />
                        I am always open and interested in new learning opportunities. Please feel free to get in touch via email at <span style={{color: "rgba(107, 181, 201, 1)"}}>contact@anishb.net</span>.
                        <br />
                        <strong>Knowledgeable</strong>:
                        <ul>
                            <li>JavaScript (TypeScript, Node.JS, React)</li>
                            <li>Linux (Arch, Gentoo, Debian)</li>
                            <li>Python (Django)</li>
                            <li>Go</li>
                            <li>MySQL</li>
                            <li>MongoDB</li>
                            and much more!
                        </ul>
                    </p>
                </Col>
            </div>
        </Container>
    )
}
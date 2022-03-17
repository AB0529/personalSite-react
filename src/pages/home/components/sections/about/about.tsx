import { Col, Container, Image } from 'react-bootstrap'
import SectionTitle from '../sectionTitle'

import '../sections.scss'
import './about.scss'
import { useGlobalState } from '../../../../../state'


export default () => {
    const variant = useGlobalState("theme")[0] ? useGlobalState("theme")[0] : "dark";

    const img = (
        <Image className="profile-pic"
            height={128}
            width={128}
            fluid={true}
            roundedCircle={true}
            src="https://play-lh.googleusercontent.com/8ddL1kuoNUB5vUvgDVjYY3_6HwQcrg1K2fd_R8soD-e2QYj8fT9cfhfh3G0hnSruLKec" />
    )
    const aboutText = `saw you venting
    Don't try to bullshit your way out of this
    You're so suspicious
    And that's clue I will not miss
    You're the impostor
    I saw you run away
    From electrical
    Where I saw blue's body lay
    Yeah, you're a sussy baka
    I see your face
    You're a sussy baka
    You're a disgrace
    You're a sussy baka
    A war of race
    You're a sussy baka
    When the impostor is
    Red`

    return (
        <Container id="about" fluid className={variant === "dark" ? "about-section-dark" : "about-section-light"}>
            <SectionTitle title="About Me" />

            <div className="d-flex about-text text-center">
                <Col md={12}>
                    {img}
                    <p className="about-text text-center">{aboutText}</p>
                </Col>
            </div>
        </Container>
    )
}
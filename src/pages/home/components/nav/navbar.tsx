import { Container, Nav, Navbar } from "react-bootstrap"
import { AiFillGithub, AiFillLinkedin } from "react-icons/ai"
import { useGlobalState } from "../../../../state";

import "./nav.scss";

import NavBrand from "./navBrand";
import ThemeIcon from "./themeIcon";

export default () => {
    const socials = [
        {
            'name': 'GitHub',
            'icon': (<AiFillGithub size={30} />),
            'link': 'https://github.com/AB0529/'
        },
        {
            'name': 'LinkedIn',
            'icon': (<AiFillLinkedin size={30} />),
            'link': 'https://www.linkedin.com/in/anish-bastola/'
        }
    ];
    
    const theme = useGlobalState('theme')[0];

    return (
        <Navbar sticky="top" collapseOnSelect expand="lg" variant={theme === "dark" ? "dark" : "light"}>
            <Container>
                {/* Logo */}
                <Navbar.Brand href="#home"> <NavBrand /> </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />

                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="#about"> <strong>About</strong> </Nav.Link>
                        <Nav.Link href="#projects"><strong>Projects</strong></Nav.Link>
                        <Nav.Link href="#contact"><strong>Contact</strong></Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link>
                            <ThemeIcon />
                        </Nav.Link>
                        {/* Socials */}
                        {socials.map(s => (
                            <Nav.Link href={s.link}>
                                <strong><a>{s.icon} {s.name}</a> </strong>

                            </Nav.Link>
                        ))}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}
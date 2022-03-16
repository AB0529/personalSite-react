import { Container, Nav, Navbar } from "react-bootstrap"
import { AiFillGithub, AiFillLinkedin } from "react-icons/ai"
import { useGlobalState } from "../../../state";
import "../home.scss";
import ThemeIcon from "./themeIcon";

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

export default () => {
    const theme = useGlobalState('theme')[0];

    return (
        <Navbar collapseOnSelect expand="lg" className={theme === 'dark' ? 'nav-dark' : 'nav-light'}>
            <Container>
                <Navbar.Brand href="#home">Anish B. </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />

                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="#about">About</Nav.Link>
                        <Nav.Link href="#projects">Projects</Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link>
                            <ThemeIcon />
                        </Nav.Link>
                        {/* Socials */}
                        {socials.map(s => (
                            <Nav.Link href={s.link}>
                                <a>{s.icon} {s.name}</a>

                            </Nav.Link>
                        ))}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}
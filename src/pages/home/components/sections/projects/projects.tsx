import { Col, Row, Container, Card, Button } from "react-bootstrap"
import * as DevIcons from "react-icons/di";
import SectionTitle from '../sectionTitle'

import '../sections.scss'
import './projects.scss'
import { useGlobalState } from "../../../../../state"
import { useEffect, useState } from "react"

export default () => {
    const variant = useGlobalState("theme")[0];
    const [projects, setProjects] = useState<any>(null);
    const ico: any = DevIcons;

    // Load projects
    useEffect(() => {
        const getIcon = (name: string): any => {
            name = name.toLowerCase();
            name = name.charAt(0).toUpperCase() + name.slice(1);

            switch (name) {
                case "Typescript":
                    name = "Javascript";
                    break
                case "Css":
                    name = "Css3"
                    break
                case "Html":
                    name = "Html5";
                    break
            }

            return ico["Di" + name];
        }

        fetch("https://api.github.com/users/AB0529/repos?per_page=100")
            .then(d => d.json())
            .then(data => {
                let projects: any = [];

                data.forEach((d: any) => {
                    projects.push({
                        title: d.name,
                        desc: d.description,
                        link: d.html_url,
                        lang: getIcon(d.language)
                    });
                });
                setProjects(projects);
            });
    }, []);

    return (
        <Container id="projects" fluid className={variant === "dark" ? "projects-section-dark" : "projects-section-light"}>
            <SectionTitle title="Projects" color="#f5425d" />

            <Container>
                <Row>
                    {projects && projects.map((p: any) => {
                        const Ico = p.lang;

                        return (
                            <Col className="d-flex align-items-center justify-content-center">
                                <Card className={`project-card ${variant === "dark" ? "project-card-dark" : "project-card-light"}`}>
                                    <Card.Body>
                                        <Card.Title> <Ico /> <strong>{p.title}</strong> </Card.Title>
                                        <Card.Text>{p.desc}</Card.Text>
                                    </Card.Body>
                                    <Button variant="success" href={p.link} target="_blank" >Link</Button>
                                </Card>
                            </Col>
                        )
                    })}
                </Row>
            </Container>

        </Container>
    )
}
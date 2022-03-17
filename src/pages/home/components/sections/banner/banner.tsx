import { Container } from "react-bootstrap"
import { Parallax, ParallaxProvider } from "react-scroll-parallax"
import Navbar from "../../nav/navbar"

import "../sections.scss"
import "./banner.scss"

export default () => {

    return (
        <>
            <ParallaxProvider>

                <Container id="#home" fluid className="banner-section">
                    <div className="d-flex align-items-center justify-content-center">
                        <Parallax speed={-20}>
                            <div className="banner-title">
                                <span id="bracs"> &lt; </span>
                                <span id="first">Anish</span>
                                <span> </span>
                                <span id="last">B.</span>
                                <span id="bracs"> /&gt; </span>
                            </div>
                        </Parallax>
                    </div>
                </Container>
            </ParallaxProvider>
        </>
    )
}
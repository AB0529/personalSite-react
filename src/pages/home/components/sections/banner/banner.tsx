import { Container } from "react-bootstrap"
import { Parallax, ParallaxBanner, ParallaxProvider } from "react-scroll-parallax"

import "../sections.scss"
import "./banner.scss"

export default () => {
    return (
        <>
            <ParallaxProvider>
                <ParallaxBanner>
                    <Container id="#home" fluid className="banner-section">
                        <div className="d-flex align-items-center justify-content-center">
                            <Parallax speed={-25}>
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
                </ParallaxBanner>
            </ParallaxProvider>
        </>
    )
}
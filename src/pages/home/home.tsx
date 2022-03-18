import About from "./components/sections/about/about";
import Banner from "./components/sections/banner/banner";
import Projects from "./components/sections/projects/projects";
import Footer from "./components/sections/footer/footer";
import Navbar from "./components/nav/navbar";
import { useEffect } from "react";

const HomePage = () => {
    return (
        <>
            <Navbar />
            <Banner />
            <About />
            <Projects />
            <Footer />
        </>
    )
}

export default HomePage;

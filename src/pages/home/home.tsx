// import About from "./components/sections/about/about";
// import Banner from "./components/sections/banner/banner";
// import Projects from "./components/sections/projects/projects";
// import Footer from "./components/sections/footer/footer";
// import Navbar from "./components/nav/navbar";

import loadable from "@loadable/component";

const Navbar = loadable(() => import('./components/nav/navbar'))
const Banner = loadable(() => import('./components/sections/banner/banner'))
const About = loadable(() => import('./components/sections/about/about'))
const Projects = loadable(() => import('./components/sections/projects/projects'))
const Footer = loadable(() => import('./components/sections/footer/footer'))

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

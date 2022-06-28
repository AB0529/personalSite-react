import { FaOctopusDeploy } from "react-icons/fa"
import { useGlobalState } from "../../../../state";

export default () => {
    const theme = useGlobalState('theme')[0];

    return (
        <div className={`nav-brand ${theme === 'dark' ? 'nav-dark' : 'nav-light'}`}>
            <span id="first"> <FaOctopusDeploy /> Anish</span>
            <span> </span>
            <span id="last">B.</span>
        </div>
    )
}
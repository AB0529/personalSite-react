import { useGlobalState } from "../../../../state";
import { MdDarkMode, MdLightMode } from "react-icons/md";
import { setDarkTheme, setLightTheme} from "../../../../state/setTheme";

export default () => {
    let theme = useGlobalState('theme')[0];

    const handleThemeChange = () => {
        switch (theme) {
            case 'dark':
                setLightTheme();
                break;
            case 'light':
                setDarkTheme();
                break;
        }
    }
    const icon = theme === 'light' ? <MdDarkMode size={30} /> : <MdLightMode size={30} />;

    return (
        <a onClick={handleThemeChange} >
            {icon}
        </a>
    );
}
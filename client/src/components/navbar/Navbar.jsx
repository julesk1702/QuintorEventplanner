import '../../index.css';
import Sidebar from "./Sidebar";
import NavbarSmallDevice from "./NavbarSmallDevice";
import {useResize} from "../../hooks/useResize";

export default function Navbar(props) {
  const checkWindowSize = useResize();

  const checkViewport = () => {
    if (checkWindowSize.width > props.maxMobileViewport) {
      return <Sidebar/>
    } else {
      return <NavbarSmallDevice/>
    }
  }

  return checkViewport();
}

import {useResize} from "../../hooks/useResize";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarDays, faHouseChimney, faLightbulb, faUser, faPlus} from "@fortawesome/free-solid-svg-icons";
import {validateIsUserLoggedIn} from "../../utils/ValidationFunctions";
import './Footer.css'

export default function Footer(props) {
  const checkWindowSize = useResize();
  const isUserLoggedIn = validateIsUserLoggedIn();

  const handleMyAccountButtonClick = () => {
    if (isUserLoggedIn) { window.location.replace("/account"); }
    else { window.location.replace("/login"); }
  }

  const handleIdeasButtonClick = () => {
    if (isUserLoggedIn) { window.location.replace("/evenement-ideeën"); }
    else { window.location.replace("/login"); }
  }

  const setLinkIconColorBasedOnCurrentPathname = (link) => {
    if (window.location.pathname.includes(link)) { return "nav-icon-active"; }
    else { return "nav-icon"; }
  }

  const checkSmallDevice = () => {
    if (checkWindowSize.width < props.maxMobileViewport) {
      return (
        <>
          <footer className="fixed-bottom mt-5 py-3 navigation-footer">
            <div className="row justify-content-center">
              <div className="col-2 mx-1 text-center">
                <a href="/home" className={`${setLinkIconColorBasedOnCurrentPathname('/home')}`} aria-current="page">
                  <FontAwesomeIcon icon={faHouseChimney} size="2x" />
                </a>
              </div>
              <div className="col-2 mx-1 text-center">
                <a href="/home" className={`${setLinkIconColorBasedOnCurrentPathname('/events')}`} aria-current="page">
                  <FontAwesomeIcon icon={faCalendarDays} size="2x"/>
                </a>
              </div>
              <div className="col-2 mx-1 bg-transparent"></div>
              <div className="col-2 mx-1 text-center text-secondary">
              <button onClick={handleIdeasButtonClick} className={`bg-transparent border-0 ${setLinkIconColorBasedOnCurrentPathname('/evenement-ideeën')}`} aria-current="page">
                  <FontAwesomeIcon icon={faLightbulb} size="2x" />
                </button>
              </div>
              <div className="col-2 mx-1 text-center">
                <button onClick={handleMyAccountButtonClick} className={`bg-transparent border-0 ${setLinkIconColorBasedOnCurrentPathname('/account')}`} aria-current="page">
                  <FontAwesomeIcon icon={faUser} size="2x" className="" />
                </button>
              </div>
            </div>
          </footer>
          <div className="fixed-bottom d-flex justify-content-center">
            <a href="/create" className="create-icon-background border-0 d-flex justify-content-center align-items-center text-white">
              <FontAwesomeIcon icon={faPlus} size="2x" />
            </a>
          </div>
        </>
      );
    }
  }

  return checkSmallDevice();
}

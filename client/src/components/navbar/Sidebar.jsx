import '../../index.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus, faHouseChimney, faUser, faLightbulb } from '@fortawesome/free-solid-svg-icons'
import {validateIsUserLoggedIn} from "../../utils/ValidationFunctions";

export default function Sidebar() {
  const isUserLoggedIn = validateIsUserLoggedIn();

  const handleMyAccountButtonClick = () => {
    if (isUserLoggedIn) { window.location.replace("/account"); }
    else { window.location.replace("/login"); }
  }

  return (
    <div className="bg-quintor-red d-flex justify-content-between flex-column min-vh-100 sticky-top default-black-shadow col-11">
      <div>
        <a href="/home" className="text-decoration-none text-white d-flex my-4 mx-3 justify-content-center">
          <span className="display-6 fw-semibold">Quintor</span>
        </a>
        <ul className='nav flex-column mb-4'>
          {isUserLoggedIn ?
            <li className="nav-item text-white fs-5 hover-darken" aria-current="page">
              <a href="/create" className="nav-link text-white" aria-current="page">
                <FontAwesomeIcon className="align-middle" icon={faPlus} size="1x" />
                <p className="d-inline mx-3 align-middle fw-normal">Maak Event</p>
              </a>
              <div className='d-flex justify-content-center'>
                <hr className="mt-2 border-2 border-white m-0 w-75" />
              </div>
            </li>
          : null}
          <li className="nav-item text-white mt-2 hover-darken" aria-current="page">
            <a href="/home" className="nav-link text-white" aria-current="page">
              <FontAwesomeIcon className="align-middle" icon={faHouseChimney} />
              <p className="d-inline fs-5 mx-3 align-middle fw-normal">Home</p>
            </a>
          </li>
          <li className="nav-item text-white mt-2 hover-darken" aria-current="page">
            <a href="/evenement-ideeën" className="nav-link text-white" aria-current="page">
              <FontAwesomeIcon className="align-middle" icon={faLightbulb} style={{color: "white"}} />
              <p className="d-inline fs-5 mx-3 align-middle fw-normal">Ideeën</p>
            </a>
          </li>
        </ul>
      </div>
      <div className="dropdown open">
        <ul className="nav nav-pills flex-column">
          <li className="nav-item text-white fs-4 hover-darken" aria-current="page">
            <button onClick={handleMyAccountButtonClick} className="nav-link text-white fs-4" aria-current="page">
              <FontAwesomeIcon className="align-middle" icon={faUser} size="1x" />
              <p className="d-inline mx-2 my-0 align-middle fw-normal">{isUserLoggedIn ? "Mijn Account" : "Inloggen"}</p>
            </button>
          </li>
        </ul>
      </div>
    </div>
  );
}

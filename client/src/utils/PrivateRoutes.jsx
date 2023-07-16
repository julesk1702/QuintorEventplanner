import {useEffect, useState} from "react";
import {useNavigate, Outlet} from "react-router-dom";
import {validateIsUserLoggedIn, validateIsUserAdmin} from "./ValidationFunctions";

export default function PrivateRoutes(props) {

  const [isAuth, setIsAuth] = useState();
  const navigate = useNavigate();

  /**
   * Checks if the user is logged in; if not, redirects to the login page.
   */
  const authenticateUser = async () => {
    const isAuthenticated = await validateIsUserLoggedIn();
    setIsAuth(isAuthenticated);
    if (!isAuthenticated) { navigate("/login"); }
  }

  /**
   * Checks if the user is an admin; if not, redirects to the previous page.
   */
  const authenticateAdmin = async () => {
    const isAdmin = await validateIsUserAdmin();
    setIsAuth(isAdmin);
    if (!isAdmin) { navigate(-1); }
  }

  useEffect(() => {
    if (props.isAdmin) { authenticateAdmin(); }
    else if (props.isLoggedIn) { authenticateUser(); }
  });

  return <>{isAuth ? <Outlet/> : null}</>;
}

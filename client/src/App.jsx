import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {ToastContainer} from 'react-toastify';

// Pages and components
import Navbar from "./components/navbar/Navbar";
import HomePage from "./views/HomePage";
import LoginPage from "./views/LoginPage";
import AccountPage from "./views/AccountPage";
import DietsPage from "./views/DietsPage";
import EventDetailPage from "./views/EventDetailPage";
import EventCreatePage from "./views/EventCreatePage";

import IdeasPage from "./views/IdeasPage";
import PrivateRoutes from "./utils/PrivateRoutes";
import Footer from "./components/footer/Footer";
import ErrorPage from "./views/ErrorPage";

/**
 * App functional component
 * @description Main App component that controls the routing
 * @returns {JSX.Element}
 */
export default function App() {
  const maxMobileViewport = 992;

  return (window.location.pathname === "/" ? window.location.replace("/home") :
      <div className="container-full" style={{backgroundColor: '#f4f4f4'}}>
        <ToastContainer/>
        <div className="row m-0 p-0">
          <div className="col-lg-2 col-md-12 m-0 p-0">
            <div className="sticky-top">
              <Navbar maxMobileViewport={maxMobileViewport}/>
            </div>
          </div>
          <div className="col-lg-10 col-md-12 m-0 p-0">
            <Router>
              <Routes>
                {/* Public Routes */}
                <Route path="/home" exact element={<HomePage/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/events/:id" exact element={<EventDetailPage/>}/>

                {/* Private Routes for Authenticated Users */}
                <Route element={<PrivateRoutes isLoggedIn={true}/>}>
                  <Route path="/account" element={<AccountPage/>}/>
                  <Route path="/account/diets" element={<DietsPage/>}/>
                  <Route path="/create" exact element={<EventCreatePage/>}/>
                  <Route path="/evenement-ideeën" exact element={<IdeasPage/>}/>
                </Route>

                {/* Private Routes for Administrators */}
                <Route element={<PrivateRoutes isAdmin={true}/>}>
                  <Route path="/admin" element={<ErrorPage/>}/>
                </Route>

                {/* Additional Routes */}
                <Route path="/error" element={<ErrorPage/>}/>
                <Route path="*" element={<ErrorPage/>}/>
              </Routes>
            </Router>
            <Footer maxMobileViewport={maxMobileViewport}/>
          </div>
        </div>
      </div>
  );
}

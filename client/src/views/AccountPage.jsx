import Accordion from "react-bootstrap/Accordion";
import React, {useCallback, useEffect, useState} from "react";
import { toast } from "react-toastify";
import EventsRepository from "../repositories/EventsRepository";
import EventsListItem from "../components/EventList/EventListItem";

export default function AccountPage() {
  const userId = parseInt(localStorage.getItem("userId"));
  const [eventsOwnedByUser, setEventsOwnedByUser] = useState([]);

  const getEventsByRegistrationAndUser = useCallback(() => EventsRepository.getEventsByRegistrationAndUser(userId), [userId]);

  /* eslint-disable */
  useEffect(() => {
    getEventsByRegistrationAndUser()
      .then(request => {
        if (request.status === 404) { window.location.replace('/error?code=404'); }
        else if (request.status === 500) {
          toast.error("Er is iets misgegaan bij het ophalen van jouw evenementen");
          return;
        }
        setEventsOwnedByUser(request.data);
      })
    return () => {}
  }, []);

  const checkDateInPast = (date) => {
    const now = new Date();
    const dateObj = new Date(date);
    return dateObj < now;
  }

  /**
   * TODO: This is a temporary solution to log out the user.
   *       Later, this won't just use local storage.
   */
  const handleLogout = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("email");
    localStorage.removeItem("isAdmin");
    localStorage.removeItem("role");
    window.location.replace("/");
  };

  const handleNavigateDiets = () => {
    window.location.replace("/account/diets");
  };

  return (
    <div className="container h-100 text-center">
      <div className="d-flex row justify-content-center min-height">
        <div>
          {/* Top */}
          <div className="d-flex mb-3">
            <div className="ms-auto mt-4">
              <button className="btn bi bi-xl bi-box-arrow-right mt-1" style={{color: "#c23350", fontSize: "1.5em", padding: 0}} onClick={handleLogout}></button>
            </div>
            <h1 className="position-absolute start-50 translate-middle mt-5 fw-semibold" style={{width: '65%'}}>Mijn Account</h1>
          </div>
          {/* Diets */}
          <button className="d-flex bg-white p-2 col-12 column justify-content-between rounded border border-0 rounded default-black-shadow" onClick={handleNavigateDiets}>
            <div className="py-2" style={{marginLeft: "0.75em"}}>Mijn Dieetwensen</div>
            <i className="bi bi-arrow-right fs-3 pe-2" style={{color: "#c23350"}}></i>
          </button>
          <Accordion className="mt-3">
            <Accordion.Item eventKey="0" className="default-black-shadow">
              <Accordion.Header>Mijn aankomende ingeschreven evenementen</Accordion.Header>
              <Accordion.Body>
                <div className="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4 mb-lg-0 pb-lg-0 text-start">
                  {eventsOwnedByUser && eventsOwnedByUser.length >= 1 && eventsOwnedByUser.map(event => !checkDateInPast(event.startDateTime) ? <EventsListItem key={event.event_id} event={event} /> : null)}
                </div>
              </Accordion.Body>
            </Accordion.Item>
            <Accordion.Item eventKey="1" className="default-black-shadow mt-3">
              <Accordion.Header>Mijn verlopen ingeschreven evenementen</Accordion.Header>
              <Accordion.Body>
                <div className="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4 mb-5 pb-5 mb-lg-0 pb-lg-0 text-start">
                 {eventsOwnedByUser && eventsOwnedByUser.length >= 1 && eventsOwnedByUser.map(event => checkDateInPast(event.startDateTime) ? <EventsListItem key={event.event_id} event={event} /> : null)}
                </div>
              </Accordion.Body>
            </Accordion.Item>
          </Accordion>
        </div>
      </div>
    </div>
  );
}

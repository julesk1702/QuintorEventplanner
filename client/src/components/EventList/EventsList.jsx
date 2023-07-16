import React, { useState } from "react";
import EventsListItem from "./EventListItem";

export default function EventsList(props) {
  const { events } = props;
  const [isDateInPast, setDateInPast] = useState(false);
  const [showAdminEvents, setShowAdminEvents] = useState(false);
  const checkAdmin = localStorage.getItem("isAdmin");
  const checkGraduate = localStorage.getItem("role");

  const checkDateInPast = (date) => {
    const now = new Date();
    const dateObj = new Date(date);
    return dateObj < now;
  }

  const returnEventItem = (event) => {
    if (!checkDateInPast(event.startDateTime) && !isDateInPast && showAdminEvents === false && event.isApproved === true) {
      return <EventsListItem key={event.event_id} event={event} checkAdmin={checkAdmin} checkGraduate={checkGraduate} />;
    }
    if (checkDateInPast(event.startDateTime) && isDateInPast && showAdminEvents === false && event.isApproved === true) {
      return <EventsListItem key={event.event_id} event={event} checkAdmin={checkAdmin} checkGraduate={checkGraduate} />;
    }
    if (showAdminEvents && event.isApproved === false) {
      return <EventsListItem key={event.event_id} event={event} checkAdmin={checkAdmin} checkGraduate={checkGraduate} />;
    }
  }

  return (
    <div className="container py-3">
      <div className="row justify-content-center d-flex gap-2 mb-3">
        <div className="col-12 col-md-3 btn-group" role="group" aria-label="Basic radio toggle button group">
          <input type="radio" className="btn-check" name="btnradio" id="btnradio1" autoComplete="off" onChange={() => {setDateInPast(false); setShowAdminEvents(false)}} checked={!isDateInPast && !showAdminEvents} />
          <label className="btn btn-outline-primary default-black-shadow" htmlFor="btnradio1">Aankomende evenementen</label>
        </div>
        <div className="col-12 col-md-3 btn-group" role="group" aria-label="Basic radio toggle button group">
          <input type="radio" className="btn-check" name="btnradio" id="btnradio2" autoComplete="off" onChange={() => {setDateInPast(true); setShowAdminEvents(false)}} checked={isDateInPast && !showAdminEvents} />
          <label className="btn btn-outline-primary default-black-shadow" htmlFor="btnradio2">Verlopen evenementen</label>
        </div>
        <div className="col-12 col-md-3 btn-group" role="group" aria-label="Basic radio toggle button group">
          { checkAdmin === true || checkAdmin === "true" ?
          <>
            <input type="radio" className="btn-check" name="btnradio" id="btnradio3" autoComplete="off" onChange={() => {setShowAdminEvents(true)}} checked={showAdminEvents} />
            <label className="btn btn-outline-primary" htmlFor="btnradio3">Beoordeel evenementen</label>
          </>
            : null}
        </div>
      </div>
      <div className="row row-cols-1 row-cols-md-2 row-cols-xl-3 g-4 mb-5 pb-5 mb-lg-0 pb-lg-0">
        {events && events.map(event => returnEventItem(event))}
      </div>
    </div>
  );
}

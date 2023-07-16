import {Link} from "react-router-dom";
import Moment from "react-moment";
import React from "react";

export default function EventsListItem(props) {
  const { event, checkAdmin, checkGraduate } = props;

  const checkEventsPerRole = () => {
    if (!event.isGraduateChecked) {
      return (
        <div className={"col px-3"}>
          <div className="card h-100 border-0 default-black-shadow" style={{backgroundColor: "#fff"}}>
            <Link to={`/events/${event.event_id}`} style={{color: 'inherit', textDecoration: 'inherit'}}>
              <div className="card-body container p-3">
                <p className="h5 card-title fw-semibold">{event.title}</p>
                <div className="card-time d-flex gap-2" style={{color: "#c23350"}}>
                  <i className="bi-clock"></i>
                  <Moment format="llll">
                    {event.startDateTime}
                  </Moment>
                </div>
                <div className="card-location d-flex gap-2" style={{color: "#c23350"}}>
                  <i className="bi-geo-alt"></i>
                  <div className="text-truncate" data-toggle="tooltip" title={event.location}>{event.location}</div>
                </div>
                <p className="card-text fw-medium">{event.briefDescription}</p>
              </div>
            </Link>
          </div>
        </div>
      )
    }
    if (event.isGraduateChecked && (checkAdmin || checkGraduate === "graduate")) {
      return (
        <div className={"col px-3"}>
          <div className="card h-100 border-0 default-black-shadow" style={{backgroundColor: "#fff"}}>
            {event.isGraduateChecked ? <span className="badge bg-secondary rounded-bottom-0">Afstudeerder evenement</span> : null}
            <Link to={`/events/${event.event_id}`} style={{color: 'inherit', textDecoration: 'inherit'}}>
              <div className="card-body container p-3">
                <p className="h5 card-title fw-semibold">{event.title}</p>
                <div className="card-time d-flex gap-2" style={{color: "#c23350"}}>
                  <i className="bi-clock"></i>
                  <Moment format="llll">
                    {event.startDateTime}
                  </Moment>
                </div>
                <div className="card-location d-flex gap-2" style={{color: "#c23350"}}>
                  <i className="bi-geo-alt"></i>
                  <div className="text-truncate" data-toggle="tooltip" title={event.location}>{event.location}</div>
                </div>
                <p className="card-text fw-medium">{event.briefDescription}</p>
              </div>
            </Link>
          </div>
        </div>
      )
    }
  }
  return checkEventsPerRole();
}

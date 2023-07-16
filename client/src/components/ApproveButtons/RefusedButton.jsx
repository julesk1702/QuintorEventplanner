import React, { useEffect, useState } from "react";
import EventsRepository from "../../repositories/EventsRepository";
import emailjs from 'emailjs-com';
import ButtonLoader from "../loaders/ButtonLoader";

export default function RefusedButton(props) {
  emailjs.init('ZWh10Wm0AePmBCitj');
  
  const eventId = props.eventId;
  const eventTitle = props.eventTitle;
  const email = props.email;
  const [isLoading, setIsLoading] = useState(false);
  const [isApproved, setIsApproved] = useState(false);

  /* eslint-disable */
  useEffect(() => {
    if (isApproved){
      sendEmail();
    }
  }, [isApproved]);

  const sendEmail = () => {
    setIsLoading(true);
    const templateParams = {
        isApproved: "afgekeurd",
        url: `http://localhost:3000/events/${eventId}`,
        eventName: eventTitle,
        mail: email,
    };

    emailjs
        .send("service_lycspk5","template_x40xcfl", templateParams)
        .then(() => {
          setIsLoading(false);
          window.location.replace("/home");
        })
        .catch((error) => {
          setIsLoading(false);
          console.error("Error sending email:", error);
        });
  }

  const handleRefusalEvent = async () => {
    await EventsRepository.approveEvent(props.eventId, false);
    setIsApproved(true);
  }

  return (
    <div className="text-start w-100">
      <button onClick={() => handleRefusalEvent()} className="btn btn-danger w-100 ms-2">
      {isLoading ? <ButtonLoader /> : <div className="d-flex flex-row gap-2 justify-content-center"><i className="bi bi-x"></i>Afkeuren</div>}
      </button>
    </div>
  );
}

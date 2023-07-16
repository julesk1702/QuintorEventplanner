import React, { useEffect, useState } from "react";
import EventsRepository from "../../repositories/EventsRepository";
import emailjs from 'emailjs-com';
import ButtonLoader from "../loaders/ButtonLoader";

export default function ApprovedButton(props) {
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
        isApproved: "goedgekeurd",
        url: `http://localhost:3000/events/${eventId}`,
        eventName: eventTitle,
        mail: email,
    };

    emailjs
        .send("service_lycspk5","template_x40xcfl", templateParams)
        .then(() => {
          setIsLoading(false);
          window.location.reload();
        })
        .catch((error) => {
          setIsLoading(false);
          console.error("Error sending email:", error);
        });
  }

  const handleApproveEvent = async () => {
    await EventsRepository.approveEvent(props.eventId, true);
    setIsApproved(true);
  }

  return (
    <div className="text-start w-100">
      <button onClick={() => handleApproveEvent()} className="btn btn-success text-white w-100 me-2">
        {isLoading ? <ButtonLoader /> : <div className="d-flex flex-row gap-2 justify-content-center"><i className="bi bi-check"></i>Goedkeuren</div>}
      </button>
    </div>
  );
}

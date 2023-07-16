import EventsRepository from "../../repositories/EventsRepository";
import emailjs from 'emailjs-com';
import { toast } from "react-toastify";
import Button from "react-bootstrap/Button";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBell} from "@fortawesome/free-solid-svg-icons";
import React, {useCallback, useEffect, useState} from "react";
import Modal from 'react-bootstrap/Modal';

export default function ReminderButton(props) {
  emailjs.init('ZWh10Wm0AePmBCitj');

  const eventId = props.eventId;
  const eventName = props.eventTitle;
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const [notEnrolled, setNotEnrolled] = useState([]);

  const getUsersNotEnrolled = useCallback(() => EventsRepository.getUsersNotEnrolled(eventId), [eventId]);

  useEffect(() => {
    getUsersNotEnrolled().then(data => setNotEnrolled(data));
  }, [eventId, getUsersNotEnrolled]);

  const sendEmail = (email) => {
    const link = `http://localhost:3000/events/${eventId}`;
    const templateParams = {
      status: "Mis dit geweldige evenement niet!",
      personen: "collega",
      url: link,
      eventName: eventName,
      message: "Het ziet er naar uit dat je nog niet ingeschreven hebt voor dit evenement. Mocht je hier nog interesse in hebben, dan kan je je nog inschrijven via de link hieronder.",
      mail: email,
    };

    emailjs.send("service_lycspk5", "template_2e5ydjn", templateParams)
  }

  const notify = () => {
    for (const email of notEnrolled.data) {
      sendEmail(email);
    }
    toast.success("Herinnering verstuurd!");
    setShow(false);
  }

  return (
    <>
      <Button onClick={handleShow} className="bg-transparent border-0 text-black">
          <FontAwesomeIcon icon={faBell} className="mt-1" size="2x" style={{color: "var(--quintor-red)",}} />
          <p className="mt-2 fst-italic d-none d-md-block display-6" style={{fontSize: '1.04rem', fontWeight: '600'}}>Herinner</p>
      </Button>
      <Modal show={show} onHide={() => setShow(false)}>
          <Modal.Header closeButton>
              <Modal.Title>Herinner je collega's</Modal.Title>
          </Modal.Header>
          <Modal.Body>
              <p>Wilt u uw collega's op de hoogte brengen van dit evenement?</p>
              <p>De collega's die zich niet hebben ingeschreven krijgen een mailtje met de informatie over het evenement.</p>
          </Modal.Body>
          <Modal.Footer>
              <Button variant="secondary" onClick={() => setShow(false)}>
                  Annuleren
              </Button>
              <Button variant="primary" onClick={notify}>
                  Herinneren
              </Button>
          </Modal.Footer>
      </Modal>
    </>
  );
}

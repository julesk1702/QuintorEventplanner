import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrashCan} from "@fortawesome/free-solid-svg-icons";
import React, {useEffect, useState} from "react";
import EventsRepository from "../../repositories/EventsRepository";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import emailjs from 'emailjs-com';
import ButtonLoader from "../loaders/ButtonLoader";

export default function RemoveButton(props) {
  emailjs.init('ZWh10Wm0AePmBCitj');

  const [isLoading, setIsLoading] = useState(false);
  const [isDeleted, setIsDeleted] = useState(false);
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const emails = props.emails;

  /* eslint-disable */
  useEffect( () => {
    if (isDeleted) {
      for (const email of emails) {
        sendEmail(email);
      }
    }}, [isDeleted]
  );

  const sendEmail = (email) => {
    setIsLoading(true);
    const templateParams = {
        status: "Evenement verwijderd",
        personen: "collega",
        url: null,
        eventName: props.eventTitle,
        message: "Met spijt delen we u mede dat het evenement waarvoor u zich heeft ingeschreven, helaas is verwijderd. Onze excuses voor het ongemak.",
        mail: email,
    };

    emailjs
        .send("service_lycspk5", "template_2e5ydjn", templateParams)
        .then(() => {
          setIsLoading(false);
          window.location.replace("/");
        })
        .catch((error) => {
          setIsLoading(false);
          console.error("Error sending email:", error);
        });
  }


  const deleteEventById = async () => {
      await EventsRepository.deleteEventById(props.eventId);
      setIsDeleted(true);
      if (emails.length === 0) {
        window.location.replace("/");
      }
    }


  return (
    <>
      <Button onClick={handleShow} className="bg-transparent border-0 text-black">
        <FontAwesomeIcon icon={faTrashCan} className="mt-1" style={{color: "var(--red-500)"}} size="2x"/>
        <p className="mt-2 fst-italic d-none d-md-block display-6" style={{fontSize: '1.04rem', fontWeight: '600'}}>Verwijderen</p>
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>{props.eventTitle} verwijderen?</Modal.Title>
        </Modal.Header>
        <Modal.Body>Wilt u het evenement '{props.eventTitle}' permanent verwijderen?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Annuleren
          </Button>
          <Button variant="danger" onClick={() => deleteEventById()}>
          {isLoading ? <ButtonLoader /> : "Verwijderen"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

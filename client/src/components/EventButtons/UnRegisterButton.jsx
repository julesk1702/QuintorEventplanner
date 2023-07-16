import Button from "react-bootstrap/Button";
import React, {useState} from "react";
import Modal from "react-bootstrap/Modal";
import EventsRepository from "../../repositories/EventsRepository";
import { toast } from 'react-toastify';

export default function UnRegisterButton(props) {
  const {event, handleDetailReload} = props;
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const userId = localStorage.getItem("userId");

  const unRegisterUser = () => {
    EventsRepository.unRegisterUser(event.event_id, userId)
      .then(() => {
        toast.success("Je bent nu uitgeschreven voor dit evenement.");
        handleClose();
        handleDetailReload();
      })
      .catch(() => {
        toast.error("Er is iets misgegaan met het uitschrijven voor dit evenement.");
      });
  }

  return (
    <>
      <button className="bg-quintor-red text-white border-0 py-3 px-5 col-12 rounded" onClick={handleShow}>
        <p className="fs-5 m-0">Uitschrijven</p>
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Uitschrijven?</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Weet je zeker dat je jezelf wilt uitschijven voor dit evenement? Je kan je op elk moment weer inschrijven voor het evenement.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Annuleren
          </Button>
          <Button style={{backgroundColor: "var(--blue-500)"}} onClick={() => unRegisterUser()}>
            Uitschrijven
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

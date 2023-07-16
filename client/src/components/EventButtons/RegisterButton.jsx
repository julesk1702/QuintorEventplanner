import React, {useState} from "react";
import Button from "react-bootstrap/Button";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheck, faX} from "@fortawesome/free-solid-svg-icons";
import { toast } from 'react-toastify';
import Modal from "react-bootstrap/Modal";
import EventsRepository from "../../repositories/EventsRepository";
import GuestList from "../AddGuests/GuestList";
import TextInput from "../inputs/SmallTextInput";
import ButtonLoader from "../loaders/ButtonLoader";

export default function RegisterButton(props) {
  const {event, handleDetailReload} = props;
  const [show, setShow] = useState(false);
  const [guestsCheck, setGuestsCheck] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const userId = localStorage.getItem("userId");

  const [registration, setRegistration] = useState({
    userNote: "",
  });

  const [guests, setGuests] = useState([]);

  const handleGuestDataChange = (guestIndex, newGuestData) => {
    const newGuests = [...guests];
    newGuests[guestIndex] = newGuestData;
    setGuests(newGuests);
  }

  const handleInputChange = (event) => {
    const {name, value} = event.target;
    setRegistration(prevState => ({...prevState, [name]: value}));
  };

  const noGuests = () => {
    setGuestsCheck(false);
    setGuests([]);
  }

  const registerUser = async () => {
    setIsLoading(true);
    EventsRepository.registerUser(event.event_id, userId, registration.userNote)
    .then((response) => {
      if (response.status === 0 || response.status === 500) {
        toast.error("Er is iets misgegaan bij het inschrijven voor dit evenement. Probeer het later opnieuw.");
        setIsLoading(false);
        return;
      }
      if(response.status === 200){
        EventsRepository.registerGuests(guests)
          .then((response) => {
            if (response.status === 0 || response.status === 500) {
              toast.error("Er is iets misgegaan bij het inschrijven voor dit evenement. Probeer het later opnieuw.");
              setIsLoading(false);
              return;
            }
            if(response.status === 200){
              setIsLoading(false);
              toast.success("Je bent nu ingeschreven voor dit evenement!");
              handleClose();
              handleDetailReload();
            }
          })
      }
    })
  }

  return (
    <>
      <button className="bg-quintor-red text-white border-0 py-3 px-5 col-12 rounded" onClick={handleShow}>
        <p className="fs-5 m-0">Inschrijven</p>
      </button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton className="d-flex flex-column">
          <Modal.Title>Inschrijven?</Modal.Title>
          <small className="m-0 fst-italic">Persoonlijke dieetwensen verander je op je <a href="/account">accountpagina</a></small>
        </Modal.Header>
        <Modal.Body>
          <div className="usercommentcontainer">
            <TextInput
              maxCharacters={100}
              handleInputChange={handleInputChange}
              displayName="Opmerking"
              name="userNote"
              placeholder="Laat een opmerking achter voor de evenementbeheerder"
              icon="bi-pencil-square"
              isTextArea={true}
            />
            <hr className="mt-2"/>
          </div>
          {event.isGuestEnabled ?
              <div className="guestscontainer mb-3">
              <p className="h4">Wil je aanhang meenemen?</p>
                <div className="d-flex gap-2">
                  <Button style={{border: `${guestsCheck ? '2px solid #c23350' : '1px solid #D9D9D9'}`}} className="bg-white" onClick={() => setGuestsCheck(true)}>
                    <FontAwesomeIcon icon={faCheck} size="2xl" style={{color: "#000000"}} />
                  </Button>
                  <Button style={{border: `${guestsCheck ? '1px solid #D9D9D9' : '2px solid #c23350'}`}} className="bg-white" onClick={() => noGuests()}>
                    <FontAwesomeIcon icon={faX} size="2xl" style={{color: "#000000"}} />
                  </Button>
                </div>
              </div>
          : null}
              {guestsCheck ? <GuestList eventId={event.event_id} userId={userId} guests={guests} setGuests={setGuests} handleGuestDataChange={handleGuestDataChange} /> : null}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Annuleren
          </Button>
          {isLoading ? (
            <Button style={{backgroundColor: "var(--blue-500)"}} disabled>
              <ButtonLoader />
           </Button>
          ) : (
            <Button style={{backgroundColor: "var(--blue-500)"}} onClick={() => {registerUser()}}>
              Inschrijven!
            </Button>
          )}
        </Modal.Footer>
      </Modal>
    </>
  );
}

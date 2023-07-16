import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit} from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import React, {useEffect, useState} from "react";
import Modal from "react-bootstrap/Modal";
import EventsRepository from "../../repositories/EventsRepository";
import emailjs from 'emailjs-com';
import ButtonLoader from "../loaders/ButtonLoader";
import {ContentState, convertFromHTML, convertToRaw, EditorState} from 'draft-js';
import {Editor} from 'react-draft-wysiwyg';
import draftToHtml from 'draftjs-to-html';

export default function EditButton(props) {
  emailjs.init('ZWh10Wm0AePmBCitj');
  
  const { eventDescription, currentEvent } = props;
  const eventId = props.eventId;
  const emails = props.emails;
  const [isUpdated, setIsUpdated] = useState(false);
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const [isLoading, setIsLoading] = useState(false);
  const [isChecked, setIsChecked] = useState(false);
  const currentDate = new Date().toISOString().slice(0, 16);

  /* eslint-disable */
  useEffect(() => {
    if (isUpdated && isChecked) {
      for (const email of emails) {
        sendEmail(email);
      }
    }
  }, [isUpdated, isChecked]
  );

  const [editorState, setEditorState] = useState(() => {
    if (eventDescription !== "") {
      const blocksFromHTML = convertFromHTML(eventDescription);
      const contentState = ContentState.createFromBlockArray(blocksFromHTML);
      return EditorState.createWithContent(contentState);
    } else {
      return EditorState.createEmpty();
    }
  });


  const sendEmail = (email) => {
    setIsLoading(true);
    const link = `http://localhost:3000/events/${eventId}`;
    const templateParams = {
      status: "Evenement aangepast",
      personen: "collega",
      url: link,
      eventName: currentEvent.title,
      message: "Het evenement waar je voor ingeschreven staat is zojuist aangepast. Hieronder staat om welk evenement het gaat. Klik op de link om naar de pagina te gaan.",
      mail: email,
    };

    emailjs
      .send("service_lycspk5", "template_2e5ydjn", templateParams)
      .then(() => {
        setIsLoading(false);
        window.location.reload();
      })
      .catch((error) => {
        setIsLoading(false);
        console.error("Error sending email:", error);
      });
  }

  const MAX_CHARACTERS = {
    TITLE: 40,
    DESCRIPTION: 500,
    BRIEF_DESCRIPTION: 100,
    LOCATION: 40
  }

  const [event, setEvent] = useState({
    title: currentEvent.title,
    description: "",
    briefDescription: currentEvent.briefDescription,
    startDateTime: currentEvent.startDateTime,
    location: currentEvent.location
  });

  const saveEditorContent = () => {
    const contentState = editorState.getCurrentContent();
    const contentRaw = convertToRaw(contentState);
    return draftToHtml(contentRaw);
  };

  const handleCheckboxChange = () => {
    setIsChecked(!isChecked);
  }

  const handleInputChange = (event, validationCheck) => {
    if (validationCheck && !validationCheck(event)) { return; }
    const { name, value } = event.target;
    setEvent(prevState => ({ ...prevState, [name]: value }));
  };

  const handleEditorChange = (newEditorState) => {
    setEditorState(newEditorState);
  };

  const maxCharacterAmountValidationCheck = (event, maxCharacterAmount) => {
    return event.target.value.length - 1 < maxCharacterAmount;
  };

  const formatDateTime = (dateTime) => {
    const parts = dateTime.split(':');
    if (parts.length === 2) {
      return `${dateTime}:00`;
    }
    return dateTime;
  }

  const handleSubmit = async () => {
    setIsLoading(true);
    const eventData = {
      title: event.title,
      description: saveEditorContent(),
      startDateTime: formatDateTime(event.startDateTime),
      location: event.location,
      briefDescription: event.briefDescription,
      isApproved: 1
    };

    await EventsRepository.updateEvent(eventId, eventData);
    setIsLoading(false);
    setIsUpdated(true);
    if (emails.length === 0 || !isChecked) {
      window.location.reload();
    }
  };

  return (
    <>
      <Button onClick={handleShow} className="bg-transparent border-0 text-black">
        <FontAwesomeIcon icon={faEdit} className="mt-1" style={{ color: "var(--blue-500)" }} size="2x" />
        <p className="mt-2 fst-italic d-none d-md-block display-6" style={{ fontSize: '1.04rem', fontWeight: '600' }}>Bewerken</p>
      </Button>

      <Modal show={show} onHide={handleClose} size="xl">
        <Modal.Header closeButton>
          <Modal.Title>Het event: '{currentEvent.title}' aanpassen?</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form className="mb-5 mb-md-0">
            <div className="form-group has-feedback has-feedback-left">
              <label className="control-label" htmlFor="title">Titel</label>
              <div className="input-group">
                <div>
                  <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                    <i className="bi-fonts"></i>
                  </span>
                </div>
                <input type="text" name="title" className="form-control border-0 shadow-none" placeholder="Pas de titel van het event aan" value={event.title} onChange={(event) => handleInputChange(event, (event) => maxCharacterAmountValidationCheck(event, MAX_CHARACTERS.TITLE))} />
                <div className="input-group-append">
                  <span
                    className={`input-group-text border-0 bg-transparent ${event.title.length < MAX_CHARACTERS.TITLE ? 'text-muted' : 'text-danger'}`}
                    style={{ paddingRight: 0 }}>
                    <small>{event.title.length}/{MAX_CHARACTERS.TITLE}</small>
                  </span>
                </div>
              </div>
              <hr className="mt-2" />
            </div>
            <div className="form-group">
              <label htmlFor="Beschrijving">Beschrijving</label>
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                    <i className="bi-pencil"></i>
                  </span>
                </div>
                <input type="text" name="briefDescription" className="form-control border-0 shadow-none" placeholder="Pas de korte beschrijving van het event aan" aria-label="beschrijving" value={event.briefDescription} onChange={(event) => handleInputChange(event, (event) => maxCharacterAmountValidationCheck(event, MAX_CHARACTERS.BRIEF_DESCRIPTION))} />
                <div className="input-group-append">
                  <span
                    className={`input-group-text border-0 bg-transparent ${event.briefDescription.length < MAX_CHARACTERS.BRIEF_DESCRIPTION ? 'text-muted' : 'text-danger'}`}
                    style={{ paddingRight: 0 }}>
                    <small>{event.briefDescription.length}/{MAX_CHARACTERS.BRIEF_DESCRIPTION}</small>
                  </span>
                </div>
              </div>
              <hr className="mt-2" />
            </div>
            <div className="form-group">
              <label htmlFor="Locatie">Locatie</label>
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                    <i className="bi-geo-alt"></i>
                  </span>
                </div>
                <input type="text" name="location" className="form-control border-0 shadow-none" placeholder="Pas de locatie van het event aan" value={event.location} onChange={(event) => handleInputChange(event, (event) => maxCharacterAmountValidationCheck(event, MAX_CHARACTERS.LOCATION))} />
                <div className="input-group-append">
                  <span
                    className={`input-group-text border-0 bg-transparent ${event.location.length < MAX_CHARACTERS.LOCATION ? 'text-muted' : 'text-danger'}`}
                    style={{ paddingRight: 0 }}>
                    <small>{event.location.length}/{MAX_CHARACTERS.LOCATION}</small>
                  </span>
                </div>
              </div>
              <hr className="mt-2" />
            </div>
            <div className="form-group">
              <label htmlFor="tijd">Tijd</label>
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                    <i className="bi-clock mt-2"></i>
                  </span>
                </div>
                <input type="datetime-local" name="startDateTime" min={currentDate} className="form-control mt-2 shadow-none" value={event.startDateTime} onChange={(event) => handleInputChange(event, (event) => maxCharacterAmountValidationCheck(event, 20))} />
              </div>
              <hr className="mt-2" />
            </div>
            <div className="form-group">
              <label>Details</label>
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                    <i className="bi-pencil-square"></i>
                  </span>
                </div>
                <div className="mt-3" style={{overflow: "scroll", maxWidth: "100%", height: "500px", outline: "1px solid", borderRadius: "px", padding: "4px" }}>
                  <Editor
                    toolbarClassName="toolbarClassName"
                    wrapperClassName="wrapperClassName"
                    editorClassName="editorClassName"
                    editorState={editorState}
                    onEditorStateChange={handleEditorChange}

                  />

                </div>
              </div>
              <hr className="mt-2" />
            </div>
            <div className="form-group">
              <label>Wil je iedereen op de hoogte brengen van deze wijziging?</label>
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                    <i className={isChecked ? 'bi bi-bell-fill animated-bell' : 'bi bi-bell'} ></i>
                    <input type="checkbox" style={{ marginLeft: 10 }} checked={isChecked} onChange={handleCheckboxChange} />
                  </span>
                </div>
              </div>
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Annuleren
          </Button>
          <Button style={{ backgroundColor: "var(--blue-500)" }} onClick={() => handleSubmit()}>
            {isLoading ? <ButtonLoader /> : "Aanpassen"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

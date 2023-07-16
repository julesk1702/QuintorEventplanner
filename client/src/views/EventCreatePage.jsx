import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import EventsRepository from "../repositories/EventsRepository";
import TextInput from "../components/inputs/SmallTextInput";
import {convertToRaw} from 'draft-js';
import {Editor} from 'react-draft-wysiwyg';
import draftToHtml from 'draftjs-to-html';
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import {toast} from "react-toastify";
import ButtonLoader from "../components/loaders/ButtonLoader";

export default function EventCreatePage() {
  const userId = parseInt(localStorage.getItem("userId"));
  const [guestChecked, setGuestChecked] = useState(1);
  const [graduateCheck, setGraduateChecked] = useState(0);
  const currentDate = new Date().toISOString().slice(0, 16);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const MAX_CHARACTERS = {
    BRIEF_DESCRIPTION: 100,
    DESCRIPTION: 500,
    LOCATION: 40,
    TITLE: 40
  }

  const [event, setEvent] = useState({
    title: "",
    description: "",
    startDateTime: "",
    location: "",
    briefDescription: "",
  });

  const [editorState, setEditorState] = useState(() => {});

  const saveEditorContent = () => {
    const contentState = editorState.getCurrentContent();
    const contentRaw = convertToRaw(contentState);
    return draftToHtml(contentRaw);
  };


  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEvent(prevState => ({ ...prevState, [name]: value }));
  };

  const handleEditorChange = (newEditorState) => {
    setEditorState(newEditorState);
  };

  const formatDateTime = (dateTime) => {
    const parts = dateTime.split(':');
    if (parts.length === 2) {
      return `${dateTime}:00`;
    }
    return dateTime;
  }

  const handleSubmit = async events => {
    events.preventDefault();
    const eventData = {
      user_id: userId,
      title: event.title,
      description: saveEditorContent(),
      startDateTime: formatDateTime(event.startDateTime),
      location: event.location,
      briefDescription: event.briefDescription,
      isApproved: false,
      isGuestEnabled: guestChecked,
      isGraduateChecked: graduateCheck
    };

    setIsLoading(true);
    await EventsRepository.createEvent(eventData).then((response) => {
      setIsLoading(false);
      if (response.status === 500 || response.status === 0) {
        toast.error("Er is iets misgegaan bij het aanmaken van het event");
        return;
      }
      toast.success("Event aangemaakt");
      navigate(`/events/${response.data.event_id}`);
    });
  };

  return (
    <div className="container h-100">
      <div className="row justify-content-center align-items-center h-100">
        <div className="col-md-6 col-lg-6 pb-5 pb-lg-0">
          <div className="card border-0 rounded default-black-shadow mb-5 mb-lg-0 mt-3">
            <div className="card-header bg-transparent border-0 border-sm-1">
              <h3 className="text-center font-weight-bold">Event Aanmaken</h3>
            </div>
            <div className="card-body">
              <form onSubmit={handleSubmit}>

                <TextInput
                  maxCharacters={MAX_CHARACTERS.TITLE}
                  handleInputChange={handleInputChange}
                  displayName="Titel"
                  name="title"
                  placeholder="Wat is de titel van je event"
                  icon="bi-fonts"
                  isInput={true}
                />

                <TextInput
                  maxCharacters={MAX_CHARACTERS.BRIEF_DESCRIPTION}
                  handleInputChange={handleInputChange}
                  displayName="Korte beschrijving"
                  name="briefDescription"
                  placeholder="Beschrijf kort je event"
                  icon="bi-pencil"
                  isInput={true}
                />

                <TextInput
                  maxCharacters={MAX_CHARACTERS.LOCATION}
                  handleInputChange={handleInputChange}
                  displayName="Locatie"
                  name="location"
                  placeholder="Locatie"
                  icon="bi-geo-alt"
                  isInput={true}
                />

                <div className="form-group">
                  <label htmlFor="tijd">Tijd</label>
                  <div className="input-group">
                    <div className="input-group-prepend">
                      <span className="input-group-text border-0 bg-transparent" style={{ paddingLeft: 0 }}>
                        <i className="bi-clock"></i>
                      </span>
                    </div>
                    <input type="datetime-local" name="startDateTime" min={currentDate} className="form-control mt-2 shadow-none" value={event.startDateTime} onChange={(event) => handleInputChange(event)} />
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
                    <div className="mt-3" style={{ maxWidth: "100%", height: "500px", outline: "1px solid", borderRadius: "px", padding: "4px"}}>
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
                  <label htmlFor="guests">Mag aanhang bij het evenement aansluiten?</label>
                  <div className="input-group">
                    <div className="form-check form-switch">
                      <input className="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked"
                        checked={guestChecked} onChange={() => setGuestChecked(guestChecked === 0 ? 1 : 0)} />
                      <label className="form-check-label" htmlFor="flexSwitchCheckChecked">
                        {guestChecked ? "Ja, tijdens het evenement is het mogelijk om aanhang mee te nemen."
                          : 'Nee, tijdens het evenement is het niet mogelijk om aanhang mee te nemen.'}
                      </label>
                    </div>
                  </div>
                  <hr className="mt-2" />
                </div>

                <div className="form-group">
                  <label htmlFor="guests">Is dit evenement specifiek voor een afstudeerder?</label>
                  <div className="input-group">
                    <div className="form-check form-switch">
                      <input className="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked2"
                        checked={graduateCheck} onChange={() => setGraduateChecked(graduateCheck === 0 ? 1 : 0)} />
                      <label className="form-check-label" htmlFor="flexSwitchCheckChecked2">
                        {graduateCheck ? "Ja, dit evenement is specifiek voor een afstudeerder."
                          : 'Nee, dit evenement is voor iedereen.'}
                      </label>
                    </div>
                  </div>
                  <hr className="mt-2" />
                </div>

                { isLoading ? (
                  <button type="submit" className="btn btn-primary w-100" aria-label="LoginPage" disabled>
                    <ButtonLoader />
                  </button>
                ) : (
                  <button type="submit" className="btn btn-primary w-100" aria-label="LoginPage">
                    "Plaats"
                  </button>
                )}
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

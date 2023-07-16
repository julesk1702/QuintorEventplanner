
import React, { useCallback, useEffect, useState } from "react";
import Moment from 'react-moment';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCaretLeft, faCaretDown, faLocationDot } from "@fortawesome/free-solid-svg-icons";
import EventsRepository from "../repositories/EventsRepository";
import { useParams, useNavigate } from "react-router-dom";
import RemoveButton from "../components/EventButtons/RemoveButton";
import EnrollButton from "../components/EventButtons/EnrollButton";
import ReminderButton from "../components/EventButtons/ReminderButton";
import {validateIsUserLoggedIn} from "../utils/ValidationFunctions";
import EditButton from "../components/EventButtons/EditButton";
import UserRepository from "../repositories/UsersRepository";
import ApprovedButton from "../components/ApproveButtons/ApprovedButton";
import RefusedButton from "../components/ApproveButtons/RefusedButton";
import Loader from "../components/loaders/Loader";
import UserInfoButton from "../components/EventButtons/UserInfoButton";
import DietsSummaryButton from "../components/EventButtons/DietsSummaryButton";
import NotesSummaryButton from "../components/EventButtons/NotesSummaryButton";
import FeedbackInput from "../components/Feedback/FeedbackInput";
import parse from 'html-react-parser';

export default function EventDetailPage() {
  const { id } = useParams();
  const userId = parseInt(localStorage.getItem("userId"));
  const [isShowRegistrationsChecked, setIsShowRegistrationsChecked] = useState(false);
  const [isShowGuestsChecked, setIsShowGuestsChecked] = useState(false);
  const [isShowFeedbackChecked, setIsShowFeedbackChecked] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [event, setEvent] = useState({ description: '' });
  const [registrations, setRegistrations] = useState([]);
  const [guests, setGuests] = useState([]);
  const [email, setEmail] = useState([]);
  const [dietCount, setDietCount] = useState([]);
  const [userFeedback, setUserFeedback] = useState([]);
  const [feedbackList, setFeedbackList] = useState([]);
  const navigate = useNavigate();
  const isUserLoggedIn = validateIsUserLoggedIn();
  const [enrolled, setEnrolled] = useState();
  const [checkOwner, setCheckOwner] = useState(false);
  const [checkAdmin, setCheckAdmin] = useState(false);

  const getEventById = useCallback(() => EventsRepository.getEventById(id), [id]);
  const getRegistrations = useCallback(() => EventsRepository.getEventRegistrations(id), [id]);
  const getGuests = useCallback(() => EventsRepository.getEventGuests(id), [id]);
  const getOwnersEmailByEventId = useCallback(() => EventsRepository.getOwnersEmailByEventId(id), [id]);
  const userById = useCallback(() => UserRepository.getUserById(userId), [userId]);
  const isEnrolled = useCallback(() => EventsRepository.checkUserIsRegisteredForEvent(id, userId), [id, userId]);
  const getEventDietsCount = useCallback(() => EventsRepository.getEventDietsCount(id), [id]);
  const getFeedbackByEventIdAndUserId = useCallback(() => EventsRepository.getFeedbackByEventIdAndUserId(id, userId), [id, userId]);
  const getFeedbackByEventId = useCallback(() => EventsRepository.getFeedbackByEventId(id), [id]);

  async function setIsEnrolled() {
    if (userId !== null && !isNaN(userId)) {
      isEnrolled().then(data => {
        data.status === 200 ? setEnrolled(true) : setEnrolled(false);
      })
    }
  }

  async function setEventData() {
    return getEventById()
      .then(request => {
        if (request.status === 404 || request.status === 500 || request.status === 0) {
          navigate(`/error?code=${request.status}`)
          return;
        }
        setEvent(request.data);
        setCheckOwner(request.data.user_id === userId);
        if (userId !== null && !isNaN(userId)) {
          userById().then(userRequest => {
            setCheckAdmin(userRequest.data.role === "admin");
          });
        }
      })
  }

  const checkDateInPast = (date) => {
    const now = new Date();
    const dateObj = new Date(date);
    return dateObj < now;
  }

  async function setEventRegistrations() {
    return getRegistrations()
    .then(request => {
      setRegistrations(request.data);
    })
  }

  async function setEventGuests() {
    return getGuests()
    .then(request => {
      setGuests(request.data);
    })
  }

  async function setOwnersEmail() {
    return getOwnersEmailByEventId()
      .then(request => {
        setEmail(request.data);
      })
  }

  async function setEventDietsCount() {
    return getEventDietsCount()
      .then(request => {
        setDietCount(request.data);
      })
  }

  async function setEventUserFeedback() {
    if (userId !== null && !isNaN(userId)) {
      return getFeedbackByEventIdAndUserId()
        .then(request => {
          if (request.data == null) {
            return setUserFeedback([]);
          }
          setUserFeedback(request.data);
        })
    }
  }

  async function setEventFeedbackList() {
    return getFeedbackByEventId()
      .then(request => {
        setFeedbackList(request.data);
      })
  }

  /* eslint-disable */
  useEffect(() => {
    handleDetailReload();
  }, []);

  const handleShowRegistrationsCheckboxChange = () => {
    setIsShowRegistrationsChecked(!isShowRegistrationsChecked);
  };

  const handleShowGuestsCheckboxChange = () => {
    setIsShowGuestsChecked(!isShowGuestsChecked);
  };

  const handleShowFeedbackCheckboxChange = () => {
    setIsShowFeedbackChecked(!isShowFeedbackChecked);
  };

  const handleDetailReload = async () => {
    setIsLoading(true);
    Promise.all([
      setEventRegistrations(),
      setEventGuests(),
      setEventData(),
      setIsEnrolled(),
      setOwnersEmail(),
      setEventDietsCount(),
      setEventUserFeedback(),
      setEventFeedbackList()
    ]).then(() => setIsLoading(false));
  }

  const ownerEmail = email;
  const userList = (registrations) ? registrations.map((email) => email) : [];
  const emailList = (registrations) ? registrations.map((user) => user.email) : [];
  const guestList = (guests) ? guests.map((guest_id) => guest_id) : [];
  const dietsList = (dietCount) ? dietCount.map((name) => name) : [];
  const userCount = userList.length;
  const userCountText = userCount === 1 ? "inschrijving" : "inschrijvingen";

  return (isLoading ? <Loader /> :
    <main className="text-break d-flex flex-column h-100">
      {checkAdmin && event.isApproved === false ?
        <div className="mx-1">
          <div className="d-flex flex-row justify-content-between w-100 p-2">
            <div className="w-50">
              <ApprovedButton eventId={id} eventTitle={event.title} email={ownerEmail} />
            </div>
            <div className="w-50">
              <RefusedButton eventId={id} eventTitle={event.title} email={ownerEmail} />
            </div>
          </div>
        </div>
        : null}
      <div className="container d-flex justify-content-center">
        <h2 className={`${checkAdmin && !event.isApproved ? 'mt-3' : 'mt-5'} d-inline mx-md-auto text-center`} style={{ fontWeight: '600' }}>{event.title}</h2>
      </div>
      <div className="mt-2 container rounded-top-5 flex-grow-1 min-height text-center smaller-desktop-width default-black-shadow" style={{ backgroundColor: '#fff' }}>
        <div className="row justify-content-center">
          <div className="col-md-8">
              { checkDateInPast(event.startDateTime) ? (
                <div className="pt-4 text-danger fst-italic">
                  <h4>Dit evenement is afgelopen</h4>
                  <hr />
                </div>
              ): ''}
              <div className="mt-3">
                <div className="d-flex flex-column w-100">
                  { !checkDateInPast(event.startDateTime) && (checkOwner || checkAdmin) ? (
                    <div>
                      <div className="d-flex flex-row justify-content-around">
                        <EditButton event={event} eventId={id} currentEvent={event} emails={emailList} eventDescription={event.description}/>
                        <RemoveButton eventId={id} eventTitle={event.title} emails={emailList}/>
                        <ReminderButton eventId={id} eventTitle={event.title}/>
                    </div>
                    <hr />
                  </div>
                ) : null}
              </div>
            </div>
            <p className={`${!checkDateInPast(event.startDateTime) && (checkOwner || checkAdmin) ? '' : 'pt-4'}`}>
              {parse(String(event.description))}
            </p>
            <hr />
          </div>
        </div>
        {/* Event Date */}
        <div className="row px-2">
          <div className="col-1 offset-md-2 col-md-1">
            <i className="bi bi-clock" style={{ color: "var(--quintor-red)", marginLeft: "0.12em", fontSize: '1.2rem' }} />
          </div>
          <div className="col-11 col-md-8 text-start" style={{ color: "var(--quintor-red)", fontWeight: '600' }}>
            <p className="date-time">
              <Moment format="llll">
                {event.startDateTime}
              </Moment>
            </p>
          </div>
        </div>
        {/* Event Location */}
        <div className="row px-2">
          <div className="col-1 offset-md-2 col-md-1">
            <i className="bi bi-geo-alt" icon={faLocationDot} style={{ color: "var(--quintor-red)", fontSize: '1.2rem' }} />
          </div>
          <div className="col-11 col-md-8 text-start" style={{ color: "var(--quintor-red)", fontWeight: '600' }}>
            <a
              href={`https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
                event.location)}`}
              target="_blank"
              rel="noopener noreferrer"
              className="location"
            >
              {event.location}
            </a>
          </div>
        </div>
        {/* Event Registrations */}
        <div className="row px-2">
          <div className="col-1 offset-md-2 col-md-1">
            <i className="bi bi-people" style={{ color: "var(--quintor-red)", fontSize: '1.2rem' }} />
          </div>
          <div className="col-11 col-md-8 text-start" style={{ color: "var(--quintor-red)", fontWeight: '600' }}>
            <label>{userCount} {userCountText}</label>
            <div className="d-inline ps-3">
              <FontAwesomeIcon
                icon={isShowRegistrationsChecked ? faCaretDown : faCaretLeft}
                onClick={handleShowRegistrationsCheckboxChange}
                style={{color: "var(--quintor-red)"}}
              />
            </div>
            <div className={`user-container ${isShowRegistrationsChecked ? 'mt-3' : ''}`}>
              <ul className="fa-ul" style={{marginLeft: "var(--fa-li-margin, 1.5em)"}}>
                {isShowRegistrationsChecked &&
                  userList.map((user) => (
                    <li className="mb-2 text-black fw-normal" key={user.email}>
                      <span className="fa-li">
                        <i className="bi bi-person" style={{color: "black"}}/>
                      </span>
                      {user.email}
                      { (checkOwner || checkAdmin) ? (
                        <UserInfoButton user={user} />
                      ): ''}
                    </li>
                  ))}
              </ul>
            </div>
          </div>
        </div>
        {/* Event Guests */}
        <div className={`row px-2 ${checkOwner || checkAdmin ? '' : 'mb-4'}`}>
          <div className="col-1 offset-md-2 col-md-1">
            <i className="bi bi-person-plus" style={{color: "var(--quintor-red)", fontSize: '1.2rem'}}/>
          </div>
          <div className="col-11 col-md-8 text-start" style={{color: "var(--quintor-red)", fontWeight: '600'}}>
            <label>{guestList.length} gasten </label>
            <div className="d-inline ps-3">
              <FontAwesomeIcon
                icon={isShowGuestsChecked ? faCaretDown : faCaretLeft}
                onClick={handleShowGuestsCheckboxChange}
                style={{color: "var(--quintor-red)"}}
              />
            </div>
            <div className={`user-container ${isShowGuestsChecked ? 'mt-3' : ''}`}>
              <ul className="fa-ul" style={{marginLeft: "var(--fa-li-margin, 1.5em)"}}>
                {isShowGuestsChecked &&
                  guestList.map((guest) => (
                    <li className="mb-2 text-black fw-normal" key={guest.guest_id}>
                      <span className="fa-li">
                        <i className="bi bi-person" style={{color: "black"}}/>
                      </span>
                      {guest.name}
                      { (checkOwner || checkAdmin) && (guest.note || guest.diets.length >= 1) ? (
                        <UserInfoButton guest={guest} />
                      ): ''}
                    </li>
                  ))}
              </ul>
            </div>
          </div>
        </div>
        {/* Feedback list */}
        {checkDateInPast(event.startDateTime) && (checkOwner || checkAdmin) ? (
          <div className="row px-2">
            <div className="col-1 offset-md-2 col-md-1">
              <i className="bi bi-chat-square-heart" style={{color: "var(--quintor-red)", fontSize: '1.2rem'}}/>
            </div>
            <div className="col-11 col-md-8 text-start" style={{color: "var(--quintor-red)", fontWeight: '600'}}>
              <label>Feedback </label>
              <div className="d-inline ps-3">
                <FontAwesomeIcon
                  icon={isShowFeedbackChecked ? faCaretDown : faCaretLeft}
                  onClick={handleShowFeedbackCheckboxChange}
                  style={{color: "var(--quintor-red)"}}
                />
              </div>
              <div className={`user-container ${isShowFeedbackChecked ? 'mt-3' : ''}`}>
                <ul className="fa-ul" style={{marginLeft: "var(--fa-li-margin, 1.5em)"}}>
                  {isShowFeedbackChecked && feedbackList && feedbackList.length > 0 ?
                    feedbackList.map(feedback => (
                      <li className="mb-2 text-black fw-normal" key={feedback.id.userId}>
                        <span className="fa-li">
                          <i className="bi bi-chat-right" style={{color: "black"}}/>
                        </span>
                        {feedback.feedback}
                      </li>
                    )) : ''}
                </ul>
              </div>
            </div>
          </div>
        ) : ''}
        {/* Info buttons */}
        {checkOwner || checkAdmin ? (
          <div className="row px-2 mb-4">
            <div className="col-12 offset-md-2 d-flex gap-2 justify-content-start">
              <DietsSummaryButton diets={dietsList} />
              <NotesSummaryButton
                userNotes={userList.map(user => user.note)}
                userCustomDiets={userList.map(user => user.customDiets)}
                guestNotes={guestList.map(guest => guest.note)}
              />
            </div>
          </div>
        ) : ''}
        {/* Event Enroll Button */}
        {!checkDateInPast(event.startDateTime) && isUserLoggedIn && event.isApproved ? (
          <div className="row mb-5">
            <div className="offset-lg-2 col-lg-8 mb-3">
              <EnrollButton event={event} enrolled={enrolled} handleDetailReload={handleDetailReload} />
            </div>
          </div>
        ) : null}
        {checkDateInPast(event.startDateTime) && enrolled ? (
          <div className="row px-2 mb-4">
            <div className="col-md-8 offset-md-2 text-start mb-5">
              <FeedbackInput
                eventId={id}
                userId={userId}
                feedback={userFeedback.feedback}
                handleDetailReload={handleDetailReload}
              />
            </div>
          </div>
        ) : null}
      </div>
    </main>
  );
}

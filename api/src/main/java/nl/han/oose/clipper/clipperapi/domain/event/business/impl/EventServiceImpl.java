package nl.han.oose.clipper.clipperapi.domain.event.business.impl;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.data.impl.DietDAO;
import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.event.business.IEventService;
import nl.han.oose.clipper.clipperapi.domain.event.data.impl.EventDAO;
import nl.han.oose.clipper.clipperapi.exceptions.BadRequestException;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import nl.han.oose.clipper.clipperapi.exceptions.PreconditionFailedException;
import nl.han.oose.clipper.clipperapi.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements IEventService {

  private final EventDAO eventDAO;
  private final DietDAO dietDAO;

  @Autowired
  public EventServiceImpl(EventDAO eventDAO, DietDAO dietDAO) {
    this.eventDAO = eventDAO;
    this.dietDAO = dietDAO;
  }

  @Override
  public List<Event> getAllEvents() {
    return eventDAO.getAllEvents();
  }

  @Override
  public Event getEventById(Long id) {
    if (eventDAO.getEventById(id) == null) throw new NotFoundException(this.eventIdDoesNotExistError(id));
    return eventDAO.getEventById(id);
  }

  @Override
  public void deleteEventById(Long id) {
    boolean exists = eventDAO.deleteEventById(id);
    if (!exists) throw new NotFoundException(this.eventIdDoesNotExistError(id));
  }

  @Override
  public Event createEvent(Event event) {
    return eventDAO.createEvent(event);
  }

  @Override
  public void updateEventById(Long id, Event event) { eventDAO.updateEventById(id, event); }

  @Override
  public void checkIfUserIsRegistered(Long id, Long userId) {
    boolean isRegistered = eventDAO.checkIfUserIsRegistered(id, userId);
    if (isRegistered) throw new NotFoundException("Registration not found");
  }

  @Override
  public void registerUser(Registration registration) {
    eventDAO.registerUser(registration);
  }

  @Override
  public void unRegisterUser(Long id, Long userId) {
    eventDAO.unRegisterUser(id, userId);
    eventDAO.deleteGuestsByEventIdAndUserId(id, userId);
  }

  @Override
  public void addGuests(List<GuestWithDiets> guests) {
    for (GuestWithDiets guest : guests) {
      Guest savedGuest = guest.getGuest();
      if (guest.getDietIds() != null && !guest.getDietIds().isEmpty()) {
        List<Diet> diets = dietDAO.getDietsByIds(guest.getDietIds());
        savedGuest.setDiets(diets);
      }
      eventDAO.addGuest(savedGuest);
    }
  }

  @Override
  public List<RegistrationWithEventDetails> getRegistrationsByEventId(Long id) { eventDAO.getRegistrationsByEventId(id);
    List<Object[]> results = eventDAO.getRegistrationsByEventId(id);
    if (results.isEmpty()) throw new NotFoundException("No registrations found for event with id: " + id);
    List<RegistrationWithEventDetails> registrations = new ArrayList<>();
    for (Object[] result : results) {
      RegistrationWithEventDetails registration = new RegistrationWithEventDetails();
      List<Diet> diets = dietDAO.getUserDiets(Long.valueOf((Integer) result[0]));
      if (diets != null && !diets.isEmpty()) {
        registration.setDiets(diets);
      }
      registration.setEmail((String) result[1]);
      registration.setNote((String) result[2]);
      registration.setCustomDiets((String) result[3]);
      registrations.add(registration);
    }
    return registrations;
  }

  @Override
  public List<Guest> getGuestsByEventId(Long id) {
    List<Guest> guests = eventDAO.getGuestsByEventId(id);
    if (guests.isEmpty()) throw new NotFoundException("No guests found for event with id: " + id);
    return guests;
  }

  @Override
  public String getOwnersEmailByEventId(Long id) {
    return eventDAO.getOwnersEmailByEventId(id);
  }

  @Override
  public List<Event> getEventsByRegistrationAndUser(Long userId) {
    return eventDAO.getEventsByRegistrationAndUser(userId);
  }

  @Override
  public List<String> getRegistrationEmailsByEventId(Long id) {
    return eventDAO.getRegistrationEmailsByEventId(id);
  }

  @Override
  public List<String> getUsersEmailNotEnrolled(Long id) {
    return eventDAO.getUsersEmailNotEnrolled(id);
  }

  @Override
  public List<Feedback> getFeedbackByEventId(Long id) {
    List<Feedback> feedback = eventDAO.getFeedbackByEventId(id);
    if (feedback == null || feedback.isEmpty()) throw new NotFoundException("No feedback found for event with id: " + id);
    return feedback;
  }

  @Override
  public Feedback getFeedbackByEventIdAndUserId(Long eventId, Long userId) {
    Feedback feedback = eventDAO.getFeedbackByEventIdAndUserId(eventId, userId);
    if (feedback == null) throw new NotFoundException("No feedback found for event with id: " + eventId + " and user with id: " + userId);
    return feedback;
  }

  @Override
  public void addFeedback(Feedback feedback) {
    if (feedback.getFeedback() == null
            || feedback.getFeedback().isEmpty()
            || feedback.getId().getEvent_id() == null
            || feedback.getId().getUser_id() == null)
      throw new BadRequestException("Feedback cannot be empty");
    Event event = eventDAO.getEventById(feedback.getId().getEvent_id());
    if (event == null)
      throw new NotFoundException(this.eventIdDoesNotExistError(feedback.getId().getEvent_id()));
    if (event.getStartDateTime().isAfter(LocalDateTime.now()))
      throw new PreconditionFailedException("Event with id: " + feedback.getId().getEvent_id() + " has not started yet.");
    if (LocalDateTime.now().isAfter(event.getStartDateTime().plusWeeks(2)))
      throw new PreconditionFailedException("You can only give feedback for an event 2 weeks after it has ended.");
    if (eventDAO.checkIfUserIsRegistered(feedback.getId().getEvent_id(), feedback.getId().getUser_id()))
      throw new UnauthorizedException("User with id: " + feedback.getId().getUser_id() + " is not registered for event with id: " + feedback.getId().getEvent_id());
    eventDAO.addFeedback(feedback);
  }

  @Override
  public void deleteFeedback(Long eventId, Long userId) {
    eventDAO.deleteFeedback(eventId, userId);
  }
  
  private String eventIdDoesNotExistError(Long eventId) {
    return "Event with id: " + eventId + " does not exist.";
  }
}

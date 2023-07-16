package nl.han.oose.clipper.clipperapi.domain.event.data.impl;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.event.data.*;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Component
public class EventDAO implements IEventDAO {

  private final EventRepository eventRepository;
  private final RegistrationRepository registrationRepository;
  private final GuestRepository guestRepository;
  private final FeedbackRepository feedbackRepository;

  @Autowired
  public EventDAO(EventRepository eventRepository, RegistrationRepository registrationRepository, GuestRepository guestRepository, FeedbackRepository feedbackRepository) {
    this.eventRepository = eventRepository;
    this.registrationRepository = registrationRepository;
    this.guestRepository = guestRepository;
    this.feedbackRepository = feedbackRepository;
  }

  @Override
  public List<Event> getAllEvents() {
    return eventRepository.findAll(Sort.by(ASC, "startDateTime"));
  }

  @Override
  public Event getEventById(Long id) { return eventRepository.findById(id).orElse(null); }

  @Override
  public Event createEvent(Event event) { return eventRepository.save(event); }

  @Override
  @Transactional
  public boolean deleteEventById(Long id) {
    boolean exists = eventRepository.existsById(id);

    // Delete event_id from both tables (events and registrations)
    registrationRepository.deleteEventRegistrationById(id);
    eventRepository.deleteById(id);

    return exists;
  }

  @Override
  @Transactional
  public void updateEventById(Long id, Event event) {
    Event eventExists = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event with id: " + id + " does not exist."));
    
    // Check title
    if (event.getTitle() != null &&
        event.getTitle().length() > 0 &&
        !Objects.equals(eventExists.getTitle(), event.getTitle())) {
          eventExists.setTitle(event.getTitle());
    }

    // Check description
    if (event.getDescription() != null &&
        event.getDescription().length() > 0 &&
        !Objects.equals(eventExists.getDescription(), event.getDescription())) {
      eventExists.setDescription(event.getDescription());
    }

    // Check briefDescription
    if (event.getBriefDescription() != null &&
        event.getBriefDescription().length() > 0 &&
        !Objects.equals(eventExists.getBriefDescription(), event.getBriefDescription())) {
      eventExists.setBriefDescription(event.getBriefDescription());
    }

    // Check startDateTime
    if (event.getStartDateTime() != null &&
        !Objects.equals(eventExists.getStartDateTime(), event.getStartDateTime())) {
      eventExists.setStartDateTime(event.getStartDateTime());
    }

    // Check location
    if (event.getLocation() != null &&
        event.getLocation().length() > 0 &&
        !Objects.equals(eventExists.getLocation(), event.getLocation())) {
      eventExists.setLocation(event.getLocation());
    }
    
    // Check isApproved
    if (!Objects.equals(eventExists.getIsApproved(), event.getIsApproved())) {
      eventExists.setIsApproved(event.getIsApproved());
    }
  }

  @Override
  public boolean checkIfUserIsRegistered(Long id, Long userId) {
    return registrationRepository.checkIfUserIsRegistered(id, userId).isEmpty();
  }

  @Override
  public void registerUser(Registration registration) {
      registrationRepository.save(registration);
  }

  @Override
  @Transactional
  public void unRegisterUser(Long id, Long userId) {
    registrationRepository.unRegisterUser(id, userId);
  }

  @Override
  @Transactional
  public void deleteGuestsByEventIdAndUserId(Long eventId, Long userId) {
    guestRepository.unRegisterGuests(eventId, userId);
  }

  @Override
  public void addGuests(List<Guest> guests) { guestRepository.saveAll(guests); }

  @Override
  public void addGuest(Guest guest) { guestRepository.save(guest); }

  @Override
  public List<Object[]> getRegistrationsByEventId(Long id) {
      return registrationRepository.getRegistrationsWithCustomDietsByEventId(id);
  }

  @Override
  public String getOwnersEmailByEventId(Long id) {
        return eventRepository.getOwnersEmailByEventId(id);
    }

  @Override
  public List<Guest> getGuestsByEventId(Long id) {
    return guestRepository.getGuestsByEventId(id);
  }
  
  @Override
  public List<Event> getEventsByRegistrationAndUser(Long userId) {
    return eventRepository.getEventsByRegistrationAndUser(userId);
  }

  @Override
  public List<String> getRegistrationEmailsByEventId(Long id) {
    return registrationRepository.getRegistrationsByEventId(id);
  }

  @Override
  public List<String> getUsersEmailNotEnrolled(Long id) {
    return eventRepository.getUsersEmailNotEnrolled(id);
  }

  @Override
  public List<Feedback> getFeedbackByEventId(Long id) {
    return feedbackRepository.findAllByEventId(id);
  }

  @Override
  public Feedback getFeedbackByEventIdAndUserId(Long eventId, Long userId) {
    return feedbackRepository.findByEventIdAndUserId(eventId, userId);
  }

  @Override
  public void addFeedback(Feedback feedback) {
    feedbackRepository.save(feedback);
  }

  @Override
  public void deleteFeedback(Long eventId, Long userId) {
    feedbackRepository.delete(new Feedback(new FeedbackId(eventId, userId), null));
  }
}

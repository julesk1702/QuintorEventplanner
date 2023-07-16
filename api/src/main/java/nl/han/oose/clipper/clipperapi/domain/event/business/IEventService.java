package nl.han.oose.clipper.clipperapi.domain.event.business;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;

import java.util.List;

public interface IEventService {

  List<Event> getAllEvents();

  Event getEventById(Long id);

  void deleteEventById(Long id);

  Event createEvent(Event event);

  void updateEventById(Long id, Event event);

  void registerUser(Registration registration);
  void unRegisterUser(Long id, Long userId);

  void addGuests(List<GuestWithDiets> guests);

  void checkIfUserIsRegistered(Long id, Long userId);

  List<RegistrationWithEventDetails> getRegistrationsByEventId(Long id);

  List<Guest> getGuestsByEventId(Long id);

  String getOwnersEmailByEventId(Long id);

  List<Event> getEventsByRegistrationAndUser(Long userId);

  List<String> getRegistrationEmailsByEventId(Long id);

  List<String> getUsersEmailNotEnrolled(Long id);

  List<Feedback> getFeedbackByEventId(Long id);

  Feedback getFeedbackByEventIdAndUserId(Long eventId, Long userId);

  void addFeedback(Feedback feedback);

  void deleteFeedback(Long eventId, Long userId);
}

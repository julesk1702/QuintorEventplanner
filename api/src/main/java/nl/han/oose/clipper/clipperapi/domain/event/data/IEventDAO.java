package nl.han.oose.clipper.clipperapi.domain.event.data;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;

import java.util.List;

public interface IEventDAO {

  List<Event> getAllEvents();

  Event getEventById(Long id);

  Event createEvent(Event event);
  
  boolean deleteEventById(Long id);
  
  void updateEventById(Long id, Event event);

  boolean checkIfUserIsRegistered(Long id, Long userId);

  void registerUser(Registration registration);

  void unRegisterUser(Long id, Long userId);

  void deleteGuestsByEventIdAndUserId(Long eventId, Long userId);

  void addGuests(List<Guest> guests);

  void addGuest(Guest guest);

  List<Object[]> getRegistrationsByEventId(Long id);

  String getOwnersEmailByEventId(Long id);

  List<Guest> getGuestsByEventId(Long id);

  List<Event> getEventsByRegistrationAndUser(Long userId);

  List<String> getRegistrationEmailsByEventId(Long id);

  List<String> getUsersEmailNotEnrolled(Long id);

  List<Feedback> getFeedbackByEventId(Long id);

  Feedback getFeedbackByEventIdAndUserId(Long eventId, Long userId);

  void addFeedback(Feedback feedback);

  void deleteFeedback(Long eventId, Long userId);
}

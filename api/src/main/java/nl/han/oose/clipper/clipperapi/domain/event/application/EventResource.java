package nl.han.oose.clipper.clipperapi.domain.event.application;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.event.business.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventResource {

  private final IEventService eventServiceImpl;

  @Autowired
  public EventResource(IEventService eventServiceImpl) {
    this.eventServiceImpl = eventServiceImpl;
  }

  @CrossOrigin
  @GetMapping("")
  public ResponseEntity<List<Event>> getAllEvents() {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getAllEvents());
  }

  @CrossOrigin
  @GetMapping("/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getEventById(id));
  }

  @CrossOrigin
  @DeleteMapping("/{id}")
  public ResponseEntity<Event> deleteEventById(@PathVariable Long id) {
    eventServiceImpl.deleteEventById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  
  @CrossOrigin
  @PostMapping("")
  public ResponseEntity<Event> createEvent(@RequestBody Event event) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.createEvent(event));
  }

  @CrossOrigin
  @PutMapping("/{id}")
  public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
    eventServiceImpl.updateEventById(id, event);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @CrossOrigin
  @GetMapping("/{id}/register/{userId}")
  public ResponseEntity<Void> checkIfUserIsRegistered(@PathVariable Long id, @PathVariable Long userId) {
    eventServiceImpl.checkIfUserIsRegistered(id, userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @CrossOrigin
  @PostMapping("/{id}/register/{userId}")
  public ResponseEntity<Void> registerUser(@PathVariable Long id, @PathVariable Long userId, @RequestBody Registration registration) {
    registration.setEvent_id(id);
    registration.setUser_id(userId);
    eventServiceImpl.registerUser(registration);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @CrossOrigin
  @DeleteMapping("/{id}/unregister/{userId}")
  public ResponseEntity<Void> unRegisterUser(@PathVariable Long id, @PathVariable Long userId) {
    eventServiceImpl.unRegisterUser(id, userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @CrossOrigin
  @PostMapping("/guests")
  public ResponseEntity<Void> addGuests(@RequestBody List<GuestWithDiets> guests) {
    eventServiceImpl.addGuests(guests);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @CrossOrigin
  @GetMapping("/{id}/registrations")
  public ResponseEntity<List<RegistrationWithEventDetails>> getRegistrationsByEventId(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getRegistrationsByEventId(id));
  }

  @CrossOrigin
  @GetMapping("/{id}/registrations/emails")
  public ResponseEntity<List<String>> getRegistrationEmailsByEventId(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getRegistrationEmailsByEventId(id));
  }

  @CrossOrigin
  @GetMapping("/{id}/guests")
  public ResponseEntity<List<Guest>> getGuestsByEventId(@PathVariable Long id) {
      return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getGuestsByEventId(id));
  }

  @CrossOrigin
  @GetMapping("/{id}/email")
  public ResponseEntity<String> getOwnersEmailByEventId(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getOwnersEmailByEventId(id));
  }

  @CrossOrigin
  @GetMapping("/registrations/{userId}")
  public ResponseEntity<List<Event>> getEventsByRegistrationAndUser(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getEventsByRegistrationAndUser(userId));
  }

    @CrossOrigin
    @GetMapping("/{id}/reminder")
    public ResponseEntity<List<String>> getUsersEmailNotEnrolled(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getUsersEmailNotEnrolled(id));
    }
    
  @CrossOrigin
  @GetMapping("/{id}/feedback")
  public ResponseEntity<List<Feedback>> getFeedbackByEventId(@PathVariable Long id) {
      return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getFeedbackByEventId(id));
  }

  @CrossOrigin
  @GetMapping("/{eventId}/feedback/{userId}")
  public ResponseEntity<Feedback> getFeedbackByEventIdAndUserId(@PathVariable Long eventId, @PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(eventServiceImpl.getFeedbackByEventIdAndUserId(eventId, userId));
  }

  @CrossOrigin
  @PostMapping("/{id}/feedback")
  public ResponseEntity<Void> addFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
      feedback.getId().setEvent_id(id);
      eventServiceImpl.addFeedback(feedback);
      return ResponseEntity.status(HttpStatus.OK).build();
  }

  @CrossOrigin
  @DeleteMapping("/{eventId}/feedback/{userId}")
  public ResponseEntity<Void> deleteFeedback(@PathVariable Long eventId, @PathVariable Long userId) {
      eventServiceImpl.deleteFeedback(eventId, userId);
      return ResponseEntity.status(HttpStatus.OK).build();
  }

}

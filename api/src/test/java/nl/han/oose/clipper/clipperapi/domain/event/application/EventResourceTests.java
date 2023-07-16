package nl.han.oose.clipper.clipperapi.domain.event.application;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.event.business.IEventService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventResourceTests {

  @Mock
  private IEventService eventServiceImpl;

  @InjectMocks
  private EventResource eventResource;

  @Test
  void testGetAllEvents() {
    // Arrange
    List<Event> events = new ArrayList<>();
    when(eventServiceImpl.getAllEvents()).thenReturn(events);

    // Act
    ResponseEntity<List<Event>> response = eventResource.getAllEvents();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(events, response.getBody());
    verify(eventServiceImpl, times(1)).getAllEvents();
  }

  @Test
  void testGetEventById() {
    // Arrange
    Long eventId = 1L;
    Event event = new Event();
    when(eventServiceImpl.getEventById(eventId)).thenReturn(event);

    // Act
    ResponseEntity<Event> response = eventResource.getEventById(eventId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(event, response.getBody());
    verify(eventServiceImpl, times(1)).getEventById(eventId);
  }

  @Test
  void testDeleteEventById() {
    // Arrange
    Long eventId = 1L;

    // Act
    ResponseEntity<Event> response = eventResource.deleteEventById(eventId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(eventServiceImpl, times(1)).deleteEventById(eventId);
  }

  @Test
  void testCreateEvent() {
    // Arrange
    Event event = new Event();

    // Act
    ResponseEntity<Event> response = eventResource.createEvent(event);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(eventServiceImpl, times(1)).createEvent(event);
  }

  @Test
  void testUpdateEvent() {
    // Arrange
    Long eventId = 1L;
    Event event = new Event();

    // Act
    ResponseEntity<Event> response = eventResource.updateEvent(eventId, event);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(eventServiceImpl, times(1)).updateEventById(eventId, event);
  }

  @Test
  void testCheckIfUserIsRegistered() {
    Long id = 1L;
    Long userId = 2L;

    // Mock the necessary dependencies
    doNothing().when(eventServiceImpl).checkIfUserIsRegistered(id, userId);

    // Call the controller method
    ResponseEntity<?> response = eventResource.checkIfUserIsRegistered(id, userId);

    // Verify that the eventServiceImpl.checkIfUserIsRegistered method was called with the correct arguments
    verify(eventServiceImpl).checkIfUserIsRegistered(id, userId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testRegisterUser() {
    // Arrange
    Long eventId = 1L;
    Long userId = 1L;
    Registration registration = new Registration();

    // Act
    ResponseEntity<?> response = eventResource.registerUser(eventId, userId, registration);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(eventServiceImpl, times(1)).registerUser(registration);
  }

  @Test
  void testUnRegisterUser() {
    Long id = 1L;
    Long userId = 2L;

    // Mock the necessary dependencies
    doNothing().when(eventServiceImpl).unRegisterUser(id, userId);

    // Call the controller method
    ResponseEntity<?> response = eventResource.unRegisterUser(id, userId);

    // Verify that the eventServiceImpl.unRegisterUser method was called with the correct arguments
    verify(eventServiceImpl).unRegisterUser(id, userId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testAddGuests() {
    List<GuestWithDiets> guests = Collections.singletonList(createGuestWithDiets("John", Collections.singletonList(1L)));
    ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).build();

    doNothing().when(eventServiceImpl).addGuests(anyList());

    ResponseEntity<Void> actualResponse = eventResource.addGuests(guests);

    assertEquals(expectedResponse, actualResponse);
    verify(eventServiceImpl).addGuests(guests);
  }

  @Test
  void testGetRegistrationsByEventId() {
    Long eventId = 123L;
    List<RegistrationWithEventDetails> expectedRegistrations = Arrays.asList(
      new RegistrationWithEventDetails(),
      new RegistrationWithEventDetails()
    );

    when(eventServiceImpl.getRegistrationsByEventId(eventId)).thenReturn(expectedRegistrations);

    ResponseEntity<List<RegistrationWithEventDetails>> responseEntity = eventResource.getRegistrationsByEventId(eventId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedRegistrations, responseEntity.getBody());
    verify(eventServiceImpl).getRegistrationsByEventId(eventId);
  }

  @Test
  void testGetRegistrationEmailsByEventId() {
    // Mock the required dependencies
    Long eventId = 1L;
    List<String> expectedEmails = Arrays.asList("email1@example.com", "email2@example.com");

    when(eventServiceImpl.getRegistrationEmailsByEventId(eventId)).thenReturn(expectedEmails);

    ResponseEntity<List<String>> responseEntity = eventResource.getRegistrationEmailsByEventId(eventId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedEmails, responseEntity.getBody());
  }

  @Test
  void testGetGuestsByEventId() {
    Long eventId = 1L;
    List<Guest> expectedGuests = Arrays.asList(
            new Guest(),
            new Guest()
    );

    when(eventServiceImpl.getGuestsByEventId(eventId)).thenReturn(expectedGuests);

    ResponseEntity<List<Guest>> responseEntity = eventResource.getGuestsByEventId(eventId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedGuests, responseEntity.getBody());
  }

  @Test
  void testGetOwnersEmailByEventId() {
    Long eventId = 123L;
    String expectedEmail = "example@example.com";

    when(eventServiceImpl.getOwnersEmailByEventId(eventId)).thenReturn(expectedEmail);

    ResponseEntity<String> responseEntity = eventResource.getOwnersEmailByEventId(eventId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedEmail, responseEntity.getBody());
    verify(eventServiceImpl).getOwnersEmailByEventId(eventId);
  }

  @Test
  void testGetEventsByRegistrationAndUser() {
    Long userId = 123L;
    List<Event> events = Arrays.asList(new Event(), new Event());
    when(eventServiceImpl.getEventsByRegistrationAndUser(userId)).thenReturn(events);

    ResponseEntity<List<Event>> response = eventResource.getEventsByRegistrationAndUser(userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(events, response.getBody());
  }

  @Test
  void testGetUsersEmailNotEnrolled() {
    Long eventId = 1L;
    List<String> expectedEmails = Arrays.asList("user1@example.com", "user2@example.com");
    when(eventServiceImpl.getUsersEmailNotEnrolled(eventId)).thenReturn(expectedEmails);

    ResponseEntity<List<String>> responseEntity = eventResource.getUsersEmailNotEnrolled(eventId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedEmails, responseEntity.getBody());
  }

  private GuestWithDiets createGuestWithDiets(String name, List<Long> dietIds) {
    GuestWithDiets guestWithDiets = new GuestWithDiets();
    Guest guest = new Guest();
    guest.setName(name);
    guestWithDiets.setGuest(guest);
    guestWithDiets.setDietIds(dietIds);
    return guestWithDiets;
  }

  @Test
  void testGetFeedbackByEventId() {
    Long eventId = 123L;
    List<Feedback> feedbacks = Arrays.asList(new Feedback(), new Feedback());
    when(eventServiceImpl.getFeedbackByEventId(eventId)).thenReturn(feedbacks);

    ResponseEntity<List<Feedback>> response = eventResource.getFeedbackByEventId(eventId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(feedbacks, response.getBody());
  }

  @Test
  void testGetFeedbackByEventIdAndUserId() {
    Long eventId = 123L;
    Long userId = 123L;
    Feedback feedback = new Feedback();
    when(eventServiceImpl.getFeedbackByEventIdAndUserId(eventId, userId)).thenReturn(feedback);

    ResponseEntity<Feedback> response = eventResource.getFeedbackByEventIdAndUserId(eventId, userId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(feedback, response.getBody());
  }

  @Test
  void testAddFeedback() {
    Long eventId = 123L;
    Feedback feedback = new Feedback(new FeedbackId(), null);
    ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).build();

    doNothing().when(eventServiceImpl).addFeedback(feedback);

    ResponseEntity<Void> actualResponse = eventResource.addFeedback(eventId, feedback);

    assertEquals(expectedResponse, actualResponse);
    verify(eventServiceImpl).addFeedback(feedback);
  }

  @Test
  void testDeleteFeedback() {
    Long eventId = 123L;
    Long feedbackId = 123L;
    ResponseEntity<Void> expectedResponse = ResponseEntity.status(HttpStatus.OK).build();

    doNothing().when(eventServiceImpl).deleteFeedback(eventId, feedbackId);

    ResponseEntity<Void> actualResponse = eventResource.deleteFeedback(eventId, feedbackId);

    assertEquals(expectedResponse, actualResponse);
    verify(eventServiceImpl).deleteFeedback(eventId, feedbackId);
  }
}
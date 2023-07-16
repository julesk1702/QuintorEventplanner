package nl.han.oose.clipper.clipperapi.domain.event.data.impl;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.event.data.EventRepository;
import nl.han.oose.clipper.clipperapi.domain.event.data.FeedbackRepository;
import nl.han.oose.clipper.clipperapi.domain.event.data.GuestRepository;
import nl.han.oose.clipper.clipperapi.domain.event.data.RegistrationRepository;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventDAOTests {

  @Mock
  private EventRepository eventRepository;

  @Mock
  private RegistrationRepository registrationRepository;

  @Mock
  private GuestRepository guestRepository;

  @Mock
  private FeedbackRepository feedbackRepository;

  @InjectMocks
  private EventDAO eventDAO;

  @Test
  void testGetAllEvents() {
    // Arrange
    List<Event> events = new ArrayList<>();
    Sort sort = Sort.by(Sort.Direction.ASC, "startDateTime");
    when(eventRepository.findAll(sort)).thenReturn(events);

    // Act
    List<Event> result = eventDAO.getAllEvents();

    // Assert
    assertEquals(events, result);
    verify(eventRepository, times(1)).findAll(sort);
  }

  @Test
  void testGetEventByIdExists() {
    // Arrange
    Long eventId = 1L;
    Event event = new Event();
    when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

    // Act
    Event result = eventDAO.getEventById(eventId);

    // Assert
    assertEquals(event, result);
    verify(eventRepository, times(1)).findById(eventId);
  }

  @Test
  void testGetEventByIdNotExists() {
    // Arrange
    Long eventId = 1L;
    when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

    // Act & Assert
    assertNull(eventDAO.getEventById(eventId));
    verify(eventRepository, times(1)).findById(eventId);
  }

  @Test
  void testCreateEvent() {
    // Arrange
    Event event = new Event();

    // Act
    eventDAO.createEvent(event);

    // Assert
    verify(eventRepository, times(1)).save(event);
  }

  @Test
  void testDeleteEventByIdExists() {
    // Arrange
    Long eventId = 1L;
    when(eventRepository.existsById(eventId)).thenReturn(true);

    // Act
    boolean result = eventDAO.deleteEventById(eventId);

    // Assert
    assertTrue(result);
    verify(registrationRepository, times(1)).deleteEventRegistrationById(eventId);
    verify(eventRepository, times(1)).deleteById(eventId);
    verify(eventRepository, times(1)).existsById(eventId);
  }

  @Test
  void testDeleteEventByIdNotExists() {
    // Arrange
    Long eventId = 1L;
    when(eventRepository.existsById(eventId)).thenReturn(false);

    // Act
    boolean result = eventDAO.deleteEventById(eventId);

    // Assert
    assertFalse(result);
    verify(registrationRepository, times(1)).deleteEventRegistrationById(eventId);
    verify(eventRepository, times(1)).deleteById(eventId);
    verify(eventRepository, times(1)).existsById(eventId);
  }

  @Test
  void testUpdateEventById() {
    // Arrange
    Long eventId = 1L;
    Event existingEvent = new Event();
    existingEvent.setEvent_id(eventId);
    existingEvent.setTitle("Existing Title");
    existingEvent.setDescription("Existing Description");
    existingEvent.setBriefDescription("Existing Brief Description");
    existingEvent.setStartDateTime(LocalDateTime.now().minusHours(1));
    existingEvent.setLocation("Existing Location");
    existingEvent.setIsApproved(false);

    Event updatedEvent = new Event();
    updatedEvent.setTitle("Updated Title");
    updatedEvent.setDescription("Updated Description");
    updatedEvent.setBriefDescription("Updated Brief Description");
    updatedEvent.setStartDateTime(LocalDateTime.now());
    updatedEvent.setLocation("Updated Location");
    updatedEvent.setIsApproved(true);

    when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));

    // Act
    eventDAO.updateEventById(eventId, updatedEvent);

    // Assert
    verify(eventRepository, times(1)).findById(eventId);
    verify(eventRepository, times(0)).save(existingEvent);

    assertEquals(updatedEvent.getTitle(), existingEvent.getTitle());
    assertEquals(updatedEvent.getDescription(), existingEvent.getDescription());
    assertEquals(updatedEvent.getBriefDescription(), existingEvent.getBriefDescription());
    assertEquals(updatedEvent.getStartDateTime(), existingEvent.getStartDateTime());
    assertEquals(updatedEvent.getLocation(), existingEvent.getLocation());
    assertEquals(updatedEvent.getIsApproved(), existingEvent.getIsApproved());
  }

  @Test
  void testUpdateEventByIdNotFound() {
    // Arrange
    Long eventId = 1L;
    Event updatedEvent = new Event();
    updatedEvent.setTitle("Updated Title");

    when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NotFoundException.class, () -> eventDAO.updateEventById(eventId, updatedEvent));

    verify(eventRepository, times(1)).findById(eventId);
    verify(eventRepository, times(0)).save(any(Event.class));
  }

  @Test
  void testCheckIfUserIsRegistered() {
    Long eventId = 1L;
    Long userId = 1L;

    when(registrationRepository.checkIfUserIsRegistered(eventId, userId)).thenReturn(Collections.emptyList());

    boolean result = eventDAO.checkIfUserIsRegistered(eventId, userId);

    assertTrue(result);
    verify(registrationRepository, times(1)).checkIfUserIsRegistered(eventId, userId);
  }

  @Test
  void testCheckIfUserIsNotRegistered() {
    Long eventId = 1L;
    Long userId = 1L;

    when(registrationRepository.checkIfUserIsRegistered(eventId, userId)).thenReturn(List.of(new Registration()));

    boolean result = eventDAO.checkIfUserIsRegistered(eventId, userId);

    assertFalse(result);
    verify(registrationRepository, times(1)).checkIfUserIsRegistered(eventId, userId);
  }

  @Test
  void testRegisterUser() {
    // Arrange
    Registration registration = new Registration();

    // Act
    eventDAO.registerUser(registration);

    // Assert
    verify(registrationRepository, times(1)).save(registration);
  }

  @Test
  void testUnRegisterUser() {
    Long eventId = 1L;
    Long userId = 1L;

    assertDoesNotThrow(() -> {
      eventDAO.unRegisterUser(eventId, userId);
    });

    verify(registrationRepository, times(1)).unRegisterUser(eventId, userId);
  }


  @Test
  void testDeleteGuestsByEventIdAndUserId() {
    Long eventId = 1L;
    Long userId = 1L;

    eventDAO.deleteGuestsByEventIdAndUserId(eventId, userId);

    verify(guestRepository, times(1)).unRegisterGuests(eventId, userId);
  }

  @Test
  void testAddGuests() {
    List<Guest> guests = Arrays.asList(new Guest(), new Guest());

    eventDAO.addGuests(guests);

    verify(guestRepository, times(1)).saveAll(guests);
  }
  
  @Test
  void testAddGuest() {
    Guest guest = new Guest();
    guest.setName("John");

    eventDAO.addGuest(guest);

    verify(guestRepository).save(guest);
  }

  @Test
  void testGetRegistrationsByEventId() {
    Long eventId = 123L;
    List<Object[]> expectedRegistrations = new ArrayList<>();

    when(registrationRepository.getRegistrationsWithCustomDietsByEventId(eventId)).thenReturn(expectedRegistrations);

    List<Object[]> registrations = eventDAO.getRegistrationsByEventId(eventId);

    assertEquals(expectedRegistrations, registrations);
  }

  @Test
  void testGetOwnersEmailByEventId() {
    Long eventId = 123L;
    String expectedEmail = "example@example.com";

    when(eventRepository.getOwnersEmailByEventId(eventId)).thenReturn(expectedEmail);

    String ownersEmail = eventDAO.getOwnersEmailByEventId(eventId);

    assertEquals(expectedEmail, ownersEmail);
  }

  @Test
  void testGetGuestsByEventId() {
    Long eventId = 1L;
    List<Guest> expectedGuests = new ArrayList<>();

    when(guestRepository.getGuestsByEventId(eventId)).thenReturn(expectedGuests);

    List<Guest> actualGuests = eventDAO.getGuestsByEventId(eventId);

    verify(guestRepository, times(1)).getGuestsByEventId(eventId);

    assertEquals(expectedGuests, actualGuests);
  }


  @Test
  void testGetEventsByRegistrationAndUser() {
    Long userId = 1L;
    List<Event> expectedEvents = new ArrayList<>();

    Event event1 = new Event();
    event1.setUser_id(userId);
    expectedEvents.add(event1);

    Event event2 = new Event();
    event1.setUser_id(userId);
    expectedEvents.add(event2);

    when(eventRepository.getEventsByRegistrationAndUser(userId)).thenReturn(expectedEvents);

    List<Event> actualEvents = eventDAO.getEventsByRegistrationAndUser(userId);

    assertEquals(expectedEvents.size(), actualEvents.size());
    assertEquals(expectedEvents, actualEvents);
  }

  @Test
  void testGetRegistrationEmailsByEventId() {
    Long eventId = 1L;
    List<String> expectedEmails = new ArrayList<>();

    when(registrationRepository.getRegistrationsByEventId(eventId)).thenReturn(expectedEmails);

    List<String> actualEmails = eventDAO.getRegistrationEmailsByEventId(eventId);

    verify(registrationRepository, times(1)).getRegistrationsByEventId(eventId);

    assertEquals(expectedEmails, actualEmails);
  }

  @Test
  void testGetUsersEmailNotEnrolled() {
    Long eventId = 1L;
    List<String> expectedEmails = new ArrayList<>();

    when(eventRepository.getUsersEmailNotEnrolled(eventId)).thenReturn(expectedEmails);

    List<String> actualEmails = eventDAO.getUsersEmailNotEnrolled(eventId);

    verify(eventRepository, times(1)).getUsersEmailNotEnrolled(eventId);

    assertEquals(expectedEmails, actualEmails);
  }


  @Test
  void testGetFeedbackByEventId() {
    Long eventId = 1L;
    List<Feedback> expectedFeedback = new ArrayList<>();

    Feedback feedback1 = new Feedback(new FeedbackId(), null);
    feedback1.getId().setEvent_id(eventId);
    expectedFeedback.add(feedback1);

    Feedback feedback2 = new Feedback(new FeedbackId(), null);
    feedback2.getId().setEvent_id(eventId);
    expectedFeedback.add(feedback2);

    when(feedbackRepository.findAllByEventId(eventId)).thenReturn(expectedFeedback);

    List<Feedback> actualFeedback = eventDAO.getFeedbackByEventId(eventId);

    assertEquals(expectedFeedback.size(), actualFeedback.size());
    assertEquals(expectedFeedback, actualFeedback);
  }

  @Test
  void testGetFeedbackByEventIdAndUserId() {
    Long eventId = 1L;
    Long userId = 1L;

    Feedback expectedFeedback = new Feedback(new FeedbackId(), null);
    expectedFeedback.getId().setEvent_id(eventId);
    expectedFeedback.getId().setUser_id(userId);

    when(feedbackRepository.findByEventIdAndUserId(eventId, userId)).thenReturn(expectedFeedback);

    Feedback actualFeedback = eventDAO.getFeedbackByEventIdAndUserId(eventId, userId);

    assertEquals(expectedFeedback, actualFeedback);
  }

  @Test
  void testAddFeedback() {
    Feedback feedback = new Feedback();

    eventDAO.addFeedback(feedback);

    verify(feedbackRepository, times(1)).save(feedback);
  }

  @Test
  void testDeleteFeedback() {
    Long eventId = 1L;
    Long userId = 1L;

    ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);

    eventDAO.deleteFeedback(eventId, userId);

    verify(feedbackRepository, times(1)).delete(feedbackCaptor.capture());
    Feedback capturedFeedback = feedbackCaptor.getValue();
    assertEquals(eventId, capturedFeedback.getId().getEvent_id());
    assertEquals(userId, capturedFeedback.getId().getUser_id());
    assertNull(capturedFeedback.getFeedback());
  }
}

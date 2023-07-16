package nl.han.oose.clipper.clipperapi.domain.event.business;

import nl.han.oose.clipper.clipperapi.domain.event.application.dto.*;
import nl.han.oose.clipper.clipperapi.domain.event.business.impl.EventServiceImpl;
import nl.han.oose.clipper.clipperapi.domain.event.data.impl.EventDAO;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;
import nl.han.oose.clipper.clipperapi.domain.diet.data.impl.DietDAO;
import nl.han.oose.clipper.clipperapi.exceptions.BadRequestException;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import nl.han.oose.clipper.clipperapi.exceptions.PreconditionFailedException;
import nl.han.oose.clipper.clipperapi.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceImplTests {

  @Mock
  private EventDAO eventDAO;

  @Mock
  private DietDAO dietDAO;
  
  @InjectMocks
  private EventServiceImpl eventService;

  @Test
  void testGetAllEvents() {
    List<Event> expectedEvents = new ArrayList<>();
    when(eventDAO.getAllEvents()).thenReturn(expectedEvents);

    List<Event> actualEvents = eventService.getAllEvents();

    assertEquals(expectedEvents, actualEvents);
    verify(eventDAO, times(1)).getAllEvents();
  }

  @Test
  void testGetEventById() {
    Long id = 1L;
    Event expectedEvent = new Event();
    when(eventDAO.getEventById(id)).thenReturn(expectedEvent);

    Event actualEvent = eventService.getEventById(id);

    assertEquals(expectedEvent, actualEvent);
    verify(eventDAO, times(2)).getEventById(id);
  }

  @Test
  void testGetEventByIdNotFound() {
    Long id = 1L;
    when(eventDAO.getEventById(id)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      eventService.getEventById(id);
    });

    verify(eventDAO, times(1)).getEventById(id);
  }

  @Test
  void testDeleteEventById() {
    Long id = 1L;
    when(eventDAO.deleteEventById(id)).thenReturn(true);

    assertDoesNotThrow(() -> {
      eventService.deleteEventById(id);
    });

    verify(eventDAO, times(1)).deleteEventById(id);
  }

  @Test
  void testDeleteEventByIdNotFound() {
    Long id = 1L;
    when(eventDAO.deleteEventById(id)).thenReturn(false);

    assertThrows(NotFoundException.class, () -> {
      eventService.deleteEventById(id);
    });

    verify(eventDAO, times(1)).deleteEventById(id);
  }

  @Test
  void testCreateEvent() {
    Event event = new Event();
    assertDoesNotThrow(() -> {
      eventService.createEvent(event);
    });

    verify(eventDAO, times(1)).createEvent(event);
  }

  @Test
  void testUpdateEventById() {
    Long id = 1L;
    Event event = new Event();
    assertDoesNotThrow(() -> {
      eventService.updateEventById(id, event);
    });

    verify(eventDAO, times(1)).updateEventById(id, event);
  }

  @Test
  void testCheckIfUserIsRegistered() {
    Long id = 1L;
    Long userId = 2L;
    when(eventDAO.checkIfUserIsRegistered(id, userId)).thenReturn(false);

    assertDoesNotThrow(() -> {
      eventService.checkIfUserIsRegistered(id, userId);
    });

    verify(eventDAO, times(1)).checkIfUserIsRegistered(id, userId);
  }

  @Test
  void testCheckIfUserIsRegisteredRegistrationNotFound() {
    Long id = 1L;
    Long userId = 2L;
    when(eventDAO.checkIfUserIsRegistered(id, userId)).thenReturn(true);

    assertThrows(NotFoundException.class, () -> {
      eventService.checkIfUserIsRegistered(id, userId);
    });

    verify(eventDAO, times(1)).checkIfUserIsRegistered(id, userId);
  }

  @Test
  void testRegisterUser() {
    Registration registration = new Registration();
    assertDoesNotThrow(() -> {
      eventService.registerUser(registration);
    });

    verify(eventDAO, times(1)).registerUser(registration);
  }

  @Test
  void testUnRegisterUser() {
    Long id = 1L;
    Long userId = 2L;
    assertDoesNotThrow(() -> {
      eventService.unRegisterUser(id, userId);
    });

    verify(eventDAO, times(1)).unRegisterUser(id, userId);
  }

  @Test
  void testAddGuests() {
    GuestWithDiets guestWithDiets = new GuestWithDiets();
    Guest guest = new Guest();
    guest.setName("John");
    guestWithDiets.setGuest(guest);
    guestWithDiets.setDietIds(Collections.singletonList(1L));
    List<GuestWithDiets> guests = Collections.singletonList(guestWithDiets);

    Diet diet = new Diet();
    diet.setDietId(1L);
    List<Diet> diets = Collections.singletonList(diet);

    when(dietDAO.getDietsByIds(Collections.singletonList(1L))).thenReturn(diets);

    eventService.addGuests(guests);

    verify(eventDAO).addGuest(guest);
    assertEquals(diets, guest.getDiets());
  }

  @Test
  void testGetRegistrationsByEventId() {
    Long eventId = 1L;
    List<Object[]> expectedRegistrations = Arrays.asList(
            new Object[]{1, "test1@example.com", "Note 1", "Custom Diets 1"},
            new Object[]{2, "test2@example.com", "Note 2", "Custom Diets 2"}
    );

    when(eventDAO.getRegistrationsByEventId(eventId)).thenReturn(expectedRegistrations);
    when(dietDAO.getUserDiets(1L)).thenReturn(Collections.singletonList(new Diet()));
    when(dietDAO.getUserDiets(2L)).thenReturn(Collections.singletonList(new Diet()));

    List<RegistrationWithEventDetails> registrations = eventService.getRegistrationsByEventId(eventId);

    assertEquals(expectedRegistrations.size(), registrations.size());

    for (int i = 0; i < expectedRegistrations.size(); i++) {
      Object[] expectedRegistration = expectedRegistrations.get(i);
      RegistrationWithEventDetails actualRegistration = registrations.get(i);
      assertEquals(expectedRegistration[1], actualRegistration.getEmail());
      assertEquals(expectedRegistration[2], actualRegistration.getNote());
      assertEquals(expectedRegistration[3], actualRegistration.getCustomDiets());
    }
  }

  @Test
  void testGetGuestsByEventId() {
    Long eventId = 1L;

    // Create a list of guests
    List<Guest> guests = new ArrayList<>();
    guests.add(new Guest());
    guests.add(new Guest());

    when(eventDAO.getGuestsByEventId(eventId)).thenReturn(guests);

    List<Guest> result = eventService.getGuestsByEventId(eventId);

    verify(eventDAO, times(1)).getGuestsByEventId(eventId);

    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }

  @Test
  void testGetGuestsByEventId_noGuestsFound() {
    Long eventId = 1L;

    when(eventDAO.getGuestsByEventId(eventId)).thenReturn(Collections.emptyList());

    assertThrows(NotFoundException.class, () -> eventService.getGuestsByEventId(eventId));

    verify(eventDAO, times(1)).getGuestsByEventId(eventId);
  }


  @Test
  void testGetOwnersEmailByEventId() {
    Long eventId = 123L;
    String expectedEmail = "example@example.com";

    when(eventDAO.getOwnersEmailByEventId(eventId)).thenReturn(expectedEmail);

    String ownersEmail = eventService.getOwnersEmailByEventId(eventId);

    assertEquals(expectedEmail, ownersEmail);
  }

  @Test
  void testGetEventsByRegistrationAndUser() {
    Long userId = 123L;
    List<Event> events = Arrays.asList(new Event(), new Event());
    when(eventDAO.getEventsByRegistrationAndUser(userId)).thenReturn(events);

    List<Event> result = eventService.getEventsByRegistrationAndUser(userId);

    assertEquals(events, result);
  }

  @Test
  void testGetFeedbackByEventIdForEventWithFeedback() {
    Long eventId = 123L;
    List<Feedback> feedbacks = Arrays.asList(new Feedback(), new Feedback());
    when(eventDAO.getFeedbackByEventId(eventId)).thenReturn(feedbacks);

    List<Feedback> result = eventService.getFeedbackByEventId(eventId);

    assertEquals(feedbacks, result);
  }

  @Test
  void testGetFeedbackByEventIdForEventWithoutFeedback() {
    Long eventId = 123L;
    when(eventDAO.getFeedbackByEventId(eventId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      eventService.getFeedbackByEventId(eventId);
    });
  }

  @Test
  void testGetFeedbackByEventIdAndUserIdForExistingFeedback() {
    Long eventId = 123L;
    Long userId = 456L;
    Feedback feedback = new Feedback();
    feedback.setFeedback("feedback");
    when(eventDAO.getFeedbackByEventIdAndUserId(eventId, userId)).thenReturn(feedback);

    Feedback result = eventService.getFeedbackByEventIdAndUserId(eventId, userId);

    assertEquals(feedback, result);
  }

  @Test
  void testGetFeedbackByEventIdAndUserIdForNonExistingFeedback() {
    Long eventId = 123L;
    Long userId = 456L;
    when(eventDAO.getFeedbackByEventIdAndUserId(eventId, userId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      eventService.getFeedbackByEventIdAndUserId(eventId, userId);
    });
  }

  @Test
  void testAddFeedbackWithCorrectData() {
    Feedback feedback = new Feedback(new FeedbackId(), null);
    feedback.getId().setEvent_id(123L);
    feedback.getId().setUser_id(456L);
    feedback.setFeedback("feedback");

    Event event = new Event();
    event.setEvent_id(123L);
    event.setStartDateTime(LocalDateTime.now().minusDays(1));

    doReturn(event).when(eventDAO).getEventById(123L);
    doReturn(false).when(eventDAO).checkIfUserIsRegistered(123L, 456L);

    assertDoesNotThrow(() -> {
      eventService.addFeedback(feedback);
    });

    verify(eventDAO, times(1)).addFeedback(feedback);
  }

  @Test
  void testAddFeedbackWithIncorrectFeedbackValues() {
    Feedback feedback = new Feedback(new FeedbackId(), null);
    feedback.getId().setEvent_id(123L);
    feedback.getId().setUser_id(456L);

    assertThrows(BadRequestException.class, () -> {
      eventService.addFeedback(feedback);
    });

    verify(eventDAO, times(0)).addFeedback(feedback);
  }

  @Test
  void testAddFeedbackWithNotExistingEvent() {
    Feedback feedback = new Feedback(new FeedbackId(), null);
    feedback.getId().setEvent_id(123L);
    feedback.getId().setUser_id(456L);
    feedback.setFeedback("feedback");

    doReturn(null).when(eventDAO).getEventById(123L);

    assertThrows(NotFoundException.class, () -> {
      eventService.addFeedback(feedback);
    });

    verify(eventDAO, times(0)).addFeedback(feedback);
  }

  @Test
  void testAddFeedbackForEventThatHasNotStartedYet() {
    Feedback feedback = new Feedback(new FeedbackId(), null);
    feedback.getId().setEvent_id(123L);
    feedback.getId().setUser_id(456L);
    feedback.setFeedback("feedback");

    Event event = new Event();
    event.setEvent_id(123L);
    event.setStartDateTime(LocalDateTime.now().plusDays(1));

    doReturn(event).when(eventDAO).getEventById(123L);

    assertThrows(PreconditionFailedException.class, () -> {
      eventService.addFeedback(feedback);
    });

    verify(eventDAO, times(0)).addFeedback(feedback);
  }

  @Test
  void testAddFeedbackForEventThatEndedMoreThanTwoWeeksAgo() {
    Feedback feedback = new Feedback(new FeedbackId(), null);
    feedback.getId().setEvent_id(123L);
    feedback.getId().setUser_id(456L);
    feedback.setFeedback("feedback");

    Event event = new Event();
    event.setEvent_id(123L);
    event.setStartDateTime(LocalDateTime.now().minusDays(15));

    doReturn(event).when(eventDAO).getEventById(123L);

    assertThrows(PreconditionFailedException.class, () -> {
      eventService.addFeedback(feedback);
    });

    verify(eventDAO, times(0)).addFeedback(feedback);
  }

  @Test
  void testGetRegistrationEmailsByEventId() {
    Long eventId = 1L;

    // Create a list of registration emails
    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com");

    when(eventDAO.getRegistrationEmailsByEventId(eventId)).thenReturn(emails);

    // Call the service method
    List<String> result = eventService.getRegistrationEmailsByEventId(eventId);

    verify(eventDAO, times(1)).getRegistrationEmailsByEventId(eventId);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("user1@example.com", result.get(0));
    assertEquals("user2@example.com", result.get(1));
  }

  @Test
  void testGetUsersEmailNotEnrolled() {
    Long eventId = 1L;

    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com");

    when(eventDAO.getUsersEmailNotEnrolled(eventId)).thenReturn(emails);

    List<String> result = eventService.getUsersEmailNotEnrolled(eventId);

    verify(eventDAO, times(1)).getUsersEmailNotEnrolled(eventId);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("user1@example.com", result.get(0));
    assertEquals("user2@example.com", result.get(1));
  }


  @Test
  void testAddFeedbackForUserWhoWasNotRegistered() {
    Feedback feedback = new Feedback(new FeedbackId(), null);
    feedback.getId().setEvent_id(123L);
    feedback.getId().setUser_id(456L);
    feedback.setFeedback("feedback");

    Event event = new Event();
    event.setEvent_id(123L);
    event.setStartDateTime(LocalDateTime.now().minusDays(1));

    doReturn(event).when(eventDAO).getEventById(123L);
    doReturn(true).when(eventDAO).checkIfUserIsRegistered(123L, 456L);

    assertThrows(UnauthorizedException.class, () -> {
      eventService.addFeedback(feedback);
    });

    verify(eventDAO, times(0)).addFeedback(feedback);
  }

  @Test
  void testDeleteFeedback() {
    eventService.deleteFeedback(123L, 456L);
    verify(eventDAO, times(1)).deleteFeedback(123L, 456L);
  }
}

package nl.han.oose.clipper.clipperapi.utils;

import nl.han.oose.clipper.clipperapi.exceptions.BadRequestException;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import nl.han.oose.clipper.clipperapi.exceptions.PreconditionFailedException;
import nl.han.oose.clipper.clipperapi.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExceptionHandlerControllerTests {

  @InjectMocks
  private ExceptionHandlerController exceptionHandlerController;

  @Test
  void testHandleExceptionReturnsInternalServerError() {
    // Given
    Exception ex = new Exception("Something went wrong");

    // When
    ResponseEntity<String> result = exceptionHandlerController.handleException(ex);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    assertEquals("An unexpected error occurred: Something went wrong", result.getBody());
  }

  @Test
  void testHandleUnauthorizedExceptionReturnsUnauthorized() {
    // Given
    UnauthorizedException ex = new UnauthorizedException("Unauthorized access");

    // When
    ResponseEntity<String> result = exceptionHandlerController.handleUnauthorizedException(ex);

    // Then
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    assertEquals("Unauthorized access", result.getBody());
  }

  @Test
  void testHandleIllegalArgumentExceptionReturnsBadRequest() {
    // Given
    IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

    // When
    ResponseEntity<String> result = exceptionHandlerController.handleIllegalArgumentException(ex);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Invalid argument", result.getBody());
  }

  @Test
  void testHandleNotFoundExceptionReturnsNotFound() {
    // Given
    NotFoundException ex = new NotFoundException("Resource not found");

    // When
    ResponseEntity<String> result = exceptionHandlerController.handleNotFoundException(ex);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    assertEquals("Resource not found", result.getBody());
  }

  @Test
  void testHandleSQLException() {
    // Given
    SQLException ex = new SQLException("Test");

    // When
    ResponseEntity<String> result = exceptionHandlerController.handleSQLException(ex);

    // Then
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    Assertions.assertEquals("Database oopsie - Test", result.getBody());
  }

  @Test
  void testHandleDataAccessException() {
    // Given
    DataAccessResourceFailureException ex = new DataAccessResourceFailureException("Test");

    // When
    ResponseEntity<String> responseEntity = exceptionHandlerController.handleDataAccessException(ex);

    // Then
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    Assertions.assertEquals("Data Access Error: - Test", responseEntity.getBody());
  }

  @Test
  void testHandlePreconditionFailedException() {
    // Given
    PreconditionFailedException ex = new PreconditionFailedException("Test");

    // When
    ResponseEntity<String> responseEntity = exceptionHandlerController.handlePreconditionFailedException(ex);

    // Then
    Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
    Assertions.assertEquals("Test", responseEntity.getBody());
  }

  @Test
  void testHandleBadRequestException() {
    // Given
    BadRequestException ex = new BadRequestException("Test");

    // When
    ResponseEntity<String> responseEntity = exceptionHandlerController.handleBadRequestException(ex);

    // Then
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    Assertions.assertEquals("Test", responseEntity.getBody());
  }
}

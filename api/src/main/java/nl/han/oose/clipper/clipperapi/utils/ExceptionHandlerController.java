package nl.han.oose.clipper.clipperapi.utils;

import nl.han.oose.clipper.clipperapi.exceptions.BadRequestException;
import nl.han.oose.clipper.clipperapi.exceptions.NotFoundException;
import nl.han.oose.clipper.clipperapi.exceptions.PreconditionFailedException;
import nl.han.oose.clipper.clipperapi.exceptions.UnauthorizedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<String> handleSQLException(SQLException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database oopsie" + " - " + e.getMessage());
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data Access Error:" + " - " + e.getMessage());
  }

  @ExceptionHandler(PreconditionFailedException.class)
  public ResponseEntity<String> handlePreconditionFailedException(PreconditionFailedException e) {
      return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}

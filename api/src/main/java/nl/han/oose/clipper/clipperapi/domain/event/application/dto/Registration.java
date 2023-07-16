package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "registrations")
public class Registration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long registration_id;
  private Long event_id;
  private Long user_id;
  private String note;

  public Long getRegistration_id() {
    return registration_id;
  }

  public void setRegistration_id(Long registration_id) {
    this.registration_id = registration_id;
  }

  public Long getEvent_id() {
    return event_id;
  }

  public void setEvent_id(Long event_id) {
    this.event_id = event_id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}

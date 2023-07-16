package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long event_id;
  private Long user_id;
  private String title;
  private String description;
  private String briefDescription;
  private LocalDateTime startDateTime;
  private String location;
  private Boolean isApproved;
  private Integer isGuestEnabled;

  private Integer isGraduateChecked;

  public Event() {
    // Empty constructor, because of SonarLint
  }
  
  public Long getEvent_id() {
    return this.event_id;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBriefDescription() {
    return briefDescription;
  }

  public void setBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(LocalDateTime startDateTime) {
    this.startDateTime = startDateTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean getIsApproved() {
    return isApproved;
  }

  public void setIsApproved(Boolean isApproved) {
    this.isApproved = isApproved;
  }

  public Integer getIsGuestEnabled() {
    return isGuestEnabled;
  }

  public void setIsGuestEnabled(Integer isGuestEnabled) {
    this.isGuestEnabled = isGuestEnabled;
  }

  public Integer getIsGraduateChecked() {
    return isGraduateChecked;
  }

  public void setIsGraduateChecked(Integer isGraduateChecked) {
    this.isGraduateChecked = isGraduateChecked;
  }
}

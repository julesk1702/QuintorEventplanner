package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import jakarta.persistence.*;
import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;

import java.util.List;

@Entity
@Table(name = "guests")
public class Guest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long guest_id;
  private String name;
  private Long event_id;

  @ManyToMany
  @JoinTable(
          name = "Guest_diets",
          joinColumns = @JoinColumn(name = "guest_id"),
          inverseJoinColumns = @JoinColumn(name = "diet_id")
  )
  private List<Diet> diets;

  public Long getGuest_id() {
    return guest_id;
  }

  public void setGuest_id(Long guest_id) {
    this.guest_id = guest_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<Diet> getDiets() {
      return diets;
  }

  public void setDiets(List<Diet> diets) {
      this.diets = diets;
  }

  private Long user_id;
  private String note;
}

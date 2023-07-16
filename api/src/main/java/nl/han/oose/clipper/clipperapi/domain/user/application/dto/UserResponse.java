package nl.han.oose.clipper.clipperapi.domain.user.application.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserResponse {

  @Id
  private Long user_id;
  private String email;
  private String role;

  public UserResponse() {
  }

  public UserResponse(Long id, String email, String role) {
    this.user_id = id;
    this.email = email;
    this.role = role;
  }
  
  public Long getUserId() {
    return user_id;
  }

  public void setUserId(Long user_id) {
    this.user_id = user_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

package nl.han.oose.clipper.clipperapi.domain.user.application.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class UserRequest {

  @Id
  private Long user_id;
  private String email;
  private String password;

  public UserRequest() {
    // Empty constructor, because of SonarLint
  }

  public Long getUserId() {
    return user_id;
  }

  public void setUserId(Long id) {
    this.user_id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

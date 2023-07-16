package nl.han.oose.clipper.clipperapi.domain.user.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long user_id;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  public User() {
  }

  public User(String email, String password, String role) {
    this.email = email;
    this.password = password;
    this.role = role;
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

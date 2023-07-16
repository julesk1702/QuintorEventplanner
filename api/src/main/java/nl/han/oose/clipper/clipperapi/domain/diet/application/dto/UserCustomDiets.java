package nl.han.oose.clipper.clipperapi.domain.diet.application.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_custom_diets")
public class UserCustomDiets {

    @Id
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "customDiets")
    private String customDiets;

    public UserCustomDiets() {
    }

    public UserCustomDiets(Long user_id, String customDiets) {
        this.user_id = user_id;
        this.customDiets = customDiets;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCustomDiets() {
        return customDiets;
    }

    public void setCustomDiets(String customDiets) {
        this.customDiets = customDiets;
    }
}

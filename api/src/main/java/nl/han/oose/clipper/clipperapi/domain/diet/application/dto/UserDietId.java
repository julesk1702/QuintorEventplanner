package nl.han.oose.clipper.clipperapi.domain.diet.application.dto;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserDietId implements Serializable {

    private Long user_id;
    private Long diet_id;

    public UserDietId() {
    }

    public UserDietId(Long user_id, Long diet_id) {
        this.user_id = user_id;
        this.diet_id = diet_id;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public Long getDietId() {
        return diet_id;
    }

    public void setDietId(Long diet_id) {
        this.diet_id = diet_id;
    }
}

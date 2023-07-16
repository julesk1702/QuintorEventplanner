package nl.han.oose.clipper.clipperapi.domain.diet.application.dto;

import jakarta.persistence.*;
import nl.han.oose.clipper.clipperapi.domain.user.application.dto.User;

@Entity
@Table(name = "user_diets")
public class UserDiet {

    @EmbeddedId
    private UserDietId id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("diet_id")
    @JoinColumn(name = "diet_id")
    private Diet diet;

    public UserDiet() {
    }

    public UserDiet(User user, Diet diet) {
        this.user = user;
        this.diet = diet;
    }

    public UserDietId getId() {
        return id;
    }

    public void setId(UserDietId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }
}


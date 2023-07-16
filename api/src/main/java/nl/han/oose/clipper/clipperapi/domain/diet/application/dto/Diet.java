package nl.han.oose.clipper.clipperapi.domain.diet.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "diets")
public class Diet {

    @Id
    private Long diet_id;

    private String name;

    public Long getDietId() {
        return diet_id;
    }

    public void setDietId(Long diet_id) {
        this.diet_id = diet_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
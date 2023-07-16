package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.Diet;

import java.util.List;

public class RegistrationWithEventDetails {

    private String email;
    private List<Diet> diets;
    private String customDiets;
    private String note;

    public RegistrationWithEventDetails() {
    }

    public RegistrationWithEventDetails(String email, List<Diet> diets, String customDiets, String note) {
        this.email = email;
        this.diets = diets;
        this.customDiets = customDiets;
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public void setDiets(List<Diet> diets) {
        this.diets = diets;
    }

    public String getCustomDiets() {
        return customDiets;
    }

    public void setCustomDiets(String customDiets) {
        this.customDiets = customDiets;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

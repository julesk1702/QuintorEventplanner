package nl.han.oose.clipper.clipperapi.domain.diet.application.dto;

public class SetUserDietsRequest {

    private Long[] dietIds;
    private String customDiets;

    public SetUserDietsRequest(Long[] dietIds, String customDiets) {
        this.dietIds = dietIds;
        this.customDiets = customDiets;
    }

    public SetUserDietsRequest() {}

    public Long[] getDietIds() {
        return dietIds;
    }

    public void setDietIds(Long[] dietIds) {
        this.dietIds = dietIds;
    }

    public String getCustomDiets() {
        return customDiets;
    }

    public void setCustomDiets(String customDiets) {
        this.customDiets = customDiets;
    }
}

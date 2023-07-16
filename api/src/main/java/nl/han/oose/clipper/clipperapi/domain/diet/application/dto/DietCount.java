package nl.han.oose.clipper.clipperapi.domain.diet.application.dto;

public class DietCount {

    private String name;
    private Long totalCount;

    public DietCount(String name, Long totalCount) {
        this.name = name;
        this.totalCount = totalCount;
    }

    public DietCount() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}

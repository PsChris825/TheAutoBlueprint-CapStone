package learn.autoblueprint.models;

import java.util.Objects;

public class Tutorial {

    private Integer tutorialId;
    private Integer planId;
    private Integer partId;
    private String videoLink;
    private String description;

    public Tutorial() {}

    public Tutorial(Integer tutorialId, Integer planId, Integer partId, String videoLink, String description) {
        this.tutorialId = tutorialId;
        this.planId = planId;
        this.partId = partId;
        this.videoLink = videoLink;
        this.description = description;
    }

    public Integer getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(Integer tutorialId) {
        this.tutorialId = tutorialId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutorial tutorial = (Tutorial) o;
        return Objects.equals(tutorialId, tutorial.tutorialId) && Objects.equals(planId, tutorial.planId) && Objects.equals(partId, tutorial.partId) && Objects.equals(videoLink, tutorial.videoLink) && Objects.equals(description, tutorial.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorialId, planId, partId, videoLink, description);
    }
}


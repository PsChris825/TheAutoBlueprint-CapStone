package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.util.Objects;

public class PlanPart {

    private Integer planPartId;
    private int planId;
    private int partId;
    private BigDecimal price;
    private String tutorialUrl;
    private String supplierUrl;
    private int timeToInstall;

    public PlanPart() {
    }

    public PlanPart(Integer planPartId, int planId, int partId, BigDecimal price, String tutorialUrl, String supplierUrl, int timeToInstall) {
        this.planPartId = planPartId;
        this.planId = planId;
        this.partId = partId;
        this.price = price;
        this.tutorialUrl = tutorialUrl;
        this.supplierUrl = supplierUrl;
        this.timeToInstall = timeToInstall;
    }

    public Integer getPlanPartId() {
        return planPartId;
    }

    public void setPlanPartId(Integer planPartId) {
        this.planPartId = planPartId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTutorialUrl() {
        return tutorialUrl;
    }

    public void setTutorialUrl(String tutorialUrl) {
        this.tutorialUrl = tutorialUrl;
    }

    public String getSupplierUrl() {
        return supplierUrl;
    }

    public void setSupplierUrl(String supplierUrl) {
        this.supplierUrl = supplierUrl;
    }

    public int getTimeToInstall() {
        return timeToInstall;
    }

    public void setTimeToInstall(int timeToInstall) {
        this.timeToInstall = timeToInstall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanPart planPart = (PlanPart) o;
        return planId == planPart.planId && partId == planPart.partId && timeToInstall == planPart.timeToInstall &&
                Objects.equals(planPartId, planPart.planPartId) &&
                Objects.equals(price, planPart.price) &&
                Objects.equals(tutorialUrl, planPart.tutorialUrl) &&
                Objects.equals(supplierUrl, planPart.supplierUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planPartId, planId, partId, price, tutorialUrl, supplierUrl, timeToInstall);
    }

    @Override
    public String toString() {
        return "PlanPart{" +
                "planPartId=" + planPartId +
                ", planId=" + planId +
                ", partId=" + partId +
                ", price=" + price +
                ", tutorialUrl='" + tutorialUrl + '\'' +
                ", supplierUrl='" + supplierUrl + '\'' +
                ", timeToInstall=" + timeToInstall +
                '}';
    }
}
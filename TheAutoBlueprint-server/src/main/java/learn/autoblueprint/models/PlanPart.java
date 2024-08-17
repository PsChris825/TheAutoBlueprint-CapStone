package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.util.Objects;

public class PlanPart {

    private Integer planPartId;
    private Part part;
    private ModificationPlan plan;
    private BigDecimal price;
    private String tutorialUrl;
    private String supplierUrl;
    private int timeToInstall; // New field

    public PlanPart() {
    }

    public PlanPart(Integer planPartId, Part part, ModificationPlan plan, BigDecimal price, String tutorialUrl, String supplierUrl, int timeToInstall) {
        this.planPartId = planPartId;
        this.part = part;
        this.plan = plan;
        this.price = price;
        this.tutorialUrl = tutorialUrl;
        this.supplierUrl = supplierUrl;
        this.timeToInstall = timeToInstall; // Initialize new field
    }

    public Integer getPlanPartId() {
        return planPartId;
    }

    public void setPlanPartId(Integer planPartId) {
        this.planPartId = planPartId;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public ModificationPlan getPlan() {
        return plan;
    }

    public void setPlan(ModificationPlan plan) {
        this.plan = plan;
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
        return timeToInstall == planPart.timeToInstall &&
                Objects.equals(planPartId, planPart.planPartId) &&
                Objects.equals(part, planPart.part) &&
                Objects.equals(plan, planPart.plan) &&
                Objects.equals(price, planPart.price) &&
                Objects.equals(tutorialUrl, planPart.tutorialUrl) &&
                Objects.equals(supplierUrl, planPart.supplierUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planPartId, part, plan, price, tutorialUrl, supplierUrl, timeToInstall);
    }

    @Override
    public String toString() {
        return "PlanPart{" +
                "planPartId=" + planPartId +
                ", part=" + part +
                ", plan=" + plan +
                ", price=" + price +
                ", tutorialUrl='" + tutorialUrl + '\'' +
                ", supplierUrl='" + supplierUrl + '\'' +
                ", timeToInstall=" + timeToInstall +
                '}';
    }
}
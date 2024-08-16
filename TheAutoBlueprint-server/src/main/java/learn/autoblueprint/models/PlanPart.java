package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.util.Objects;

public class PlanPart {

    private Integer planPartId;
    private Part part;
    private BigDecimal price;
    private String tutorialUrl;
    private String supplierUrl;

    public PlanPart() {
    }

    public PlanPart(Integer planPartId, Part part, BigDecimal price, String tutorialUrl, String supplierUrl) {
        this.planPartId = planPartId;
        this.part = part;
        this.price = price;
        this.tutorialUrl = tutorialUrl;
        this.supplierUrl = supplierUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanPart planPart = (PlanPart) o;
        return Objects.equals(planPartId, planPart.planPartId) && Objects.equals(part, planPart.part) && Objects.equals(price, planPart.price) && Objects.equals(tutorialUrl, planPart.tutorialUrl) && Objects.equals(supplierUrl, planPart.supplierUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planPartId, part, price, tutorialUrl, supplierUrl);
    }
}
package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.util.Objects;

public class PlanPart {

    private Integer planId;
    private Integer partId;
    private Integer supplierId;
    private BigDecimal price;

    public PlanPart() {}

    public PlanPart(Integer planId, Integer partId, Integer supplierId, BigDecimal price) {
        this.planId = planId;
        this.partId = partId;
        this.supplierId = supplierId;
        this.price = price;
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

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanPart planPart = (PlanPart) o;
        return Objects.equals(planId, planPart.planId) && Objects.equals(partId, planPart.partId) && Objects.equals(supplierId, planPart.supplierId) && Objects.equals(price, planPart.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, partId, supplierId, price);
    }
}


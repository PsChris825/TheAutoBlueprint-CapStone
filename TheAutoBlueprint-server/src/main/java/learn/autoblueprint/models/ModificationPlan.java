package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class ModificationPlan {

    private Integer planId;
    private AppUser appUserId;
    private Car carId;
    private List<PlanPart> planParts;
    private String planName; // New field
    private String planDescription;
    private Integer planHoursOfCompletion;
    private BigDecimal budget;
    private BigDecimal totalCost;
    private BigDecimal costVersusBudget;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public AppUser getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(AppUser appUserId) {
        this.appUserId = appUserId;
    }

    public Car getCarId() {
        return carId;
    }

    public void setCarId(Car carId) {
        this.carId = carId;
    }

    public List<PlanPart> getPlanParts() {
        return planParts;
    }

    public void setPlanParts(List<PlanPart> planParts) {
        this.planParts = planParts;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Integer getPlanHoursOfCompletion() {
        return planHoursOfCompletion;
    }

    public void setPlanHoursOfCompletion(Integer planHoursOfCompletion) {
        this.planHoursOfCompletion = planHoursOfCompletion;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getCostVersusBudget() {
        return costVersusBudget;
    }

    public void setCostVersusBudget(BigDecimal costVersusBudget) {
        this.costVersusBudget = costVersusBudget;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void calculateTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (PlanPart part : planParts) {
            total = total.add(part.getPrice());
        }
        this.totalCost = total;
        this.costVersusBudget = total.subtract(budget);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationPlan that = (ModificationPlan) o;
        return Objects.equals(planId, that.planId) && Objects.equals(appUserId, that.appUserId) && Objects.equals(carId, that.carId) && Objects.equals(planParts, that.planParts) && Objects.equals(planName, that.planName) && Objects.equals(planDescription, that.planDescription) && Objects.equals(planHoursOfCompletion, that.planHoursOfCompletion) && Objects.equals(budget, that.budget) && Objects.equals(totalCost, that.totalCost) && Objects.equals(costVersusBudget, that.costVersusBudget) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, appUserId, carId, planParts, planName, planDescription, planHoursOfCompletion, budget, totalCost, costVersusBudget, createdAt, updatedAt);
    }
}
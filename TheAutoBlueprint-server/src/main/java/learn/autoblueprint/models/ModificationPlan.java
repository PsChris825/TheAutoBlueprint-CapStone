package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class ModificationPlan {

    private Integer planId;
    private Integer userId;
    private Integer carId;
    private String planDescription;
    private Integer planHoursOfCompletion;
    private BigDecimal budget;
    private BigDecimal totalCost;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ModificationPlan() {}

    public ModificationPlan(Integer planId, Integer userId, Integer carId, String planDescription, Integer planHoursOfCompletion, BigDecimal budget, BigDecimal totalCost, Timestamp createdAt, Timestamp updatedAt) {
        this.planId = planId;
        this.userId = userId;
        this.carId = carId;
        this.planDescription = planDescription;
        this.planHoursOfCompletion = planHoursOfCompletion;
        this.budget = budget;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationPlan that = (ModificationPlan) o;
        return Objects.equals(planId, that.planId) && Objects.equals(userId, that.userId) && Objects.equals(carId, that.carId) && Objects.equals(planDescription, that.planDescription) && Objects.equals(planHoursOfCompletion, that.planHoursOfCompletion) && Objects.equals(budget, that.budget) && Objects.equals(totalCost, that.totalCost) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, userId, carId, planDescription, planHoursOfCompletion, budget, totalCost, createdAt, updatedAt);
    }
}

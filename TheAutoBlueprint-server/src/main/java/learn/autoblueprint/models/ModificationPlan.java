package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModificationPlan {

    private Integer planId;
    private int appUserId;
    private int carId;
    private List<PlanPart> planParts = new ArrayList<>();
    private String planName;
    private String planDescription;
    private Integer planHoursOfCompletion;
    private BigDecimal budget = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;
    private BigDecimal costVersusBudget = BigDecimal.ZERO;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public List<PlanPart> getPlanParts() {
        return planParts;
    }

    public void setPlanParts(List<PlanPart> planParts) {
        this.planParts = planParts;
        calculatePlanHours();
        calculateTotalCost();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void calculateTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (PlanPart part : planParts) {
            if (part.getPrice() != null) {
                total = total.add(part.getPrice());
            }
        }
        this.totalCost = total;
        this.costVersusBudget = budget.subtract(total);
    }

    public void calculatePlanHours() {
        int totalHours = 0;
        for (PlanPart part : planParts) {
            totalHours += part.getTimeToInstall();
        }
        this.planHoursOfCompletion = totalHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationPlan that = (ModificationPlan) o;
        return appUserId == that.appUserId && carId == that.carId && Objects.equals(planId, that.planId) && Objects.equals(planParts, that.planParts) && Objects.equals(planName, that.planName) && Objects.equals(planDescription, that.planDescription) && Objects.equals(planHoursOfCompletion, that.planHoursOfCompletion) && Objects.equals(budget, that.budget) && Objects.equals(totalCost, that.totalCost) && Objects.equals(costVersusBudget, that.costVersusBudget) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, appUserId, carId, planParts, planName, planDescription, planHoursOfCompletion, budget, totalCost, costVersusBudget, createdAt, updatedAt);
    }
}
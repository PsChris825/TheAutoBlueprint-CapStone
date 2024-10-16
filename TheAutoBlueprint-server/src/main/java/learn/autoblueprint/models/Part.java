package learn.autoblueprint.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Part {

    private Integer partId;
    private String partName;
    private String partNumber;
    private String manufacturer;
    private String OEMNumber;
    private BigDecimal weight;
    private String details;
    private int categoryId;
    private int carId;

    public Part() {}

    public Part(Integer partId, String partName, String partNumber, String manufacturer, String OEMNumber, BigDecimal weight, String details, int categoryId, int carId) {
        this.partId = partId;
        this.partName = partName;
        this.partNumber = partNumber;
        this.manufacturer = manufacturer;
        this.OEMNumber = OEMNumber;
        this.weight = weight;
        this.details = details;
        this.categoryId = categoryId;
        this.carId = carId;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOEMNumber() {
        return OEMNumber;
    }

    public void setOEMNumber(String OEMNumber) {
        this.OEMNumber = OEMNumber;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return categoryId == part.categoryId && carId == part.carId && Objects.equals(partId, part.partId) && Objects.equals(partName, part.partName) && Objects.equals(partNumber, part.partNumber) && Objects.equals(manufacturer, part.manufacturer) && Objects.equals(OEMNumber, part.OEMNumber) && Objects.equals(weight, part.weight) && Objects.equals(details, part.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, partName, partNumber, manufacturer, OEMNumber, weight, details, categoryId, carId);
    }

    @Override
    public String toString() {
        return "Part{" +
                "partId=" + partId +
                ", partName='" + partName + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", OEMNumber='" + OEMNumber + '\'' +
                ", weight=" + weight +
                ", details='" + details + '\'' +
                ", categoryId=" + categoryId +
                ", carId=" + carId +
                '}';
    }
}
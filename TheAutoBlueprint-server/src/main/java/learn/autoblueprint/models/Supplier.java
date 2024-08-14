package learn.autoblueprint.models;

import java.util.Objects;

public class Supplier {

    private Integer supplierId;
    private String supplierName;
    private String website;

    public Supplier() {}

    public Supplier(Integer supplierId, String supplierName, String website) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.website = website;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(supplierId, supplier.supplierId) && Objects.equals(supplierName, supplier.supplierName) && Objects.equals(website, supplier.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId, supplierName, website);
    }
}


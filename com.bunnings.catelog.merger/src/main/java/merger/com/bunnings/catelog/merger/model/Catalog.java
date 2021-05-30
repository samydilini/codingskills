package merger.com.bunnings.catelog.merger.model;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private final String sku;
    private final String description;
    private List<SupplierBarCode> supplierBarCodes;

    public Catalog(String sku , String description) {
        this.sku = sku;
        this.description = description;
        this.supplierBarCodes = new ArrayList<>();
    }

    public void addSupplierBarCodes(SupplierBarCode supplierBarCode) {
        if(supplierBarCode == null) {
            this.supplierBarCodes = new ArrayList<>();
        }
        supplierBarCodes.add(supplierBarCode);
    }

    public String getSku() {
        return sku;
    }

    public String getDescription() {
        return description;
    }

    public List<SupplierBarCode> getSupplierBarCodes() {
        return supplierBarCodes;
    }

    @Override
    public String toString() {
        return "Catalog{" +
            "sku='" + sku + '\'' +
            ", description='" + description + '\'' +
            ", supplierBarCodes=" + supplierBarCodes +
            '}';
    }

}

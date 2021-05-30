package merger.com.bunnings.catelog.merger.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier {

    private final String id;
    private final String name;
    private List<SupplierBarCode> barcodes;

    public Supplier(String id, String name) {
        this.id = id;
        this.name = name;
        this.barcodes = new ArrayList<>();
    }

    public void addBarcodes(SupplierBarCode barcode) {
        if (this.barcodes == null) {
            this.barcodes = new ArrayList<>();
        }
        this.barcodes.add(barcode);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SupplierBarCode> getBarcodes() {
        return barcodes;
    }

    @Override
    public String toString() {
        return "Supplier{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", barcodes=" + barcodes +
            '}';
    }

}

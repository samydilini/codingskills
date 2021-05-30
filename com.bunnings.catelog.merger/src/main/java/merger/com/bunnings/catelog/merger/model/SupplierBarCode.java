package merger.com.bunnings.catelog.merger.model;

public class SupplierBarCode {

    private final Catalog catalog;
    private final Supplier supplier;
    private final String barcode;

    public SupplierBarCode(Supplier supplier, Catalog catalog, String barcode) {
        this.catalog = catalog;
        this.supplier = supplier;
        this.barcode = barcode;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public String getBarcode() {
        return barcode;
    }

}

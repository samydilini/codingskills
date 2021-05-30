package merger.com.bunnings.catelog.merger.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupplierBarCode that = (SupplierBarCode) o;
        return catalog.getSku().equals(that.catalog.getSku()) &&
            supplier.getId().equals(that.supplier.getId()) &&
            barcode.equals(that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalog, supplier, barcode);
    }

}

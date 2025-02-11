package merger.com.bunnings.catelog.merger.model;

public enum BarcodeHeaders {
    SKU("SKU"),
    SUPPLIER_ID("SupplierID"),
    BARCODE("Barcode");

    private String header;

    BarcodeHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}

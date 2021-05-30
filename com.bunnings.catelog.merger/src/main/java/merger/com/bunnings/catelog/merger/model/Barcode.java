package merger.com.bunnings.catelog.merger.model;

public class Barcode {

    private final String supplierId;
    private final String sku;
    private final String barCode;

    public Barcode(String supplierId, String sku, String barCode) {
        this.supplierId = supplierId;
        this.sku = sku;
        this.barCode = barCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getSku() {
        return sku;
    }

    public String getBarCode() {
        return barCode;
    }

}

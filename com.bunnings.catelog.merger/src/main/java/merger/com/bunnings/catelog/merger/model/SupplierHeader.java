package merger.com.bunnings.catelog.merger.model;

public enum SupplierHeader {
    ID("ID"),
    NAME("Name");
    private String header;

    SupplierHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}

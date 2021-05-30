package merger.com.bunnings.catelog.merger.model;

public enum CatelogHeaders {
    SKU("SKU"),DESCRIPTION ("Description");

    private String header;

    CatelogHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}

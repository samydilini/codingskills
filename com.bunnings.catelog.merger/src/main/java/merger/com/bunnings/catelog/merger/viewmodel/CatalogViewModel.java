package merger.com.bunnings.catelog.merger.viewmodel;

import java.util.Objects;

public class CatalogViewModel {

    private final String companyName;
    private final String description;
    private final String sku;

    public CatalogViewModel(String companyName, String description, String sku) {
        this.companyName = companyName;
        this.description = description;
        this.sku = sku;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CatalogViewModel that = (CatalogViewModel) o;
        return companyName.equals(that.companyName) &&
            description.equals(that.description) &&
            sku.equals(that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, description, sku);
    }

}

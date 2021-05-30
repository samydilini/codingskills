package merger.com.bunnings.catelog.merger.model;

import java.util.List;
import java.util.Optional;

public class BusinessCatalog {

    private final String companyName;
    private final List<Catalog> catalogs;

    public BusinessCatalog(String companyName, List<Catalog> catalogs) {
        this.companyName = companyName;
        this.catalogs = catalogs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public Optional<Catalog> findCatalogById(String sku) {
        return catalogs.stream().filter(catalog -> catalog.getSku().equalsIgnoreCase(sku)).findFirst();
    }

}

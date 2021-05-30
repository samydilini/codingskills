package merger.com.bunnings.catelog.merger.service;

import merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService;
import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

public class MergerService {

private PropertyReaderService propertyReaderService;

    public MergerService() {
        propertyReaderService = PropertyReaderService.getPropertyReaderServiceInstance();
    }

    public CatalogViewModel merge() {
        return new CatalogViewModel();
    }

}

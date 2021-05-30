package merger.com.bunnings.catelog.merger.service;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;
import merger.com.bunnings.catelog.merger.model.BusinessCatalog;
import merger.com.bunnings.catelog.merger.model.SupplierBarCode;
import merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService;
import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MergerService {

    private PropertyReaderService propertyReaderService;
    private BusinessCatalogGenerator businessCatalogGenerator;

    public MergerService() {
        propertyReaderService = PropertyReaderService.getPropertyReaderServiceInstance();
        businessCatalogGenerator = BusinessCatalogGenerator.getBusinessCatalogGenerator();
    }

    public MergerService(PropertyReaderService propertyReaderService,
                         BusinessCatalogGenerator businessCatalogGenerator) {
        this.propertyReaderService = propertyReaderService;
        this.businessCatalogGenerator = businessCatalogGenerator;
    }

    public List<CatalogViewModel> merge() {
        try {
            Map<String, List<String>> properties = propertyReaderService.read();
            Iterator<Entry<String, List<String>>> iterator = properties.entrySet().iterator();
            BusinessCatalog motherCompanyCatalog = getBusinessCatalog(iterator.next());
            String motherCompanyName = motherCompanyCatalog.getCompanyName();
            BusinessCatalog aqquringCompanyCatalog = getBusinessCatalog(iterator.next());
            String acquiredCompanyName = aqquringCompanyCatalog.getCompanyName();

            List<CatalogViewModel> catelogViewModels = getCatalogViewModels(motherCompanyCatalog, motherCompanyName,
                aqquringCompanyCatalog, acquiredCompanyName);
            System.out.println(catelogViewModels.size());
            return catelogViewModels;
        } catch (MergerException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<CatalogViewModel> getCatalogViewModels(BusinessCatalog motherCompanyCatalog, String motherCompanyName,
                                                        BusinessCatalog aqquringCompanyCatalog,
                                                        String acquiredCompanyName) {
        List<SupplierBarCode> motherCompanySupplierBarcodes = motherCompanyCatalog.getCatalogs()
            .stream().map(catalog -> catalog.getSupplierBarCodes()).flatMap(List::stream).collect(
                Collectors.toList());
        List<SupplierBarCode> acquiringCompanySupplierBarcodes = aqquringCompanyCatalog.getCatalogs()
            .stream().map(catalog -> catalog.getSupplierBarCodes()).flatMap(List::stream).collect(
                Collectors.toList());
        List<SupplierBarCode> uniqueMotherCompany = motherCompanySupplierBarcodes.stream()
            .filter(aObject -> !acquiringCompanySupplierBarcodes.contains(aObject))
            .collect(Collectors.toList());

        List<SupplierBarCode> commons = motherCompanySupplierBarcodes.stream()
            .filter(aObject -> acquiringCompanySupplierBarcodes.contains(aObject))
            .collect(Collectors.toList());

        List<SupplierBarCode> uniqueAcquired = acquiringCompanySupplierBarcodes.stream()
            .filter(aObject -> !motherCompanySupplierBarcodes.contains(aObject))
            .collect(Collectors.toList());

        List<CatalogViewModel> catalogViewModels = new ArrayList<>();
        catalogViewModels.addAll(uniqueMotherCompany.stream()
            .map(mother -> new CatalogViewModel(motherCompanyName,
                mother.getCatalog().getDescription(), mother.getCatalog().getSku())).collect(Collectors.toList()));

        catalogViewModels.addAll(commons.stream()
            .map(mother -> new CatalogViewModel(motherCompanyName,
                mother.getCatalog().getDescription(), mother.getCatalog().getSku())).collect(Collectors.toList()));
        catalogViewModels.addAll(uniqueAcquired.stream()
            .map(mother -> new CatalogViewModel(acquiredCompanyName,
                mother.getCatalog().getDescription(), mother.getCatalog().getSku())).collect(Collectors.toList()));
        return catalogViewModels;
    }

    private BusinessCatalog getBusinessCatalog(Entry<String, List<String>> next) throws MergerException {
        Entry<String, List<String>> motherCompanyFiles = next;
        return businessCatalogGenerator
            .generateCatelog(motherCompanyFiles.getKey(), motherCompanyFiles.getValue());
    }

}

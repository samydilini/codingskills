package merger.com.bunnings.catelog.merger.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;
import merger.com.bunnings.catelog.merger.model.BusinessCatalog;
import merger.com.bunnings.catelog.merger.model.Catalog;
import merger.com.bunnings.catelog.merger.model.Supplier;
import merger.com.bunnings.catelog.merger.model.SupplierBarCode;
import merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService;
import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MergerServiceTest {

    MergerService mergerService;
    @Mock
    private PropertyReaderService propertyReaderService;
    @Mock
    private BusinessCatalogGenerator businessCatalogGenerator;

    @Before
    public void setUp() {
        mergerService = new MergerService(propertyReaderService, businessCatalogGenerator);
    }

    @Test
    public void testMergeCatalogPropertyReadErros() throws MergerException {
        when(propertyReaderService.read()).thenThrow(new MergerException("my exception"));
        List<CatalogViewModel> mergedList = mergerService.merge();
        assertEquals(0, mergedList.size());
    }

    @Test
    public void testMergeCatalog() throws MergerException {
        Map<String, List<String>> properties = new HashMap<>();
        List<String> companyAFiles = Arrays.asList("file1", "file2");
        String companyA = "companyA";
        properties.put(companyA, companyAFiles);

        List<String> companyBFiles = Arrays.asList("file1", "file2");
        String companyB = "companyB";
        properties.put(companyB, companyBFiles);

        when(propertyReaderService.read()).thenReturn(properties);

        List<Catalog> companyACatalogs = new ArrayList<>();
        Catalog catalogA1 = new Catalog("11A1", "test 11A1");
        Supplier supplierA1 = new Supplier("SA1", " phill");
        SupplierBarCode barCodeA1 = new SupplierBarCode(supplierA1, catalogA1, "barcodeA1");
        catalogA1.addSupplierBarCodes(barCodeA1);

        Catalog catalogA11 = new Catalog("111A11", "test 111A11");
        Supplier supplierA11 = new Supplier("SA11", " phill");
        SupplierBarCode barCodeA11 = new SupplierBarCode(supplierA11, catalogA11, "barcodeA11");
        catalogA11.addSupplierBarCodes(barCodeA11);

        companyACatalogs.add(catalogA1);
        companyACatalogs.add(catalogA11);
        BusinessCatalog mothercompanyCatalog = new BusinessCatalog(companyA, companyACatalogs);

        when(businessCatalogGenerator.generateCatelog(companyA, companyAFiles)).thenReturn(mothercompanyCatalog);
        List<Catalog> companyBCatalogs = new ArrayList<>();
        Catalog catalogB1 = new Catalog("11B1", "test 11B1");
        Supplier supplierB1 = new Supplier("SB1", " phill");
        SupplierBarCode barCodeB1 = new SupplierBarCode(supplierB1, catalogB1, "bBrcodeB1");
        catalogB1.addSupplierBarCodes(barCodeB1);

        Catalog catalogB11 = new Catalog("11A1", "test 11A1");
        Supplier supplierB11 = new Supplier("SA1", " phill");
        SupplierBarCode barCodeB11 = new SupplierBarCode(supplierB11, catalogB11, "barcodeA1");
        catalogB11.addSupplierBarCodes(barCodeB11);
        companyBCatalogs.add(catalogB1);
        companyBCatalogs.add(catalogB11);
        BusinessCatalog aqquringCompanyCatalog = new BusinessCatalog(companyB, companyBCatalogs);
        when(businessCatalogGenerator.generateCatelog(companyB, companyBFiles)).thenReturn(aqquringCompanyCatalog);
        List<CatalogViewModel> catalogViewModels = mergerService.merge();
        assertEquals(CatalogViewModel.class, catalogViewModels.get(0).getClass());
        assertEquals(3, catalogViewModels.size());
    }

}

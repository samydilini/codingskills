package merger.com.bunnings.catelog.merger.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;
import merger.com.bunnings.catelog.merger.model.BusinessCatalog;
import merger.com.bunnings.catelog.merger.model.Catalog;
import merger.com.bunnings.catelog.merger.model.Record;
import merger.com.bunnings.catelog.merger.service.files.FileReaderService;
import merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BusinessCatalogGeneratorTest {

    @Mock
    private FileReaderService fileReaderService;
    @InjectMocks
    private BusinessCatalogGenerator bussinssCatalogGenerator;

    @Before
    public void setUp() {
        bussinssCatalogGenerator = new BusinessCatalogGenerator(fileReaderService);
    }

    @Test(expected = MergerException.class)
    public void testErrorFileNotFound() throws MergerException {
        when(fileReaderService.readFile(any())).thenReturn(Optional.empty());
        List<String> fileNames = Arrays.asList("catelog1", "barcode1");
        bussinssCatalogGenerator.generateCatelog("company A", fileNames);
    }

    @Test(expected = MergerException.class)
    public void testCatalogWithWrongFileNames() throws MergerException {
        Record record = Mockito.mock(Record.class);
        when(fileReaderService.readFile(any())).thenReturn(Optional.of(record));
        List<String> fileNames = Arrays.asList("catelog1", "barcode1");
        bussinssCatalogGenerator.generateCatelog("companyA", fileNames);
    }

    @Test(expected = MergerException.class)
    public void testCatalogWithCorrectFileNamesWrongHeaders() throws MergerException {
        Record record = Mockito.mock(Record.class);
        String[] headers = {"header1", "header2"};
        when(record.getHeaders()).thenReturn(headers);
        when(fileReaderService.readFile(any())).thenReturn(Optional.of(record));
        String companyName = "companyA";
        List<String> fileNames = Arrays.asList(PropertyReaderService.CATELOG + companyName,
            PropertyReaderService.BARCODES + companyName, PropertyReaderService.SUPPLIERS + companyName);
        bussinssCatalogGenerator.generateCatelog(companyName, fileNames);
    }

    @Test
    public void testCatalogWithCorrectValues() throws MergerException {
        String companyName = "companyA";

        Record catalogRecord = Mockito.mock(Record.class);
        String[] catalogHeaders = {"SKU", "Description"};
        when(catalogRecord.getHeaders()).thenReturn(catalogHeaders);
        List<String[]> catalogRows = new ArrayList<>();
        String[] row1 = {"1111", "first product"};
        catalogRows.add(row1);
        String[] row2 = {"2222", "Second product"};
        catalogRows.add(row2);
        when(catalogRecord.getRows()).thenReturn(catalogRows);

        when(fileReaderService.readFile(PropertyReaderService.CATELOG + companyName))
            .thenReturn(Optional.of(catalogRecord));

        Record barcodeRecord = Mockito.mock(Record.class);
        String[] barcodeHeaders = {"SKU", "SupplierID", "BarCode"};
        when(barcodeRecord.getHeaders()).thenReturn(barcodeHeaders);
        List<String[]> barcodeRows = new ArrayList<>();
        String[] barRow1 = {"1111", "1", "abcd"};
        barcodeRows.add(barRow1);
        String[] barRow2 = {"2222", "2", "pqrs"};
        barcodeRows.add(barRow2);
        String[] barRow3 = {"1111", "1", "abcde"};
        barcodeRows.add(barRow3);
        when(barcodeRecord.getRows()).thenReturn(barcodeRows);

        when(fileReaderService.readFile(PropertyReaderService.BARCODES + companyName))
            .thenReturn(Optional.of(barcodeRecord));

        Record supplierRecord = Mockito.mock(Record.class);
        String[] supplierHeaders = {"ID", "Name"};
        when(supplierRecord.getHeaders()).thenReturn(supplierHeaders);
        List<String[]> supplierRows = new ArrayList<>();
        String[] supplierRow1 = {"1", "Phill"};
        supplierRows.add(supplierRow1);
        String[] supplierRow2 = {"2", "Lill"};
        supplierRows.add(supplierRow2);
        when(supplierRecord.getRows()).thenReturn(supplierRows);
        when(fileReaderService.readFile(PropertyReaderService.SUPPLIERS + companyName))
            .thenReturn(Optional.of(supplierRecord));

        List<String> fileNames = Arrays.asList(PropertyReaderService.CATELOG + companyName,
            PropertyReaderService.BARCODES + companyName, PropertyReaderService.SUPPLIERS + companyName);
        BusinessCatalog businessCatalog = bussinssCatalogGenerator.generateCatelog(companyName, fileNames);

        assertEquals(BusinessCatalog.class, businessCatalog.getClass());
        assertEquals(companyName, businessCatalog.getCompanyName());

        Catalog catalog = businessCatalog.findCatalogById("1111").get();
        assertEquals(2,catalog.getSupplierBarCodes().size());
        assertEquals("Phill",catalog.getSupplierBarCodes().get(0).getSupplier().getName());
        assertEquals("Phill",catalog.getSupplierBarCodes().get(1).getSupplier().getName());
    }

}

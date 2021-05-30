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
import merger.com.bunnings.catelog.merger.model.Catalog;
import merger.com.bunnings.catelog.merger.model.Record;
import merger.com.bunnings.catelog.merger.service.files.FileReaderService;
import merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BussinssCatalogGeneratorTest {

    @Mock
    private FileReaderService fileReaderService;
    @InjectMocks
    private BussinssCatalogGenerator bussinssCatalogGenerator;

    @Before
    public void setUp() {
        bussinssCatalogGenerator = new BussinssCatalogGenerator(fileReaderService);
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
        assertEquals(Catalog.class, bussinssCatalogGenerator.generateCatelog("companyA", fileNames).get(0).getClass());
    }

    @Test
    public void testCatalogWithCorrectFileNames() throws MergerException {
        Record record = Mockito.mock(Record.class);
        when(fileReaderService.readFile(any())).thenReturn(Optional.of(record));
        String companyName = "companyA";
        List<String> fileNames = Arrays.asList(PropertyReaderService.CATELOG + companyName,
            PropertyReaderService.BARCODES + companyName, PropertyReaderService.SUPPLIERS + companyName);
        assertEquals(Catalog.class, bussinssCatalogGenerator.generateCatelog(companyName, fileNames).get(0).getClass());
    }

}

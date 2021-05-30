package merger.com.bunnings.catelog.merger.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;
import merger.com.bunnings.catelog.merger.service.files.FileReaderService;

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
        bussinssCatalogGenerator.generateCatelog("company A", fileNames).get(0).getClass();
    }

}

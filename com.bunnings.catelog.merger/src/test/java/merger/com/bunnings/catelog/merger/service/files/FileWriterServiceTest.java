package merger.com.bunnings.catelog.merger.service.files;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileWriterServiceTest {

    FileWriterService fileWriterService = new FileWriterService();

    @Test
    public void testWrite() throws IOException {
        List<CatalogViewModel> viewModels = new ArrayList<>();
        CatalogViewModel catelogViewModel = new CatalogViewModel("comany A", "test description", "12345");
        viewModels.add(catelogViewModel);
        fileWriterService.write(viewModels);
        BufferedReader csvReader = new BufferedReader(
            new FileReader(this.getClass().getResource("/").getPath() + "result_output.csv"));
        Stream<String> lines = csvReader.lines();
        int lineCount = (int) lines.count();
        csvReader.close();
        assertEquals(2, lineCount);
    }

}

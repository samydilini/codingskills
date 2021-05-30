package merger.com.bunnings.catelog.merger.service.files;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import merger.com.bunnings.catelog.merger.model.Record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class FileReaderServiceTest {

    FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = FileReaderService.getFileReaderServiceInstance();
    }

    @Test
    public void testReadFileFromWrongPath() {
        assertEquals(Optional.empty(), fileReaderService.readFile("wrongPath"));
    }

    @Test
    public void testReadFileFromcorrectPath() throws IOException {
        final String testPath = "src/test/resources";
        final String path = "/suppliersA.csv";
        final Record record = fileReaderService.readFile(path).get();
        BufferedReader csvReader = new BufferedReader(new FileReader(testPath + path));
        Stream<String> lines = csvReader.lines();
        int lineCount = (int) lines.count();
        csvReader.close();
        assertEquals(Record.class, record.getClass());
        assertEquals(lineCount - 1, record.getRows().size());
        assertEquals(record.getHeaders().length, record.getRows().get(0).length);

    }

}

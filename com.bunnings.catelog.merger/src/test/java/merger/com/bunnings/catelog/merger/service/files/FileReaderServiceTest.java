package merger.com.bunnings.catelog.merger.service.files;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

import merger.com.bunnings.catelog.merger.model.Record;

public class FileReaderServiceTest {

    @Test
    public void testReadFileFromWrongPath() {
        FileReaderService fileReaderService = new FileReaderService();
        assertEquals(Optional.empty(), fileReaderService.readFile("wrongPath"));
    }

    @Test
    public void testReadFileFromcorrectPath() throws IOException {
        FileReaderService fileReaderService = new FileReaderService();
        final String path = "src/test/resources/suppliersA.csv";

        final Record record = fileReaderService.readFile(path).get();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        Stream<String> lines = csvReader.lines();
        int lineCount = (int)lines.count();
        csvReader.close();
        assertEquals(Record.class, record.getClass());
        assertEquals(lineCount -1, record.getRows().size());
        assertEquals(record.getHeaders().length, record.getRows().get(0).length);
        
    }
}

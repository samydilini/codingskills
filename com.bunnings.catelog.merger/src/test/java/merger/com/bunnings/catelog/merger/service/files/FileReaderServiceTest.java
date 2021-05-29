package merger.com.bunnings.catelog.merger.service.files;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import merger.com.bunnings.catelog.merger.model.Record;

public class FileReaderServiceTest {

    @Test
    public void testReadFileFromWrongPath() {
        FileReaderService fileReaderService = new FileReaderService();
        assertEquals(Optional.empty(), fileReaderService.readFile("wrongPath"));
    }

    @Test
    public void testReadFileFromcorrectPath() {
        FileReaderService fileReaderService = new FileReaderService();
        final Record record = fileReaderService.readFile("src/test/resources/suppliersA.csv").get();
        assertEquals(Record.class, record.getClass());
    }
}

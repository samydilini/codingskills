package merger.com.bunnings.catelog.merger.service.files;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import merger.com.bunnings.catelog.merger.model.Record;

public class FileReaderService {

    private final static Logger LOGGER = Logger.getLogger(FileReaderService.class.getName());

    public Optional<Record> readFile(String path) {
        try {
            FileReader reader = new FileReader(path);
            return Optional.of(new Record());
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "File with the path " + path + " was not found", ex);
            return Optional.empty();
        }

    }
}

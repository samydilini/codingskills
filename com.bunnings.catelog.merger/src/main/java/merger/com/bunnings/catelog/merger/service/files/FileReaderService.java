package merger.com.bunnings.catelog.merger.service.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import merger.com.bunnings.catelog.merger.model.Record;

public class FileReaderService {

    private final static Logger LOGGER = Logger.getLogger(FileReaderService.class.getName());

    public Optional<Record> readFile(String path) {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            final Record record = readLines(csvReader.lines());
            csvReader.close();
            return Optional.of(record);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "File with the path \"" + path + "\" was not found", ex);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error while closing reader for file: \"" + path + "\" ", e);
        }
        return Optional.empty();
    }

    private Record readLines(Stream<String> lines) {
        List<String[]> lineValues = lines.map(line -> line.split(",")).collect(Collectors.toList());
        return new Record(lineValues.remove(0), lineValues);
    }
}

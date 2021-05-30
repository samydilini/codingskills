package merger.com.bunnings.catelog.merger.service;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;
import merger.com.bunnings.catelog.merger.model.Catalog;
import merger.com.bunnings.catelog.merger.model.Record;
import merger.com.bunnings.catelog.merger.service.files.FileReaderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BussinssCatalogGenerator {

    private FileReaderService fileReaderService;


    public BussinssCatalogGenerator() {
        fileReaderService = FileReaderService.getFileReaderServiceInstance();
    }

    public BussinssCatalogGenerator(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    public List<Catalog> generateCatelog(String companyName, List<String> fileNames) throws MergerException {
        List<Catalog> unSortedCatelogs = null;
        for (String fileName : fileNames) {
            Optional<Record> optionalRecords = fileReaderService.readFile(fileName);
            if (optionalRecords.isPresent()) {
                Record records = optionalRecords.get();
            } else {
                throw new MergerException("no records found for file" + fileName);
            }
        }

        return Arrays.asList(new Catalog());
    }

}

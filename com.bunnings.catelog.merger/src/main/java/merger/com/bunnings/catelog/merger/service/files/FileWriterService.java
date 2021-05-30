package merger.com.bunnings.catelog.merger.service.files;

import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWriterService {

    Logger LOGGER = Logger.getLogger(FileWriterService.class.getName());
    public void write(List<CatalogViewModel> viewModels) {

        try( FileWriter csvWriter = new FileWriter(this.getClass().getResource("/").getPath()+"result_output.csv")) {
            csvWriter.append("SKU");
            csvWriter.append(",");
            csvWriter.append("Description");
            csvWriter.append(",");
            csvWriter.append("Source");
            csvWriter.append("\n");

            for (CatalogViewModel rowData : viewModels) {
                csvWriter.append(rowData.getSku());
                csvWriter.append(",");
                csvWriter.append(rowData.getDescription());
                csvWriter.append(",");
                csvWriter.append(rowData.getCompanyName());
                csvWriter.append("\n");
            }
            csvWriter.flush();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Out put file did not get processed");
        }
    }

}

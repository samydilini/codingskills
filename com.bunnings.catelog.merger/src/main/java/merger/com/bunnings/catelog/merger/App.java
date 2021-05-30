package merger.com.bunnings.catelog.merger;


import merger.com.bunnings.catelog.merger.service.MergerService;
import merger.com.bunnings.catelog.merger.service.files.FileWriterService;
import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

import java.util.List;

public class App 
{
    static MergerService mergerService = new MergerService();
    static FileWriterService writerService = new FileWriterService();
    public static void main( String[] args )
    {
        List<CatalogViewModel> viewModels = mergerService.merge();
        writerService.write(viewModels);
    }
}

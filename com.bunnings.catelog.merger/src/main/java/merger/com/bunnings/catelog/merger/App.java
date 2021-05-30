package merger.com.bunnings.catelog.merger;


import merger.com.bunnings.catelog.merger.service.MergerService;
import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

import java.util.List;

public class App 
{
    static MergerService mergerService = new MergerService();
    public static void main( String[] args )
    {
        List<CatalogViewModel> viewModels = mergerService.merge();
        System.out.println(viewModels);
    }
}

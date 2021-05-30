package merger.com.bunnings.catelog.merger.service;

import static org.junit.Assert.*;

import org.junit.Test;

import merger.com.bunnings.catelog.merger.viewmodel.CatalogViewModel;

public class MergerServiceTest {

    MergerService mergerService = new MergerService();
    @Test
    public void testMergeCatalog() {
        assertEquals(CatalogViewModel.class,mergerService.merge().getClass());
    }

}

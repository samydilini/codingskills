package merger.com.bunnings.catelog.merger.service.properties;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;

@PrepareForTest({ PropertyReaderService.class, Logger.class })
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("jdk.internal.reflect.*")
@SuppressStaticInitializationFor("merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService")
public class PropertyReaderServiceTest {
    PropertyReaderService propertyReaderService = new PropertyReaderService();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Logger.class);

    }

    @Test(expected = MergerException.class)
    public void testReadNonExcistingProperty() throws MergerException {
        List<String> wrongList = new ArrayList<>();
        wrongList.add("wrong.filed1");
        wrongList.add("wrong.filed2");
        Whitebox.setInternalState(PropertyReaderService.class, "MOTHER_COMPANY", wrongList);
        assertEquals(0, propertyReaderService.read().size());
    }

    @Test
    public void testReadExcistingProperty() throws MergerException {
        final List<String> motherCompanyList = new ArrayList<>();
        motherCompanyList.add("company.mother");
        motherCompanyList.add("company.mother.suppliers");
        motherCompanyList.add("company.mother.barcodes");
        motherCompanyList.add("company.mother.catalog");
        Whitebox.setInternalState(PropertyReaderService.class, "MOTHER_COMPANY", motherCompanyList);
        
        final List<String> aqquiredCompanyList = new ArrayList<>();
        aqquiredCompanyList.add("company.aqquired");
        aqquiredCompanyList.add("company.aqquired.suppliers");
        aqquiredCompanyList.add("company.aqquired.barcodes");
        aqquiredCompanyList.add("company.aqquired.catalog");
        Whitebox.setInternalState(PropertyReaderService.class, "AQQUIRED_COMPANY", aqquiredCompanyList);

        final Map<String, List<String>> readValues = propertyReaderService.read();
        assertEquals(2, readValues.size());
         String firstCompany = readValues.entrySet().iterator().next().getKey();
        assertEquals(3, readValues.get(firstCompany).size());
    }

}

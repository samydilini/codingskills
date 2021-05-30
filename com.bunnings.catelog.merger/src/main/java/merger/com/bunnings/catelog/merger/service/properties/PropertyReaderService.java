package merger.com.bunnings.catelog.merger.service.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;

public class PropertyReaderService {

    private static final String PREFIX = ".";
    private static final String COMPANY_AQQUIRED = "company.aqquired";
    public static final String CATELOG = "catalog";
    public static final String BARCODES = "barcodes";
    public static final String SUPPLIERS = "suppliers";
    private static final Logger LOGGER = Logger.getLogger(PropertyReaderService.class.getName());
    private static final String COMPANY_MOTHER_STR = "company.mother";
    private static final List<String> MOTHER_COMPANY = new LinkedList<>(Arrays.asList(COMPANY_MOTHER_STR,
        COMPANY_MOTHER_STR + PREFIX + CATELOG, COMPANY_MOTHER_STR + PREFIX + BARCODES,
        COMPANY_MOTHER_STR + PREFIX + SUPPLIERS));
    private static final List<String> AQQUIRED_COMPANY = new LinkedList<>(Arrays.asList(COMPANY_AQQUIRED,
        COMPANY_AQQUIRED + PREFIX + CATELOG, COMPANY_AQQUIRED + PREFIX + BARCODES,
        COMPANY_AQQUIRED + PREFIX + SUPPLIERS));

    private static PropertyReaderService propertyReaderService = null;

    private PropertyReaderService() {
    }

    public static PropertyReaderService getPropertyReaderServiceInstance() {
        if (propertyReaderService == null) {
            propertyReaderService = new PropertyReaderService();
        }
        return propertyReaderService;
    }

    public Map<String, List<String>> read() throws MergerException {
        Map<String, List<String>> files = new HashMap<>();
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("/company.properties"));
            readCompany(files, prop, MOTHER_COMPANY);
            readCompany(files, prop, AQQUIRED_COMPANY);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "properties were not loaded", e);
        }
        return files;
    }

    private void readCompany(Map<String, List<String>> files, Properties properties, List<String> companyKeys)
        throws MergerException {
        String name = companyKeys.remove(0);
        String nameProperty = findProperty(properties, name);
        List<String> companyFiles = new ArrayList<>();

        for (String fileProperty : companyKeys) {
            companyFiles.add(findProperty(properties, fileProperty));
        }
        files.put(nameProperty, companyFiles);
    }

    private String findProperty(Properties prop, String propertyString) throws MergerException {
        String property = prop.getProperty(propertyString);
        if (!StringUtils.isBlank(property)) {
            return property;
        } else {
            String message = "property: \"" + propertyString + "\" not found";
            LOGGER.log(Level.SEVERE, message);
            throw new MergerException(message);
        }
    }

}

package merger.com.bunnings.catelog.merger.service;

import static merger.com.bunnings.catelog.merger.model.CatelogHeaders.values;

import merger.com.bunnings.catelog.merger.exceptins.MergerException;
import merger.com.bunnings.catelog.merger.model.SupplierBarCode;
import merger.com.bunnings.catelog.merger.model.Barcode;
import merger.com.bunnings.catelog.merger.model.BarcodeHeaders;
import merger.com.bunnings.catelog.merger.model.BusinessCatalog;
import merger.com.bunnings.catelog.merger.model.Catalog;
import merger.com.bunnings.catelog.merger.model.CatelogHeaders;
import merger.com.bunnings.catelog.merger.model.Record;
import merger.com.bunnings.catelog.merger.model.Supplier;
import merger.com.bunnings.catelog.merger.model.SupplierHeader;
import merger.com.bunnings.catelog.merger.service.files.FileReaderService;
import merger.com.bunnings.catelog.merger.service.properties.PropertyReaderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BusinessCatalogGenerator {

    private static final Logger LOGGER = Logger.getLogger(BusinessCatalogGenerator.class.getName());
    public static final String SRC_MAIN_RESOURCES = "/";
    public static final String CSV = ".csv";
    private FileReaderService fileReaderService;
    private static BusinessCatalogGenerator businessCatalogGenerator = null;

    private BusinessCatalogGenerator() {
        fileReaderService = FileReaderService.getFileReaderServiceInstance();
    }

    public static BusinessCatalogGenerator getBusinessCatalogGenerator() {
        if (businessCatalogGenerator == null) {
            businessCatalogGenerator = new BusinessCatalogGenerator();
        }
        return businessCatalogGenerator;
    }

    public BusinessCatalogGenerator(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    public BusinessCatalog generateCatelog(String companyName, List<String> fileNames) throws MergerException {
        List<Catalog> unSortedCatelogs = null;
        List<Barcode> unSortedBarcodes = null;
        List<Supplier> unsortedSuppliers = null;

        for (String fileName : fileNames) {
            Record records = readFileRecords(SRC_MAIN_RESOURCES + fileName);
            String[] headers = records.getHeaders();
            List<String[]> rows = records.getRows();
            if (fileName.equalsIgnoreCase(PropertyReaderService.CATELOG + companyName + CSV)) {
                unSortedCatelogs = generateUnsortedCatalogs(headers, rows);
            } else if (fileName.equalsIgnoreCase(PropertyReaderService.BARCODES + companyName + CSV)) {
                unSortedBarcodes = generateUnsortedBarcodes(headers, rows);

            } else if (fileName.equalsIgnoreCase(PropertyReaderService.SUPPLIERS + companyName + CSV)) {
                unsortedSuppliers = generateUnsortedSuppliers(headers, rows);
            } else {
                throw new MergerException("File \"" + fileName + "\" doeas not correspond to standerd naming. Please "
                    + "check the read me");
            }
        }

        return createBussinessCatalogFromData(companyName, unSortedCatelogs, unSortedBarcodes,
            unsortedSuppliers);
    }

    private BusinessCatalog createBussinessCatalogFromData(String companyName,
                                                           List<Catalog> unSortedCatelogs,
                                                           List<Barcode> unSortedBarcodes,
                                                           List<Supplier> unsortedSuppliers) {
        unSortedBarcodes.forEach(barcode -> {
            String catalogSku = barcode.getSku();
            String supplierId = barcode.getSupplierId();

            Optional<Catalog> catalogOption = unSortedCatelogs.stream()
                .filter(catalog -> {
                    return catalog.getSku() == catalogSku;
                }).findFirst();
            Optional<Supplier> supplierOption = unsortedSuppliers.stream()
                .filter(supplier -> {
                    return supplier.getId() == supplierId;
                }).findFirst();
            if (catalogOption.isPresent() && supplierOption.isPresent()) {
                Catalog catalog = catalogOption.get();
                Supplier supplier = supplierOption.get();
                SupplierBarCode supplierBarCode = new SupplierBarCode(supplier, catalog, barcode.getBarCode());
                catalog.addSupplierBarCodes(supplierBarCode);
                supplier.addBarcodes(supplierBarCode);
            } else {
                LOGGER
                    .log(Level.WARNING, "Sku \"" + catalogSku + "\" or supplier id \"" + supplierId + "\" from barcode "
                        + "file was not found in one of the other files");
            }
        });
        return new BusinessCatalog(companyName, unSortedCatelogs);
    }


    private List<Supplier> generateUnsortedSuppliers(String[] headers, List<String[]> rows) throws MergerException {
        int idIndex = 0;
        int nameIndex = 0;
        for (int i = 0; i < headers.length; i++) {
            SupplierHeader currentHeader = validateSupplierHeader(headers[i]);
            switch (currentHeader) {
                case ID:
                    idIndex = i;
                    break;
                case NAME:
                    nameIndex = i;
                    break;
            }
        }
        final int finalIdIndex = idIndex;
        final int finalNameIndex = nameIndex;
        return rows.stream().map(row -> new Supplier(row[finalIdIndex], row[finalNameIndex])).collect(
            Collectors.toList());
    }

    private SupplierHeader validateSupplierHeader(String header) throws MergerException {
        for (SupplierHeader supplierHeader : SupplierHeader.values()) {
            if (supplierHeader.getHeader().equals(header)) {
                return supplierHeader;
            }
        }
        String message = "Given header \"" + header + "\"is not found";
        LOGGER.log(Level.SEVERE, message);
        throw new MergerException(message);
    }

    private List<Barcode> generateUnsortedBarcodes(String[] headers, List<String[]> rows)
        throws MergerException {
        List<Barcode> unSortedBarcods = new ArrayList<>();
        int skuArrayIndex = 0;
        int supplierIdIndex = 0;
        int barcodeIndex = 0;

        for (int i = 0; i < headers.length; i++) {
            BarcodeHeaders currentHeader = validateBarcodeHeader(headers[i]);
            switch (currentHeader) {
                case SKU:
                    skuArrayIndex = i;
                    break;
                case SUPPLIER_ID:
                    supplierIdIndex = i;
                    break;
                case BARCODE:
                    barcodeIndex = i;
                    break;
            }
        }
        final int finalSkuArrayIndex = skuArrayIndex;
        final int finalSupplierIdIndex = supplierIdIndex;
        final int finalBarcodeIndex = barcodeIndex;

        Set<Barcode> set = rows.stream()
            .map(row -> new Barcode(row[finalSupplierIdIndex], row[finalSkuArrayIndex],
                row[finalBarcodeIndex])).collect(
                Collectors.toSet());
        unSortedBarcods.addAll(set);
        return unSortedBarcods;
    }

    private List<Catalog> generateUnsortedCatalogs(String[] headers, List<String[]> rows) throws MergerException {

        int skuArrayIndex = 0;
        int descriptionIndex = 0;
        for (int i = 0; i < headers.length; i++) {
            CatelogHeaders currentHeader = validateCatelogheader(headers[i]);
            switch (currentHeader) {
                case SKU:
                    skuArrayIndex = i;
                    break;
                case DESCRIPTION:
                    descriptionIndex = i;
                    break;
            }
        }
        final int finalSkuArrayIndex = skuArrayIndex;
        final int finalDescriptionIndex = descriptionIndex;
        return rows.stream().map(row -> new Catalog(row[finalSkuArrayIndex], row[finalDescriptionIndex])).collect(
            Collectors.toList());
    }

    private BarcodeHeaders validateBarcodeHeader(String header) throws MergerException {
        for (BarcodeHeaders barcodeHeader : BarcodeHeaders.values()) {
            if (barcodeHeader.getHeader().equals(header)) {
                return barcodeHeader;
            }
        }
        String message = "Given header \"" + header + "\"is not found";
        LOGGER.log(Level.SEVERE, message);
        throw new MergerException(message);
    }

    private CatelogHeaders validateCatelogheader(String header) throws MergerException {
        for (CatelogHeaders catelogHeader : values()) {
            if (catelogHeader.getHeader().equals(header)) {
                return catelogHeader;
            }
        }
        String message = "Given header \"" + header + "\"is not found";
        LOGGER.log(Level.SEVERE, message);
        throw new MergerException(message);
    }

    private Record readFileRecords(String fileName) throws MergerException {

        Optional<Record> optionalRecords = fileReaderService.readFile(fileName);
        if (optionalRecords.isPresent()) {
            return optionalRecords.get();
        } else {
            throw new MergerException("no records found for file" + fileName);
        }
    }

}

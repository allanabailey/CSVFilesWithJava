package CSVPractice;
import edu.duke.*;
import org.apache.commons.csv.*;

public class ParsingExportDataAssignment1 {
    public String countryInfo(CSVParser parser, String country) {
        String result = "";
        for(CSVRecord record : parser) {
            String countryCheck = record.get("Country");
            String export = record.get("Exports");
            String value = record.get("Value (dollars)");
            if(countryCheck.contains(country)) {
                result = country + ": " + export + ": " + value;
                System.out.println(result);
            } else {
                result = "NOT FOUND";
            }
        }
        return result;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for(CSVRecord record : parser) {
            String export = record.get("Exports");
            if(export.contains(exportItem1) && export.contains(exportItem2)) {
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for(CSVRecord record : parser) {
            String export = record.get("Exports");
            if(export.contains(exportItem)) {
                count ++;
            }
        }
        System.out.println(count);
        return count;
    }
    
    public String bigExporters(CSVParser parser, String amount) {
        String bigExporters = "";
        for(CSVRecord record : parser) {
            String country = record.get("Country");
            String value = record.get("Value (dollars)");
            int valueNum = value.length();
            int amountNum = amount.length();
            if(valueNum > amountNum) {
                bigExporters = country + " " + value;
                System.out.println(bigExporters);
            }
        }
        return bigExporters;
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //countryInfo(parser, "Nauru");
        //listExportersTwoProducts(parser, "fish", "nuts");
        //numberOfExporters(parser, "sugar");
        bigExporters(parser, "$999,999,999,999");
    }
}

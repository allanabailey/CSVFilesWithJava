package CSVPractice;
import edu.duke.*;
import org.apache.commons.csv.*;

/**
 * Write a description of WhichCountriesExport here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WhichCountriesExport {
    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV file
        for(CSVRecord record: parser) {
            //look at the exports column
            String export = record.get("Exports");
            //check is it contains exportOfInterest
            if(export.contains(exportOfInterest)) {
                //if so write the country down.
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    public void whoExportsCoffee() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser, "coffee");
    }
}

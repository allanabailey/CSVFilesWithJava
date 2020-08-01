package CSVPractice;
import edu.duke.*;
import org.apache.commons.csv.*;


/**
 * Write a description of FirstCSVExample here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FirstCSVExample {
    public void readFood() {
        //empty so you can choose file from pop up dialog.
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser) {
            System.out.println(record.get("Name") + " ");
            System.out.println(record.get("Favorite Food") + " ");
            System.out.println(record.get("Favorite Color"));
        }
    }
}

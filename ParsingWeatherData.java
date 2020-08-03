package MaxTemperatureClass;
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of ParsingWeatherData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParsingWeatherData {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestSoFar = null;
        for(CSVRecord currentRow : parser) {
            if(coldestSoFar == null) {
                coldestSoFar = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if(currentTemp < coldestTemp && currentTemp != -9999) {
                    coldestSoFar = currentRow;
                }
            }
        }
        return coldestSoFar;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF") + " at " + coldest.get("DateUTC"));
    }
    
    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord coldestSoFar = null;
        String fileName = "";
        File coldestFile = null;
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            if(coldestSoFar == null) {
                coldestSoFar = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if(currentTemp < coldestTemp && currentTemp != -9999) {
                    currentRow = coldestSoFar;
                    fileName = f.getName();
                    coldestFile = f;
                }
            }
        }
        FileResource coldestFR = new FileResource(coldestFile);
        CSVParser coldestCSV = coldestFR.getCSVParser();
        System.out.println("Coldest day was in file " + fileName);
        System.out.println("Coldest temperature on that day was " + coldestHourInFile(coldestCSV).get("TemperatureF"));
        System.out.println("The temperatures on the coldest day were: ");
        for(CSVRecord record : coldestFR.getCSVParser()) {
            System.out.println(record.get("DateUTC") + " " + record.get("TimeEST") + " " + record.get("TemperatureF"));
        }
        return fileName;
    }
    
    public void testFileWithColdestTemperature() {
        String coldest = fileWithColdestTemperature();
        System.out.println("Coldest temperature was in the file " + coldest);
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for(CSVRecord currentRow : parser) {
            if(lowestSoFar == null) {
                lowestSoFar = currentRow;
            } else {
                String currHumid = currentRow.get("Humidity");
                if(currHumid.contains("N/A")) {
                    lowestSoFar = lowestSoFar;
                } else {
                    double currentHumid = Double.parseDouble(currentRow.get("Humidity"));
                    double lowestHumid = Double.parseDouble(lowestSoFar.get("Humidity"));
                    if(currentHumid < lowestHumid) {
                        lowestSoFar = currentRow;
                    }
                }
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestSoFar = null;
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            if(lowestSoFar == null) {
                lowestSoFar = currentRow;
            } else {
                String currHumid = currentRow.get("Humidity");
                if(currHumid.contains("N/A")) {
                    lowestSoFar = lowestSoFar;
                } else {
                    double currentHumid = Double.parseDouble(currentRow.get("Humidity"));
                    double lowestHumid = Double.parseDouble(lowestSoFar.get("Humidity"));
                    if(currentHumid < lowestHumid) {
                        lowestSoFar = currentRow;
                    }
                }
            }
        }
        System.out.println("Lowest humidity was " + lowestSoFar.get("Humidity") + " at " + lowestSoFar.get("DateUTC"));
        return lowestSoFar;
    }
    
    public void testLowestHumidInManyFiles() {
        CSVRecord csv = lowestHumidityInManyFiles();
        System.out.println("Test: " + "Lowest humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        double average = 0.0;
        double total = 0.0;
        int count = 0;
        for(CSVRecord current : parser) {
            double currentTemp = Double.parseDouble(current.get("TemperatureF"));
            total += currentTemp;
            count++;
        }
        average = total/count;
        System.out.println("Average temperature in file is " + average);
        return average;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double average = averageTemperatureInFile(parser);
        System.out.println("Test: Average temperature in file is " + average);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double average = 0.0;
        double total = 0.0;
        int count = 0;
        for(CSVRecord current : parser) {
            double currentHumid = Double.parseDouble(current.get("Humidity"));
            if(currentHumid >= value) {
                double currentTemp = Double.parseDouble(current.get("TemperatureF"));
                total += currentTemp;
                count++;
            }
        }
        average = total/count;
        if(count == 0) {
        System.out.println("No temperature with that humidity");
        } else {
            System.out.println("Average temperature when high humidity is " + average);
        }
        return average;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double average = averageTemperatureWithHighHumidityInFile(parser, 80);
        boolean checkAverage = Double.isNaN(average);
        if(checkAverage == false) {
            System.out.println("Test: Average temperature with high humidity in file is " + average);
        } else {
            System.out.println("Test: No temperatures with that humidity");
        }
    }
}

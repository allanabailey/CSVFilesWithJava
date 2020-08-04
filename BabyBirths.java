package babyNamesTotals;
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name: " + rec.get(0) +
                    " Gender: " + rec.get(1) +
                    " Num Born: " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int boyCount = 0;
        int girlCount = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                boyCount ++;
            } else {
                totalGirls += numBorn;
                girlCount ++;
            }
        }
        System.out.println("Total births = " + totalBirths);
        System.out.println("Female births = " + totalGirls);
        System.out.println("Male births = " + totalBoys);
        System.out.println("Female names = " + girlCount);
        System.out.println("Male names = " + boyCount);
    }

    public void testTotalBirths () {
        FileResource fr = new FileResource();
        //FileResource fr = new FileResource("babyNamesTotals/data/yob2012.csv");
        totalBirths(fr);
    }

    public int getRank(int year, String name, String gender) {
        String fileName = "babyNamesTotals/data/yob" + year + ".csv";
        //String fileName = "babyNamesTotals/data/us_babynames_small/testing/yob" + year + "short.csv";
        FileResource fr = new FileResource(fileName);
        CSVParser parser = fr.getCSVParser(false);
        int rank = 0;
        int loopCount = 0;
        for(CSVRecord rec : parser) {
            if(rec.get(1).equals(gender)) {
                loopCount ++;
            }
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                rank = loopCount;
                return rank;
            } else {
                rank = -1;
            }
        }
        return rank;
    }

    public String getName(int year, int rank, String gender) {
        //String fileName = "babyNamesTotals/data/us_babynames_small/testing/yob" + year + "short.csv";
        String fileName = "babyNamesTotals/data/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        CSVParser parser = fr.getCSVParser(false);  
        int loopCount = 0;
        String name = "";
        for(CSVRecord rec : parser) {
            if(rec.get(1).equals(gender)) {
                loopCount ++;
            }
            if(loopCount == rank) {
                name = rec.get(0);
            }
        }
        if(name.equals("")) {
            name = "NO NAME";
        }
        return name;
    }

    public String whatIsNameInYear(String name, int year, int newYear, String gender) {
        int oldRank = getRank(year, name, gender);
        String newName = getName(newYear, oldRank, gender);
        System.out.println(name + " was born in " + year +
            " and would be called " + newName +
            " if htey were born in " + newYear);
        return newName;
    }

    public int getYear(File f) {
        String fileName = f.getName();
        String yearString = fileName.substring((fileName.length() - 8), (fileName.length() - 4));
        //string length for the test data.
        //String yearString = fileName.substring((fileName.length() - 13), (fileName.length() - 9));
        int year = Integer.parseInt(yearString);
        System.out.println(year);
        return year;
    }

    public void testgetYear() {
        File f = new File("babyNamesTotals/data/yob2012.csv");
        getYear(f);
    }

    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int currYear = 0;
        int highestYear = 0;
        int highestRank = 0;
        for(File f : dr.selectedFiles()) {
            currYear = getYear(f);
            int currRank = getRank(currYear, name, gender);
            if(highestRank == 0 && currRank != -1) {
                highestRank = currRank;
                highestYear = currYear;
            } else {
                if(currRank == -1) {
                    highestYear = -1;
                    return highestYear;
                }
                if(currRank < highestRank && currRank != -1) {
                    highestRank = currRank;
                    highestYear = currYear;
                } 
            } 
        }
        System.out.println("The year that " + name + " was most popular in the gender specified was " + highestYear);
        return highestYear;
    }


    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        double total = 0.0;
        double count = 0;
        double average = 0.0;
        for(File f : dr.selectedFiles()) {
            int currYear = getYear(f);
            int currRank = getRank(currYear, name, gender);
            if(currRank != -1) {
                total += currRank;
                count ++;
            } 
        }
        average = total/count;
        System.out.println("Average rank for " + name + " in the files selected is " + average);
        return average;
    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        //String fileName = "babyNamesTotals/data/us_babynames_small/testing/yob" + year + "short.csv";
        String fileName = "babyNamesTotals/data/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        CSVParser parser = fr.getCSVParser(false);
        int currRank = getRank(year, name, gender);
        int count = 0;
        for(CSVRecord rec : parser) {
            int newRank = getRank(year, rec.get(0), gender);
            int births = Integer.parseInt(rec.get(2));
            if(currRank > newRank && rec.get(1).equals(gender)) {
                count += births;
            }
        }
        return count;
    }
}


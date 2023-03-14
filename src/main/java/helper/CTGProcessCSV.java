package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Closing-the-Gap data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are the the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statments!
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au

 */
public class CTGProcessCSV {

   // MODIFY these to load/store to/from the correct locations
   
   private static final String DATABASE = "jdbc:sqlite:database/ctg.db";
   private static final String AGE16 = "database/lga_indigenous_status_by_age_by_sex_census_2016.csv";
   private static final String LTHC16 = "database/lga_long_term_health_conditions_by_indigenous_status_by_sex_2021.csv";
   private static final String SCHOOL16 = "database/lga_highest_year_of_school_completed_by_indigenous_status_by_sex_2016.csv";
   private static final String INCOME16 = "database/lga_total_household_income_weekly_by_indigenous_status_of_household_2016.csv";
   private static final String AGE21 = "database/lga_indigenous_status_by_age_by_sex_census_2021.csv";
   private static final String SCHOOL21 = "database/lga_highest_year_of_school_completed_by_indigenous_status_by_sex_2021.csv";
   private static final String INCOME21 = "database/lga_total_household_income_weekly_by_indigenous_status_of_household_2021.csv";
   private static final String LGA20 = "database/lgas_2020.csv";


   public static void main (String[] args) {
      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing


      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(LGA20));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            String lgaName = rowScanner.next();

            String type = rowScanner.next();
            String area = rowScanner.next();
            String lat = rowScanner.next();
            

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() ) {
               String lon = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into LGA20 VALUES ("
                              + lgaCode + ","
                              + "'" + lgaName + "',"
                              + "'" + type + "',"
                              + "'" + area + "',"
                              + "'" + lat + "',"
                              + lon + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               row++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      String category[] = {
         "_0_4",
         "_5_9",
         "_10_14",
         "_15_19",
         "_20_24",
         "_25_29",
         "_30_34",
         "_35_39",
         "_40_44",
         "_45_49",
         "_50_54",
         "_55_59",
         "_60_64",
         "_65_yrs_ov"
      };
      String status[] = {
         "indig",
         "non_indig",
         "indig_ns"
      };
      String sex[] = {
         "f",
         "m"
      };

      // JDBC Database Object
      connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(AGE16));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Skip lga_name
            String lgaName = rowScanner.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into PopulationStatistics2016 VALUES ("
                              + lgaCode + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category[indexCategory] + "',"
                              + count + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }



      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category1[] = {
         "arthritis",
         "asthma",
         "cancer",
         "dementia",
         "diabetes",
         "heartdisease",
         "kidneydisease",
         "lungcondition",
         "mentalhealth",
         "stroke",
         "other",
      };

      // JDBC Database Object
      //Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner1 = new Scanner(new File(LTHC16));

         // Read the first line of "headings"
         String header = lineScanner1.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         //connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row1 = 1;
         while (lineScanner1.hasNext()) {
            // Always get scan by line
            String line = lineScanner1.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner1 = new Scanner(line);
            rowScanner1.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner1.next();

            // Skip lga_name
            String lgaName = rowScanner1.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner1.hasNext() && indexCategory < category1.length) {
               String count = rowScanner1.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into LTHC VALUES ("
                              + lgaCode + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category1[indexCategory] + "',"
                              + count + ")";
               
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row1++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      


      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category2[] = {
         "did_not_go_to_school",
         "y8_below",
         "y9_equivalent",
         "y10_equivalent",
         "y11_equivalent",
         "y12_equivalent",
      };

      // JDBC Database Object
      //Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner2 = new Scanner(new File(SCHOOL16));

         // Read the first line of "headings"
         String header = lineScanner2.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         //connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row2 = 1;
         while (lineScanner2.hasNext()) {
            // Always get scan by line
            String line = lineScanner2.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner2 = new Scanner(line);
            rowScanner2.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner2.next();

            // Skip lga_name
            String lgaName = rowScanner2.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner2.hasNext() && indexCategory < category2.length) {
               String count = rowScanner2.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into SchoolCompletion2016 VALUES ("
                              + lgaCode + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category2[indexCategory] + "',"
                              + count + ")";
               
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row2++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }



      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category3[] = {
         "1_149",
         "150_299",
         "300_399",
         "400_499",
         "500_649",
         "650_799",
         "800_999",
         "1000_1249",
         "1250_1499",
         "1500_1999",
         "2000_2499",
         "2500_2999",
         "3000_more"
      };

      String status3[] = {
         "hhds_with_indig_persons",
         "other_hhds",
         "total_hhds"
      };
      // JDBC Database Object
      //Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner3 = new Scanner(new File(INCOME16));

         // Read the first line of "headings"
         String header = lineScanner3.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         //connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row3 = 1;
         while (lineScanner3.hasNext()) {
            // Always get scan by line
            String line = lineScanner3.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner3 = new Scanner(line);
            rowScanner3.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner3.next();

            // Skip lga_name
            String lgaName = rowScanner3.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner3.hasNext() && indexCategory < category3.length) {
               String count = rowScanner3.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into WeeklyIncome2016 VALUES ("
                              + lgaCode + ","
                              + "'" + status3[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category3[indexCategory] + "',"
                              + count + ")";
               
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status3.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row3++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      // JDBC Database Object
      connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(AGE21));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner.next();

            // Skip lga_name
            String lgaName = rowScanner.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner.hasNext() && indexCategory < category.length) {
               String count = rowScanner.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into PopulationStatistics2021 VALUES ("
                              + lgaCode + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category[indexCategory] + "',"
                              + count + ")";

               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      


      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing

      // JDBC Database Object
      //Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner2 = new Scanner(new File(SCHOOL21));

         // Read the first line of "headings"
         String header = lineScanner2.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         //connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row2 = 1;
         while (lineScanner2.hasNext()) {
            // Always get scan by line
            String line = lineScanner2.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner2 = new Scanner(line);
            rowScanner2.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner2.next();

            // Skip lga_name
            String lgaName = rowScanner2.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner2.hasNext() && indexCategory < category2.length) {
               String count = rowScanner2.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into SchoolCompletion2021 VALUES ("
                              + lgaCode + ","
                              + "'" + status[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category2[indexCategory] + "',"
                              + count + ")";
               
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row2++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }



      // The following arrays define the order of columns in the INPUT CSV.
      // The order of each array MUST match the order of the CSV.
      // These are specific to the given file and should be changed for each file.
      // This is a *simple* way to help you get up and running quickly wihout being confusing
      String category4[] = {
         "1_149",
         "150_299",
         "300_399",
         "400_499",
         "500_649",
         "650_799",
         "800_999",
         "1000_1249",
         "1250_1499",
         "1500_1749",
         "1750_1999",
         "2000_2499",
         "2500_2999",
         "3000_3499",
         "3500_more"
      };

      // JDBC Database Object
      //Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner3 = new Scanner(new File(INCOME21));

         // Read the first line of "headings"
         String header = lineScanner3.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC data base
         //connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row3 = 1;
         while (lineScanner3.hasNext()) {
            // Always get scan by line
            String line = lineScanner3.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner3 = new Scanner(line);
            rowScanner3.useDelimiter(",");

            // These indicies track which column we are up to
            int indexStatus = 0;
            int indexSex = 0;
            int indexCategory = 0;

            // Save the lga_code as we need it for the foreign key
            String lgaCode = rowScanner3.next();

            // Skip lga_name
            String lgaName = rowScanner3.next();

            // Go through the data for the row
            // If we run out of categories, stop for safety (so the code doesn't crash)
            while (rowScanner3.hasNext() && indexCategory < category3.length) {
               String count = rowScanner3.next();

               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();

               // Create Insert Statement
               String query = "INSERT into WeeklyIncome2021 VALUES ("
                              + lgaCode + ","
                              + "'" + status3[indexStatus] + "',"
                              + "'" + sex[indexSex] + "',"
                              + "'" + category3[indexCategory] + "',"
                              + count + ")";
               
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);

               // Update indices - go to next sex
               indexSex++;
               if (indexSex >= sex.length) {
                  // Go to next status
                  indexSex = 0;
                  indexStatus++;

                  if (indexStatus >= status3.length) {
                     // Go to next Category
                     indexStatus = 0;
                     indexCategory++;
                  }
               }
               row3++;
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}

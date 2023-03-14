package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/ctg.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs(String input) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();
        int number = Integer.parseInt(input);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE lga_code = " + number;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String name16  = results.getString("lga_name16");
                String type16 = results.getString("lga_type16");
                Float area_sqkm = results.getFloat("area_sqkm");

                // Create a LGA Object
                LGA lga = new LGA(code16, name16, type16, area_sqkm);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }
    public ArrayList<LGA> getLGAs20(String input) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();
        int number = Integer.parseInt(input);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA20 WHERE lga_code = " + number;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String name16  = results.getString("lga_name16");
                String type16 = results.getString("lga_type16");
                Float area_sqkm = results.getFloat("area_sqkm");

                // Create a LGA Object
                LGA lga = new LGA(code16, name16, type16, area_sqkm);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }
    public ArrayList<SocioOutcome> getSOs() {
        // Create the ArrayList of LGA objects to return
        ArrayList<SocioOutcome> so = new ArrayList<SocioOutcome>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM SocioeconomicOutcome";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                //SEPERATE
                int id     = results.getInt("id_SO");
                String title  = results.getString("title");
                String desc  = results.getString("descriptions_SO");

                // Create a LGA Object
                SocioOutcome sos = new SocioOutcome(id, title, desc);

                // Add the lga object to the array
                so.add(sos);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return so;
    }
    // TODO: Add your required methods here
    public ArrayList<Persona> getPersonas() {
        // Create the ArrayList of LGA objects to return
        ArrayList<Persona> personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Persona";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name = results.getString("persona_name");
                String imgPath = results.getString("image_path");

                // Create a LGA Object
                Persona persona = new Persona(name, imgPath);

                // Add the lga object to the array
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return personas;
    }
    
    public ArrayList<personaAttributes> getAttributes(String persona) {
        ArrayList<personaAttributes> personaAttribute = new ArrayList<personaAttributes>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "Select id_PA, persona_name, attribute, descriptions_PA FROM personaAttribute WHERE PERSONA_NAME = '" + persona + "';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
                
                while (results.next()){
                int id_PA    = results.getInt("id_PA");
                String persona_name  = results.getString("persona_name");
                String attribute = results.getString("attribute");
                String descriptions_PA = results.getString("descriptions_PA");

                personaAttributes att = new personaAttributes(id_PA, persona_name, attribute, descriptions_PA);

                personaAttribute.add(att);
                }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        // Finally we return all of the movies
        return personaAttribute;
    }

    public String getImagePath(String persona) {
        String path = "";
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT IMAGE_PATH FROM PERSONA WHERE PERSONA_NAME ='" + persona + "';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            path = results.getString(1);

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        // Finally we return all of the movies
        return path;
        
    }

    public ArrayList<PopulationStatistics> getPopStats16(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<PopulationStatistics> ps = new ArrayList<PopulationStatistics>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM PopulationStatistics2016 ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String age = results.getString("age");
                int count = results.getInt("count");

                // Create a LGA Object
                PopulationStatistics pss = new PopulationStatistics(code16, status, sex, age, count);

                // Add the lga object to the array
                ps.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return ps;
    }
    
    public ArrayList<PopulationStatistics> getPopStatsCode16(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<PopulationStatistics> ps = new ArrayList<PopulationStatistics>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM PopulationStatistics2016 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String age = results.getString("age");
                int count = results.getInt("count");

                // Create a LGA Object
                PopulationStatistics pss = new PopulationStatistics(code16, status, sex, age, count);

                // Add the lga object to the array
                ps.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return ps;
    }

    public ArrayList<PopulationStatistics> getPopStatsCode20(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<PopulationStatistics> ps = new ArrayList<PopulationStatistics>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM PopulationStatistics2021 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String age = results.getString("age");
                int count = results.getInt("count");

                // Create a LGA Object
                PopulationStatistics pss = new PopulationStatistics(code16, status, sex, age, count);

                // Add the lga object to the array
                ps.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return ps;
    }

    public ArrayList<LTHC> getLTHC(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LTHC> lthc = new ArrayList<LTHC>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM LTHC ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String condition = results.getString("condition");
                int count = results.getInt("count");

                // Create a LGA Object
                LTHC pss = new LTHC(code16, status, sex, condition, count);

                // Add the lga object to the array
                lthc.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lthc;
    }

    public ArrayList<LTHC> getLTHCCode(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LTHC> lthc = new ArrayList<LTHC>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM LTHC WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String condition = results.getString("condition");
                int count = results.getInt("count");

                // Create a LGA Object
                LTHC pss = new LTHC(code16, status, sex, condition, count);

                // Add the lga object to the array
                lthc.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lthc;
    }

    public ArrayList<SchoolCompletion> getSchoolComp16(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<SchoolCompletion> sc = new ArrayList<SchoolCompletion>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM SchoolCompletion2016 ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String year = results.getString("school_year");
                int count = results.getInt("count");

                // Create a LGA Object
                SchoolCompletion pss = new SchoolCompletion(code16, status, sex, year, count);

                // Add the lga object to the array
                sc.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return sc;
    }

    public ArrayList<SchoolCompletion> getSchoolCompCode16(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<SchoolCompletion> sc = new ArrayList<SchoolCompletion>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM SchoolCompletion2016 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String year = results.getString("school_year");
                int count = results.getInt("count");

                // Create a LGA Object
                SchoolCompletion pss = new SchoolCompletion(code16, status, sex, year, count);

                // Add the lga object to the array
                sc.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return sc;
    }

    public ArrayList<WeeklyIncome> getWeeklyIncome16(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<WeeklyIncome> wi = new ArrayList<WeeklyIncome>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM WeeklyIncome2016 ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String income = results.getString("income_bracket");
                int count = results.getInt("count");

                // Create a LGA Object
                WeeklyIncome pss = new WeeklyIncome(code16, status, sex, income, count);

                // Add the lga object to the array
                wi.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return wi;
    }

    public ArrayList<WeeklyIncome> getWeeklyIncomeCode16(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<WeeklyIncome> wi = new ArrayList<WeeklyIncome>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM WeeklyIncome2016 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String income = results.getString("income_bracket");
                int count = results.getInt("count");

                // Create a LGA Object
                WeeklyIncome pss = new WeeklyIncome(code16, status, sex, income, count);

                // Add the lga object to the array
                wi.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return wi;
    }

    //2021 DATA

    public ArrayList<PopulationStatistics> getPopStats21(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<PopulationStatistics> ps = new ArrayList<PopulationStatistics>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM PopulationStatistics2021 ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String age = results.getString("age");
                int count = results.getInt("count");

                // Create a LGA Object
                PopulationStatistics pss = new PopulationStatistics(code16, status, sex, age, count);

                // Add the lga object to the array
                ps.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return ps;
    }
    
    public ArrayList<PopulationStatistics> getPopStatsCode21(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<PopulationStatistics> ps = new ArrayList<PopulationStatistics>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM PopulationStatistics2021 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String age = results.getString("age");
                int count = results.getInt("count");

                // Create a LGA Object
                PopulationStatistics pss = new PopulationStatistics(code16, status, sex, age, count);

                // Add the lga object to the array
                ps.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return ps;
    }

    public ArrayList<SchoolCompletion> getSchoolComp21(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<SchoolCompletion> sc = new ArrayList<SchoolCompletion>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM SchoolCompletion2021 ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String year = results.getString("school_year");
                int count = results.getInt("count");

                // Create a LGA Object
                SchoolCompletion pss = new SchoolCompletion(code16, status, sex, year, count);

                // Add the lga object to the array
                sc.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return sc;
    }

    public ArrayList<SchoolCompletion> getSchoolCompCode21(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<SchoolCompletion> sc = new ArrayList<SchoolCompletion>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM SchoolCompletion2021 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String year = results.getString("school_year");
                int count = results.getInt("count");

                // Create a LGA Object
                SchoolCompletion pss = new SchoolCompletion(code16, status, sex, year, count);

                // Add the lga object to the array
                sc.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return sc;
    }

    public ArrayList<WeeklyIncome> getWeeklyIncome21(String sort, String order) {
        // Create the ArrayList of LGA objects to return
        ArrayList<WeeklyIncome> wi = new ArrayList<WeeklyIncome>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM WeeklyIncome2021 ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String income = results.getString("income_bracket");
                int count = results.getInt("count");

                // Create a LGA Object
                WeeklyIncome pss = new WeeklyIncome(code16, status, sex, income, count);

                // Add the lga object to the array
                wi.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return wi;
    }

    public ArrayList<WeeklyIncome> getWeeklyIncomeCode21(String sort, String order, int code) {
        // Create the ArrayList of LGA objects to return
        ArrayList<WeeklyIncome> wi = new ArrayList<WeeklyIncome>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            if (sort == null){
                sort = " ASC";
            }
            if (order == null){
                order = "lga_code";
            }
            String query = "SELECT * FROM WeeklyIncome2021 WHERE lga_code = "+code+" ORDER BY "+order+sort+" LIMIT 500;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String status  = results.getString("indigenous_status");
                String sex  = results.getString("sex");
                String income = results.getString("income_bracket");
                int count = results.getInt("count");

                // Create a LGA Object
                WeeklyIncome pss = new WeeklyIncome(code16, status, sex, income, count);

                // Add the lga object to the array
                wi.add(pss);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return wi;
    }

    public int useQueryCount(String q) {
        int count = 0;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // Get Result
            ResultSet results = statement.executeQuery(q);

            count = results.getInt(1);

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return count;
    }
    public String StateAtIndex(String codeS) {
        char firstIndex = codeS.charAt(0);
        String result;

        if (firstIndex == '1') {
            result = "NSW";
        }
        else if (firstIndex == '2') {
            result = "Victoria";
        }
        else if (firstIndex == '3') {
            result = "QLD";
        }
        else if (firstIndex == '4') {
            result = "South Australia";
        }
        else if (firstIndex == '5') {
            result = "Western Australia";
        }
        else if (firstIndex == '6') {
            result = "Tasmania";
        }
        else if (firstIndex == '7') {
            result = "Northern Territory";
        }
        else if (firstIndex == '8') {
            result = "Other Australian Territories";
        }
        else {
            result = null;
        }
        return result;
    }

    public ArrayList<LGA> getLGACode(String State) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();
        int number = Integer.parseInt(State);

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query

            String query = "SELECT * FROM LGA20 WHERE lga_code LIKE '" + number + "%'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int code16     = results.getInt("lga_code");
                String name16  = results.getString("lga_name16");
                String type16 = results.getString("lga_type16");
                Float area_sqkm = results.getFloat("area_sqkm");

                // Create a LGA Object
                LGA lga = new LGA(code16, name16, type16, area_sqkm);

                // Add the lga object to the array
                lgas.add(lga);
            }
                // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }
}
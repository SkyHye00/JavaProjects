package app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.lang.Math;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST22 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/lga.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>LGA Comparison</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
            <div class='header'>
                <h1>
                    LGA Comparison
                </h1>
            </div>
        """;

        html = html + """
            <div class='banner'>
                <img src='homepageBanner.png' class='banner' alt='Banner'/>
            </div>
        """;

        html = html + """
            <div class='logo'>
                <a href='/'><img src='logo.png' class='logo' alt='Logo'/></a>
            </div>
        """;

         // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='outcomes.html'>Outcome Data</a>
                <a href='lga.html'>LGA Comparison</a>
            </div>
        """;
                html = html + "<div class='content'>";

        // Add Div for page Content
        html = html + "<div class='textLayout'>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        ArrayList<String> results = new ArrayList<>();

        results.add("Population difference between LGA");
        results.add("Population difference between States");
        results.add("Difference between age data");
        results.add("Difference between long term health data");
        results.add("Difference between school completion data");
        results.add("Difference between weekly income data");


        html = html + "<form action='/lga.html' method='post'>";
        html = html + "<div class='inlineblock'>";
        html = html + "<h1> Select Specific LGA </h1>";
        html = html + "<input type='number' name='lga' id='lga' placeholder='LGA Code Here'>";
        html = html + "<h2> OR </h3>";
        html = html + "<h1> Select State </h1>";
        html = html + "<div class='box'>";
        html = html + "<select id='states' name='states'>";
        html = html + "<option value='0'>Please Select</option>";
        html = html + "<option value='1'>NSW</option>";
        html = html + "<option value='2'>Victoria</option>";
        html = html + "<option value='3'>QLD</option>";
        html = html + "<option value='4'>South Australia</option>";
        html = html + "<option value='5'>Western Australia</option>";
        html = html + "<option value='6'>Tasmania</option>";
        html = html + "<option value='7'>Northern Territory</option>";
        html = html + "<option value='8'>ACT</option>";
        html = html + "<option value='9'>Other Australia Territories</option>";
        html = html + "</select>";
        html = html + "</div>";
        html = html + "<br><h2> NOW </h3>";
        html = html + "<button type='submit' class='submit2'>Show Data!</button>";
        html = html + "</div>";

        String lga = context.formParam("lga");
        String state = context.formParam("states");
        String q;
        int a, b;
        DecimalFormat df = new DecimalFormat("###,###");
        
        if (lga != null && !(lga.equals("")) && Integer.parseInt(lga) >= 10050 && Integer.parseInt(lga) <= 99799 && state.equals("0")){
            ArrayList<LGA> lgaC = jdbc.getLGAs20(lga);
            html = html + "<h1> 2020 Results </h1>";
            html = html + """
                <div class='tables'>
                <table class='sort'>
                    <tr>
                        <th>Name</th>
                        <th>State</th>
                        <th>Type</th>
                        <th>Area_Sqkm</th>
                    </tr>
                        """;
            for (LGA items: lgaC){
                html = html + 
                "<tr>" +
                    "<td>" + items.getName16() + "</td>" +
                    "<td>" + jdbc.StateAtIndex(lga)+ "</td>" +
                    "<td>" + items.getType16()+ "</td>" +
                    "<td>" + items.getArea() + "</td>" +
                "</tr>";
            }
            html = html + "</table></div>";

            html = html + "<h1> Comparisons with LGA Code "+lga+"</h1>";

            q = "SELECT sum(count) FROM PopulationStatistics2016 WHERE lga_code = " + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM PopulationStatistics2021 WHERE lga_code = " + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<h3>The Change in Total Population between 2016 and 2021: " + df.format(Math.abs(b-a)) + "</h3><br>";

            //Pop Changes
            html = html + "<h2> Population Statistics Changes</h2>";

            q = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'indig' AND age = '_0_4' and lga_code = " + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Indigenous people who are between 0 to 4 years old from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'indig' AND age = '_0_4' and lga_code = " + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Indigenous people who are between 0 to 4 years old from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";

            q = "SELECT sum(count) FROM PopulationStatistics2016 where lga_code = " + lga + " and indigenous_status = 'indig' AND sex = 'f' AND age = '_15_19' OR AGE = '_20_24' OR AGE = '_25_29' OR AGE = '_30_34';";
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Female Indigenous people who are between 15 to 34 years old from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + lga + " and indigenous_status = 'indig' AND sex = 'f' AND age = '_15_19' OR AGE = '_20_24' OR AGE = '_25_29' OR AGE = '_30_34';";
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Female Indigenous people who are between 15 to 34 years old from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";
            
            q = "SELECT sum(count) FROM PopulationStatistics2016 where lga_code = " + lga + " and indigenous_status = 'non_indig' AND age = '_50_54' OR AGE = '_55_59' OR AGE = '_60_64';";
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Non-Indigenous people who are between 50 to 64 years old from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + lga + " and indigenous_status = 'non_indig' AND age = '_50_54' OR AGE = '_55_59' OR AGE = '_60_64';";
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Non-Indigenous people who are between 50 to 64 years old from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";

            //School changes
            html = html + "<h2>School Completion Changes</h2>";

            q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND school_year = 'did_not_go_to_school' and lga_code=" + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Indigenous people who did not go school from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND school_year = 'did_not_go_to_school' and lga_code=" + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Indigenous people who did not go school from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";

            q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND sex = 'f' AND (school_year = 'y9_equivalent' OR school_year = 'y10_equivalent' OR school_year = 'y11_equivalent') and lga_code=" + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Female Indigenous people who completed between School Years 9 to 11 from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND sex = 'f' AND (school_year = 'y9_equivalent' OR school_year = 'y10_equivalent' OR school_year = 'y11_equivalent') and lga_code=" + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Female Indigenous people who completed between School Years 9 to 11 from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";
            
            q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'non_indig' AND (school_year = 'y10_equivalent' OR school_year = 'y11_equivalent' OR school_year = 'y12_equivalent') and lga_code=" + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Non-Indigenous people who completed between School Years 10 to 12 from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'non_indig' AND (school_year = 'y10_equivalent' OR school_year = 'y11_equivalent' OR school_year = 'y12_equivalent') and lga_code=" + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Non-Indigenous people who completed between School Years 10 to 12 from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";

            //weekly income changes
            html = html + "<h2>Weekly Income Changes</h2>";

            q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '1_149' and lga_code = " + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Households who have Indigenous persons who earned between $1 and $149 from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '1_149' and lga_code = " + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Households who have Indigenous persons who earned between $1 and $149 from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";

            q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND sex = 'f' AND (income_bracket = '400_499' OR income_bracket = '500_649' OR income_bracket = '650_799') and lga_code = " + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Households who have Female Indigenous persons who earned between $400 to $799 from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND sex = 'f' AND (income_bracket = '400_499' OR income_bracket = '500_649' OR income_bracket = '650_799') and lga_code = " + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Households who have Female Indigenous persons who earned between $400 to $799 from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";
            
            q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'other_hhds' AND (income_bracket = '800_999' OR income_bracket = '1000_1249' OR income_bracket = '1250_1499') and lga_code = " + lga;
            a = jdbc.useQueryCount(q);
            html = html + "<p> Number of Households who do not have Indigenous persons who earned between $800 and $1499 from 2016 - "+ df.format(a) + " people</p>";
            q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'other_hhds' AND (income_bracket = '800_999' OR income_bracket = '1000_1249' OR income_bracket = '1250_1499') and lga_code = " + lga;
            b = jdbc.useQueryCount(q);
            html = html + "<p> Number of Households who do not have Indigenous persons who earned between $800 and $1499 from 2021 - "+ df.format(b) + " people</p>";
            html = html + "<p> The changes in data: " + df.format(b - a) + " people</p><br>";

        }
        else if (state != null && !(state.equals("0")) && (lga == null || (lga.equals("")))) {
            ArrayList<LGA> lgaS = jdbc.getLGACode(state);
            html = html + """
                <center>
                <h2>Names</h2>
                <div class='list'>
                        """;
            for (LGA items: lgaS) {
                html = html + 
                    "<li style='font-size: 18px;'>" + items.getName16() + "</li>";
            }
            html = html + "</center></div><br>";
        }
        else 
            html = html + "<h2><i>Please choose either LGA or State</i></h2>";


        html = html + "</div>";
        html = html + "</div>";
        html = html + """
            <div class='footer'>
                <p>&copyCOSC2803 - By Nikhil Sanjay and Steven Nguyen</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}

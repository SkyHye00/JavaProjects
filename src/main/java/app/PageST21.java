package app;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
public class PageST21 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/outcomes.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Outcome Data</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
            <div class='header'>
                <h1>
                    Outcome Data
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

        // Add Div for page Content
        html = html + "<div class='content'>";
        html = html + """
            <div class='textLayout'>
                <center>
                    <h1>2016 to 2021 Gap Comparisons</h1>
                        <p>
                            Here is presented information on 'The Gap' between Indigenous and 
                            non-Indigenous peoples as observed in both 2016 and 2021 data. Long Term Health
                            Condition Data only has 2021 Data and not 2016, therefore there will
                            not be any comparisons and only the table.
                        </p>
                    </div><br>
            """;

        html = html + "<div class='textLayout'>";
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        ArrayList<String> outcomes = new ArrayList<>();
        outcomes.add("Age Data - Aboriginal and Torres Strait Islander people enjoy long and healthy lives.");
        outcomes.add("Long Term Health Data - Aboriginal and Torres Strait Islander people enjoy long and healthy lives.");
        outcomes.add("School Completion Data - Aboriginal and Torres Strait Islander students achieve their full learning potential.");
        outcomes.add("Weekly Income Data - Strong economic participation and development of Aboriginal and Torres Strait Islander people and communities.");

        html = html + "<form action='/outcomes.html' method='post'>";
        html = html + "<div class='form-group' id='outcomeRadio'>";
        html = html + "<h1> Select Outcome </h1>";
        html = html + "<h2> Outcome 1: </h2>";
        html = html + "<input type='radio' name='outcome'  id='1' value='1' checked>";
        html = html + "<label for='1' class='outcome'>"+outcomes.get(0)+"</label><br><br>";
        html = html + "<input type='radio' name='outcome'  id='2' value='2'>";
        html = html + "<label for='2' class='outcome'>"+outcomes.get(1)+"</label>";
        html = html + "<h2> Outcome 5: </h2>";
        html = html + "<input type='radio' name='outcome'  id='3' value='3'>";
        html = html + "<label for='3' class='outcome'>"+outcomes.get(2)+"</label>";
        html = html + "<h2> Outcome 8: </h2>";
        html = html + "<input type='radio' name='outcome'  id='4' value='4'>";
        html = html + "<label for='4' class='outcome'>"+outcomes.get(3)+"</label><br><br>";
        html = html + "</div>";
        html = html + "<h1> Select Specific LGA </h1>";
        html = html + "<input type='number' name='lga' id='lga' placeholder='LGA Code Here'>";
        html = html + "<h3> Not required </h3>";
        html = html + "<div class='inlineblock'>";
        html = html + "<h1> Sort </h1>";
        html = html + "<div class='box'>";
        html = html + "<select id='sort' name='sort'>";
        html = html + "<option value=' ASC'>Ascending</option>";
        html = html + "<option value=' DESC'>Descending</option>";
        html = html + "</select>";
        html = html + "</div>";
        html = html + "</div>";
        html = html + "<div class='inlineblock'>";
        html = html + "<h1> Order by </h1>";
        html = html + "<div class='box'>";
        html = html + "<select id='order' name='order'>";
        html = html + "<option value='lga_code'>LGA Code</option>";
        html = html + "<option value='indigenous_status'>Indigenous Status</option>";
        html = html + "<option value='sex'>Sex</option>";
        html = html + "<option value='count'>Count</option>";
        html = html + "<option value='age'>Age (If Applicable)</option>";
        html = html + "<option value='condition'>Condition (If Applicable)</option>";
        html = html + "<option value='school_year'>Year (If Applicable)</option>";
        html = html + "<option value='income_bracket'>Income (If Applicable)</option>";
        html = html + "</select>";
        html = html + "</div>";
        html = html + "</div><br>";
        html = html + "<button type='submit' class='submit'>Show Data!</button>";
        html = html + "</form>";
        
        String indexOutcome = context.formParam("outcome");
        String order = context.formParam("order");
        String sort = context.formParam("sort");
        String codeS = context.formParam("lga");
        String q;
        String qq;
        int a;
        int b;
        double c;
        double d;
        DecimalFormat df = new DecimalFormat("###,###");
        DecimalFormat dfdf = new DecimalFormat("#.##");

        if (indexOutcome == null) 
            html = html + "<h2><i>No Results to show</i></h2>";
        else if (codeS == null ||codeS.equals("") || Integer.parseInt(codeS) < 10050 || Integer.parseInt(codeS) > 99799){
            int index = Integer.parseInt(indexOutcome);
            if (index == 1){
                ArrayList<PopulationStatistics> list16 = jdbc.getPopStats16(sort, order);
                ArrayList<PopulationStatistics> list21 = jdbc.getPopStats21(sort, order);
                if (list16.size() == 0)
                    html = html + "<h2><i>No Results to show</i></h2>";
                else{
                html = html + "<h1> Comparisons </h1>";
                
                q = "SELECT sum(count) FROM PopulationStatistics2016";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM PopulationStatistics2021";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";

                q = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'indig' AND age = '_0_4';";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Number of Indigenous people who are between 0 to 4 years old from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'indig' AND age = '_0_4';";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Number of Indigenous people who are between 0 to 4 years old from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";

                q = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'indig' AND sex = 'f' AND age = '_15_19' OR AGE = '_20_24' OR AGE = '_25_29' OR AGE = '_30_34';";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Number of Female Indigenous people who are between 15 to 34 years old from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'indig' AND sex = 'f' AND age = '_15_19' OR AGE = '_20_24' OR AGE = '_25_29' OR AGE = '_30_34';";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Number of Female Indigenous people who are between 15 to 34 years old from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
                
                q = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'non_indig' AND age = '_50_54' OR AGE = '_55_59' OR AGE = '_60_64';";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Number of Non-Indigenous people who are between 50 to 64 years old from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'non_indig' AND age = '_50_54' OR AGE = '_55_59' OR AGE = '_60_64';";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Number of Non-Indigenous people who are between 50 to 64 years old from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";

                q = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'indig' AND age = '_65_yrs_ov'";
                qq = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'indig'";
                c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Indigenous people who are 65 years old and older to the Total number of Indigenous people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                q = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'indig' AND age = '_65_yrs_ov'";
                qq = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'indig'";
                d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Indigenous people who are 65 years old and older to the Total number of Indigenous people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                
                q = "SELECT sum(count) FROM PopulationStatistics2016 where (indigenous_status = 'non_indig' AND sex='m') AND (age = '_30_34' OR AGE = '_35_39' OR AGE = '_40_44' OR AGE = '_45_49')";
                qq = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'non_indig' AND sex='m'";
                c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Non Indigenous Male people who are between 30 and 49 years old to the Total number of Non Indigenous Male people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                q = "SELECT sum(count) FROM PopulationStatistics2021 where (indigenous_status = 'non_indig' AND sex='m') AND (age = '_30_34' OR AGE = '_35_39' OR AGE = '_40_44' OR AGE = '_45_49')";
                qq = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'non_indig' AND sex='m'";
                d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Non Indigenous Male people who are between 30 and 49 years old to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";


                html = html + """
                </p>
                <h2 style='padding-right:15%; display:inline-block;'> Population Statistics 2016</h2>
                <h2 style='padding-left:15%; display:inline-block;'> Population Statistics 2021</h2>

                <div class='tables'>
                <table class='sort'>
                    <tr>
                        <th>LGA Code</th>
                        <th>Indigenous Status</th>
                        <th>Sex</th>
                        <th>Age</th>
                        <th>Count</th>
                    </tr>
                        """;
                for (PopulationStatistics item: list16){
                    html = html + 
                    "<tr>" +
                        "<td>" + item.getCode16() + "</td>" +
                        "<td>" + item.getStatus() + "</td>" +
                        "<td>" + item.getSex() + "</td>" +
                        "<td>" + item.getAge() + "</td>" +
                        "<td>" + item.getCount() + "</td>" +
                    "</tr>";
                }
                html = html + "</table><br>";

                html = html + """
                
                <table class='sort'>
                    <tr>
                        <th>LGA Code</th>
                        <th>Indigenous Status</th>
                        <th>Sex</th>
                        <th>Age</th>
                        <th>Count</th>
                    </tr>
                        """;
                for (PopulationStatistics item: list21){
                    html = html + 
                    "<tr>" +
                        "<td>" + item.getCode16() + "</td>" +
                        "<td>" + item.getStatus() + "</td>" +
                        "<td>" + item.getSex() + "</td>" +
                        "<td>" + item.getAge() + "</td>" +
                        "<td>" + item.getCount() + "</td>" +
                    "</tr>";
                }
                html = html + "</div></table>";
            }
        }
            else if (index == 2){
                ArrayList<LTHC> list = jdbc.getLTHC(sort, order);
                if (list.size() == 0)
                    html = html + "<h2><i>No Results to show</i></h2>";
                else{
                    html = html + "<h1> Comparisons </h1>";
                    q = "SELECT sum(count) FROM LTHC";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Total number of people from 2021 - "+ df.format(a) + " people</p>";
    
                    q = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' AND condition = 'cancer';";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Indigenous people with Cancer from 2021 - "+ df.format(a) + " people</p>";
    
                    q = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' AND sex = 'f' AND condition = 'heartdisease' OR condition = 'kidneydisease' OR condition = 'lungcondition' OR condition = 'diabetes';";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Female Indigenous people who have Heart and Kindney Disease, Lung conditions, and Diabetes from 2021 - "+ df.format(a) + " people</p>";
                    
                    q = "SELECT sum(count) FROM LTHC where indigenous_status = 'non_indig' AND condition = 'mentalhealth' OR condition = 'stroke' OR condition = 'asthma';";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Non-Indigenous people who have Mental Health conditions, Strokes, or Asthma from 2021 - "+ df.format(a) + " people</p>";
                    
                    q = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' AND condition = 'dementia'";
                    qq = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig'";
                    c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                    html = html + "<p> Percentage of Indigenous people who have Dementia to the Total number of Indigenous people from 2021 - "+ dfdf.format(c) + "% of people</p>";
                    
                    q = "SELECT sum(count) FROM LTHC where (indigenous_status = 'non_indig' AND sex='m') AND (condition = 'arthritis' OR condition = 'dementia' OR condition = 'other')";
                    qq = "SELECT sum(count) FROM LTHC where indigenous_status = 'non_indig' AND sex='m'";
                    c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                    html = html + "<p> Percentage of Non Indigenous Male people who have Arthritis, Dementia, or other uncommon/unlisted conditions to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(c) + "% of people</p>";
    
                
                html = html + """
                    <h2> Long Term Health Conditions Data 2021</h2>
                    <center>
                    <table class='sort'>
                        <tr>
                            <th>LGA Code</th>
                            <th>Indigenous Status</th>
                            <th>Sex</th>
                            <th>Condition</th>
                            <th>Count</th>
                        </tr>
                            """;
                    for (LTHC item: list){
                        html = html + 
                        "<tr>" +
                            "<td>" + item.getCode16() + "</td>" +
                            "<td>" + item.getStatus() + "</td>" +
                            "<td>" + item.getSex() + "</td>" +
                            "<td>" + item.getCondition() + "</td>" +
                            "<td>" + item.getCount() + "</td>" +
                        "</tr>";
                    }
                    html = html + "</center></table>";
                }
            }
            else if (index == 3){
                ArrayList<SchoolCompletion> list16 = jdbc.getSchoolComp16(sort, order);
                ArrayList<SchoolCompletion> list21 = jdbc.getSchoolComp21(sort, order);
                if (list16.size() == 0)
                    html = html + "<h2><i>No Results to Show</i></h2>";
                else{
                html = html + "<h1> Comparisons </h1>";
                
                q = "SELECT sum(count) FROM SchoolCompletion2016";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM SchoolCompletion2021";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";

                q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND school_year = 'did_not_go_to_school';";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Number of Indigenous people who did not go school from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND school_year = 'did_not_go_to_school';";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Number of Indigenous people who did not go school from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";

                q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND sex = 'f' AND school_year = 'y9_equivalent' OR school_year = 'y10_equivalent' OR school_year = 'y11_equivalent';";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Number of Female Indigenous people who completed between School Years 9 to 11 from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND sex = 'f' AND school_year = 'y9_equivalent' OR school_year = 'y10_equivalent' OR school_year = 'y11_equivalent';";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Number of Female Indigenous people who completed between School Years 9 to 11 from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
                
                q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'non_indig' AND school_year = 'y10_equivalent' OR school_year = 'y11_equivalent' OR school_year = 'y12_equivalent';";
                a = jdbc.useQueryCount(q);
                html = html + "<p> Number of Non-Indigenous people who completed between School Years 10 to 12 from 2016 - "+ df.format(a) + " people</p>";
                q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'non_indig' AND school_year = 'y10_equivalent' OR school_year = 'y11_equivalent' OR school_year = 'y12_equivalent';";
                b = jdbc.useQueryCount(q);
                html = html + "<p> Number of Non-Indigenous people who completed between School Years 10 to 12 from 2021 - "+ df.format(b) + " people</p>";
                html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";

                q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND school_year = 'y12_equivalent'";
                qq = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig'";
                c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Indigenous people who School year 12 or equivalent to the Total number of Indigenous people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND school_year = 'y12_equivalent'";
                qq = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig'";
                d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Indigenous people who School year 12 or equivalent to the Total number of Indigenous people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                
                q = "SELECT sum(count) FROM SchoolCompletion2016 where (indigenous_status = 'non_indig' AND sex='m') AND (school_year = 'did_not_go_to_school' OR school_year = 'y8_below')";
                qq = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'non_indig' AND sex='m'";
                c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Non Indigenous Male people who either did not go to school or completed School year 8 to the Total number of Non Indigenous Male people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                q = "SELECT sum(count) FROM SchoolCompletion2021 where (indigenous_status = 'non_indig' AND sex='m') AND (school_year = 'did_not_go_to_school' OR school_year = 'y8_below')";
                qq = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'non_indig' AND sex='m'";
                d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                html = html + "<p> Percentage of Non Indigenous Male people who either did not go to school or completed School year 8 to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";


                html = html + """
                    <h2 style='padding-right:15%; display:inline-block;'> School Completion 2016</h2>
                    <h2 style='padding-left:15%; display:inline-block;'> School Completion 2021</h2>

                    <div class='tables'>
                    <table class='sort'>
                        <tr>
                            <th>LGA Code</th>
                            <th>Indigenous Status</th>
                            <th>Sex</th>
                            <th>Year</th>
                            <th>Count</th>
                        </tr>
                            """;
                    for (SchoolCompletion item: list16){
                        html = html + 
                        "<tr>" +
                            "<td>" + item.getCode16() + "</td>" +
                            "<td>" + item.getStatus() + "</td>" +
                            "<td>" + item.getSex() + "</td>" +
                            "<td>" + item.getYear() + "</td>" +
                            "<td>" + item.getCount() + "</td>" +
                        "</tr>";
                    }
                    html = html + "</table><br>";
                    
                    html = html + """
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Year</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (SchoolCompletion item: list21){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getYear() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</div></table>";
            }
        }
            else{
                ArrayList<WeeklyIncome> list16 = jdbc.getWeeklyIncome16(sort, order);
                ArrayList<WeeklyIncome> list21 = jdbc.getWeeklyIncome21(sort, order);
                if (list16.size() == 0)
                    html = html + "<h2><i>No Results to show</i></h2>";
                else{
                    html = html + "<h1> Comparisons </h1>";
                
                    q = "SELECT sum(count) FROM WeeklyIncome2016";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
                    q = "SELECT sum(count) FROM WeeklyIncome2021";
                    b = jdbc.useQueryCount(q);
                    html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
                    html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
    
                    q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '1_149';";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Households who have Indigenous persons who earned between $1 and $149 from 2016 - "+ df.format(a) + " people</p>";
                    q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '1_149';";
                    b = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Households who have Indigenous persons who earned between $1 and $149 from 2021 - "+ df.format(b) + " people</p>";
                    html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
    
                    q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND sex = 'f' AND (income_bracket = '400_499' OR income_bracket = '500_649' OR income_bracket = '650_799');";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Households who have Female Indigenous persons who earned between $400 to $799 from 2016 - "+ df.format(a) + " people</p>";
                    q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND sex = 'f' AND (income_bracket = '400_499' OR income_bracket = '500_649' OR income_bracket = '650_799');";
                    b = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Households who have Female Indigenous persons who earned between $400 to $799 from 2021 - "+ df.format(b) + " people</p>";
                    html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
                    
                    q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'other_hhds' AND income_bracket = '800_999' OR income_bracket = '1000_1249' OR income_bracket = '1250_1499';";
                    a = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Households who do not have Indigenous persons who earned between $800 and $1499 from 2016 - "+ df.format(a) + " people</p>";
                    q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'other_hhds' AND income_bracket = '800_999' OR income_bracket = '1000_1249' OR income_bracket = '1250_1499';";
                    b = jdbc.useQueryCount(q);
                    html = html + "<p> Number of Households who do not have Indigenous persons who earned between $800 and $1499 from 2021 - "+ df.format(b) + " people</p>";
                    html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
    
                    q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '3000_more'";
                    qq = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons'";
                    c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                    html = html + "<p> Percentage of Households who have Indigenous persons who earned over $3000 to the Total number of Indigenous people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                    q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '3500_more' OR income_bracket = '3000_3499'";
                    qq = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons'";
                    d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                    html = html + "<p> Percentage of Households who have Indigenous persons who earned over $3000 to the Total number of Indigenous people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                    html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                    
                    q = "SELECT sum(count) FROM WeeklyIncome2016 where (indigenous_status = 'other_hhds' AND sex='m') AND (income_bracket = '2000_2499' OR income_bracket = '2500_2999')";
                    qq = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'other_hhds' AND sex='m'";
                    c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                    html = html + "<p> Percentage of Male Households who do not have Indigenous persons who earned between $2000 and $2999 to the Total number of Non Indigenous Male people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                    q = "SELECT sum(count) FROM WeeklyIncome2021 where (indigenous_status = 'other_hhds' AND sex='m') AND (income_bracket = '2000_2499' OR income_bracket = '2500_2999')";
                    qq = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'other_hhds' AND sex='m'";
                    d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                    html = html + "<p> Percentage of Male Households who do not have Indigenous persons who earned between $2000 and $2999 to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                    html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";

                html = html + """
                    <h2 style='padding-right:15%; display:inline-block;'> Weekly Income 2016</h2>
                    <h2 style='padding-left:15%; display:inline-block;'> Weekly Income 2021</h2>

                    <div class='tables'>
                    <table class='sort'>
                        <tr>
                            <th>LGA Code</th>
                            <th>Indigenous Status</th>
                            <th>Sex</th>
                            <th>Income</th>
                            <th>Count</th>
                        </tr>
                            """;
                    for (WeeklyIncome item: list16){
                        html = html + 
                        "<tr>" +
                            "<td>" + item.getCode16() + "</td>" +
                            "<td>" + item.getStatus() + "</td>" +
                            "<td>" + item.getSex() + "</td>" +
                            "<td>" + item.getIncome() + "</td>" +
                            "<td>" + item.getCount() + "</td>" +
                        "</tr>";
                    }
                    html = html + "</table><br>";

                    html = html + """
                    <table class='sort'>
                        <tr>
                            <th>LGA Code</th>
                            <th>Indigenous Status</th>
                            <th>Sex</th>
                            <th>Income</th>
                            <th>Count</th>
                        </tr>
                            """;
                    for (WeeklyIncome item: list21){
                        html = html + 
                        "<tr>" +
                            "<td>" + item.getCode16() + "</td>" +
                            "<td>" + item.getStatus() + "</td>" +
                            "<td>" + item.getSex() + "</td>" +
                            "<td>" + item.getIncome() + "</td>" +
                            "<td>" + item.getCount() + "</td>" +
                        "</tr>";
                    }
                    html = html + "</div></table>";
                }
            }
        }
            else{
                int index = Integer.parseInt(indexOutcome);
                int code = Integer.parseInt(codeS);
                if (index == 1){
                    if (code > 99399)
                        code = 99399;
                        
                        ArrayList<PopulationStatistics> list16 = jdbc.getPopStatsCode16(sort, order, code);
                        ArrayList<PopulationStatistics> list21 = jdbc.getPopStatsCode21(sort, order, code);
                        
                        if (list16.size() == 0)
                            html = html + "<h2><i>No Results to show</i></h2>";
                        else{
                            html = html + "<h1> Comparisons with LGA Code "+code+"</h1>";

                            q = "SELECT sum(count) FROM PopulationStatistics2016 WHERE lga_code = " + code;
                            a = jdbc.useQueryCount(q);
                            html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
                            q = "SELECT sum(count) FROM PopulationStatistics2021 WHERE lga_code = " + code;
                            b = jdbc.useQueryCount(q);
                            html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
                            html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
            
                            q = "SELECT sum(count) FROM PopulationStatistics2016 where indigenous_status = 'indig' AND age = '_0_4' and lga_code = " + code;
                            a = jdbc.useQueryCount(q);
                            html = html + "<p> Number of Indigenous people who are between 0 to 4 years old from 2016 - "+ df.format(a) + " people</p>";
                            q = "SELECT sum(count) FROM PopulationStatistics2021 where indigenous_status = 'indig' AND age = '_0_4' and lga_code = " + code;
                            b = jdbc.useQueryCount(q);
                            html = html + "<p> Number of Indigenous people who are between 0 to 4 years old from 2021 - "+ df.format(b) + " people</p>";
                            html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
            
                            q = "SELECT sum(count) FROM PopulationStatistics2016 where lga_code = " + code + " and indigenous_status = 'indig' AND sex = 'f' AND age = '_15_19' OR AGE = '_20_24' OR AGE = '_25_29' OR AGE = '_30_34';";
                            a = jdbc.useQueryCount(q);
                            html = html + "<p> Number of Female Indigenous people who are between 15 to 34 years old from 2016 - "+ df.format(a) + " people</p>";
                            q = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + code + " and indigenous_status = 'indig' AND sex = 'f' AND age = '_15_19' OR AGE = '_20_24' OR AGE = '_25_29' OR AGE = '_30_34';";
                            b = jdbc.useQueryCount(q);
                            html = html + "<p> Number of Female Indigenous people who are between 15 to 34 years old from 2021 - "+ df.format(b) + " people</p>";
                            html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
                            
                            q = "SELECT sum(count) FROM PopulationStatistics2016 where lga_code = " + code + " and indigenous_status = 'non_indig' AND age = '_50_54' OR AGE = '_55_59' OR AGE = '_60_64';";
                            a = jdbc.useQueryCount(q);
                            html = html + "<p> Number of Non-Indigenous people who are between 50 to 64 years old from 2016 - "+ df.format(a) + " people</p>";
                            q = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + code + " and indigenous_status = 'non_indig' AND age = '_50_54' OR AGE = '_55_59' OR AGE = '_60_64';";
                            b = jdbc.useQueryCount(q);
                            html = html + "<p> Number of Non-Indigenous people who are between 50 to 64 years old from 2021 - "+ df.format(b) + " people</p>";
                            html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
            
                            q = "SELECT sum(count) FROM PopulationStatistics2016 where lga_code = " + code + " and indigenous_status = 'indig' AND age = '_65_yrs_ov'";
                            qq = "SELECT sum(count) FROM PopulationStatistics2016  where lga_code = " + code + " and indigenous_status = 'indig'";
                            c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                            html = html + "<p> Percentage of Indigenous people who are 65 years old and older to the Total number of Indigenous people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                            q = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + code + " and indigenous_status = 'indig' AND age = '_65_yrs_ov'";
                            qq = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + code + " and indigenous_status = 'indig'";
                            d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                            html = html + "<p> Percentage of Indigenous people who are 65 years old and older to the Total number of Indigenous people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                            html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                            
                            q = "SELECT sum(count) FROM PopulationStatistics2016  where lga_code = " + code + " and (indigenous_status = 'non_indig' AND sex='m') AND (age = '_30_34' OR AGE = '_35_39' OR AGE = '_40_44' OR AGE = '_45_49')";
                            qq = "SELECT sum(count) FROM PopulationStatistics2016 where lga_code = " + code + " and indigenous_status = 'non_indig' AND sex='m'";
                            c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                            html = html + "<p> Percentage of Non Indigenous Male people who are between 30 and 49 years old to the Total number of Non Indigenous Male people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                            q = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + code + " And (indigenous_status = 'non_indig' AND sex='m') AND (age = '_30_34' OR AGE = '_35_39' OR AGE = '_40_44' OR AGE = '_45_49')";
                            qq = "SELECT sum(count) FROM PopulationStatistics2021 where lga_code = " + code + " AND indigenous_status = 'non_indig' AND sex='m'";
                            d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                            html = html + "<p> Percentage of Non Indigenous Male people who are between 30 and 49 years old to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                            html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";

                        html = html + """
                        <h2 style='padding-right:15%; display:inline-block;'> Population Statistics 2016</h2>
                        <h2 style='padding-left:15%; display:inline-block;'> Population Statistics 2021</h2>
        
                        <div class='tables'>
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Age</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (PopulationStatistics item: list16){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getAge() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</table><br>";
        
                        html = html + """
                        
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Age</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (PopulationStatistics item: list21){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getAge() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</div></table>";
                    }
            }
                else if (index == 2){
                    ArrayList<LTHC> list = jdbc.getLTHCCode(sort, order, code);
                    if (list.size() == 0)
                        html = html + "<h2><i>No Results to show</i></h2>";
                    else{
                        html = html + "<h1> Comparisons with LGA Code "+code+"</h1>";
                        q = "SELECT sum(count) FROM LTHC where lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Total number of people from 2021 - "+ df.format(a) + " people</p>";
        
                        q = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' AND condition = 'cancer' and lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Indigenous people with Cancer from 2021 - "+ df.format(a) + " people</p>";
        
                        q = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' AND sex = 'f' AND (condition = 'heartdisease' OR condition = 'kidneydisease' OR condition = 'lungcondition' OR condition = 'diabetes') and lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Female Indigenous people who have Heart and Kindney Disease, Lung conditions, and Diabetes from 2021 - "+ df.format(a) + " people</p>";
                        
                        q = "SELECT sum(count) FROM LTHC where indigenous_status = 'non_indig' AND (condition = 'mentalhealth' OR condition = 'stroke' OR condition = 'asthma') and lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Non-Indigenous people who have Mental Health conditions, Strokes, or Asthma from 2021 - "+ df.format(a) + " people</p>";
                        
                        q = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' AND condition = 'dementia'  and lga_code = " + code;
                        qq = "SELECT sum(count) FROM LTHC where indigenous_status = 'indig' and lga_code = " + code;
                        c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Indigenous people who have Dementia to the Total number of Indigenous people from 2021 - "+ dfdf.format(c) + "% of people</p>";
                        
                        q = "SELECT sum(count) FROM LTHC where (indigenous_status = 'non_indig' AND sex='m') AND (condition = 'arthritis' OR condition = 'dementia' OR condition = 'other') and lga_code = " + code;
                        qq = "SELECT sum(count) FROM LTHC where indigenous_status = 'non_indig' AND sex='m' and lga_code = " + code;
                        c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Non Indigenous Male people who have Arthritis, Dementia, or other uncommon/unlisted conditions to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(c) + "% of people</p>";

                    html = html + """
                        <h2> Long Term Health Conditions Data </h2>
                        <center>
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Condition</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (LTHC item: list){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getCondition() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</center></table>";
                }
            }
                else if (index == 3){
                    ArrayList<SchoolCompletion> list16 = jdbc.getSchoolCompCode16(sort, order, code);
                    ArrayList<SchoolCompletion> list21 = jdbc.getSchoolCompCode21(sort, order, code);
                    if (list16.size() == 0)
                        html = html + "<h2><i>No Results to Show</i></h2>";
                    else{
                        html = html + "<h1> Comparisons with LGA Code "+code+"</h1>";
                
                        q = "SELECT sum(count) FROM SchoolCompletion2016 where lga_code=" + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM SchoolCompletion2021 where lga_code=" + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
        
                        q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND school_year = 'did_not_go_to_school' and lga_code=" + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Indigenous people who did not go school from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND school_year = 'did_not_go_to_school' and lga_code=" + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Indigenous people who did not go school from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
        
                        q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND sex = 'f' AND (school_year = 'y9_equivalent' OR school_year = 'y10_equivalent' OR school_year = 'y11_equivalent') and lga_code=" + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Female Indigenous people who completed between School Years 9 to 11 from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND sex = 'f' AND (school_year = 'y9_equivalent' OR school_year = 'y10_equivalent' OR school_year = 'y11_equivalent') and lga_code=" + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Female Indigenous people who completed between School Years 9 to 11 from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
                        
                        q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'non_indig' AND (school_year = 'y10_equivalent' OR school_year = 'y11_equivalent' OR school_year = 'y12_equivalent') and lga_code=" + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Non-Indigenous people who completed between School Years 10 to 12 from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'non_indig' AND (school_year = 'y10_equivalent' OR school_year = 'y11_equivalent' OR school_year = 'y12_equivalent') and lga_code=" + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Non-Indigenous people who completed between School Years 10 to 12 from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
        
                        q = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' AND school_year = 'y12_equivalent' and lga_code=" + code;
                        qq = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'indig' and lga_code=" + code;
                        c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Indigenous people who School year 12 or equivalent to the Total number of Indigenous people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                        q = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' AND school_year = 'y12_equivalent' and lga_code=" + code;
                        qq = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'indig' and lga_code=" + code;
                        d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Indigenous people who School year 12 or equivalent to the Total number of Indigenous people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                        html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                        
                        q = "SELECT sum(count) FROM SchoolCompletion2016 where (indigenous_status = 'non_indig' AND sex='m') AND (school_year = 'did_not_go_to_school' OR school_year = 'y8_below') and lga_code=" + code;
                        qq = "SELECT sum(count) FROM SchoolCompletion2016 where indigenous_status = 'non_indig' AND sex='m' and lga_code=" + code;
                        c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Non Indigenous Male people who either did not go to school or completed School year 8 to the Total number of Non Indigenous Male people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                        q = "SELECT sum(count) FROM SchoolCompletion2021 where (indigenous_status = 'non_indig' AND sex='m') AND (school_year = 'did_not_go_to_school' OR school_year = 'y8_below') and lga_code=" + code;
                        qq = "SELECT sum(count) FROM SchoolCompletion2021 where indigenous_status = 'non_indig' AND sex='m' and lga_code=" + code;
                        d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Non Indigenous Male people who either did not go to school or completed School year 8 to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                        html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
           
                        
                    html = html + """
                    <h2 style='padding-right:15%; display:inline-block;'> School Completion 2016</h2>
                    <h2 style='padding-left:15%; display:inline-block;'> School Completion 2021</h2>

                    <div class='tables'>
                    <table class='sort'>
                        <tr>
                            <th>LGA Code</th>
                            <th>Indigenous Status</th>
                            <th>Sex</th>
                            <th>Year</th>
                            <th>Count</th>
                        </tr>
                            """;
                    for (SchoolCompletion item: list16){
                        html = html + 
                        "<tr>" +
                            "<td>" + item.getCode16() + "</td>" +
                            "<td>" + item.getStatus() + "</td>" +
                            "<td>" + item.getSex() + "</td>" +
                            "<td>" + item.getYear() + "</td>" +
                            "<td>" + item.getCount() + "</td>" +
                        "</tr>";
                    }
                    html = html + "</table><br>";
                    
                    html = html + """
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Year</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (SchoolCompletion item: list21){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getYear() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</div></table>";
            }
            }
                else{
                    ArrayList<WeeklyIncome> list16 = jdbc.getWeeklyIncomeCode16(sort, order, code);
                    ArrayList<WeeklyIncome> list21 = jdbc.getWeeklyIncomeCode21(sort, order, code);
                    if (list16.size() == 0)
                        html = html + "<h2><i>No Results to show</i></h2>";
                    else{
                        html = html + "<h1> Comparisons with LGA Code "+code+"</h1>";
                
                        q = "SELECT sum(count) FROM WeeklyIncome2016 where lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Total number of people from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM WeeklyIncome2021 where lga_code = " + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Total number of people from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
        
                        q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '1_149' and lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Households who have Indigenous persons who earned between $1 and $149 from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '1_149' and lga_code = " + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Households who have Indigenous persons who earned between $1 and $149 from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
        
                        q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND sex = 'f' AND (income_bracket = '400_499' OR income_bracket = '500_649' OR income_bracket = '650_799') and lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Households who have Female Indigenous persons who earned between $400 to $799 from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND sex = 'f' AND (income_bracket = '400_499' OR income_bracket = '500_649' OR income_bracket = '650_799') and lga_code = " + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Households who have Female Indigenous persons who earned between $400 to $799 from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
                        
                        q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'other_hhds' AND (income_bracket = '800_999' OR income_bracket = '1000_1249' OR income_bracket = '1250_1499') and lga_code = " + code;
                        a = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Households who do not have Indigenous persons who earned between $800 and $1499 from 2016 - "+ df.format(a) + " people</p>";
                        q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'other_hhds' AND (income_bracket = '800_999' OR income_bracket = '1000_1249' OR income_bracket = '1250_1499') and lga_code = " + code;
                        b = jdbc.useQueryCount(q);
                        html = html + "<p> Number of Households who do not have Indigenous persons who earned between $800 and $1499 from 2021 - "+ df.format(b) + " people</p>";
                        html = html + "<p> The Gap in data: " + df.format(b - a) + " people</p><br>";
        
                        q = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons' AND income_bracket = '3000_more' and lga_code = " + code;
                        qq = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'hhds_with_indig_persons'";
                        c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Households who have Indigenous persons who earned over $3000 to the Total number of Indigenous people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                        q = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons' AND (income_bracket = '3500_more' OR income_bracket = '3000_3499') and lga_code = " + code;
                        qq = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'hhds_with_indig_persons'";
                        d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Households who have Indigenous persons who earned over $3000 to the Total number of Indigenous people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                        html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                        
                        q = "SELECT sum(count) FROM WeeklyIncome2016 where (indigenous_status = 'other_hhds' AND sex='m') AND (income_bracket = '2000_2499' OR income_bracket = '2500_2999') and lga_code = " + code;
                        qq = "SELECT sum(count) FROM WeeklyIncome2016 where indigenous_status = 'other_hhds' AND sex='m' and lga_code = " + code;
                        c = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Male Households who do not have Indigenous persons who earned between $2000 and $2999 to the Total number of Non Indigenous Male people from 2016 - "+ dfdf.format(c) + "% of people</p>";
                        q = "SELECT sum(count) FROM WeeklyIncome2021 where (indigenous_status = 'other_hhds' AND sex='m') AND (income_bracket = '2000_2499' OR income_bracket = '2500_2999') and lga_code = " + code;
                        qq = "SELECT sum(count) FROM WeeklyIncome2021 where indigenous_status = 'other_hhds' AND sex='m' and lga_code = " + code;
                        d = ((double)jdbc.useQueryCount(q) / jdbc.useQueryCount(qq)) * 100;
                        html = html + "<p> Percentage of Male Households who do not have Indigenous persons who earned between $2000 and $2999 to the Total number of Non Indigenous Male people from 2021 - "+ dfdf.format(d) + "% of people</p>";
                        html = html + "<p> The Gap in data: " + dfdf.format(d - c) + "% of people</p><br>";
                    html = html + """
                        <h2 style='padding-right:15%; display:inline-block;'> Weekly Income 2016</h2>
                        <h2 style='padding-left:15%; display:inline-block;'> Weekly Income 2021</h2>

                        <div class='tables'>
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Income</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (WeeklyIncome item: list16){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getIncome() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</table><br>";

                        html = html + """
                        <table class='sort'>
                            <tr>
                                <th>LGA Code</th>
                                <th>Indigenous Status</th>
                                <th>Sex</th>
                                <th>Income</th>
                                <th>Count</th>
                            </tr>
                                """;
                        for (WeeklyIncome item: list21){
                            html = html + 
                            "<tr>" +
                                "<td>" + item.getCode16() + "</td>" +
                                "<td>" + item.getStatus() + "</td>" +
                                "<td>" + item.getSex() + "</td>" +
                                "<td>" + item.getIncome() + "</td>" +
                                "<td>" + item.getCount() + "</td>" +
                            "</tr>";
                        }
                        html = html + "</div></table>";

                    }
                }
            }
        
        html = html + "</div><br>";

        // Close Content div
        html = html + "</div>";

        // Footer
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

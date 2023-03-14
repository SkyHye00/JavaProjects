package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                    Homepage
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

        //Welcome Introduction
        html = html + """
            <div class='textLayout'>
                <h1>The Big Picture - Aboriginal and Torres Strait Islander People</h1>
                <h2>Wominjeka</h2>
                <p>
                    Welcome to our webpage where we Welcome all those who are Aboriginal and Torres Strait Islander people
                    today. Our job here is to help governments, Indigenous leaders and the general public observe
                    progress towards the targets of the National Agreement on Closing the Gap. We are here to present different 
                    statistics and calculated information about Australia's progress on a selection of the 17 socioeconomic 
                    outcomes using data in an informative but respectful manner. We have catered for diverse users who are 
                    interested in high-level concepts or an in-depth analysis of the data. Our core focus is on progression 
                    towards the socioeconomic outcomes across the last 5 years as captured in the 2016 and 2021 censuses.
                </p>
                <img src='intro.png' class='introImg' alt='Welcome Image'/>
                <br>
            </div>
            <br>
            """;

        //Socioeconomic Outcome
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<SocioOutcome> SO = jdbc.getSOs();
        html = html + """
            <div class='textLayout'>
                <h1>What are the 17 Socioeconomic Outcomes?</h1>
                <p>
                    The National Agreement on Closing the Gap (the National Agreement) has 17 Socioeconomic targets across 
                    areas that have an impact on life outcomes for Aboriginal and Torres Strait Islander people. The progress against 
                    the targets will be monitored by the Productivity Commission and will help all parties to the National Agreement 
                    understand how their efforts are contributing to progress over the next ten years.

                    Each of the targets and their outcomes are listed below.

                    Data tables showing the starting points for each Closing the Gap target by jurisdictions are now available on the 
                    Productivity Commission Closing the Gap Information Repository Dashboard.
                </p>
                <p style ='color:#464646'>
                    <em>
                        Information on all of this can be found on the 
                        <a href='https://www.closingthegap.gov.au/national-agreement/national-agreement-closing-the-gap'>
                            <q>
                                National Agreement on Closing the Gap Website
                            </q>
                        </a>
                    </em>
                </p>
            <section class='accordion'>
                <ul>
            """;
        for (SocioOutcome db : SO) {
            if (db.getID() == 1){
            html = html + "<li>"+
                "<label for='" + db.getID() + "'> " + db.getID() + ". " + db.getTitle() + " <span>&#x3e;</span></label>"+
                "<input type='radio' name='accordion' id='" + db.getID() + "' checked>"+
                "<section class='outcomes'>"+
                    "<p>" + db.getDesc() + "</p>"+
                "</section>"+
            "</li>";
            }
            else{
                html = html + "<li>"+
                    "<label for='" + db.getID() + "'> "+ db.getID() + ". " + db.getTitle() + " <span>&#x3e;</span></label>"+
                    "<input type='radio' name='accordion' id='" + db.getID() + "'>"+
                    "<section class='outcomes'>"+
                        "<p>" + db.getDesc() + "</p>"+
                    "</section>"+
                "</li>";
                }
        }
        html = html + """
            </ul>   
            </div><br>
        """;

        html = html + """
            <div class='textLayout'>
                <h1>Our Socioeconomic Target!</h1>
                <p>
                    As our community is expanding, there needs to be more emphasis on those that
                    have been the pillar of our growth. In this sense, there must be some form 
                    of recognition for the Indigenous people that have contributed to the 
                    advancement of modern society as it is today. With this in mind, our goal is 
                    to bridge the gap and provide equal or better opportunity for them. Our target """;
    html = html + "from these 17 socioeconomic outcomes will be Outcome 8 ("+SO.get(8).getTitle()+")";
    html = html + """  
                    with better funding and government support in order to be a more inclusive community
                    to help these Indigenous people become employed and have sustainable lives.  
                    Hopefully by garnering attention from Outcome 8, people become more aware of the Indigenous
                    community.
                </p>
            </div>
            """;


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

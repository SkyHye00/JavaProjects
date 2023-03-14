package app;

import java.util.ArrayList;

//import javax.sound.sampled.SourceDataLine;

//import java.util.ArrayList;

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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        html = html + """
            <div class='header'>
                <h1>
                    Our Mission
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
        html = html + """
            <div class='textLayout'>
                <center>
                    <h1>Closing The Gap!!!</h1>
                        <p> 
                            In recent years, there has been an ongoing problem of social 
                            acceptance of certain demographics. As a result of this people of 
                            different cultures have experienced trouble with opportunities that 
                            could further improve their lives. In order to tackle this inequality,
                            it is necessary to highlight these problems on platforms which are 
                            consistently used by the general public to garner their attention. 
                            The members of the Aboriginal and Torres Strait Islander Australians 
                            that are here today stand on their motherlands deserve the same freedom 
                            and recognition as the rest of society. As a community, 
                            it is not fair on the grounds that they have to face segregation and hardships
                            which follow their social and cultural statuses. As a result, 
                            the Australian government are seeking into 'closing the gap' between the 
                            relationships between them, Indigenous leaders and the common public.
                        </p>
                        <p>
                            Being able to work and have a job with one another is one great way to 
                            start recognising and respecting the aborigines. Our lives are mostly 
                            working for this country and in order to connect with them, having them 
                            beside us is a great way to help both for them and for us. Addressing the 
                            social challenge is a step that we must take; inequality towards them has 
                            been a huge issue within the community, and finding a great way to help 
                            truly connect is something we must all seek to do. In our modern age of 
                            technology, one way of successfully incorporating this would be in the 
                            form of a website, where other Indigenous people can easily access job vacancies. 
                            The website however must be explicit in targeting the Indigenous demographic 
                            in order to separate it from other job hunting websites like Seek and Indeed etc. 
                            On the contrary, the website is designed to help employers, who respect and 
                            recognise the disparity between the Indigenous tribe and normal society, 
                            to recruit new upcoming talent from different cultural backgrounds.
                        </p>
                    </div><br>
            """;
        html = html + "<div class='textLayout'>";
        html = html + "<h1>Who is the Website Intended for?</h1>";
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        ArrayList<Persona> personadb = jdbc.getPersonas();

        html = html + "<form action='/mission.html' method='post'>";
        html = html + "<div class='form-group'>";
        html = html + "<h2> Choose Persona </h2>";
        html = html + "<div class='box'>";
        html = html + "<select id='personas' name='personas'>";
        html = html + "<option value='null'>Select Persona</option>";
        for (Persona name : personadb){
            html = html + "<option value='" + name.getName() + "'>" + name.getName() + "</option>";
        }
        html = html + "</select>";
        html = html + "</div>";
        html = html + "</div><br><br><br><br>";
        html = html + "<button type='submit' class='submit'>Show profile!</button>";
        html = html + "</form>";
        
        String personas = context.formParam("personas");

        ArrayList<personaAttributes> list = jdbc.getAttributes(personas);
        if (personas == null || personas.equals("null")) {
            html = html + "<h2><i>No Results to show</i></h2>";
        } else {
            // If NOT NULL, then lookup the movie by type!
            String path = jdbc.getImagePath(personas);
            html = html + "<h3> " + personas + "</h3>";
            html = html + "<img src='" + path + "' class='personaImage' alt='Persona Image'/>";
            for (personaAttributes attributes: list) {
                html = html + "<h3> " + attributes.attribute + "</h3>";
                html = html + "<p> " + attributes.descriptions_PA + "</p>";
            }
        }
        
        html = html + "</div><br>";

        html = html + """
                <div class='textLayout'>
                    <h1>How to use our Webpage!</h1>
                    <p>
                        Our website is used to find different information regarding specific economic outcomes related to 
                        the Indigenous people of Australia. 
                        It was made to be user friendly. You can use our website by clicking on the tabs
                        on top of the webpage to see what kind of information you are looking for. Our homepage
                        at a glance, allows you to quickly view the 17 socioeconomic outcomes. You can simply
                        do this by just clicking on a a box and the information will drop down.
                    </p>
                </div><br>
                """;

        html = html + """
        <div class='textLayout'>
            <h1> Who we are </h1>
                <p> Here are the individuals who have created this webpage </p>
                <p class='inlineblock'> <b>
                    Nikhil Sanjay<br><br>
                    S3942586
                </p>
                <p class='inlineblock'> 
                    Steven Nguyen<br><br>
                    S3874130 
                </p></b>
        </div>
        """;
        // Close Content div
        html = html + "</div>";
        html = html + "</center>";
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

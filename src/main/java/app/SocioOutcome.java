package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class SocioOutcome {
  
   private int id;
   private String title;
   private String desc;
   

   /**
    * Create an LGA and set the fields
    */
   public SocioOutcome(int id, String title, String desc) {
      this.id = id;
      this.title = title;
      this.desc = desc;
   }

   public int getID() {
      return id;
   }

   public String getTitle() {
      return title;
   }

   public String getDesc() {
      return desc;
   }
}

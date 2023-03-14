package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class Persona {

   private String name;
   private String imgPath;

   /**
    * Create an LGA and set the fields
    */
   public Persona(String name, String imgPath) {
      this.name = name;
      this.imgPath = imgPath;
   }

   public String getName() {
      return name;
   }

   public String getPath() {
      return imgPath;
   }
}

package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class LGA {
   // LGA 2016 Code
   private int code16;

   // LGA 2016 Name
   private String name16;
   private String type16;
   private Float area_sqkm;

   /**
    * Create an LGA and set the fields
    */
   public LGA(int code16, String name16, String type16, Float area_sqkm) {
      this.code16 = code16;
      this.name16 = name16;
      this.type16 = type16;
      this.area_sqkm = area_sqkm;
   }

   public int getCode16() {
      return code16;
   }

   public String getName16() {
      return name16;
   }

   public String getType16() {
      return type16;
   }

   public Float getArea() {
      return area_sqkm;
   }

}

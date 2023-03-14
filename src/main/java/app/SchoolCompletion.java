package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class SchoolCompletion {

   private int code16;
   private String status;
   private String sex;
   private String year;
   private int count;

   public SchoolCompletion(int code16, String status, String sex, String year, int count) {
      this.code16 = code16;
      this.status = status;
      this.sex = sex;
      this.year = year;
      this.count = count;
   }

   public int getCode16() {
      return code16;
   }
   public String getStatus() {
      if (status.equals("indig"))
         return "Indigenous";
      else if (status.equals("non_indig"))
         return "Non-indigenous";
      else
         return "Not Stated";
   }
   public String getSex() {
      if (sex.equals("f"))
         return "Female";
      else
         return "Male";
   }
   public String getYear() {
      if (year.equals("did_not_go_to_school"))
         return "Did not attend School";

      else if (year.equals("y8_below"))
         return "Year 8 or Below";

      else if (year.equals("y9_equivalent"))
         return "Year 9 or Equivalent";

      else if (year.equals("y10_equivalent"))
         return "Year 10 or Equivalent";

      else if (year.equals("y11_equivalent"))
         return "Year 11 or Equivalent";

      else
         return "Year 12 or Equivalent";
   }
   public int getCount() {
      return count;
   }
}

package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class LTHC {

   private int code16;
   private String status;
   private String sex;
   private String condition;
   private int count;

   public LTHC(int code16, String status, String sex, String condition, int count) {
      this.code16 = code16;
      this.status = status;
      this.sex = sex;
      this.condition = condition;
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
   public String getCondition() {
      return condition.substring(0, 1).toUpperCase() + condition.substring(1);
   }
   public int getCount() {
      return count;
   }
}

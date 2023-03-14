package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class PopulationStatistics {

   private int code16;
   private String status;
   private String sex;
   private String age;
   private int count;

   public PopulationStatistics(int code16, String status, String sex, String age, int count) {
      this.code16 = code16;
      this.status = status;
      this.sex = sex;
      this.age = age;
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
   public String getAge() {
      if (age.equals("_0_4"))
         return "0 to 4 years old";

      else if (age.equals("_5_9"))
         return "5 to 10 years old";

      else if (age.equals("_10_14"))
         return "10 to 15 years old";

      else if (age.equals("_15_19"))
         return "15 to 19 years old";

      else if (age.equals("_20_24"))
         return "20 to 24 years old";

      else if (age.equals("_25_29"))
         return "25 to 29 years old";

      else if (age.equals("_30_34"))
         return "30 to 34 years old";

      else if (age.equals("_35_39"))
         return "35 to 39 years old";

      else if (age.equals("_40_44"))
         return "40 to 44 years old";

      else if (age.equals("_45_49"))
         return "45 to 49 years old";

      else if (age.equals("_50_54"))
         return "50 to 54 years old";

      else if (age.equals("_55_59"))
         return "55 to 59 years old";

      else if (age.equals("_60_64"))
         return "60 to 64 years old";

      else
         return "65+ years old";
   }
   public int getCount() {
      return count;
   }
}

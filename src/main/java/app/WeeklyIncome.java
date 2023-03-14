package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class WeeklyIncome {

   private int code16;
   private String status;
   private String sex;
   private String income;
   private int count;

   public WeeklyIncome(int code16, String status, String sex, String income, int count) {
      this.code16 = code16;
      this.status = status;
      this.sex = sex;
      this.income = income;
      this.count = count;
   }

   public int getCode16() {
      return code16;
   }
   public String getStatus() {
      if (status.equals("hhds_with_indig_persons"))
         return "Household with Indigenous Persons";
      else if (status.equals("other_hhds"))
         return "Household without Indigenous Persons";
      else
         return "Total Households in LGA";
   }
   public String getSex() {
      if (sex.equals("f"))
         return "Female";
      else
         return "Male";
   }
   public String getIncome() {
      if (income.equals("1_149"))
         return "$1 and $149";

      else if (income.equals("150_299"))
         return "$150 and $299";

      else if (income.equals("300_399"))
         return "$300 and $399";

      else if (income.equals("400_499"))
         return "$400 and $499";

      else if (income.equals("500_649"))
         return "$500 and $649";

      else if (income.equals("650_799"))
         return "$650 and $799";

      else if (income.equals("800_999"))
         return "$800 and $999";

      else if (income.equals("1000_1249"))
         return "$1000 and $1249";

      else if (income.equals("1250_1499"))
         return "$1250 and $1499";

      else if (income.equals("1500_1749"))
         return "$1500 and $1749";

      else if (income.equals("1750_1999"))
         return "$1750 and $1999";

      else if (income.equals("2000_2499"))
         return "$2000 and $2499";

      else if (income.equals("2500_2999"))
         return "$2500 and $2999";

      else if (income.equals("3000_3499"))
         return "$3000 and $3499";
      //2021
      else if (income.equals("1500_1749"))
         return "$1500 and $1749";

      else if (income.equals("1750_1999"))
         return "$1750 and $1999";

      else if (income.equals("3000_more"))
         return "above $3000";

      // end 2021
      else
         return "above $3500";
   }
   public int getCount() {
      return count;
   }
}

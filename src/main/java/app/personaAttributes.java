package app;

/**
 * Class represeting a Movie from the Movies database
 * For simplicity this uses public fields
 * You could use private fields with getters to be safer
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class personaAttributes {
   // Movie id
   public int id_PA;

   // Movie name
   public String persona_name;

   // Year the movie was made
   public String attribute;

   // String representing the movie genre
   public String descriptions_PA;

   /**
    * Create a movie withou setting any of the fields,
    * Fields will have default "empty" values
    */
   public personaAttributes() {
   }

   /**
    * Create a movie setting all of the fields
    */
   public personaAttributes(int id_PA, String persona_name, String attribute, String descriptions_PA) {
      this.id_PA = id_PA;
      this.persona_name = persona_name;
      this.attribute = attribute;
      this.descriptions_PA = descriptions_PA;
   }
}

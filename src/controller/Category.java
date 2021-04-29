package controller;

public enum Category {
   Red("\033[0;31m"), // enum constant which represents the red colour category
   White("\033[0;0m"), // enum constant which represents the white colour category
   Blue("\033[0;34m"), // enum constant which represents the blue colour category
   Purple("\033[0;35m"), // enum constant which represents the purple colour category
   Yellow("\033[0;33m"), // enum constant which represents the yellow colour category
   Green("\033[0;32m"); // enum constant which represents the green colour category

   private final String colour; // creates a private instance string called colour

   // creates a constructor for the category enum
   Category(String c) {
      this.colour = c; // sets the current instance of colour as the parameters instance
   }

   // getter method for colour
   public String getColour() {
      return colour; // returns colour as a string
   }
}

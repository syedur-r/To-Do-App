package controller;

import java.awt.*;

public enum Category {
   Red(Color.RED), // enum constant which represents the red colour category
   White(Color.GRAY), // enum constant which represents the white colour category
   Blue(new Color(0, 128, 255)), // enum constant which represents the blue colour category
   Purple(new Color(128, 0, 128)), // enum constant which represents the purple colour category
   Yellow(new Color(230, 191, 0)), // enum constant which represents the yellow colour category
   Green(new Color(39, 144, 39)); // enum constant which represents the green colour category

   private final Color colour; // creates a private instance string called colour

   // creates a constructor for the category enum
   Category(Color c) {
      this.colour = c; // sets the current instance of colour as the parameters instance
   }

   // getter method for colour
   public Color getColour() {
      return colour; // returns colour as a string
   }
}

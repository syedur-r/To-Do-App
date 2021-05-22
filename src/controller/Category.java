package controller;
import java.awt.*;

public enum Category {
   Red(Color.RED), // enum constant which represents the red colour category
   White(Color.WHITE), // enum constant which represents the white colour category
   Blue(Color.BLUE), // enum constant which represents the blue colour category
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

   // this method returns all the category options as a string array
   public static String[] getAllCategories() {
      String[] categories = new String[Category.values().length + 1]; // initialises the string array to the size of the number of values in Category + 1
      String[] finalCategories = new String[categories.length]; // initialises a string array to store the final categories

      for (int i = 0; i < Category.values().length; i++) { // iterates through the category values
         categories[i] = String.valueOf(Category.values()[i]); // assigns each category into the array
      }
      categories[categories.length - 1] = "Please select a category"; // adds another element to the end of the categories array
      // the finalCategories copies all the elements from the categories array
      System.arraycopy(categories, 0, finalCategories, 1, categories.length - 1);
      finalCategories[0] = categories[categories.length - 1]; // sets the last element as the first element
      return finalCategories; // returns the finalCategories string with all the categories
   }
}

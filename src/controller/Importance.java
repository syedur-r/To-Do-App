package controller;

public enum Importance {
   Low, // enum constant which represents low importance task
   Normal, // enum constant which represents normal importance task
   High; // enum constant which represents high importance task

   // this method returns all the importance options as a string array
   public static String[] getAllImportance() {
      String[] importance = new String[Importance.values().length + 1]; // initialises the string array to the size of the number of values in Importance + 1
      String[] finalImportance = new String[importance.length]; // initialises a string array to store the final importance

      for (int i = 0; i < Importance.values().length; i++) { // iterates through the importance values
         importance[i] = String.valueOf(Importance.values()[i]); // assigns each importance into the array
      }
      importance[importance.length - 1] = "Please select an importance"; // adds another element to the end of the importance array
      // the finalImportance copies all the elements from the importance array
      System.arraycopy(importance, 0, finalImportance, 1, importance.length - 1);
      finalImportance[0] = importance[importance.length - 1]; // sets the last element as the first element
      return finalImportance; // returns the finalImportance string with all the importance options
   }
}

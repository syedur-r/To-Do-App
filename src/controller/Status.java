package controller;

public enum Status {
   Pending, // enum constant which represents a pending task
   Started, // enum constant which represents a started task
   Partial, // enum constant which represents a partially completed task
   Completed; // enum constant which represents a completed task

   // this method returns all the status options as a string array
   public static String[] getAllStatuses() {
      String[] status = new String[Status.values().length + 1]; // initialises the string array to the size of the number of values in Status + 1
      String[] finalStatus = new String[status.length]; // initialises a string array to store the final status

      for (int i = 0; i < Status.values().length; i++) { // iterates through the status values
         status[i] = String.valueOf(Status.values()[i]); // assigns each status into the array
      }
      status[status.length - 1] = "Please select a status"; // adds another element to the end of the status array
      // the finalStatus copies all the elements from the status array
      System.arraycopy(status, 0, finalStatus, 1, status.length - 1);
      finalStatus[0] = status[status.length - 1]; // sets the last element as the first element
      return finalStatus; // returns the finalStatus string with all the status options
   }
}

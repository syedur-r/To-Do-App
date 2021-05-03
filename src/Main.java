import model.TodoDB;
import view.GUI;
import javax.swing.*;

public class Main {
   public static void main(String[] args) {
      TodoDB dataModel = new TodoDB(); // creates an instance of the todoDB class
      if (!dataModel.isConnected()) { // checks if a database connection hasn't been established
         System.out.println("Can't connect to the database"); // outputs a message saying there is no database connection
         return; // breaks out of the main method
      }
      dataModel.createTable(); // executes the create table command, to create the database if it doesn't exist
      dataModel.closeConnection(); // closes the database connection
      SwingUtilities.invokeLater(GUI::new); // executes the GUI class as a new runnable
   }
}

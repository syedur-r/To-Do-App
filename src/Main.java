import model.TodoDB;
import view.GUI;
import javax.swing.*;

public class Main {
   public static void main(String[] args) {
      TodoDB dataModel = new TodoDB();
      if (!dataModel.openConnection()) {
         System.out.println("Can't connect to the database");
         return;
      }
      dataModel.createTable();
      dataModel.closeConnection();
      SwingUtilities.invokeLater(GUI::new);
   }
}

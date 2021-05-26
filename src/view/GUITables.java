package view;

import controller.Category;
import model.Todo;
import model.TodoDB;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class GUITables {
   // this method creates the jTable
   public static JTable createTodoTable(Font font, DefaultTableModel todoTableModel) {
      JTable todoTable = new JTable(todoTableModel) { // creates a new JTable object, passing in the DefaultTableModel parameter
         @Override // overrides the isCellEditable method, to disable editing of the table
         public boolean isCellEditable(int row, int column) {
            return false; // disables editing of cells
         }

         // overriding prepareRenderer to change the colour of the cell based on the category selected by the user
//         @Override
//         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//            // stores the table to be rendered, the row to be rendered, and the column to be rendered
//            Component comp = super.prepareRenderer(renderer, row, column); // this will be used to render the state of the cell
//            Object value = getModel().getValueAt(row, column); // stores the value of a specific cell in the todoTable
//            Category red = Category.Red; // stores the red category enum
//            Category white = Category.White; // stores the white category enum
//            Category blue = Category.Blue; // stores the blue category enum
//            Category purple = Category.Purple; // stores the purple category enum
//            Category yellow = Category.Yellow; // stores the yellow category enum
//            Category green = Category.Green; // stores the green category enum
//
//            if (value.equals(red)) { // checks if the cell value contains the string Red
//               comp.setForeground(Color.WHITE); // sets the foreground colour of the cell as white
//               comp.setBackground(red.getColour()); // sets the background colour of the cell as red
//            } else if (value.equals(white)) { // checks if the cell value contains the string White
//               comp.setForeground(Color.WHITE); // sets the foreground colour of the cell as white
//               comp.setBackground(white.getColour()); // sets the background colour of the cell as white (grey)
//            } else if (value.equals(blue)) { // checks if the cell value contains the string Blue
//               comp.setForeground(Color.WHITE); // sets the foreground colour of the cell as white
//               comp.setBackground(blue.getColour()); // sets the background colour of the cell as blue
//            } else if (value.equals(purple)) { // checks if the cell value contains the string Purple
//               comp.setForeground(Color.WHITE); // sets the foreground colour of the cell as white
//               comp.setBackground(purple.getColour()); // sets the background colour of the cell as purple
//            } else if (value.equals(yellow)) { // checks if the cell value contains the string Yellow
//               comp.setForeground(Color.WHITE); // sets the foreground colour of the cell as white
//               comp.setBackground(yellow.getColour()); // sets the background colour of the cell as yellow
//            } else if (value.equals(green)) { // checks if the cell value contains the string Green
//               comp.setForeground(Color.WHITE); // sets the foreground colour of the cell as white
//               comp.setBackground(green.getColour()); // sets the background colour of the cell as green
//            } else { // otherwise if the cell doesn't contain neither values
//               comp.setForeground(Color.BLACK); // sets the foreground colour of the cell as black
//               comp.setBackground(Color.WHITE); // sets the background colour of the cell as white
//            }
//            return comp; // returns the component
//         }

         // overriding prepareRenderer to change the colour of the row based on the category selected by the user
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column); // this will be used to render the state of the row
            String colourVal = (this.getModel().getValueAt(row, 3)).toString(); // stores the value of a row containing a colour value
            Category red = Category.Red; // stores the red category enum
            Category white = Category.White; // stores the white category enum
            Category blue = Category.Blue; // stores the blue category enum
            Category purple = Category.Purple; // stores the purple category enum
            Category yellow = Category.Yellow; // stores the yellow category enum
            Category green = Category.Green; // stores the green category enum

            switch (colourVal) { // switch statement used to switch between different colours
               case "Red" -> { // checks if the row value contains the string Red
                  comp.setForeground(white.getColour()); // sets the foreground colour of the row as white
                  comp.setBackground(red.getColour()); // sets the background colour of the row as red
               }
               case "White" -> { // checks if the row value contains the string White
                  comp.setForeground(Color.BLACK); // sets the foreground colour of the row as black
                  comp.setBackground(white.getColour()); // sets the background colour of the row as white
               }
               case "Blue" -> { // checks if the row value contains the string Blue
                  comp.setForeground(white.getColour()); // sets the foreground colour of the row as white
                  comp.setBackground(blue.getColour()); // sets the background colour of the row as blue
               }
               case "Purple" -> { // checks if the row value contains the string Purple
                  comp.setForeground(white.getColour()); // sets the foreground colour of the row as white
                  comp.setBackground(purple.getColour()); // sets the background colour of the row as purple
               }
               case "Yellow" -> { // checks if the row value contains the string Yellow
                  comp.setForeground(white.getColour()); // sets the foreground colour of the row as white
                  comp.setBackground(yellow.getColour()); // sets the background colour of the row as yellow
               }
               case "Green" -> { // checks if the row value contains the string Green
                  comp.setForeground(white.getColour()); // sets the foreground colour of the row as white
                  comp.setBackground(green.getColour()); // sets the background colour of the row as green
               }
            }
            return comp;
         }
      };

      TableColumn id = todoTable.getColumnModel().getColumn(0); // gets the ID column from the table
      TableColumn text = todoTable.getColumnModel().getColumn(1); // gets the ID column from the table
      id.setMaxWidth(40); // sets the maximum width of the ID column as 40
      id.setResizable(false); // doesn't allow the user to resize the ID column
      text.setMinWidth(250); // sets the minimum width of the text column as 250

      // customising the columns of the jTable
      todoTable.setEnabled(false); // disables the ability to make selections or type on the todoTable
      todoTable.getTableHeader().setFont(font.deriveFont(20f)); // sets the table headers font style as the font from the parameter to a size of 20
      todoTable.setFont(font.deriveFont(15f)); // sets the font style of the rows as the font from the parameter to a size of 15
      todoTable.getTableHeader().setReorderingAllowed(false); // doesn't allows the user to reorder the columns of the todoTable

      // customising the rows of the jTable
      todoTable.setRowHeight(30); // sets the height of all the rows in the table as 30
      todoTable.setBackground(Color.WHITE); // sets the background colour of all the rows as white

      // aligning the cells the in the record to the center
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); // creates a defeaultTableCellRenderer to render the state of cells
      centerRenderer.setHorizontalAlignment(JLabel.CENTER); // sets the alignment of all the cells to the center
      for (int i = 0; i < todoTable.getColumnModel().getColumnCount(); i++) { // iterates through the number of columns in the table
         todoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // aligns all the header cells to the center
      }

      // aligning the headers of the columns to the center
      JTableHeader header = todoTable.getTableHeader(); // stores the header of the JTable
      header.setBackground(new Color(133,147,152)); // sets the background colour of the table header as light grey
      header.setForeground(Color.WHITE); // sets the foreground colour of the table header as white
      header.setPreferredSize(new Dimension(100, 30)); // sets the preferred size of the header as 100x30
      // stores the cell renderer of the table header cells
      DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) todoTable.getTableHeader().getDefaultRenderer();
      renderer.setHorizontalAlignment(SwingConstants.CENTER); // aligns the header cells to the center
      return todoTable; // returns the todoTable as a JTable
   }

   // retrieves all the records from the database and stores it inside an object to be displayed on the jTable
   public static void getTodoRows(DefaultTableModel todoTableModel) {
      Object[] rowData = new Object[6]; // initialises a new object to store the row date, the the length of the number of columns in the table
      TodoDB dataSource = new TodoDB(); // creates a new instance of the TodoDB class
      if (!dataSource.isConnected()) { // checks if the instance is not connected to the database
         System.out.println("To-Do Database Connection Failed!"); // if it isn't, an error message will be displayed
         return; // exits the method
      }

      ArrayList<Todo> todoList = dataSource.queryTodoList(); // stores all the rows from the database, inside an arraylist of type Todo called todoList
      if(todoList == null) { // checks if the todoList is empty
         System.out.println("Your To-Do List is Empty!"); // if it is, a message will appear saying that the users to-do list is empty
         return; // exits the method
      }

      for (Todo todo : todoList) { // iterates through the todoList arraylist
         rowData[0] = todo.getTaskID(); // sets the first index of the row as the ID number of the to-do row
         rowData[1] = todo.getText(); // sets the second index of the row as the text of the to-do row
         rowData[2] = todo.getDue(); // sets the third index of the row as the due date of the to-do row
         rowData[3] = todo.getCat(); // sets the fourth index of the row as the category of the to-do row
         rowData[4] = todo.getImportance(); // sets the fifth index of the row as the importance of the to-do row
         rowData[5] = todo.getCompletion(); // sets the sixth index of the row as the status of the to-do row
         todoTableModel.addRow(rowData); // adds the row onto the todoTable
      }
      dataSource.closeConnection(); // closes the database connection
   }
<<<<<<< HEAD

   // retrieves all the records from the database and stores it inside an object to be displayed on the jTable
   public static void getSearchedRows(DefaultTableModel todoTableModel, String todoText) {
      Object[] rowData = new Object[6]; // initialises a new object to store the row date, the the length of the number of columns in the table
      TodoDB dataSource = new TodoDB(); // creates a new instance of the TodoDB class
      if (!dataSource.isConnected()) { // checks if the instance is not connected to the database
         System.out.println("To-Do Database Connection Failed!"); // if it isn't, an error message will be displayed
         return; // exits the method
      }

      ArrayList<Todo> todoList = dataSource.querySearchTodo(todoText); // stores all the searched rows from the database, inside an arraylist of type Todo called todoList
      if(todoList == null) { // checks if the todoList is empty
         System.out.println("Your To-Do List is Empty!"); // if it is, a message will appear saying that the users to-do list is empty
         return; // exits the method
      }

      for (Todo todo : todoList) { // iterates through the todoList arraylist
         rowData[0] = todo.getTaskID(); // sets the first index of the row as the ID number of the to-do row
         rowData[1] = todo.getText(); // sets the second index of the row as the text of the to-do row
         rowData[2] = todo.getDue(); // sets the third index of the row as the due date of the to-do row
         rowData[3] = todo.getCat(); // sets the fourth index of the row as the category of the to-do row
         rowData[4] = todo.getImportance(); // sets the fifth index of the row as the importance of the to-do row
         rowData[5] = todo.getCompletion(); // sets the sixth index of the row as the status of the to-do row
         todoTableModel.addRow(rowData); // adds the row onto the todoTable
      }
      dataSource.closeConnection(); // closes the database connection
   }
}
=======
}
>>>>>>> 62e6a9cb8a166fe0ac6acdcdb9208c197ec13b00

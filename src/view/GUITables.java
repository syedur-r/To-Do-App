package view;

import controller.Category;
import model.Todo;
import model.TodoDB;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class GUITables {
   // creates the jTable
   public static JTable createTodoTable(Font font, DefaultTableModel todoTableModel) {
      JTable todoTable = new JTable(todoTableModel) {
         @Override
         public boolean isCellEditable(int row, int column) {
            return false;
         }

         // overriding prepareRenderer to change the colour of the cell based on the category entered by the user
         @Override
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            Object value = getModel().getValueAt(row, column);
            Category red = Category.Red;
            Category white = Category.White;
            Category blue = Category.Blue;
            Category purple = Category.Purple;
            Category yellow = Category.Yellow;
            Category green = Category.Green;

            if (value.equals(red)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(red.getColour());
            } else if (value.equals(white)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(white.getColour());
            } else if (value.equals(blue)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(blue.getColour());
            } else if (value.equals(purple)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(purple.getColour());
            } else if (value.equals(yellow)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(yellow.getColour());
            } else if (value.equals(green)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(green.getColour());
            } else {
               comp.setForeground(Color.BLACK);
               comp.setBackground(Color.WHITE);
            }
            return comp;
         }
      };

      TableColumn id = todoTable.getColumnModel().getColumn(0); // gets the ID column from the table
      id.setMaxWidth(40); // sets the width of the ID column as 40
      id.setResizable(false); // doesn't allow the user to resize the ID column

      // customising the columns of the jTable
      todoTable.setEnabled(false);
      todoTable.getTableHeader().setFont(font.deriveFont(20f));
      todoTable.setFont(font.deriveFont(15f));
      todoTable.getTableHeader().setReorderingAllowed(false);

      // customising the rows of the jTable
      todoTable.setRowHeight(30);
      todoTable.setBackground(Color.WHITE);

      // aligning the cells the in the record to the center
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      for (int i = 0; i < todoTable.getColumnModel().getColumnCount(); i++) {
         todoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
      }

      // aligning the headers of the columns to the center
      JTableHeader header = todoTable.getTableHeader();
      header.setBackground(new Color(133,147,152));
      header.setForeground(Color.WHITE);
      header.setPreferredSize(new Dimension(100, 30));
      DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) todoTable.getTableHeader().getDefaultRenderer();
      renderer.setHorizontalAlignment(SwingConstants.CENTER);
      return todoTable;
   }

   // retrieves all the records from the database and stores it inside an object to be displayed on the jTable
   public static void getTodoRows(DefaultTableModel todoTableModel) {
      Object[] rowData = new Object[6];
      TodoDB dataSource = new TodoDB();
      if (!dataSource.isConnected()) {
         System.out.println("To-Do Database Connection Failed!");
         return;
      }

      ArrayList<Todo> todoList = dataSource.queryTodoList();
      if(todoList == null) {
         System.out.println("Your To-Do List is Empty!");
         return;
      }

      for (Todo todo : todoList) {
         rowData[0] = todo.getTaskID();
         rowData[1] = todo.getText();
         rowData[2] = todo.getDue();
         rowData[3] = todo.getCat();
         rowData[4] = todo.getImportance();
         rowData[5] = todo.getCompletion();
         todoTableModel.addRow(rowData);
      }
      dataSource.closeConnection();
   }
}
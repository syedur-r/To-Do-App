package view;
import controller.Category;
import model.Todo;
import model.TodoDB;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class GUIHelpers {

   public static String[] setBackgroundImage() {
      return new String[] {
              "images/todo-bg.png",
              "images/todo-bg2.png",
              "images/todo-bg3.png",
              "images/todo-bg4.png",
              "images/todo-bg5.png",
              "images/todo-bg6.png",
              "images/todo-bg7.png",
              "images/todo-bg8.png",
              "images/todo-bg9.png",
              "images/todo-bg10.png"
      };
   }

   public static String[] setQuote() {
      return new String[] {
              "“The secret to getting things done is to act!” - Dante Alighieri",
              "“You can do anything, but not everything.” - David Allen",
              "“Without hustle, talent will only carry you so far.” - Gary Vaynerchuk",
              "“The dream is free. The hustle is sold separately.” - Steve Harvey",
              "“Get things done: Think big but start small.” - Oumar Dieng",
      };
   }

   public static String getCurrentDate() {
      LocalDateTime date = LocalDateTime.now();
      return date.getDayOfWeek().toString().substring(0, 1).toUpperCase() +
              date.getDayOfWeek().toString().substring(1).toLowerCase() + ", " +
              date.getDayOfMonth() + " " +
              date.getMonth().toString().substring(0, 1).toUpperCase() +
              date.getMonth().toString().substring(1).toLowerCase() + " " + date.getYear();
   }

   // sets the greeting message based on the time of the day
   public static String setGreetingMessage() {
      String message;
      Calendar date = Calendar.getInstance();
      int hour = date.get(Calendar.HOUR_OF_DAY);

      if (hour <= 6) {
         message = "Good Morning,";
      } else if (hour <= 17) {
         message = "Good Afternoon,";
      } else {
         message = "Good Evening,";
      }
      return message;
   }

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

      // customising the columns of the jTable
      TableColumn id = todoTable.getColumnModel().getColumn(0);
      todoTable.setEnabled(false);
      todoTable.getTableHeader().setFont(font.deriveFont(20f));
      todoTable.setFont(font.deriveFont(15f));
      todoTable.getTableHeader().setReorderingAllowed(false);

      // customising the rows of the jTable
      todoTable.setRowHeight(30);
      todoTable.setBackground(Color.WHITE);
      id.setMaxWidth(40);
      id.setResizable(false);

      // aligning the cells the in the record to the center
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      for (int i = 0; i < 6; i++) {
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
      if (!dataSource.openConnection()) {
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

   // sets the coordinates of the grid constraints in the grid layout
   public static void setGridCoord(GridBagConstraints gridConst, int x, int y, JPanel jPanel, JComponent component) {
      gridConst.gridx = x;
      gridConst.gridy = y;
      jPanel.add(component, gridConst);
   }

   // displays a panel for a specific page each time the user navigates to it, by pressing its button
   public static void displayPanel(JLabel title, String text, JPanel todoTable, boolean state1, JPanel homeName, boolean state2, JPanel homeText, boolean state3,
                                   JPanel horizontalLine, boolean state4, JPanel quote, boolean state5, JPanel addInput, boolean state6, JPanel updateInput,
                                   boolean state7, JPanel deleteInput, boolean state8, JPanel addBtnPanel, boolean state9, JPanel updateBtnPanel, boolean state10,
                                   JPanel deleteBtnPanel, boolean state11) {
      title.setText(text);
      todoTable.setVisible(state1);
      homeName.setVisible(state2);
      homeText.setVisible(state3);
      horizontalLine.setVisible(state4);
      quote.setVisible(state5);
      addInput.setVisible(state6);
      updateInput.setVisible(state7);
      deleteInput.setVisible(state8);
      addBtnPanel.setVisible(state9);
      updateBtnPanel.setVisible(state10);
      deleteBtnPanel.setVisible(state11);
   }

   // enables all the components in the update panel
   public static void enableUpdateInputs(JComponent text, JComponent dueDate, JComponent dueTime,
                                         JComponent category, JComponent importance,
                                         JComponent status, JComponent updateBtn) {
      text.setEnabled(true);
      dueDate.setEnabled(true);
      dueTime.setEnabled(true);
      category.setEnabled(true);
      importance.setEnabled(true);
      status.setEnabled(true);
      updateBtn.setEnabled(true);
   }

   // disables all the components in the update panel
   public static void disableUpdateInputs(JComponent text, JComponent dueDate, JComponent dueTime,
                                          JComponent category, JComponent importance,
                                          JComponent status, JComponent updateBtn) {
      text.setEnabled(false);
      dueDate.setEnabled(false);
      dueTime.setEnabled(false);
      category.setEnabled(false);
      importance.setEnabled(false);
      status.setEnabled(false);
      updateBtn.setEnabled(false);
   }

   // clears all the components in the update panel
   public static void clearUpdateInputs(JTextField id, String idText, JTextField text,
                                        JXDatePicker dueDate, JComboBox<String> category,
                                        JComboBox<String> importance, JComboBox<String> status) {
      id.setText(idText);
      text.setText("");
      dueDate.getEditor().setText("");
      category.setSelectedIndex(0);
      importance.setSelectedIndex(0);
      status.setSelectedIndex(0);
   }

   // checks if a localDateTime format is valid
   public static boolean isValid(String dateTime) {
      try {
         LocalDateTime.parse(dateTime);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
}

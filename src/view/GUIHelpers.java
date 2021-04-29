package view;
import controller.Category;
import model.Todo;
import model.TodoDB;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class GUIHelpers {

   public static String greetingMessage() {
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

            if (value.equals(Category.Red)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(Color.RED);
            } else if (value.equals(Category.White)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(Color.GRAY);
            } else if (value.equals(Category.Blue)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(new Color(0, 128, 255));
            } else if (value.equals(Category.Purple)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(new Color(128, 0, 128));
            } else if (value.equals(Category.Yellow)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(new Color(230, 191, 0));
            } else if (value.equals(Category.Green)) {
               comp.setForeground(Color.WHITE);
               comp.setBackground(new Color(39, 144, 39));
            } else {
               comp.setForeground(Color.BLACK);
               comp.setBackground(Color.WHITE);
            }
            return comp;
         }
      };
      TableColumn id = todoTable.getColumnModel().getColumn(0);

      todoTable.setEnabled(false);
      todoTable.getTableHeader().setFont(font.deriveFont(20f));
      todoTable.setFont(font.deriveFont(15f));
      todoTable.getTableHeader().setReorderingAllowed(false);

      todoTable.setRowHeight(30);
      todoTable.setBackground(Color.WHITE);
      id.setMaxWidth(40);
      id.setResizable(false);

      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      for (int i = 0; i < 6; i++) {
         todoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
      }
      JTableHeader header = todoTable.getTableHeader();
      header.setBackground(new Color(133,147,152));
      header.setForeground(Color.WHITE);
      header.setPreferredSize(new Dimension(100, 30));
      DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) todoTable.getTableHeader().getDefaultRenderer();
      renderer.setHorizontalAlignment(SwingConstants.CENTER);
      return todoTable;
   }

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

   public static void addToGrid(GridBagConstraints gridConst, int x, int y, JPanel jPanel, JComponent component) {
      gridConst.gridx = x;
      gridConst.gridy = y;
      jPanel.add(component, gridConst);
   }

//   public static void displayPanel(JLabel title, String text , JPanel todoTable, JPanel homeName, JPanel homeText, JPanel horizontalLine, JPanel quote,
//                                   JPanel addInput, JPanel updateInput, JPanel deleteInput, JPanel addBtnPanel, JPanel updateBtnPanel, JPanel deleteBtnPanel) {
//      title.setText(text);
//      todoTable.setVisible(false);
//      homeName.setVisible(false);
//      homeText.setVisible(false);
//      horizontalLine.setVisible(false);
//      quote.setVisible(false);
//      addInput.setVisible(false);
//      updateInput.setVisible(true);
//      deleteInput.setVisible(false);
//      addBtnPanel.setVisible(false);
//      updateBtnPanel.setVisible(true);
//      deleteBtnPanel.setVisible(false);
//   }

   public static void enableUpdateInputs(JComponent text, JComponent dueDate, JComponent dueTime, JComponent category, JComponent importance, JComponent status, JComponent updateBtn) {
      text.setEnabled(true);
      dueDate.setEnabled(true);
      dueTime.setEnabled(true);
      category.setEnabled(true);
      importance.setEnabled(true);
      status.setEnabled(true);
      updateBtn.setEnabled(true);
   }

   public static void disableUpdateInputs(JComponent text, JComponent dueDate, JComponent dueTime, JComponent category, JComponent importance, JComponent status, JComponent updateBtn) {
      text.setEnabled(false);
      dueDate.setEnabled(false);
      dueTime.setEnabled(false);
      category.setEnabled(false);
      importance.setEnabled(false);
      status.setEnabled(false);
      updateBtn.setEnabled(false);
   }

   public static boolean isValid(String dateTime) {
      try {
         LocalDateTime.parse(dateTime);
         return true;
      } catch (Exception e) {
         return false;
      }
   }
}

package view;
import model.TodoDB;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ActionEvents {
   public static void addTaskPerformed(JFrame frame, JTextField txtTextAdd, JXDatePicker dtDueDateAdd, JSpinner spDueTimeAdd,
                                       JComboBox<String> cmbCategoryAdd, JComboBox<String> cmbImportanceAdd,
                                       DefaultTableModel todoTableModel, Font montserrat) {
      try {
         String todoText = txtTextAdd.getText();
         String todoDueDate = new SimpleDateFormat("yyyy-MM-dd").format(dtDueDateAdd.getDate());
         String todoDueTime = new SimpleDateFormat("HH:mm").format(spDueTimeAdd.getValue());
         String todoCategory = cmbCategoryAdd.getItemAt(cmbCategoryAdd.getSelectedIndex());
         String todoImportance = cmbImportanceAdd.getItemAt(cmbImportanceAdd.getSelectedIndex());

         TodoDB dataSource = new TodoDB();
         if (!dataSource.isConnected()) {
            System.out.println("Can't connect to the database");
            return;
         } else if (!GUIHelpers.isDateTimeValid(todoDueDate + "T" + todoDueTime)) {
            JOptionPane.showMessageDialog(frame, "Please Select a Valid Date and Time","Error", JOptionPane.ERROR_MESSAGE);
            return;
         } else if (GUIHelpers.isDateTimeValid(todoDueDate + "T" + todoDueTime) ||
                 !todoCategory.equals("Please select a category") ||
                 !todoImportance.equals("Please select an importance")) {
            dataSource.insertTodo(todoText, todoDueDate + "T" + todoDueTime, todoCategory, todoImportance);
         } else {
            JOptionPane.showMessageDialog(frame, "Please Select all Drop-Down Boxes","Error", JOptionPane.ERROR_MESSAGE);
            return;
         }
         dataSource.closeConnection();

         todoTableModel.setRowCount(0);
         GUITables.getTodoRows(todoTableModel);
         txtTextAdd.setText("");
         dtDueDateAdd.getEditor().setText("");
         GUIStyles.setSpinnerStyle(spDueTimeAdd, montserrat);
         cmbCategoryAdd.setSelectedIndex(0);
         cmbImportanceAdd.setSelectedIndex(0);
         JOptionPane.showMessageDialog(frame, "Task Has Successfully Been Added!", "Successful", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception exception) {
         JOptionPane.showMessageDialog(frame, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   public static void updateTaskPerformed(JFrame frame, JTextField txtID, JTextField txtTextUpdate, JXDatePicker dtDueDateUpdate,
                                          JSpinner spDueTimeUpdate, JComboBox<String> cmbCategoryUpdate, JComboBox<String> cmbImportanceUpdate,
                                          JComboBox<String> cmbStatus, JComponent updateTask, DefaultTableModel todoTableModel, Font montserrat) {
      try {
         TodoDB dataSource = new TodoDB();
         if (!dataSource.isConnected()) {
            System.out.println("Can't connect to the database");
            return;
         }
         if (dataSource.getTodoCount() == 0) {
            JOptionPane.showMessageDialog(frame,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
         } else {
            int getTodoIndex = Integer.parseInt(txtID.getText());
            String todoText = txtTextUpdate.getText();
            String todoDueDate = new SimpleDateFormat("yyyy-MM-dd").format(dtDueDateUpdate.getDate());
            String todoDueTime = new SimpleDateFormat("HH:mm").format(spDueTimeUpdate.getValue());
            String todoCategory = cmbCategoryUpdate.getItemAt(cmbCategoryUpdate.getSelectedIndex());
            String todoImportance = cmbImportanceUpdate.getItemAt(cmbImportanceUpdate.getSelectedIndex());
            String todoStatus = cmbStatus.getItemAt(cmbStatus.getSelectedIndex());

            if (txtID.getText().equals("Enter a To-Do ID to Update")) {
               JOptionPane.showMessageDialog(frame, "Please Enter a Valid To-Do ID","Error", JOptionPane.ERROR_MESSAGE);
               return;
            } else if (!GUIHelpers.isDateTimeValid(todoDueDate + "T" + todoDueTime)) {
               JOptionPane.showMessageDialog(frame, "Please Select a Valid Date and Time", "Error", JOptionPane.ERROR_MESSAGE);
               return;
            } else if (todoCategory.equals("Please select a category") ||
                    todoImportance.equals("Please select an importance") ||
                    todoStatus.equals("Please select a status")) {
               JOptionPane.showMessageDialog(frame, "Please Select all Drop-Down Boxes","Error", JOptionPane.ERROR_MESSAGE);
               return;
            }

            dataSource.updateTodo(TodoDB.TODO_NAME, todoText, getTodoIndex);
            dataSource.updateTodo(TodoDB.TODO_DUE_DATE, todoDueDate + "T" + todoDueTime, getTodoIndex);
            dataSource.updateTodo(TodoDB.TODO_CATEGORY, todoCategory, getTodoIndex);
            dataSource.updateTodo(TodoDB.TODO_IMPORTANCE, todoImportance, getTodoIndex);
            dataSource.updateTodo(TodoDB.TODO_STATUS, todoStatus, getTodoIndex);
            dataSource.closeConnection();

            todoTableModel.setRowCount(0);
            GUITables.getTodoRows(todoTableModel);
            GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat);
            GUIHelpers.clearUpdateInputs(txtID, "Enter a To-Do ID to Update", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
            GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
            JOptionPane.showMessageDialog(frame, "Task Has Successfully Been Updated!", "Successful", JOptionPane.INFORMATION_MESSAGE);
         }
      }  catch (Exception exception) {
         JOptionPane.showMessageDialog(frame, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
         exception.printStackTrace();
      }
   }

   public static void deleteTaskPerformed(JFrame frame, JTextField txtDeleteId, DefaultTableModel todoTableModel) {
      TodoDB dataSource = new TodoDB();
      if (!dataSource.isConnected()) {
         System.out.println("Can't connect to the database");
         return;
      }
      if (dataSource.getTodoCount() == 0) {
         JOptionPane.showMessageDialog(frame,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
         txtDeleteId.setText("Enter a To-Do ID");
      } else {
         int todoIndex = -1;
         int confirmationBox;
         ArrayList<Integer> recordID = dataSource.getAllTodoID();

         while (todoIndex < 0 || !recordID.contains(todoIndex)) {
            try {
               todoIndex = Integer.parseInt(txtDeleteId.getText());
               if (todoIndex > 0 && recordID.contains(todoIndex)) {
                  confirmationBox = JOptionPane.showConfirmDialog(frame, "Are You Sure You Wish To Delete This Task?", "Delete", JOptionPane.YES_NO_OPTION);
               } else {
                  JOptionPane.showMessageDialog(frame, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                  JOptionPane.showMessageDialog(frame, "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                  txtDeleteId.setText("Enter a To-Do ID");
                  break;
               }

               if(confirmationBox == JOptionPane.YES_OPTION) {
                  dataSource.deleteTodo(todoIndex);
                  dataSource.closeConnection();

                  todoTableModel.setRowCount(0);
                  GUITables.getTodoRows(todoTableModel);
                  txtDeleteId.setText("");
                  JOptionPane.showMessageDialog(frame,"Task Has Successfully Been Deleted!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                  txtDeleteId.setText("Enter a To-Do ID");
                  break;
               }
            } catch (Exception exception) {
               JOptionPane.showMessageDialog(frame, "Please Enter a Valid Number!", "Error", JOptionPane.ERROR_MESSAGE);
               txtDeleteId.setText("Enter a To-Do ID");
               break;
            }
         }
      }
   }
}

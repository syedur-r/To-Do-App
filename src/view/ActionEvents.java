package view;
import model.TodoDB;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActionEvents {
   // this method performs the task of adding a To-Do task to the To-Do list
   public static void addTaskPerformed(JFrame frame, JTextField txtTextAdd, JXDatePicker dtDueDateAdd, JSpinner spDueTimeAdd,
                                       JComboBox<String> cmbCategoryAdd, JComboBox<String> cmbImportanceAdd,
                                       DefaultTableModel todoTableModel, Font montserrat) {
      try { // using try and catch to handle errors gracefully, without breaking the app
         String todoText = txtTextAdd.getText(); // stores the To-Do text inside a string
         String todoDueDate = new SimpleDateFormat("yyyy-MM-dd").format(dtDueDateAdd.getDate()); // stores the Due Date inside a string
         String todoDueTime = new SimpleDateFormat("HH:mm").format(spDueTimeAdd.getValue()); // stores the Due Time inside a string
         String todoCategory = cmbCategoryAdd.getItemAt(cmbCategoryAdd.getSelectedIndex()); // stores the selected category inside a string
         String todoImportance = cmbImportanceAdd.getItemAt(cmbImportanceAdd.getSelectedIndex()); // stores the selected importance inside a string
         String concatDateTime = todoDueDate + "T" + todoDueTime; // concatenates the date and time with T in the middle, so it can be parsed as LocalDateTime

         TodoDB dataSource = new TodoDB(); // creates a new instance of the TodoDB class, to carry out database queries
         if (!dataSource.isConnected()) { // checks if the dataSource is connected to the database or not
            System.out.println("Can't connect to the database"); // if it isn't, an error message will be outputted to the console
            return; // the add task method will terminate after this statement
         } else if (todoText.trim().length() == 0) { // checks if the To-Do text is empty (including whitespaces)
            // if it isn't an error message dialogue will be displayed asking the user to enter a To-Do text
            JOptionPane.showMessageDialog(frame, "Please Enter a To-Do text for the Task","Error", JOptionPane.ERROR_MESSAGE);
            return; // the method will be terminated after this statement
         } else if (!GUIHelpers.isDateTimeValid(concatDateTime)) { // checks if the Due Date and Due Time concatenated, is not a valid LocalDateTime format
            // if it isn't an error message dialogue will be displayed asking the user to select a valid date and time
            JOptionPane.showMessageDialog(frame, "Please Select a Valid Date and Time","Error", JOptionPane.ERROR_MESSAGE);
            return; // the method will be terminated after this statement
         } else if (LocalDateTime.parse(concatDateTime).isBefore(LocalDateTime.now())) { // checks if the Due Date and Due Time concatenated is before current time
            // if it is an error message dialogue will be displayed asking the user to select a current Date and Time in the future
            JOptionPane.showMessageDialog(frame, "Please Select a Date and Time in Present Day or Future","Error", JOptionPane.ERROR_MESSAGE);
            return; // the method will be terminated after this statement
         } else if (todoCategory.equals("Please select a category") || // checks if the category dropdown box is selected as "Please select a category"
                 todoImportance.equals("Please select an importance")) { // checks if the importance dropdown box is selected as "Please select an importance"
            // if either of these conditions are met, a message dialogue box will be displayed, asking the user to a select all drop-down boxes
            JOptionPane.showMessageDialog(frame, "Please Select all Drop-Down Boxes","Error", JOptionPane.ERROR_MESSAGE);
            return; // the add task method will terminate after this statement
         } else {
            // if none of the above conditions are true,
            // the insert query will be executed from the todoDB class. This will store all the input details into the database
            dataSource.insertTodo(todoText, concatDateTime, todoCategory, todoImportance);
         }
         dataSource.closeConnection(); // closes the database connection

         todoTableModel.setRowCount(0); // clears the todoTable, which contains the records of all the To-Do tasks
         GUITables.getTodoRows(todoTableModel); // re-populates the todoTable with the updated To-Do tasks
         txtTextAdd.setText(""); // clears the To-Do text input field
         dtDueDateAdd.getEditor().setText(""); // clears the datePicker (due date) input field
         GUIStyles.setSpinnerStyle(spDueTimeAdd, montserrat); // clears the jSpinner (due time) input field
         cmbCategoryAdd.setSelectedIndex(0); // clears the category drop-down box
         cmbImportanceAdd.setSelectedIndex(0); // clears the importance drop-down box
         // displays a dialogue message stating that the To-Do task has successfully been added to the database
         JOptionPane.showMessageDialog(frame, "Task Has Successfully Been Added!", "Successful", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception exception) { // if the try block fails, the exception will handle the error gracefully
         // displays an error message dialogue box, asking the user to enter valid details
         JOptionPane.showMessageDialog(frame, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   // this method performs the task of updating a To-Do task in the To-Do list
   public static void updateTaskPerformed(JFrame frame, JTextField txtID, JTextField txtTextUpdate, JXDatePicker dtDueDateUpdate,
                                          JSpinner spDueTimeUpdate, JComboBox<String> cmbCategoryUpdate, JComboBox<String> cmbImportanceUpdate,
                                          JComboBox<String> cmbStatus, JComponent updateTask, DefaultTableModel todoTableModel, Font montserrat) {
      try { // using try and catch to handle errors gracefully, without breaking the app
         TodoDB dataSource = new TodoDB(); // creates a new instance of the TodoDB class, to carry out database queries
         if (!dataSource.isConnected()) { // checks if the dataSource is connected to the database or not
            System.out.println("Can't connect to the database"); // if it isn't, an error message will be outputted to the console
            return; // the update task method will terminate after this statement
         }
         if (dataSource.getTodoCount() == 0) {  // checks if the todoList is not empty by getting the number of rows
            // if the row count is 0, it means the todoList is empty. If this is the case, an error message will be displayed saying the To-Do list is empty
            JOptionPane.showMessageDialog(frame,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
         } else { // otherwise, the update task will be proceeded
            int getTodoIndex = Integer.parseInt(txtID.getText()); // stores the to-do ID input inside an integer
            String todoText = txtTextUpdate.getText(); // stores the to-do text inside a string
            String todoDueDate = new SimpleDateFormat("yyyy-MM-dd").format(dtDueDateUpdate.getDate()); // stores the due date inside a string after formatting it into localDate
            String todoDueTime = new SimpleDateFormat("HH:mm").format(spDueTimeUpdate.getValue()); // stores the due time inside a string after formatting it into localTime
            String todoCategory = cmbCategoryUpdate.getItemAt(cmbCategoryUpdate.getSelectedIndex()); // stores the selected category inside a string
            String todoImportance = cmbImportanceUpdate.getItemAt(cmbImportanceUpdate.getSelectedIndex()); // stores the selected importance inside a string
            String todoStatus = cmbStatus.getItemAt(cmbStatus.getSelectedIndex()); // stores the selected status inside a string
            String concatDateTime = todoDueDate + "T" + todoDueTime; // concatenates the date and time with T in the middle, so it can be parsed as LocalDateTime

            if (txtID.getText().equals("Enter a To-Do ID to Update")) { // checks if the to-do ID input does not contain the placeholder
               // if it does, an error message will be displayed asking the user to enter a valid ID
               JOptionPane.showMessageDialog(frame, "Please Enter a Valid To-Do ID","Error", JOptionPane.ERROR_MESSAGE);
               return; // the method will be terminated after this statement
            } else if (todoText.trim().length() == 0) { // checks if the To-Do text is empty (including whitespaces)
               // if it isn't an error message dialogue will be displayed asking the user to enter a To-Do text
               JOptionPane.showMessageDialog(frame, "Please Enter a To-Do text for the Task","Error", JOptionPane.ERROR_MESSAGE);
               return; // the method will be terminated after this statement
            } else if (!GUIHelpers.isDateTimeValid(concatDateTime)) { // checks if the Due Date and Due Time concatenated, is not a valid LocalDateTime format
               // if the Due Date and Due Time concatenated, does not produce a valid LocalDateTime format, an error message will be displayed asking the user
               // to select a valid date and time
               JOptionPane.showMessageDialog(frame, "Please Select a Valid Date and Time", "Error", JOptionPane.ERROR_MESSAGE);
               return; // the method will be terminated after this statement
            } else if (LocalDateTime.parse(concatDateTime).isBefore(LocalDateTime.now())) { // checks if the Due Date and Due Time concatenated is before current time
               // if it is an error message dialogue will be displayed asking the user to select a current Date and Time in the future
               JOptionPane.showMessageDialog(frame, "Please Select a Date and Time in Present Day or Future","Error", JOptionPane.ERROR_MESSAGE);
               return; // the method will be terminated after this statement
            } else if (todoCategory.equals("Please select a category") || // checks if the category dropdown box is selected as "Please select a category"
                    todoImportance.equals("Please select an importance") || // checks if the importance dropdown box is selected as "Please select an importance"
                    todoStatus.equals("Please select a status")) { // checks if the status dropdown box is selected as "Please select a status"
               // if either of these conditions are met, a message dialogue box will be displayed, asking the user to a select all drop-down boxes
               JOptionPane.showMessageDialog(frame, "Please Select all Drop-Down Boxes","Error", JOptionPane.ERROR_MESSAGE);
               return; // the update task method will terminate after this statement
            }

            // if none of the above conditions are true
            dataSource.updateTodo(TodoDB.TODO_NAME, todoText, getTodoIndex); // executes the update query for the To-Do text column in the todoList database
            dataSource.updateTodo(TodoDB.TODO_DUE_DATE, concatDateTime, getTodoIndex); // executes the update query for the To-Do due date in the todoList database
            dataSource.updateTodo(TodoDB.TODO_CATEGORY, todoCategory, getTodoIndex); // executes the update query for the To-Do category in the todoList database
            dataSource.updateTodo(TodoDB.TODO_IMPORTANCE, todoImportance, getTodoIndex); // executes the update query for the To-Do importance in the todoList database
            dataSource.updateTodo(TodoDB.TODO_STATUS, todoStatus, getTodoIndex); // executes the update query for the To-Do status in the todoList database
            dataSource.closeConnection(); // closes the database connection

            todoTableModel.setRowCount(0); // clears the todoTable, which contains the records of all the To-Do tasks
            GUITables.getTodoRows(todoTableModel); // re-populates the todoTable with the updated To-Do tasks
            GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat); // resets the text in the due time textfield
            // clears all the inputs in the update panel
            GUIHelpers.clearUpdateInputs(txtID, "Enter a To-Do ID to Update", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
            // disables all the components in the update panel, until a valid ID is entered
            GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
            // a dialogue message is displayed stating that the To-Do task has successfully been updated in the database
            JOptionPane.showMessageDialog(frame, "Task Has Successfully Been Updated!", "Successful", JOptionPane.INFORMATION_MESSAGE);
         }
      }  catch (Exception exception) { // if the try block fails, the exception will handle the error gracefully
         // displays an error message dialogue box, asking the user to enter valid details
         JOptionPane.showMessageDialog(frame, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   // this method performs the task of deleting a To-Do task from the To-Do list
   public static void deleteTaskPerformed(JFrame frame, JTextField txtDeleteId, DefaultTableModel todoTableModel) {
      TodoDB dataSource = new TodoDB(); // creates a new instance of the TodoDB class, to carry out database queries
      if (!dataSource.isConnected()) { // checks if the dataSource is connected to the database or not
         System.out.println("Can't connect to the database"); // if it isn't, an error message will be outputted to the console
         return; // the delete task method will terminate after this statement
      }
      if (dataSource.getTodoCount() == 0) {  // checks if the todoList is not empty by getting the number of rows
         // if the row count is 0, it means the todoList is empty. If this is the case, an error message will be displayed saying the To-Do list is empty
         JOptionPane.showMessageDialog(frame,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
         txtDeleteId.setText("Enter a To-Do ID"); // resets the ID textbox field to the original placeholder "Enter a To-Do ID"
      } else {
         int todoIndex = -1; // initialises the todoIndex for the to-do task as -1
         int confirmationBox; // stores the confirmation dialogue box inside an integer, to indicate the option the user has selected
         ArrayList<Integer> recordID = dataSource.getAllTodoID(); // an arraylist is created, storing all the record IDs from the database

         // a while loop is used to check if the character entered by the user is a digit,
         // checks if the index is more than 0 and
         // checks whether the index matches any ID in the recordID arraylist
         while (todoIndex < 0 || !recordID.contains(todoIndex)) {
            try { // using try and catch to handle errors gracefully, without breaking the app
               todoIndex = Integer.parseInt(txtDeleteId.getText()); // stores the user input of the ID as an integer inside todoIndex
               if (todoIndex > 0 && recordID.contains(todoIndex)) { // checks if the todoIndex is more than 0 and whether it matches any ID in the recordID arraylist
                  // assigns the confirmationBox to a JOptionPane dialogue box to confirm the user's decision of whether they would like to delete a task
                  confirmationBox = JOptionPane.showConfirmDialog(frame, "Are You Sure You Wish To Delete This Task?", "Delete", JOptionPane.YES_NO_OPTION);
               } else { // if the above condition is not met
                  // an error message dialogue box will be displayed telling the user that the To-Do task doesn't exist
                  JOptionPane.showMessageDialog(frame, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                  // another dialogue box will be displayed asking the user to check the list task page for the to-do ID
                  JOptionPane.showMessageDialog(frame, "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                  txtDeleteId.setText("Enter a To-Do ID"); // resets the ID textbox field to the original placeholder "Enter a To-Do ID"
                  break; // breaks out of the while loop
               }

               if(confirmationBox == JOptionPane.YES_OPTION) { // checks if the user has clicked the yes option
                  dataSource.deleteTodo(todoIndex); // if the user has clicked yes, the delete query will be executed from the todoDB class
                  // this will delete the record containing the given ID, from the database
                  dataSource.closeConnection(); // closes the database connection

                  todoTableModel.setRowCount(0); // clears the todoTable, which contains the records of all the To-Do tasks
                  GUITables.getTodoRows(todoTableModel); // re-populates the todoTable with the updated To-Do tasks
                  txtDeleteId.setText(""); // clears the ID textbox field
                  JOptionPane.showMessageDialog(frame,"Task Has Successfully Been Deleted!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                  txtDeleteId.setText("Enter a To-Do ID"); // resets the ID textbox field to the original placeholder "Enter a To-Do ID"
                  break; // breaks out of the while loop
               }
            } catch (Exception exception) { // if the try block fails, the exception will handle the error gracefully
               // displays an error message dialogue box, asking the user to enter a valid number
               JOptionPane.showMessageDialog(frame, "Please Enter a Valid Number!", "Error", JOptionPane.ERROR_MESSAGE);
               txtDeleteId.setText("Enter a To-Do ID"); // resets the ID textbox field to the original placeholder "Enter a To-Do ID"
               break; // breaks out of the while loop
            }
         }
      }
   }
}

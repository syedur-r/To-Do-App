package view;
import model.TodoDB;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class KeyEvents {
   // this method adds a key listener to the ID textfield of the update panel
   public static void getUpdateIdKeyListener(JTextField txtID, JFrame frame, JTextField txtTextUpdate,
                                             JXDatePicker dtDueDateUpdate, JSpinner spDueTimeUpdate,
                                             JComboBox<String> cmbCategoryUpdate, JComboBox<String> cmbImportanceUpdate,
                                             JComboBox<String> cmbStatus, JComponent updateTask, Font montserrat) {
      txtID.addKeyListener(new KeyAdapter() { // adds a key listener to the textbox
         @Override
         public void keyTyped(KeyEvent e) { // overrides the keyTyped method  for the update ID textfield
            if (!Character.isDigit(e.getKeyChar())) { // checks if the character entered in the textbox is not a digit
               e.consume(); // if the condition is met, the character will not be processed (it will not be shown on the textbox)
            } else if (txtID.getText().equals("Enter a To-Do ID to Update")) { // checks if the textbox contains the placeholder "Enter a To-Do ID to Update"
               txtID.setText(""); // if it does, the text will be cleared
            }
         }

         @Override
         public void keyPressed(KeyEvent e) { // overrides the keyPressed method for the update ID textfield
            // checks if the textbox contains the placeholder "Enter a To-Do ID to Update"
            if (txtID.getText().equals("Enter a To-Do ID to Update")) txtID.setText(""); // if it does, the text will be cleared
         }

         @Override
         public void keyReleased(KeyEvent e) { // overrides the keyReleased method for the update ID textfield
            TodoDB dataSource = new TodoDB(); // creates an instance of the TodoDB class
            if (!dataSource.isConnected()) { // checks if the dataSource instance is not connected to the database
               System.out.println("Can't connect to the database"); // if it isn't then a message will be logged to the console
               return; // the return statement will exit out the method
            }
            if (dataSource.getTodoCount() == 0) { // checks if there are no records in the database
               // clears all the inputs in the update panel
               GUIHelpers.clearUpdateInputs(txtID, "", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
               // disables all the components in the update panel, until a valid ID is entered
               GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
               // if there are no records, a dialogue box will appear saying that the To-Do List is Empty
               JOptionPane.showMessageDialog(frame,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
               txtID.setText("Enter a To-Do ID to Update"); // the ID textbox will set its text to "Enter a To-Do ID to Update"
               txtID.setFocusable(false); // removes the highlighted text from the placeholder by disabling the focus
               txtID.setFocusable(true); // re-enables the focus to the ID textbox
            } else {
               if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) { // checks if the user hasn't pressed the backspace key
                  int index = -1; // intialises an index as a offset value
                  if (txtID.getText().equals("Enter a To-Do ID to Update")) { // checks if the textbox contains the placeholder "Enter a To-Do ID to Update"
                     txtID.setText(""); // if it does, the text will be cleared
                  }
                  else if (txtID.getText().equals("")) { // checks if the textbox has whitespaces being entered
                     e.consume(); // if it does, it will not be processed (it will not be shown on the textbox)
                  } else {
                     index = Integer.parseInt(txtID.getText()); // otherwise the index will be set to the number entered on the textbox
                  }

                  ArrayList<Integer> recordID = dataSource.getAllTodoID(); // an arraylist is created, storing all the record IDs from the database
                  // checks if the character entered by the user is a digit, checks if the index is more than 0 and checks whether the index matches any ID in the recordID arraylist
                  if (Character.isDigit(e.getKeyChar()) && index > 0 && recordID.contains(index)) {
                     // if it does then all the components from the update panel will be enabled
                     GUIHelpers.enableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
                     txtTextUpdate.setText(dataSource.getTodoColumns(TodoDB.TODO_NAME, index)); // populates the "text" textbox with the text of the to-do from the given ID
                     // retrieves the due date from the database, via the given ID and only retrieves the substring of the date since it is LocalDateTime
                     String dbDate = dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).substring(0, dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).lastIndexOf( "T"));
                     // retrieves the due time from the database, via the given ID and only retrieves the substring of the time since it is LocalDateTime
                     String dbTime = dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).substring(11);
                     populateDatePicker(dbDate, dtDueDateUpdate); // populates the "due date" datepicker with the date produced from dbDate
                     populateTimeSpinner(dbTime, spDueTimeUpdate); // populates the "due time" with the time produced from dbTime
                     cmbCategoryUpdate.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_CATEGORY, index)); // populates the "category" dropdown box with the category of the to-do from the given ID
                     // populates the "importance" dropdown box with the importance of the to-do from the given ID
                     cmbImportanceUpdate.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_IMPORTANCE, index));
                     // populates the "importance" dropdown box with the importance of the to-do from the given ID
                     cmbStatus.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_STATUS, index));
                     dataSource.closeConnection(); // closes the database connection
                  } else {
                     GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat); // resets the text in the due time textfield
                     // clears all the inputs in the update panel
                     GUIHelpers.clearUpdateInputs(txtID, "", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
                     // disables all the components in the update panel, until a valid ID is entered
                     GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
                     JOptionPane.showMessageDialog(frame, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE); // a dialogue box is displayed saying the the To-Do Task doesn't exist
                     // another dialogue box is displayed asking the user to check the list task page for the to-do id
                     JOptionPane.showMessageDialog(frame, "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                     txtID.setText("Enter a To-Do ID to Update"); // the ID textbox will set its text to "Enter a To-Do ID to Update"
                     txtID.setFocusable(false); // removes the highlighted text from the placeholder by disabling the focus
                     txtID.setFocusable(true); // re-enables the focus to the ID textbox
                  }
               } else { // once the user has pressed backspace, and the textbox is empty, it should display the placeholder again
                  if (txtID.getText().equals("")) { // checks if the ID textbox is empty
                     // if it is, then
                     GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat); // the jSpinner which contains the time will be reset
                     // all the textfields and dropdown boxes will be cleared/reset
                     GUIHelpers.clearUpdateInputs(txtID, "Enter a To-Do ID to Update", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
                     // all the components in the update panel will be disabled until another valid ID is entered
                     GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
                  }
               }
            }
         }
      });
   }

   // this helper method populates the jxDatePicker in the update panel
   private static void populateDatePicker(String dbDate, JXDatePicker datePicker) {
      try {
         Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dbDate); // constructs a date pattern with the given format yyyy-MM-dd,
         // which is part of LocalDateTime. The parse method is then chained along to produce the date for the datePicker
         datePicker.getEditor().setValue(date); // sets the value of the datePicker as the date object
      } catch (ParseException parseException) {
         // if the try block fails, the catch block will catch the exception and display an error message gracefully
         System.out.println("Unable to parse String object to Date");
      }
   }

   // this helper method populates the jSpinner in the update panel
   private static void populateTimeSpinner(String dbTime, JSpinner timeSpinner) {
      try {
         Date date = new SimpleDateFormat("HH:mm", Locale.ENGLISH).parse(dbTime); // constructs a date pattern with the given format HH:mm,
         // which is part of LocalDateTime. The parse method is then chained along to produce the time for the jSpinner
         timeSpinner.getModel().setValue(date); // sets the value of the jSpinner as the time from the date object
      } catch (ParseException parseException) {
         // if the try block fails, the catch block will catch the exception and display an error message gracefully
         System.out.println("Unable to parse String object to Time Format");
      }
   }

   // this method adds a key listener to the ID textfield of the delete panel
   public static void getDeleteIdKeyListener(JTextField txtDeleteId) {
      txtDeleteId.addKeyListener(new KeyAdapter() { // adds a placeholder for the delete panel ID text field
         @Override
         public void keyTyped(KeyEvent e) { // overrides the keyTyped method for the delete ID textfield
            if (!Character.isDigit(e.getKeyChar())) { // checks if the user hasn't entered a digit for the ID
               e.consume(); // if the user hasn't entered a digit, it will be consume (the textbox will not produce anything)
            } else if (txtDeleteId.getText().equals("Enter a To-Do ID")) { // checks if the delete ID textbox contains the placeholder "Enter a To-Do ID"
               txtDeleteId.setText(""); // clears the textbox
            }
         }

         @Override
         public void keyPressed(KeyEvent e) { // overrides the keyPressed method for the delete ID textfield
            if (txtDeleteId.getText().equals("Enter a To-Do ID")) { // checks if the delete ID textbox contains the placeholder "Enter a To-Do ID"
               txtDeleteId.setText(""); // clears the textbox
            }
         }

         @Override
         public void keyReleased(KeyEvent e) { // overrides the keyReleased method for the delete ID textfield
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) { // checks if the user hasn't pressed the backspace key
               if (txtDeleteId.getText().equals("")) { // checks if the delete ID textbox is empty
                  e.consume(); // any character entered by the user will not be processed
                  txtDeleteId.setText("Enter a To-Do ID"); // resets the textfield to the original placeholder, "Enter a To-Do ID"
               }
            } else { // once the user has pressed backspace, and the textbox is empty, it should display the placeholder again
               if (txtDeleteId.getText().equals("")) { // checks if the delete ID textbox is empty
                  txtDeleteId.setText("Enter a To-Do ID"); // resets the textfield to the original placeholder, "Enter a To-Do ID"
               }
            }
         }
      });
   }
}

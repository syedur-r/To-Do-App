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
   public static void getUpdateIdKeyListener(JTextField txtID, JFrame frame, JTextField txtTextUpdate,
                                             JXDatePicker dtDueDateUpdate, JSpinner spDueTimeUpdate,
                                             JComboBox<String> cmbCategoryUpdate, JComboBox<String> cmbImportanceUpdate,
                                             JComboBox<String> cmbStatus, JComponent updateTask, Font montserrat) {
      txtID.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            TodoDB dataSource = new TodoDB();
            if (!dataSource.openConnection()) {
               System.out.println("Can't connect to the database");
               return;
            }
            if (dataSource.getTodoCount() == 0) {
               JOptionPane.showMessageDialog(frame,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
               txtID.setText("Enter a To-Do ID to Update");
            } else {
               if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                  int index = -1;
                  if (txtID.getText().equals("Enter a To-Do ID to Update")) {
                     txtID.setText("");
                  }
                  else if (txtID.getText().equals("")) {
                     e.consume();
                  } else {
                     index = Integer.parseInt(txtID.getText());
                  }

                  ArrayList<Integer> recordID = dataSource.getAllTodoID();
                  if (Character.isDigit(e.getKeyChar()) && index > 0 && recordID.contains(index)) {
                     GUIHelpers.enableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
                     txtTextUpdate.setText(dataSource.getTodoColumns(TodoDB.TODO_NAME, index));
                     String dbDate = dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).substring(0, dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).lastIndexOf( "T"));
                     try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dbDate);
                        dtDueDateUpdate.getEditor().setValue(date);
                     } catch (ParseException parseException) {
                        System.out.println("Unable to parse String object to Date");
                     }

                     String dbTime = dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).substring(11);
                     try {
                        Date date = new SimpleDateFormat("HH:mm", Locale.ENGLISH).parse(dbTime);
                        spDueTimeUpdate.getModel().setValue(date);
                     } catch (ParseException parseException) {
                        System.out.println("Unable to parse String object to Time");
                     }

                     cmbCategoryUpdate.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_CATEGORY, index));
                     cmbImportanceUpdate.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_IMPORTANCE, index));
                     cmbStatus.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_STATUS, index));
                     dataSource.closeConnection();
                  } else {
                     GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat);
                     GUIHelpers.clearUpdateInputs(txtID, "", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
                     GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
                     JOptionPane.showMessageDialog(frame, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                     JOptionPane.showMessageDialog(frame, "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                     txtID.setText("Enter a To-Do ID to Update");
                  }
               } else {
                  if (txtID.getText().equals("")) {
                     GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat);
                     GUIHelpers.clearUpdateInputs(txtID, "Enter a To-Do ID to Update", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
                     GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
                  }
               }
            }
         }

         @Override
         public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
               e.consume();
            } else if (txtID.getText().equals("Enter a To-Do ID to Update")) {
               txtID.setText("");
            }
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (txtID.getText().equals("Enter a To-Do ID to Update")) txtID.setText("");
         }
      });
   }

   public static void getDeleteIdKeyListener(JTextField txtDeleteId) {
      txtDeleteId.addKeyListener(new KeyAdapter() { // adds a placeholder for the delete panel ID text field
         @Override
         public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
               if (txtDeleteId.getText().equals("")) {
                  e.consume();
                  txtDeleteId.setText("Enter a To-Do ID");
               }
            } else {
               if (txtDeleteId.getText().equals("")) {
                  txtDeleteId.setText("Enter a To-Do ID");
               }
            }
         }

         @Override
         public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
               e.consume();
            } else if (txtDeleteId.getText().equals("Enter a To-Do ID")) {
               txtDeleteId.setText("");
            }
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (txtDeleteId.getText().equals("Enter a To-Do ID")) {
               txtDeleteId.setText("");
            }
         }
      });
   }
}

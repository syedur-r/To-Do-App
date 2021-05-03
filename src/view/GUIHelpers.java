package view;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
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

   public static String[] setMotivationalQuote() {
      return new String[] {
              "“All growth starts at the end of your comfort zone” - Tony Robbins",
              "“Doing less is not being lazy. Don’t give in to a culture that values personal sacrifice over personal productivity.” - Tim Ferriss",
              "“Get things done: Think big but start small.” - Oumar Dieng",
              "“Nothing is particularly hard if you divide it into small jobs.” - Henry Ford",
              "“It always seems impossible until it’s done.” - Nelson Mandela",
              "“Only put off until tomorrow what you are willing to die having left undone.” - Pablo Picasso",
              "“Procrastination is opportunity’s assassin.” - Victor Kiam",
              "“Until we can manage time, we can manage nothing else.” - Peter Drucker",
              "“The secret to getting ahead is getting started.” - Mark Twain",
              "“Your mind is for having ideas, not holding them.” - David Allen",
              "“You can do anything, but not everything.” - David Allen",
              "“Without hustle, talent will only carry you so far.” - Gary Vaynerchuk",
      };
   }

   public static String getCurrentDate() {
      LocalDateTime date = LocalDateTime.now(); // initialises a date variable which contains the current date-time
      return date.getDayOfWeek().toString().substring(0, 1).toUpperCase() + // gets the first letter for the current day of the week, as a capital letter
              date.getDayOfWeek().toString().substring(1).toLowerCase() + ", " + // gets the rest of the letters for the current day of the week, as lowercase letters
              date.getDayOfMonth() + " " + // gets the day of the month
              date.getMonth().toString().substring(0, 1).toUpperCase() + // gets the first letter for the current month, as a capital letter
              date.getMonth().toString().substring(1).toLowerCase() + // gets the rest of the letters for the current month, as lowercase letters
              " " + date.getYear(); // gets the current year
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
   public static boolean isDateTimeValid(String dateTime) {
      try {
         LocalDateTime.parse(dateTime); // parses the date string from the parameter into LocalDateTime
         return true; // returns true if successful
      } catch (Exception e) {
         return false; // otherwise returns false
      }
   }
}

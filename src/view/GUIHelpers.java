package view;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Calendar;

public class GUIHelpers {

   // this helper method returns an array of strings which contain file paths to all the background images stored in the images folder
   // which will be used for generating a random background image each time the app is loaded
   public static String[] setBackgroundImage() {
      return new String[] { // returns an anonymous string array containing file paths
              "images/todo-bg.png", // background image 1
              "images/todo-bg2.png", // background image 2
              "images/todo-bg3.png", // background image 3
              "images/todo-bg4.png", // background image 4
              "images/todo-bg5.png", // background image 5
              "images/todo-bg6.png", // background image 6
              "images/todo-bg7.png", // background image 7
              "images/todo-bg8.png", // background image 8
              "images/todo-bg9.png", // background image 9
              "images/todo-bg10.png" // background image 10
      };
   }

   // this helper method returns an array of strings which contain motivational quotes
   // these quotes will randomly be selected using a random index, and the quote
   // in that index will be displayed in the home panel
   public static String[] setMotivationalQuote() {
      return new String[] { // returns an anonymous string array containing quotes
              "“All growth starts at the end of your comfort zone” - Tony Robbins", // quote 1
              "“Doing less is not being lazy. Don’t give in to a culture that values personal sacrifice over personal productivity.” - Tim Ferriss", // quote 2
              "“Get things done: Think big but start small.” - Oumar Dieng", // quote 3
              "“Nothing is particularly hard if you divide it into small jobs.” - Henry Ford", // quote 4
              "“It always seems impossible until it’s done.” - Nelson Mandela", // quote 5
              "“Only put off until tomorrow what you are willing to die having left undone.” - Pablo Picasso", // quote 6
              "“Procrastination is opportunity’s assassin.” - Victor Kiam", // quote 7
              "“Until we can manage time, we can manage nothing else.” - Peter Drucker", // quote 8
              "“The secret to getting ahead is getting started.” - Mark Twain", // quote 9
              "“Your mind is for having ideas, not holding them.” - David Allen", // quote 10
              "“You can do anything, but not everything.” - David Allen", // quote 11
              "“Without hustle, talent will only carry you so far.” - Gary Vaynerchuk", // quote 12
      };
   }

   // this helper method returns a string which contains the current date in the format (Monday 26th April 2021)
   public static String getCurrentDate() {
      LocalDateTime date = LocalDateTime.now(); // initialises a date variable which contains the current date-time
      return date.getDayOfWeek().toString().substring(0, 1).toUpperCase() + // gets the first letter for the current day of the week, as a capital letter
              date.getDayOfWeek().toString().substring(1).toLowerCase() + ", " + // gets the rest of the letters for the current day of the week, as lowercase letters
              date.getDayOfMonth() + " " + // gets the day of the month
              date.getMonth().toString().substring(0, 1).toUpperCase() + // gets the first letter for the current month, as a capital letter
              date.getMonth().toString().substring(1).toLowerCase() + // gets the rest of the letters for the current month, as lowercase letters
              " " + date.getYear(); // gets the current year
   }

   // this helper method sets the greeting message based on the time of the day
   public static String setGreetingMessage() {
      Calendar date = Calendar.getInstance(); // stores the current date inside a Calendar object
      int hourOfDay = date.get(Calendar.HOUR_OF_DAY); // stores the hour of the day from the calendar instance, inside an integer

      if (hourOfDay > 0 && hourOfDay < 12) { // checks if the hour of the day is more than 12am and less than 12pm
         return "Good Morning "; // a good morning message will be returned
      } else if (hourOfDay > 12 && hourOfDay < 18) { // checks if the hour of the day is more than 12pm less than 6pm
         return "Good Afternoon "; // a good afternoon message will be returned
      }
      return "Good Evening "; // if no conditions are met above, a good evening message will be returned
   }

   // this helper method sets the coordinates of the grid constraints in the grid layout
   public static void setGridCoord(GridBagConstraints gridConst, int x, int y, JPanel jPanel, JComponent component) {
      gridConst.gridx = x; // sets the X axis of the grid bag constraint as the X value passed through the parameter
      gridConst.gridy = y; // sets the Y axis of the grid bag constraint as the Y value passed through the parameter
      jPanel.add(component, gridConst); // the JPanel then adds the component along with the constraints to set its location
   }

   // this helper method displays a panel for a specific page each time the user navigates to it, by pressing its button
   public static void displayPanel(JLabel title, String text, JPanel todoTable, boolean state1, JPanel greetingText, boolean state2, JPanel questionText, boolean state3,
                                   JPanel horizontalLine, boolean state4, JPanel quote, boolean state5, JPanel addInput, boolean state6, JPanel updateInput,
                                   boolean state7, JPanel deleteInput, boolean state8, JPanel addBtnPanel, boolean state9, JPanel updateBtnPanel, boolean state10,
                                   JPanel deleteBtnPanel, boolean state11) {
      title.setText(text); // sets the text for the title via the parameters
      todoTable.setVisible(state1); // sets the visibility state for the table of records via the parameters
      greetingText.setVisible(state2); // sets the visibility state for the greeting text panel via the parameters
      questionText.setVisible(state3); // sets the visibility state for the question text panel via the parameters
      horizontalLine.setVisible(state4); // sets the visibility state for the horizontal line panel via the parameters
      quote.setVisible(state5); // sets the visibility state for the quote panel via the parameters
      addInput.setVisible(state6); // sets the visibility state for the add input panel via the parameters
      updateInput.setVisible(state7); // sets the visibility state for the update input panel via the parameters
      deleteInput.setVisible(state8); // sets the visibility state for the delete input panel via the parameters
      addBtnPanel.setVisible(state9); // sets the visibility state for the 'Add Task' button panel via the parameters
      updateBtnPanel.setVisible(state10); // sets the visibility state for the 'Update Task' button panel via the parameters
      deleteBtnPanel.setVisible(state11); // sets the visibility state for the 'Delete Task' button panel via the parameters
   }

   // this helper method enables all the components in the update panel
   public static void enableUpdateInputs(JComponent text, JComponent dueDate, JComponent dueTime,
                                         JComponent category, JComponent importance,
                                         JComponent status, JComponent updateBtn) {
      text.setEnabled(true); // enables the text component allowing the user to input text
      dueDate.setEnabled(true); // enables the dueDate component allowing the user to input a date
      dueTime.setEnabled(true); // enables the dueTime component allowing the user to input a time
      category.setEnabled(true); // enables the category component allowing the user to select the category of a task
      importance.setEnabled(true); // enables the importance component allowing the user to select the importance of a task
      status.setEnabled(true); // enables the status component allowing the user to select the status of a task
      updateBtn.setEnabled(true); // enables the updateBtn component allowing the user to click the update button, to update a task
   }

   // this helper method disables all the components in the update panel
   public static void disableUpdateInputs(JComponent text, JComponent dueDate, JComponent dueTime,
                                          JComponent category, JComponent importance,
                                          JComponent status, JComponent updateBtn) {
      text.setEnabled(false); // disables the text component, restricting the user from inputting text
      dueDate.setEnabled(false); // disables the dueDate component, restricting the user from inputting a date
      dueTime.setEnabled(false); // disables the dueTime component, restricting the user from inputting a time
      category.setEnabled(false); // disables the category component, restricting the user from selecting the category of a task
      importance.setEnabled(false); // disables the importance component, restricting the user from selecting the importance of a task
      status.setEnabled(false); // disables the status component, restricting the user from selecting the status of a task
      updateBtn.setEnabled(false); // disables the updateBtn component, restricting the user from clicking the update button, to update a task
   }

   // this helper method clears all the components in the update panel
   public static void clearUpdateInputs(JTextField id, String idText, JTextField text,
                                        JXDatePicker dueDate, JComboBox<String> category,
                                        JComboBox<String> importance, JComboBox<String> status) {
      id.setText(idText); // clears the "ID" text box component
      text.setText(""); // clears the "To-Do text" text box component
      dueDate.getEditor().setText(""); // clears the "Due Date" date picker component
      category.setSelectedIndex(0); // resets the index of the category drop down box as "Please select a category"
      importance.setSelectedIndex(0); // resets the index of the importance drop down box as "Please select an importance"
      status.setSelectedIndex(0); // resets the index of the status drop down box as "Please select a status"
   }

   // this helper method checks if a localDateTime format is valid
   public static boolean isDateTimeValid(String dateTime) {
      try { // using try and catch to allow errors to be handled gracefully
         LocalDateTime.parse(dateTime); // parses the date string from the parameter into LocalDateTime
         return true; // returns true if successful
      } catch (Exception e) {
         return false; // if the try block fails, the method will return false
      }
   }
}

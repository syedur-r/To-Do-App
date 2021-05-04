package view;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Calendar;
import java.util.Date;

public class GUIStyles {

   // this method sets the foreground colour of all the labels in the home panel
   public static void setHomePanelLabelForeground(JLabel welcomeText, JLabel introText, JLabel quote, JLabel title) {
      welcomeText.setForeground(Color.WHITE); // sets the foreground colour of the "Good Morning/Afternoon/Evening" text as white
      introText.setForeground(Color.WHITE); // sets the foreground colour of the introText (what are your tasks for today?) as white
      quote.setForeground(Color.WHITE); // sets the foreground colour of the quotes as white
      title.setForeground(Color.WHITE); // sets the foreground colour of the title as white
   }

   // this method sets the font style of all the labels in the home panel
   public static void setHomePanelLabelFont(JLabel title, JLabel welcomeText, JLabel introText, JLabel quote, Font montserrat) {
      welcomeText.setFont(montserrat.deriveFont(Font.BOLD,70f)); // sets the font style of the "Good Morning/Evening" text as montserrat
      introText.setFont(montserrat.deriveFont(40f)); // sets the font style of the introText (what are your tasks for today?) as montserrat
      quote.setFont(montserrat.deriveFont(14f)); // sets the foreground colour of the quotes as montserrat
      title.setFont(montserrat.deriveFont(Font.BOLD, 40f)); // sets the foreground colour of the title as montserrat
   }

   // this method sets the JLabel styles and text
   public static void setLabelStyle(JLabel label, Font font, String text) {
      label.setText(text); // sets the text of the label as the parameter text
      label.setForeground(Color.WHITE); // sets the foreground of the label as white
      label.setFont(font.deriveFont(20f)); // sets the font style of the label as the font from the parameter to a size of 20
   }

   // this method sets the JTextField styles
   public static void setTextStyle(JTextField text, Font font) {
      text.setForeground(Color.BLACK); // sets the foreground of the textbox as black
      text.setFont(font.deriveFont(18f)); // sets the font of the textbox as the font from the parameter to a size of 18
      text.setColumns(10); // sets the number of columns for the textbox as 10 (the width of the textbox)
   }

   // this method sets the JComboBox styles
   public static void setSelectionBoxStyle(JComboBox<String> sBox, Font font) {
      sBox.setForeground(Color.BLACK); // sets the foreground colour of the dropdown box as black
      sBox.setFont(font.deriveFont(18f)); // sets the font of the dropdown box as the font from the parameter
   }

   // this method sets the style of the JXDatePicker
   public static void setDatePickerStyle(JXDatePicker jxDatePicker, Font font) {
      jxDatePicker.setForeground(Color.BLACK); // sets the foreground of the datePicker as black
      jxDatePicker.setFont(font.deriveFont(18f)); // sets the font of the dropdown box as the font from the parameter to a size of 18
      jxDatePicker.setFormats("yyyy-MM-dd"); // sets the date format of the datePicker as yyyy-MM-dd for LocalDateTime parsing
   }

   // this method sets the style of the JSpinner
   public static void setSpinnerStyle(JSpinner timeSpinner, Font font) {
      // creates a new date sequence that represents the time of the day
      SpinnerDateModel timeSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
      timeSpinner.setModel(timeSpinnerModel); // the jSpinner model is then set as the date sequence model
      JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm") { // creates a dateEditor for the jSpinner since it contains a DateModel

         // overriding text field of jSpinner, to allow updating both hours and minutes
         @Override
         public JFormattedTextField getTextField() {
            JFormattedTextField timeText = super.getTextField(); // retrieves the text from the jSpinner
            int selectionStart = timeText.getSelectionStart(); // gets the start position of the text
            int textSize = timeText.getText().length(); // gets the length of the text
            // checks if the start of the text matches the length of the text, if it does then the start position will be set as the last position of the text
            if (selectionStart == textSize) timeText.setSelectionStart(textSize - 1);
            return timeText; // returns the text to be displayed onto the jSpinner
         }
      };
      timeSpinner.setEditor(dateEditor); // displays the value of the jSpinner's date model

      JComponent timeSpinnerEditor = timeSpinner.getEditor(); // stores the component that displays the time
      JSpinner.DefaultEditor deTimeSpinner = (JSpinner.DefaultEditor) timeSpinnerEditor; // stores the timeSpinner component in the DefaultEditor component as a read-only view
      deTimeSpinner.getTextField().setHorizontalAlignment(JTextField.LEFT); // aligns the text field of the jSpinner to the left
      timeSpinner.setForeground(Color.BLACK); // sets the foreground colour of the jSpinner as black
      timeSpinner.setFont(font.deriveFont(18f)); // sets the font size of the jSpinner as the font size from the parameter to a size of 18
   }

   // this method sets the active nav button when a panel is displayed
   public static void setActiveNavButton(Font font, JButton activeBtn) {
      activeBtn.setFont(font.deriveFont(Font.BOLD,20f)); // sets the font of the active button as the font from the parameter, and makes it bold
   }

   // this method sets the inactive nav button when another nav button is clicked
   public static void setInActiveNavButtons(Font font, JButton inactiveBtn1, JButton inactiveBtn2,
                                            JButton inactiveBtn3, JButton inactiveBtn4) {
      inactiveBtn1.setFont(font); // sets the inactiveBtn1 font as a plain font from the parameter
      inactiveBtn2.setFont(font); // sets the inactiveBtn2 font as a plain font from the parameter
      inactiveBtn3.setFont(font); // sets the inactiveBtn3 font as a plain font from the parameter
      inactiveBtn4.setFont(font); // sets the inactiveBtn4 font as a plain font from the parameter
   }

   // this method sets the styles of the nav buttons
   public static void setNavButtonStyles(JButton btn) {
      btn.setAlignmentX(Component.CENTER_ALIGNMENT); // aligns the nav buttons to the center
      btn.setBorderPainted(false); // removes the borders from the button
      btn.setSize(200,130); // sets the width of the button as 200, and height as 130
      btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // sets the cursor of the button as a hand cursor
      btn.setForeground(Color.WHITE); // sets the foreground colour of the button as white
      btn.setFont(new Font("Arial",Font.PLAIN,20)); // sets the font style of the button as plain Arial and a size of 20

      // mouse events for buttons
      btn.addMouseListener(new MouseAdapter() { // hovering over the button
         @Override // overrides the mouseEntered method, when the user hovers over the button
         public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setForeground(new Color(174, 192, 201)); // changes the foreground colour to a darker shade when the button is hovered over
         }

         @Override // overrides the mouseExited method, when the user exits the hover of the button
         public void mouseExited(java.awt.event.MouseEvent e) { // exiting from button hover state
            btn.setForeground(UIManager.getColor("control")); // resets the foreground colour as the original colour before the button was hovered over
         }
      });
   }

   // this method sets the styles of the task buttons for each panel e.g. Add Task, Update Task, Delete Task
   public static void setTaskButtonStyles(JButton btn, Font font) {
      btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // sets the cursor of the button as a hand cursor
      btn.setFont(font.deriveFont(20f)); // sets the font of the button as the font from the parameter, to a size of 20
      btn.setForeground(Color.WHITE); // sets the foreground colour of the button as white
      btn.setBackground(new Color(133,147,152)); // sets the background colour of the button as a light grey colour
      btn.setOpaque(true); // removes any transparency added to the button
      btn.setBorderPainted(false); // removes any borders from the button

      // mouse events for buttons
      btn.addMouseListener(new MouseAdapter() { // hovering over the button
         @Override // overrides the mouseEntered method, when the user hovers over the button
         public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setBackground(new Color(51,51,51)); // changes the foreground colour to a darker shade of grey when the button is hovered over
         }

         @Override // overrides the mouseExited method, when the user exits the hover of the button
         public void mouseExited(java.awt.event.MouseEvent e) { // exiting from button hover state
            btn.setBackground(new Color(133,147,152)); // resets the foreground colour as the original colour before the button was hovered over
         }
      });
   }
}

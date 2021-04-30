package view;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class GUIStyles {
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

   public static void setLabelStyle(JLabel label, Font font, String text) {
      label.setText(text);
      label.setForeground(Color.WHITE);
      label.setFont(font.deriveFont(20f));
   }

   public static void setTextStyle(JTextField text, Font font) {
      text.setForeground(Color.BLACK);
      text.setFont(font.deriveFont(18f));
      text.setColumns(10);
   }

   public static void setSelectionBoxStyle(JComboBox<String> sBox, Font font) {
      sBox.setForeground(Color.BLACK);
      sBox.setFont(font.deriveFont(18f));
   }

   public static void setDatePickerStyle(JXDatePicker jxDatePicker, Font font) {
      jxDatePicker.setForeground(Color.BLACK);
      jxDatePicker.setFont(font.deriveFont(18f));
      jxDatePicker.setFormats("yyyy-MM-dd");
   }

   public static void setSpinnerStyle(JSpinner timeSpinner, Font font) {
      Date date = new Date();
      SpinnerDateModel timeSpinnerModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
      timeSpinner.setModel(timeSpinnerModel);
      JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm") {

         // overriding text field of jSpinner, to allow updating both hours and minutes
         @Override
         public JFormattedTextField getTextField() {
            JFormattedTextField timeText = super.getTextField();
            int selectionStart = timeText.getSelectionStart();
            int textSize = timeText.getText().length();
            if (selectionStart == textSize) timeText.setSelectionStart(textSize - 1);
            return timeText;
         }
      };
      timeSpinner.setEditor(dateEditor);

      JComponent timeSpinnerEditor = timeSpinner.getEditor();
      JSpinner.DefaultEditor deTimeSpinner = (JSpinner.DefaultEditor) timeSpinnerEditor;
      deTimeSpinner.getTextField().setHorizontalAlignment(JTextField.LEFT);
      timeSpinner.setForeground(Color.BLACK);
      timeSpinner.setFont(font.deriveFont(18f));
   }

   public static void setActiveButton(Font font, JButton activeBtn, JButton inactiveBtn1, JButton inactiveBtn2, JButton inactiveBtn3, JButton inactiveBtn4) {
      activeBtn.setFont(font.deriveFont(Font.BOLD,20f));
      inactiveBtn1.setFont(new Font("Arial",Font.PLAIN,20));
      inactiveBtn2.setFont(new Font("Arial",Font.PLAIN,20));
      inactiveBtn3.setFont(new Font("Arial",Font.PLAIN,20));
      inactiveBtn4.setFont(new Font("Arial",Font.PLAIN,20));
   }

   public static void setNavButtonStyles(JButton btn) {
      btn.setAlignmentX(Component.CENTER_ALIGNMENT);
      btn.setBorderPainted(false);
      btn.setSize(200,130);
      btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      btn.setForeground(Color.WHITE);
      btn.setFont(new Font("Arial",Font.PLAIN,20));

      // mouse events for buttons
      btn.addMouseListener(new MouseAdapter() { // hovering over the button
         @Override
         public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setForeground(new Color(174, 192, 201));
         }

         @Override
         public void mouseExited(java.awt.event.MouseEvent e) { // exiting from button hover state
            btn.setForeground(UIManager.getColor("control"));
         }
      });
   }

   public static void setTaskButtonStyles(JButton btn, Font font) {
      btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      btn.setFont(font.deriveFont(20f));
      btn.setForeground(Color.WHITE);
      btn.setBackground(new Color(133,147,152));
      btn.setOpaque(true);
      btn.setBorderPainted(false);

      // mouse events for buttons
      btn.addMouseListener(new MouseAdapter() { // hovering over the button
         @Override
         public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setBackground(new Color(51,51,51));
         }

         @Override
         public void mouseExited(java.awt.event.MouseEvent e) { // exiting from button hover state
            btn.setBackground(new Color(133,147,152));
         }
      });
   }
}

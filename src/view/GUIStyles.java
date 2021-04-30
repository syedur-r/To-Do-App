package view;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.Calendar;
import java.util.Date;

public class GUIStyles {
   public static void setPanelSize(JFrame frame, JPanel header, JPanel navBar, JPanel todoTable, JPanel homeName, JPanel homeText, JPanel horizontalLine,
                                   JPanel inspirationalQuote, JPanel addInput, JPanel updateInput, JPanel deleteInput, JPanel addBtnPanel, JPanel updateBtnPanel,
                                   JPanel deleteBtnPanel) {
      header.setPreferredSize(new Dimension(100, 200));
      navBar.setPreferredSize(new Dimension(200, 40));
      todoTable.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight() + 200) / 2, 460));
      homeName.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 200));
      homeText.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 400));
      horizontalLine.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 600));
      inspirationalQuote.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 250));
      addInput.setMaximumSize(new Dimension(frame.getWidth(), 250));
      updateInput.setMaximumSize(new Dimension(frame.getWidth(), 330));
      deleteInput.setMaximumSize(new Dimension(frame.getWidth(), 120));
      addBtnPanel.setMaximumSize(new Dimension(150, 40));
      updateBtnPanel.setMaximumSize(new Dimension(180, 70));
      deleteBtnPanel.setMaximumSize(new Dimension(180, 50));
   }

   public static void setPanelLayout(JPanel header, JPanel navBar, JPanel mainContent, JPanel todoTable, JPanel homeText, JPanel homeName, JPanel horizontalLine,
                               JPanel inspirationalQuote, JPanel addInput, JPanel updateInput, JPanel deleteInput, JPanel addBtnPanel, JPanel updateBtnPanel,
                               JPanel deleteBtnPanel, JLabel backgroundImage) {
      header.setLayout(new BorderLayout());
      navBar.setLayout(new FlowLayout());
      mainContent.setLayout(new BorderLayout());
      todoTable.setLayout(new BorderLayout());
      homeText.setLayout(new BorderLayout());
      homeName.setLayout(new BorderLayout());
      horizontalLine.setLayout(new BorderLayout());
      inspirationalQuote.setLayout(new BorderLayout());
      addInput.setLayout(new GridBagLayout());
      updateInput.setLayout(new GridBagLayout());
      deleteInput.setLayout(new GridBagLayout());
      addBtnPanel.setLayout(new BorderLayout());
      updateBtnPanel.setLayout(new BorderLayout());
      deleteBtnPanel.setLayout(new BorderLayout());
      backgroundImage.setLayout(new BoxLayout(backgroundImage, BoxLayout.Y_AXIS));
   }

   public static void setPanelAsOpaque(JPanel navBar, JPanel homeText, JPanel homeName, JPanel horizontalLine, JPanel inspirationalQuote, JPanel addInput,
                                 JPanel updateInput, JPanel deleteInput, JPanel addBtnPanel, JPanel updateBtnPanel, JPanel deleteBtnPanel) {
      navBar.setOpaque(false);
      homeText.setOpaque(false);
      homeName.setOpaque(false);
      horizontalLine.setOpaque(false);
      inspirationalQuote.setOpaque(false);
      addInput.setOpaque(false);
      updateInput.setOpaque(false);
      deleteInput.setOpaque(false);
      addBtnPanel.setOpaque(false);
      updateBtnPanel.setOpaque(false);
      deleteBtnPanel.setOpaque(false);
   }

   public static void setPanelVisibility(JPanel todoTable, JPanel homeText, JPanel homeName, JPanel addInput, JPanel updateInput, JPanel deleteInput,
                                   JPanel addBtnPanel, JPanel updateBtnPanel, JPanel deleteBtnPanel) {
      todoTable.setVisible(false);
      homeText.setVisible(true);
      homeName.setVisible(true);
      addInput.setVisible(false);
      updateInput.setVisible(false);
      deleteInput.setVisible(false);
      addBtnPanel.setVisible(false);
      updateBtnPanel.setVisible(false);
      deleteBtnPanel.setVisible(false);
   }

   public static void setLabelForeground(JLabel welcomeText, JLabel introText, JLabel quote, JLabel title) {
      welcomeText.setForeground(Color.WHITE);
      introText.setForeground(Color.WHITE);
      quote.setForeground(Color.WHITE);
      title.setForeground(Color.WHITE);
   }

   public static void setLabelFont(JLabel title, JLabel welcomeText, JLabel introText, JLabel quote, Font montserrat) {
      title.setFont(montserrat.deriveFont(Font.BOLD, 40f));
      welcomeText.setFont(montserrat.deriveFont(Font.BOLD,70f));
      introText.setFont(montserrat.deriveFont(40f));
      quote.setFont(montserrat.deriveFont(14f));
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

package view;

import javax.swing.*;
import java.awt.*;

public class GUIPanels {
   // this method creates the header panel, for the title of all the other panels
   public static JPanel createHeaderPanel(JPanel header) {
      header.setPreferredSize(new Dimension(100, 200)); // sets the preferred size of the header panel as a 100x200
      header.setLayout(new BorderLayout()); // sets the layout manager for the header as a border layout
      return header; // returns the header panel
   }

   // this method creates the navbar panel, to store all the navigation buttons
   public static JPanel createNavBarPanel(JPanel navBar) {
      navBar.setPreferredSize(new Dimension(200, 40)); // sets the preferred size of the navbar panel as a 200x400
      navBar.setLayout(new FlowLayout()); // sets the layout manager for the navbar as a flow layout
      navBar.setOpaque(false); // makes the navbar panel transparent
      return navBar; // returns the navbar panel
   }

   // this method creates the main content panel, which will display the content of the panels itself
   public static JPanel createMainContentPanel(JPanel mainContent) {
      mainContent.setLayout(new BorderLayout()); // sets the layout manager for the main content as a border layout
      return mainContent; // returns the main content panel
   }

   // this method creates the todoTable panel to store the jTable of all the To-Do list items
   public static JPanel createTodoTablePanel(JPanel todoTable, JFrame frame) {
      // sets the maximum size of the todoTable panel as the (jFrame width + height + 200) x 400
      todoTable.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight() + 200) / 2, 460));
      todoTable.setLayout(new BorderLayout()); // sets the layout manager of the todoTable panel as border layout
      todoTable.setVisible(false); // hides the todoTable panel
      return todoTable; // returns the todoTable panel
   }

   // this method creates the homeName panel which displays the greeting message e.g. "Good Morning"
   public static JPanel createGreetingTextPanel(JPanel greetingText, JFrame frame) {
      // sets the maximum size of the homeMessage panel as the (jFrame width + height /2) x 200
      greetingText.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 200));
      greetingText.setLayout(new BorderLayout()); // sets the layout manager of the homeMessage panel as a border layout
      greetingText.setOpaque(false); // makes the homeMessage panel transparent
      greetingText.setVisible(true); // enables the visibility of the homeName panel
      return greetingText; // returns the homeName panel
   }

   // this method creates the homeText panel which displays the message "what are your tasks for today?"
   public static JPanel createQuestionTextPanel(JPanel questionText, JFrame frame) {
      // sets the maximum size of the homeText panel as the (jFrame width + height /2) x 400
      questionText.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 400));
      questionText.setLayout(new BorderLayout()); // sets the layout manager of the homeText panel as a border layout
      questionText.setOpaque(false); // makes the homeText panel transparent
      questionText.setVisible(true); // enables the visibility of the homeText panel
      return questionText; // returns the homeText panel
   }

   // this method creates the horizontalLine panel which displays the solid line underneath the welcome text
   public static JPanel createHorizontalLinePanel(JPanel horizontalLine, JFrame frame) {
      // sets the maximum size of the horizontalLine panel as the (jFrame width + height /2) x 600
      horizontalLine.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 600));
      horizontalLine.setLayout(new BorderLayout()); // sets the layout manager of the horizontalLine panel as a border layout
      horizontalLine.setOpaque(false); // makes the horizontalLine panel transparent
      return horizontalLine; // returns the horizontalLine panel
   }

   // this method creates the quotePanel which displays the random quotes underneath the solid line
   public static JPanel createQuotePanel(JPanel quote, JFrame frame) {
      // sets the maximum size of the quote panel as the (jFrame width + height /2) x 250
      quote.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 250));
      quote.setLayout(new BorderLayout()); // sets the layout manager of the quote panel as a border layout
      quote.setOpaque(false); // makes the quote panel transparent
      return quote; // returns the quote panel
   }

   // this method creates the addInput panel which displays all the components the for add task panel
   public static JPanel createAddInputPanel(JPanel addInput, JFrame frame) {
      // sets the maximum size of the addInput panel as the jFrame width x 250
      addInput.setMaximumSize(new Dimension(frame.getWidth(), 250));
      addInput.setLayout(new GridBagLayout()); // sets the layout manager of the addInput panel as a grid bag layout
      addInput.setOpaque(false); // makes the addInput panel transparent
      addInput.setVisible(false); // hides the addInput panel
      return addInput; // returns the addInput panel
   }

   // this method creates the updateInput panel which displays all the components the for update task panel
   public static JPanel createUpdateInputPanel(JPanel updateInput, JFrame frame) {
      // sets the maximum size of the updateInput panel as the jFrame width x 330
      updateInput.setMaximumSize(new Dimension(frame.getWidth(), 330));
      updateInput.setLayout(new GridBagLayout()); // sets the layout manager of the updateInput panel as a grid bag layout
      updateInput.setOpaque(false); // makes the updateInput panel transparent
      updateInput.setVisible(false); // hides the updateInput panel
      return updateInput; // returns the updateInput panel
   }

   // this method creates the deleteInput panel which displays all the components the for delete task panel
   public static JPanel createDeleteInputPanel(JPanel deleteInput, JFrame frame) {
      // sets the maximum size of the deleteInput panel as the jFrame width x 120
      deleteInput.setMaximumSize(new Dimension(frame.getWidth(), 120));
      deleteInput.setLayout(new GridBagLayout()); // sets the layout manager of the deleteInput panel as a grid bag layout
      deleteInput.setOpaque(false); // makes the deleteInput panel transparent
      deleteInput.setVisible(false); // hides the deleteInput panel
      return deleteInput; // returns the deleteInput panel
   }

   // this method creates the addBtn panel which displays the 'Add Task' button in the Add Task Panel
   public static JPanel createAddBtnPanel(JPanel addBtnPanel) {
      addBtnPanel.setMaximumSize(new Dimension(150, 40)); // sets the maximum size of the addBtn panel as 150x40
      addBtnPanel.setLayout(new BorderLayout()); // sets the layout manager of the addBtn panel as a border layout
      addBtnPanel.setOpaque(false); // makes the addBtn panel transparent
      addBtnPanel.setVisible(false); // hides the addBtn panel
      return addBtnPanel; // returns the addBtn panel
   }

   // this method creates the updateBtn panel which displays the 'Update Task' button in the Update Task Panel
   public static JPanel createUpdateBtnPanel(JPanel updateBtnPanel) {
      updateBtnPanel.setMaximumSize(new Dimension(180, 70)); // sets the maximum size of the updateBtn panel as 180x70
      updateBtnPanel.setLayout(new BorderLayout()); // sets the layout manager of the updateBtn panel as a border layout
      updateBtnPanel.setOpaque(false); // makes the updateBtn panel transparent
      updateBtnPanel.setVisible(false); // hides the updateBtn panel
      return updateBtnPanel; // returns the updateBtn panel
   }

   // this method creates the deleteBtn panel which displays the 'Delete Task' button in the Delete Task Panel
   public static JPanel createDeleteBtnPanel(JPanel deleteBtnPanel) {
      deleteBtnPanel.setMaximumSize(new Dimension(180, 50)); // sets the maximum size of the deleteBtn panel as 180x50
      deleteBtnPanel.setLayout(new BorderLayout()); // sets the layout manager of the deleteBtn panel as a border layout
      deleteBtnPanel.setOpaque(false); // makes the deleteBtn panel transparent
      deleteBtnPanel.setVisible(false); // hides the deleteBtn panel
      return deleteBtnPanel; // returns the deleteBtn panel
   }
}

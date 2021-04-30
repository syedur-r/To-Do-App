package view;

import javax.swing.*;
import java.awt.*;

public class GUIPanels {
   public static JPanel createHeaderPanel(JPanel header) {
      header.setPreferredSize(new Dimension(100, 200));
      header.setLayout(new BorderLayout());
      return header;
   }

   public static JPanel createNavBarPanel(JPanel navBar) {
      navBar.setPreferredSize(new Dimension(200, 40));
      navBar.setLayout(new FlowLayout());
      navBar.setOpaque(false);
      return navBar;
   }

   public static JPanel createMainContentPanel(JPanel mainContent) {
      mainContent.setLayout(new BorderLayout());
      return mainContent;
   }

   public static JPanel createTodoTablePanel(JPanel todoTable, JFrame frame) {
      todoTable.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight() + 200) / 2, 460));
      todoTable.setLayout(new BorderLayout());
      todoTable.setVisible(false);
      return todoTable;
   }

   public static JPanel createHomeNamePanel(JPanel homeName, JFrame frame) {
      homeName.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 200));
      homeName.setLayout(new BorderLayout());
      homeName.setOpaque(false);
      homeName.setVisible(true);
      return homeName;
   }

   public static JPanel createHomeTextPanel(JPanel homeText, JFrame frame) {
      homeText.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 400));
      homeText.setLayout(new BorderLayout());
      homeText.setOpaque(false);
      homeText.setVisible(true);
      return homeText;
   }

   public static JPanel createHorizontalLinePanel(JPanel horizontalLine, JFrame frame) {
      horizontalLine.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 600));
      horizontalLine.setLayout(new BorderLayout());
      horizontalLine.setOpaque(false);
      return horizontalLine;
   }

   public static JPanel createQuotePanel(JPanel quote, JFrame frame) {
      quote.setMaximumSize(new Dimension((frame.getWidth() + frame.getHeight()) / 2, 250));
      quote.setLayout(new BorderLayout());
      quote.setOpaque(false);
      return quote;
   }

   public static JPanel createAddInputPanel(JPanel addInput, JFrame frame) {
      addInput.setMaximumSize(new Dimension(frame.getWidth(), 250));
      addInput.setLayout(new GridBagLayout());
      addInput.setOpaque(false);
      addInput.setVisible(false);
      return addInput;
   }

   public static JPanel createUpdateInputPanel(JPanel updateInput, JFrame frame) {
      updateInput.setMaximumSize(new Dimension(frame.getWidth(), 330));
      updateInput.setLayout(new GridBagLayout());
      updateInput.setOpaque(false);
      updateInput.setVisible(false);
      return updateInput;
   }

   public static JPanel createDeleteInputPanel(JPanel deleteInput, JFrame frame) {
      deleteInput.setMaximumSize(new Dimension(frame.getWidth(), 120));
      deleteInput.setLayout(new GridBagLayout());
      deleteInput.setOpaque(false);
      deleteInput.setVisible(false);
      return deleteInput;
   }

   public static JPanel createAddBtnPanel(JPanel addBtnPanel) {
      addBtnPanel.setMaximumSize(new Dimension(150, 40));
      addBtnPanel.setLayout(new BorderLayout());
      addBtnPanel.setOpaque(false);
      addBtnPanel.setVisible(false);
      return addBtnPanel;
   }

   public static JPanel createUpdateBtnPanel(JPanel updateBtnPanel) {
      updateBtnPanel.setMaximumSize(new Dimension(180, 70));
      updateBtnPanel.setLayout(new BorderLayout());
      updateBtnPanel.setOpaque(false);
      updateBtnPanel.setVisible(false);
      return updateBtnPanel;
   }

   public static JPanel createDeleteBtnPanel(JPanel deleteBtnPanel) {
      deleteBtnPanel.setMaximumSize(new Dimension(180, 50));
      deleteBtnPanel.setLayout(new BorderLayout());
      deleteBtnPanel.setOpaque(false);
      deleteBtnPanel.setVisible(false);
      return deleteBtnPanel;
   }
}

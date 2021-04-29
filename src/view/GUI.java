package view;
import controller.Category;
import controller.Importance;
import controller.Status;
import model.TodoDB;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class GUI {
   Font montserrat; // creates a custom font called montserrat
   String[] columns = {"ID", "Text", "Due Date", "Category", "Importance", "Status"}; // creates the column names for the todo table
   DefaultTableModel todoTableModel = new DefaultTableModel(columns, 0); // creates the model of the todo table and passes the column names inside it

   // constructor
   public GUI() {
      JFrame todoFrm = new JFrame("To-Do List App");
      todoFrm.setSize(1270, 790);
      todoFrm.setLocationRelativeTo(null);
      todoFrm.setLayout(new BorderLayout());
      todoFrm.setResizable(false);
      todoFrm.setVisible(true);
      todoFrm.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent evt) {
            int exitApp = JOptionPane.showConfirmDialog(todoFrm,"Are you sure you wish to exit?", "Exit", JOptionPane.YES_NO_OPTION);

            if (exitApp == JOptionPane.YES_OPTION) {
               todoFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               todoFrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            } else {
               todoFrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
         }
      });

      try {
         montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf"));
         GraphicsEnvironment fontGraphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
         fontGraphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf")));
      } catch (IOException | FontFormatException e) {
         System.out.println("Couldn't register this font");
      }

      JPanel header = new JPanel() {
         @Override
         protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color darkColour = new Color(40,48,72);
            Color lightColour = new Color(133,147,152);
            GradientPaint gradientPaint = new GradientPaint(getWidth(), getHeight(), darkColour, 180, getHeight(), lightColour);
            graphics2D.setPaint(gradientPaint);
            graphics2D.fillRect(0, 0, getWidth(), getHeight());
         }
      };
      JPanel navBar = new JPanel();
      JPanel mainContent = new JPanel();
      JPanel todoTable = new JPanel();
      JPanel homeName = new JPanel();
      JPanel homeText = new JPanel();
      JPanel horizontalLine = new JPanel() {
         @Override
         protected void paintComponent(Graphics graphics) {
            Graphics2D twoDimGraphics = (Graphics2D) graphics;
            twoDimGraphics.setColor(Color.WHITE);
            twoDimGraphics.setStroke(new BasicStroke(5));
            twoDimGraphics.drawLine((getWidth() - getWidth()) + 100, 0, getWidth() - 100, 0);
         }
      };
      JPanel inspirationalQuote = new JPanel();
      JPanel addInput = new JPanel();
      JPanel updateInput = new JPanel();
      JPanel addBtnPanel = new JPanel();
      JPanel updateBtnPanel = new JPanel();
      JPanel deleteInput = new JPanel();
      JPanel deleteBtnPanel = new JPanel();

      header.setPreferredSize(new Dimension(100, 200));
      navBar.setPreferredSize(new Dimension(200, 40));
      todoTable.setMaximumSize(new Dimension((todoFrm.getWidth() + todoFrm.getHeight() + 200) / 2, 460));
      homeName.setMaximumSize(new Dimension((todoFrm.getWidth() + todoFrm.getHeight()) / 2, 200));
      homeText.setMaximumSize(new Dimension((todoFrm.getWidth() + todoFrm.getHeight()) / 2, 400));
      horizontalLine.setMaximumSize(new Dimension((todoFrm.getWidth() + todoFrm.getHeight()) / 2, 600));
      inspirationalQuote.setMaximumSize(new Dimension((todoFrm.getWidth() + todoFrm.getHeight()) / 2, 250));
      addInput.setMaximumSize(new Dimension(todoFrm.getWidth(), 250));
      updateInput.setMaximumSize(new Dimension(todoFrm.getWidth(), 330));
      deleteInput.setMaximumSize(new Dimension(todoFrm.getWidth(), 120));
      addBtnPanel.setMaximumSize(new Dimension(150, 40));
      updateBtnPanel.setMaximumSize(new Dimension(180, 70));
      deleteBtnPanel.setMaximumSize(new Dimension(180, 50));

      header.setLayout(new BorderLayout());
      navBar.setLayout(new FlowLayout());
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

      GridBagConstraints gridConst = new GridBagConstraints();
      gridConst.insets = new Insets(10, 10, 10, 10);
      gridConst.fill = GridBagConstraints.HORIZONTAL;

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

      navBar.setBackground(new Color(0,0,0,0));
      todoTable.setVisible(false);
      homeText.setVisible(true);
      homeName.setVisible(true);
      addInput.setVisible(false);
      updateInput.setVisible(false);
      deleteInput.setVisible(false);
      addBtnPanel.setVisible(false);
      updateBtnPanel.setVisible(false);
      deleteBtnPanel.setVisible(false);

      String[] images = {
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
      Random randomImg = new Random();
      int imageIdx = randomImg.nextInt(images.length);

      mainContent.setLayout(new BorderLayout());
      JLabel backgroundImage = new JLabel(new ImageIcon(images[imageIdx]));
      backgroundImage.setLayout(new BoxLayout(backgroundImage, BoxLayout.Y_AXIS));
      backgroundImage.add(Box.createRigidArea(new Dimension(100, 50)));

      mainContent.add(header, BorderLayout.NORTH);
      header.add(navBar, BorderLayout.SOUTH);
      mainContent.add(backgroundImage, BorderLayout.CENTER);
      backgroundImage.add(todoTable, BorderLayout.CENTER);
      todoFrm.add(mainContent, BorderLayout.CENTER);

      JLabel welcomeText = new JLabel(GUIHelpers.setGreetingMessage(), SwingConstants.CENTER);
      welcomeText.setForeground(Color.WHITE);
      welcomeText.setFont(montserrat.deriveFont(Font.BOLD,70f));
      backgroundImage.add(homeName, BorderLayout.CENTER);
      homeName.add(welcomeText);

      JLabel introText = new JLabel("What are your tasks for today?", SwingConstants.CENTER);
      introText.setForeground(Color.WHITE);
      introText.setFont(montserrat.deriveFont(40f));
      backgroundImage.add(homeText, BorderLayout.CENTER);
      homeText.add(introText);

      backgroundImage.add(horizontalLine, BorderLayout.CENTER);
      String[] quotes = {
              "“The secret to getting things done is to act!” - Dante Alighieri",
              "“You can do anything, but not everything.” - David Allen",
              "“Without hustle, talent will only carry you so far.” - Gary Vaynerchuk",
              "“The dream is free. The hustle is sold separately.” - Steve Harvey",
              "“Get things done: Think big but start small.” - Oumar Dieng",
      };
      Random randomQuote = new Random();
      int quoteIdx = randomQuote.nextInt(quotes.length);

      JLabel quote = new JLabel(quotes[quoteIdx], SwingConstants.CENTER);
      quote.setForeground(Color.WHITE);
      quote.setFont(montserrat.deriveFont(14f));
      backgroundImage.add(inspirationalQuote, BorderLayout.CENTER);
      inspirationalQuote.add(quote);

      LocalDateTime date = LocalDateTime.now();
      String currentDate = date.getDayOfWeek().toString().substring(0, 1).toUpperCase() +
              date.getDayOfWeek().toString().substring(1).toLowerCase() + ", " +
              date.getDayOfMonth() + " " +
              date.getMonth().toString().substring(0, 1).toUpperCase() +
              date.getMonth().toString().substring(1).toLowerCase() + " " + date.getYear();
      JLabel title = new JLabel(currentDate, SwingConstants.CENTER);
      title.setForeground(Color.WHITE);
      title.setFont(montserrat.deriveFont(Font.BOLD, 40f));
      header.add(title, BorderLayout.CENTER);

      JButton homeBtn = new JButton("Home");
      JButton addBtn = new JButton("Add Task");
      JButton listBtn = new JButton("List Tasks");
      JButton updateBtn = new JButton("Update Task");
      JButton deleteBtn = new JButton("Delete Task");
      JButton addTask = new JButton("Add Task");
      JButton updateTask = new JButton("Update Task");
      JButton deleteTask = new JButton("Delete Task");

      // add styling to buttons
      GUIStyles.setNavButtonStyles(homeBtn);
      // since home page is the first active page upon loading, its button will be bold
      homeBtn.setFont(montserrat.deriveFont(Font.BOLD,20f));
      GUIStyles.setNavButtonStyles(listBtn);
      GUIStyles.setNavButtonStyles(addBtn);
      GUIStyles.setNavButtonStyles(updateBtn);
      GUIStyles.setNavButtonStyles(deleteBtn);
      GUIStyles.setHandlerButtonStyles(addTask, montserrat);
      GUIStyles.setHandlerButtonStyles(updateTask, montserrat);
      GUIStyles.setHandlerButtonStyles(deleteTask, montserrat);

      // add buttons onto navBar panel
      navBar.add(homeBtn);
      navBar.add(addBtn);
      navBar.add(listBtn);
      navBar.add(updateBtn);
      navBar.add(deleteBtn);

      String[] categories = {"Please select a category", String.valueOf(Category.Red),
              String.valueOf(Category.White), String.valueOf(Category.Blue), String.valueOf(Category.Purple),
              String.valueOf(Category.Yellow), String.valueOf(Category.Green)};
      String[] importance = {"Please select an importance", String.valueOf(Importance.Low),
              String.valueOf(Importance.Normal), String.valueOf(Importance.High)};
      String[] status = {"Please select a status", String.valueOf(Status.Pending), String.valueOf(Status.Started),
              String.valueOf(Status.Partial), String.valueOf(Status.Completed)};

      JLabel lblID = new JLabel();
      JTextField txtID = new JTextField();
      JLabel lblTextAdd = new JLabel();
      JTextField txtTextAdd = new JTextField();
      JLabel lblDueDateAdd = new JLabel();
      JXDatePicker dtDueDateAdd = new JXDatePicker();
      JLabel lblDueTimeAdd = new JLabel();
      JSpinner spDueTimeAdd = new JSpinner();
      JLabel lblCategoryAdd = new JLabel();
      JComboBox cmbCategoryAdd = new JComboBox(categories);
      JLabel lblImportanceAdd = new JLabel();
      JComboBox cmbImportanceAdd = new JComboBox(importance);
      JLabel lblStatus = new JLabel();
      JComboBox cmbStatus = new JComboBox(status);
      JLabel lblTextUpdate = new JLabel();
      JTextField txtTextUpdate = new JTextField();
      JLabel lblDueDateUpdate = new JLabel();
      JLabel lblDueTimeUpdate = new JLabel();
      JXDatePicker dtDueDateUpdate = new JXDatePicker();
      JSpinner spDueTimeUpdate = new JSpinner();
      JLabel lblCategoryUpdate = new JLabel();
      JComboBox cmbCategoryUpdate = new JComboBox(categories);
      JLabel lblImportanceUpdate = new JLabel();
      JComboBox cmbImportanceUpdate = new JComboBox(importance);
      JLabel lblDeleteId = new JLabel();
      JTextField txtDeleteId = new JTextField();

      GUIStyles.setLabelStyle(lblID, montserrat, "To-Do ID");
      GUIStyles.setLabelStyle(lblDeleteId, montserrat, "To-Do ID");
      GUIStyles.setLabelStyle(lblTextAdd, montserrat, "To-Do text");
      GUIStyles.setLabelStyle(lblDueDateAdd, montserrat, "Due Date");
      GUIStyles.setLabelStyle(lblDueTimeAdd, montserrat, "Due Time");
      GUIStyles.setLabelStyle(lblCategoryAdd, montserrat, "Category");
      GUIStyles.setLabelStyle(lblImportanceAdd, montserrat, "Importance");
      GUIStyles.setLabelStyle(lblStatus, montserrat, "Status");
      GUIStyles.setLabelStyle(lblTextUpdate, montserrat, "To-Do text");
      GUIStyles.setLabelStyle(lblDueDateUpdate, montserrat, "Due Date");
      GUIStyles.setLabelStyle(lblDueTimeUpdate, montserrat, "Due Time");
      GUIStyles.setLabelStyle(lblCategoryUpdate, montserrat, "Category");
      GUIStyles.setLabelStyle(lblImportanceUpdate, montserrat, "Importance");
      GUIStyles.setTextStyle(txtID, montserrat);
      GUIStyles.setTextStyle(txtDeleteId, montserrat);
      GUIStyles.setTextStyle(txtTextAdd, montserrat);
      GUIStyles.setTextStyle(txtTextUpdate, montserrat);
      GUIStyles.setSelectionBoxStyle(cmbCategoryAdd, montserrat);
      GUIStyles.setSelectionBoxStyle(cmbImportanceAdd, montserrat);
      GUIStyles.setSelectionBoxStyle(cmbCategoryUpdate, montserrat);
      GUIStyles.setSelectionBoxStyle(cmbImportanceUpdate, montserrat);
      GUIStyles.setSelectionBoxStyle(cmbStatus, montserrat);
      GUIStyles.setDatePickerStyle(dtDueDateAdd, montserrat);
      GUIStyles.setDatePickerStyle(dtDueDateUpdate, montserrat);
      GUIStyles.setSpinnerStyle(spDueTimeAdd, montserrat);
      GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat);

      GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
      txtID.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent e) {
            TodoDB dataSource = new TodoDB();
            if (!dataSource.openConnection()) {
               System.out.println("Can't connect to the database");
               return;
            }
            if (dataSource.getTodoCount() == 0) {
               JOptionPane.showMessageDialog(todoFrm,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
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
                     JOptionPane.showMessageDialog(todoFrm, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                     JOptionPane.showMessageDialog(todoFrm, "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
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

         public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
               e.consume();
            } else if (txtID.getText().equals("Enter a To-Do ID to Update")) {
               txtID.setText("");
            }
         }

         public void keyPressed(KeyEvent e) {
            if (txtID.getText().equals("Enter a To-Do ID to Update")) {
               txtID.setText("");
            }
         }
      });

      txtDeleteId.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
               if (txtDeleteId.getText().equals("Enter a To-Do ID")) {
                  txtDeleteId.setText("");
               }
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

         public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
               e.consume();
            } else if (txtDeleteId.getText().equals("Enter a To-Do ID")) {
               txtDeleteId.setText("");
            }
         }

         public void keyPressed(KeyEvent e) {
            if (txtDeleteId.getText().equals("Enter a To-Do ID")) {
               txtDeleteId.setText("");
            }
         }
      });

      // add task
      backgroundImage.add(addInput, BorderLayout.NORTH);
      GUIHelpers.setGridCoord(gridConst, 0, 0, addInput, lblTextAdd);
      GUIHelpers.setGridCoord(gridConst, 1, 0, addInput, txtTextAdd);
      GUIHelpers.setGridCoord(gridConst, 0, 1, addInput, lblDueDateAdd);
      GUIHelpers.setGridCoord(gridConst, 1, 1, addInput, dtDueDateAdd);
      GUIHelpers.setGridCoord(gridConst, 0, 2, addInput, lblDueTimeAdd);
      GUIHelpers.setGridCoord(gridConst, 1, 2, addInput, spDueTimeAdd);
      GUIHelpers.setGridCoord(gridConst, 0, 3, addInput, lblCategoryAdd);
      GUIHelpers.setGridCoord(gridConst, 1, 3, addInput, cmbCategoryAdd);
      GUIHelpers.setGridCoord(gridConst, 0, 4, addInput, lblImportanceAdd);
      GUIHelpers.setGridCoord(gridConst, 1, 4, addInput, cmbImportanceAdd);

      // add button
      backgroundImage.add(addBtnPanel, BorderLayout.SOUTH);
      addBtnPanel.add(addTask, BorderLayout.SOUTH);

      // update task
      backgroundImage.add(updateInput, BorderLayout.NORTH);
      GUIHelpers.setGridCoord(gridConst, 0, 0, updateInput, lblID);
      GUIHelpers.setGridCoord(gridConst, 1, 0, updateInput, txtID);
      GUIHelpers.setGridCoord(gridConst, 0, 1, updateInput, lblTextUpdate);
      GUIHelpers.setGridCoord(gridConst, 1, 1, updateInput, txtTextUpdate);
      GUIHelpers.setGridCoord(gridConst, 0, 2, updateInput, lblDueDateUpdate);
      GUIHelpers.setGridCoord(gridConst, 1, 2, updateInput, dtDueDateUpdate);
      GUIHelpers.setGridCoord(gridConst, 0, 3, updateInput, lblDueTimeUpdate);
      GUIHelpers.setGridCoord(gridConst, 1, 3, updateInput, spDueTimeUpdate);
      GUIHelpers.setGridCoord(gridConst, 0, 4, updateInput, lblCategoryUpdate);
      GUIHelpers.setGridCoord(gridConst, 1, 4, updateInput, cmbCategoryUpdate);
      GUIHelpers.setGridCoord(gridConst, 0, 5, updateInput, lblImportanceUpdate);
      GUIHelpers.setGridCoord(gridConst, 1, 5, updateInput, cmbImportanceUpdate);
      GUIHelpers.setGridCoord(gridConst, 0, 6, updateInput, lblStatus);
      GUIHelpers.setGridCoord(gridConst, 1, 6, updateInput, cmbStatus);

      // update button
      backgroundImage.add(updateBtnPanel, BorderLayout.SOUTH);
      updateBtnPanel.add(updateTask, BorderLayout.SOUTH);

      // delete task
      backgroundImage.add(deleteInput, BorderLayout.NORTH);
      GUIHelpers.setGridCoord(gridConst, 0, 0, deleteInput, lblDeleteId);
      GUIHelpers.setGridCoord(gridConst, 1, 0, deleteInput, txtDeleteId);

      // delete button
      backgroundImage.add(deleteBtnPanel, BorderLayout.SOUTH);
      deleteBtnPanel.add(deleteTask, BorderLayout.SOUTH);

      // creating the table of rows
      todoTable.add(new JScrollPane(GUIHelpers.createTodoTable(montserrat, todoTableModel))); // creates the JTable to view all the to-do list tasks
      GUIHelpers.getTodoRows(todoTableModel);

      /* DISPLAY HOME PANEL */
      homeBtn.addActionListener(e -> {
         GUIStyles.setActiveButton(montserrat, homeBtn, listBtn, addBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(title, currentDate, todoTable, false, homeName, true, homeText, true,
                 horizontalLine, true, inspirationalQuote, true, addInput, false, updateInput, false,
                 deleteInput, false, addBtnPanel, false, updateBtnPanel, false, deleteBtnPanel, false);
      });

      /* DISPLAY ADD TASK PANEL */
      addBtn.addActionListener(e -> {
         GUIStyles.setActiveButton(montserrat, addBtn, listBtn, homeBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(title, "Add Task", todoTable, false, homeName, false, homeText, false,
                 horizontalLine, false, inspirationalQuote, false, addInput, true, updateInput, false,
                 deleteInput, false, addBtnPanel, true, updateBtnPanel, false, deleteBtnPanel, false);
      });

      /* DISPLAY LIST TASKS PANEL */
      listBtn.addActionListener(e -> {
         GUIStyles.setActiveButton(montserrat, listBtn, homeBtn, addBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(title, "List Tasks", todoTable, true, homeName, false, homeText, false,
                 horizontalLine, false, inspirationalQuote, false, addInput, false, updateInput, false,
                 deleteInput, false, addBtnPanel, false, updateBtnPanel, false, deleteBtnPanel, false);
      });

      /* DISPLAY UPDATE TASK PANEL */
      updateBtn.addActionListener(e -> {
         GUIStyles.setActiveButton(montserrat, updateBtn, addBtn, listBtn, homeBtn, deleteBtn);
         GUIHelpers.displayPanel(title, "Update Task", todoTable, false, homeName, false, homeText, false,
                 horizontalLine, false, inspirationalQuote, false, addInput, false, updateInput, true,
                 deleteInput, false, addBtnPanel, false, updateBtnPanel, true, deleteBtnPanel, false);
         if (txtID.getText().equals("")) {
            txtID.setText("Enter a To-Do ID to Update");
         }
      });

      /* DISPLAY DELETE TASK PANEL */
      deleteBtn.addActionListener(e -> {
         GUIStyles.setActiveButton(montserrat, deleteBtn, updateBtn, addBtn, listBtn, homeBtn);
         GUIHelpers.displayPanel(title, "Delete Task", todoTable, false, homeName, false, homeText, false,
         horizontalLine, false, inspirationalQuote, false, addInput, false, updateInput, false,
         deleteInput, true, addBtnPanel, false, updateBtnPanel, false, deleteBtnPanel, true);
         txtDeleteId.setText("Enter a To-Do ID");
      });

      /* ADD TASK BUTTON */
      addTask.addActionListener(e -> {
         try {
            String todoText = txtTextAdd.getText();
            String todoDueDate = new SimpleDateFormat("yyyy-MM-dd").format(dtDueDateAdd.getDate());
            String todoDueTime = new SimpleDateFormat("HH:mm").format(spDueTimeAdd.getValue());
            String todoCategory = cmbCategoryAdd.getItemAt(cmbCategoryAdd.getSelectedIndex()).toString();
            String todoImportance = cmbImportanceAdd.getItemAt(cmbImportanceAdd.getSelectedIndex()).toString();

            TodoDB dataSource = new TodoDB();
            if (!dataSource.openConnection()) {
               System.out.println("Can't connect to the database");
               return;
            } else if (!GUIHelpers.isValid(todoDueDate + "T" + todoDueTime)) {
               JOptionPane.showMessageDialog(todoFrm, "Please Select a Valid Date and Time","Error", JOptionPane.ERROR_MESSAGE);
               return;
            } else if (GUIHelpers.isValid(todoDueDate + "T" + todoDueTime) ||
                    !todoCategory.equals("Please select a category") ||
                    !todoImportance.equals("Please select an importance")) {
               dataSource.insertTodo(todoText, todoDueDate + "T" + todoDueTime, todoCategory, todoImportance);
            } else {
               JOptionPane.showMessageDialog(todoFrm, "Please Select all Drop-Down Boxes","Error", JOptionPane.ERROR_MESSAGE);
               return;
            }
            dataSource.closeConnection();

            todoTableModel.setRowCount(0);
            GUIHelpers.getTodoRows(todoTableModel);
            txtTextAdd.setText("");
            dtDueDateAdd.getEditor().setText("");
            GUIStyles.setSpinnerStyle(spDueTimeAdd, montserrat);
            cmbCategoryAdd.setSelectedIndex(0);
            cmbImportanceAdd.setSelectedIndex(0);
            JOptionPane.showMessageDialog(todoFrm, "Task Has Successfully Been Added!", "Successful", JOptionPane.INFORMATION_MESSAGE);
         } catch (Exception exception) {
            JOptionPane.showMessageDialog(todoFrm, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
         }
      });

      /* UPDATE TASK BUTTON */
      updateTask.addActionListener(e -> {
         try {
            TodoDB dataSource = new TodoDB();
            if (!dataSource.openConnection()) {
               System.out.println("Can't connect to the database");
               return;
            }
            if (dataSource.getTodoCount() == 0) {
               JOptionPane.showMessageDialog(todoFrm,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
            } else {
               int getTodoIndex = Integer.parseInt(txtID.getText());
               String todoText = txtTextUpdate.getText();
               String todoDueDate = new SimpleDateFormat("yyyy-MM-dd").format(dtDueDateUpdate.getDate());
               String todoDueTime = new SimpleDateFormat("HH:mm").format(spDueTimeUpdate.getValue());
               String todoCategory = cmbCategoryUpdate.getItemAt(cmbCategoryUpdate.getSelectedIndex()).toString();
               String todoImportance = cmbImportanceUpdate.getItemAt(cmbImportanceUpdate.getSelectedIndex()).toString();
               String todoStatus = cmbStatus.getItemAt(cmbStatus.getSelectedIndex()).toString();

               if (txtID.getText().equals("Enter a To-Do ID to Update")) {
                  JOptionPane.showMessageDialog(todoFrm, "Please Enter a Valid To-Do ID","Error", JOptionPane.ERROR_MESSAGE);
                  return;
               } else if (!GUIHelpers.isValid(todoDueDate + "T" + todoDueTime)) {
                  JOptionPane.showMessageDialog(todoFrm, "Please Select a Valid Date and Time", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
               } else if (todoCategory.equals("Please select a category") ||
                       todoImportance.equals("Please select an importance") ||
                       todoStatus.equals("Please select a status")) {
                  JOptionPane.showMessageDialog(todoFrm, "Please Select all Drop-Down Boxes","Error", JOptionPane.ERROR_MESSAGE);
                  return;
               }

               dataSource.updateTodo(TodoDB.TODO_NAME, todoText, getTodoIndex);
               dataSource.updateTodo(TodoDB.TODO_DUE_DATE, todoDueDate + "T" + todoDueTime, getTodoIndex);
               dataSource.updateTodo(TodoDB.TODO_CATEGORY, todoCategory, getTodoIndex);
               dataSource.updateTodo(TodoDB.TODO_IMPORTANCE, todoImportance, getTodoIndex);
               dataSource.updateTodo(TodoDB.TODO_STATUS, todoStatus, getTodoIndex);
               dataSource.closeConnection();

               todoTableModel.setRowCount(0);
               GUIHelpers.getTodoRows(todoTableModel);
               GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat);
               GUIHelpers.clearUpdateInputs(txtID, "Enter a To-Do ID to Update", txtTextUpdate, dtDueDateUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus);
               GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
               JOptionPane.showMessageDialog(todoFrm, "Task Has Successfully Been Updated!", "Successful", JOptionPane.INFORMATION_MESSAGE);
            }
         }  catch (Exception exception) {
            JOptionPane.showMessageDialog(todoFrm, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
         }
      });

      /* DELETE TASK BUTTON */
      deleteTask.addActionListener(e -> {
         TodoDB dataSource = new TodoDB();
         if (!dataSource.openConnection()) {
            System.out.println("Can't connect to the database");
            return;
         }
         if (dataSource.getTodoCount() == 0) {
            JOptionPane.showMessageDialog(todoFrm,"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
            txtDeleteId.setText("Enter a To-Do ID");
         } else {
            int todoIndex = -1;
            int confirmationBox;
            ArrayList<Integer> recordID = dataSource.getAllTodoID();

            while (todoIndex < 0 || !recordID.contains(todoIndex)) {
               try {
                  todoIndex = Integer.parseInt(txtDeleteId.getText());
                  if (todoIndex > 0 && recordID.contains(todoIndex)) {
                     confirmationBox = JOptionPane.showConfirmDialog(todoFrm, "Are You Sure You Wish To Delete This Task?", "Delete", JOptionPane.YES_NO_OPTION);
                  } else {
                     JOptionPane.showMessageDialog(todoFrm, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                     JOptionPane.showMessageDialog(todoFrm, "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                     txtDeleteId.setText("Enter a To-Do ID");
                     break;
                  }

                  if(confirmationBox == JOptionPane.YES_OPTION) {
                     dataSource.deleteTodo(todoIndex);
                     dataSource.closeConnection();

                     todoTableModel.setRowCount(0);
                     GUIHelpers.getTodoRows(todoTableModel);
                     txtDeleteId.setText("");
                     JOptionPane.showMessageDialog(todoFrm,"Task Has Successfully Been Deleted!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                     txtDeleteId.setText("Enter a To-Do ID");
                     break;
                  }
               } catch (Exception exception) {
                  JOptionPane.showMessageDialog(todoFrm, "Please Enter a Valid Number!", "Error", JOptionPane.ERROR_MESSAGE);
                  txtDeleteId.setText("Enter a To-Do ID");
                  break;
               }
            }
         }
      });
   }
}
package view;
import model.TodoDB;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
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
         GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
         g.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf")));
      } catch (IOException | FontFormatException e) {
         e.printStackTrace();
      }

      JPanel header = new JPanel() {
         @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color darkColour = new Color(40,48,72);
            Color lightColour = new Color(133,147,152);
            GradientPaint gradientPaint = new GradientPaint(getWidth(), getHeight(), darkColour, 180, getHeight(), lightColour);
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
         }
      };
      JPanel navBar = new JPanel();
      JPanel mainContent = new JPanel();
      JPanel todoTable = new JPanel();
      JPanel homeName = new JPanel();
      JPanel homeText = new JPanel();
      JPanel horizontalLine = new JPanel() {
         protected void paintComponent(Graphics g) {
            Graphics2D twoDimGraphics = (Graphics2D) g;
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
      Random random = new Random();
      int randomImgIndex = random.nextInt(images.length);

      mainContent.setLayout(new BorderLayout());
      JLabel backgroundImage = new JLabel(new ImageIcon(images[randomImgIndex]));
      backgroundImage.setLayout(new BoxLayout(backgroundImage, BoxLayout.Y_AXIS));
      backgroundImage.add(Box.createRigidArea(new Dimension(100, 50)));

      mainContent.add(header, BorderLayout.NORTH);
      header.add(navBar, BorderLayout.SOUTH);
      mainContent.add(backgroundImage, BorderLayout.CENTER);
      backgroundImage.add(todoTable, BorderLayout.CENTER);
      todoFrm.add(mainContent, BorderLayout.CENTER);

      JLabel welcomeText = new JLabel(GUIHelpers.greetingMessage(), SwingConstants.CENTER);
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
      Random r = new Random();
      int randomQuoteIndex = r.nextInt(quotes.length);

      JLabel quote = new JLabel(quotes[randomQuoteIndex], SwingConstants.CENTER);
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
      GUIStyles.navButtonStyles(homeBtn);
      homeBtn.setFont(montserrat.deriveFont(Font.BOLD,20f));
      GUIStyles.navButtonStyles(listBtn);
      GUIStyles.navButtonStyles(addBtn);
      GUIStyles.navButtonStyles(updateBtn);
      GUIStyles.navButtonStyles(deleteBtn);

      GUIStyles.handlerButtonStyles(addTask, montserrat);
      GUIStyles.handlerButtonStyles(updateTask, montserrat);
      GUIStyles.handlerButtonStyles(deleteTask, montserrat);

      // add buttons onto navBar panel
      navBar.add(homeBtn);
      navBar.add(addBtn);
      navBar.add(listBtn);
      navBar.add(updateBtn);
      navBar.add(deleteBtn);

      String[] categories = {"Please select a category", "Red", "White", "Blue", "Purple", "Yellow", "Green"};
      String[] importance = {"Please select an importance", "Low", "Normal", "High"};
      String[] status = {"Please select a status", "Pending", "Started", "Partial", "Completed"};

      JLabel lblID = new JLabel();
      JTextField txtID = new JTextField();
      JLabel lblTextAdd = new JLabel();
      JTextField txtTextAdd = new JTextField();
      JLabel lblDueDateAdd = new JLabel();
      JXDatePicker dtDueDateAdd = new JXDatePicker();
      JLabel lblDueTimeAdd = new JLabel();
      JSpinner spDueTimeAdd = new JSpinner();
      JLabel lblCategoryAdd = new JLabel();
      JComboBox txtCategoryAdd = new JComboBox(categories);
      JLabel lblImportanceAdd = new JLabel();
      JComboBox txtImportanceAdd = new JComboBox(importance);
      JLabel lblStatus = new JLabel();
      JComboBox txtStatus = new JComboBox(status);
      JLabel lblTextUpdate = new JLabel();
      JTextField txtTextUpdate = new JTextField();
      JLabel lblDueDateUpdate = new JLabel();
      JLabel lblDueTimeUpdate = new JLabel();
      JXDatePicker dtDueDateUpdate = new JXDatePicker();
      JSpinner spDueTimeUpdate = new JSpinner();
      JLabel lblCategoryUpdate = new JLabel();
      JComboBox txtCategoryUpdate = new JComboBox(categories);
      JLabel lblImportanceUpdate = new JLabel();
      JComboBox txtImportanceUpdate = new JComboBox(importance);
      JLabel lblDeleteId = new JLabel();
      JTextField txtDeleteId = new JTextField();

      GUIStyles.labelStyle(lblID, montserrat, "To-Do ID");
      GUIStyles.labelStyle(lblDeleteId, montserrat, "To-Do ID");
      GUIStyles.labelStyle(lblTextAdd, montserrat, "To-Do text");
      GUIStyles.labelStyle(lblDueDateAdd, montserrat, "Due Date");
      GUIStyles.labelStyle(lblDueTimeAdd, montserrat, "Due Time");
      GUIStyles.labelStyle(lblCategoryAdd, montserrat, "Category");
      GUIStyles.labelStyle(lblImportanceAdd, montserrat, "Importance");
      GUIStyles.labelStyle(lblStatus, montserrat, "Status");
      GUIStyles.labelStyle(lblTextUpdate, montserrat, "To-Do text");
      GUIStyles.labelStyle(lblDueDateUpdate, montserrat, "Due Date");
      GUIStyles.labelStyle(lblDueTimeUpdate, montserrat, "Due Time");
      GUIStyles.labelStyle(lblCategoryUpdate, montserrat, "Category");
      GUIStyles.labelStyle(lblImportanceUpdate, montserrat, "Importance");

      GUIStyles.textStyle(txtID, montserrat);
      GUIStyles.textStyle(txtDeleteId, montserrat);
      GUIStyles.textStyle(txtTextAdd, montserrat);
      GUIStyles.textStyle(txtTextUpdate, montserrat);

      GUIStyles.selectionBoxStyle(txtCategoryAdd, montserrat);
      GUIStyles.selectionBoxStyle(txtImportanceAdd, montserrat);
      GUIStyles.selectionBoxStyle(txtCategoryUpdate, montserrat);
      GUIStyles.selectionBoxStyle(txtImportanceUpdate, montserrat);
      GUIStyles.selectionBoxStyle(txtStatus, montserrat);

      GUIStyles.datePickerStyle(dtDueDateAdd, montserrat);
      GUIStyles.datePickerStyle(dtDueDateUpdate, montserrat);

      GUIStyles.spinnerStyle(spDueTimeAdd, montserrat);
      GUIStyles.spinnerStyle(spDueTimeUpdate, montserrat);

      GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, txtCategoryUpdate, txtImportanceUpdate, txtStatus, updateTask);
      txtID.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent e) {
            TodoDB dataSource = new TodoDB();
            if (!dataSource.openConnection()) {
               System.out.println("Can't connect to the database");
               return;
            }
            if (dataSource.getTodoCount() == 0) {
               JOptionPane.showMessageDialog(todoFrm,"Your To-do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
            } else {
               if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                  int index = -1;
                  if (txtID.getText().equals("")) {
                     e.consume();
                  } else if (txtID.getText().equals("Enter a To-Do ID to Update")) {
                     txtID.setText("");
                  } else {
                     index = Integer.parseInt(txtID.getText());
                  }

                  ArrayList<Integer> recordID = dataSource.getAllTodoID();
                  if (Character.isDigit(e.getKeyChar()) && index > 0 && recordID.contains(index)) {
                     GUIHelpers.enableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, txtCategoryUpdate, txtImportanceUpdate, txtStatus, updateTask);
                     txtTextUpdate.setText(dataSource.getTodoColumns(TodoDB.TODO_NAME, index));
                     String dbDate = dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index)
                             .substring(0, dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).lastIndexOf( "T"));
                     DateFormat formatDbDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                     try {
                        Date date = formatDbDate.parse(dbDate);
                        dtDueDateUpdate.getEditor().setValue(date);
                     } catch (ParseException parseException) {
                        System.out.println("Unable to parse String object to Date");
                     }

                     String dbTime = dataSource.getTodoColumns(TodoDB.TODO_DUE_DATE, index).substring(11);
                     DateFormat formatDbTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                     try {
                        Date date = formatDbTime.parse(dbTime);
                        spDueTimeUpdate.getModel().setValue(date);
                     } catch (ParseException parseException) {
                        System.out.println("Unable to parse String object to Time");
                     }

                     txtCategoryUpdate.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_CATEGORY, index));
                     txtImportanceUpdate.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_IMPORTANCE, index));
                     txtStatus.setSelectedItem(dataSource.getTodoColumns(TodoDB.TODO_STATUS, index));
                     dataSource.closeConnection();
                  } else {
                     txtID.setText("");
                     txtTextUpdate.setText("");
                     dtDueDateUpdate.getEditor().setText("");
                     GUIStyles.spinnerStyle(spDueTimeUpdate, montserrat);
                     txtCategoryUpdate.setSelectedIndex(0);
                     txtImportanceUpdate.setSelectedIndex(0);
                     txtStatus.setSelectedIndex(0);
                     GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, txtCategoryUpdate, txtImportanceUpdate, txtStatus, updateTask);
                     JOptionPane.showMessageDialog(todoFrm, "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
                     JOptionPane.showMessageDialog(todoFrm, "Please Check List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                     txtID.setText("Enter a To-Do ID to Update");
                  }
               } else {
                  if (txtID.getText().equals("")) {
                     txtID.setText("Enter a To-Do ID to Update");
                     txtTextUpdate.setText("");
                     dtDueDateUpdate.getEditor().setText("");
                     GUIStyles.spinnerStyle(spDueTimeUpdate, montserrat);
                     txtCategoryUpdate.setSelectedIndex(0);
                     txtImportanceUpdate.setSelectedIndex(0);
                     txtStatus.setSelectedIndex(0);
                     GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, txtCategoryUpdate, txtImportanceUpdate, txtStatus, updateTask);
                  }
               }
            }
         }

         public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
               e.consume();
            }
            else if (txtID.getText().equals("Enter a To-Do ID to Update")) {
               txtID.setText("");
            }
         }

         public void keyPressed(KeyEvent e) {
            if (txtID.getText().equals("Enter a To-Do ID to Update")) {
               txtID.setText("");
            }
         }
      });

      // add task
      backgroundImage.add(addInput, BorderLayout.NORTH);
      GUIHelpers.addToGrid(gridConst, 0, 0, addInput, lblTextAdd);
      GUIHelpers.addToGrid(gridConst, 1, 0, addInput, txtTextAdd);
      GUIHelpers.addToGrid(gridConst, 0, 1, addInput, lblDueDateAdd);
      GUIHelpers.addToGrid(gridConst, 1, 1, addInput, dtDueDateAdd);
      GUIHelpers.addToGrid(gridConst, 0, 2, addInput, lblDueTimeAdd);
      GUIHelpers.addToGrid(gridConst, 1, 2, addInput, spDueTimeAdd);
      GUIHelpers.addToGrid(gridConst, 0, 3, addInput, lblCategoryAdd);
      GUIHelpers.addToGrid(gridConst, 1, 3, addInput, txtCategoryAdd);
      GUIHelpers.addToGrid(gridConst, 0, 4, addInput, lblImportanceAdd);
      GUIHelpers.addToGrid(gridConst, 1, 4, addInput, txtImportanceAdd);

      // add button
      backgroundImage.add(addBtnPanel, BorderLayout.SOUTH);
      addBtnPanel.add(addTask, BorderLayout.SOUTH);

      // update task
      backgroundImage.add(updateInput, BorderLayout.NORTH);
      GUIHelpers.addToGrid(gridConst, 0, 0, updateInput, lblID);
      GUIHelpers.addToGrid(gridConst, 1, 0, updateInput, txtID);
      GUIHelpers.addToGrid(gridConst, 0, 1, updateInput, lblTextUpdate);
      GUIHelpers.addToGrid(gridConst, 1, 1, updateInput, txtTextUpdate);
      GUIHelpers.addToGrid(gridConst, 0, 2, updateInput, lblDueDateUpdate);
      GUIHelpers.addToGrid(gridConst, 1, 2, updateInput, dtDueDateUpdate);
      GUIHelpers.addToGrid(gridConst, 0, 3, updateInput, lblDueTimeUpdate);
      GUIHelpers.addToGrid(gridConst, 1, 3, updateInput, spDueTimeUpdate);
      GUIHelpers.addToGrid(gridConst, 0, 4, updateInput, lblCategoryUpdate);
      GUIHelpers.addToGrid(gridConst, 1, 4, updateInput, txtCategoryUpdate);
      GUIHelpers.addToGrid(gridConst, 0, 5, updateInput, lblImportanceUpdate);
      GUIHelpers.addToGrid(gridConst, 1, 5, updateInput, txtImportanceUpdate);
      GUIHelpers.addToGrid(gridConst, 0, 6, updateInput, lblStatus);
      GUIHelpers.addToGrid(gridConst, 1, 6, updateInput, txtStatus);

      // update button
      backgroundImage.add(updateBtnPanel, BorderLayout.SOUTH);
      updateBtnPanel.add(updateTask, BorderLayout.SOUTH);

      // delete task
      backgroundImage.add(deleteInput, BorderLayout.NORTH);
      GUIHelpers.addToGrid(gridConst, 0, 0, deleteInput, lblDeleteId);
      GUIHelpers.addToGrid(gridConst, 1, 0, deleteInput, txtDeleteId);

      // delete button
      backgroundImage.add(deleteBtnPanel, BorderLayout.SOUTH);
      deleteBtnPanel.add(deleteTask, BorderLayout.SOUTH);

      // creating the table of rows
      todoTable.add(new JScrollPane(GUIHelpers.createTodoTable(montserrat, todoTableModel))); // creates the JTable to view all the to-do list tasks
      GUIHelpers.getTodoRows(todoTableModel);

      // adding event listeners
      homeBtn.addActionListener(e -> {
         GUIStyles.activeButton(montserrat, homeBtn, listBtn, addBtn, updateBtn, deleteBtn);
         title.setText(currentDate);
         todoTable.setVisible(false);
         homeName.setVisible(true);
         homeText.setVisible(true);
         horizontalLine.setVisible(true);
         inspirationalQuote.setVisible(true);
         addInput.setVisible(false);
         updateInput.setVisible(false);
         deleteInput.setVisible(false);
         addBtnPanel.setVisible(false);
         updateBtnPanel.setVisible(false);
         deleteBtnPanel.setVisible(false);
      });

      addBtn.addActionListener(e -> {
         GUIStyles.activeButton(montserrat, addBtn, listBtn, homeBtn, updateBtn, deleteBtn);
         title.setText("Add Task");
         todoTable.setVisible(false);
         homeName.setVisible(false);
         homeText.setVisible(false);
         horizontalLine.setVisible(false);
         inspirationalQuote.setVisible(false);
         addInput.setVisible(true);
         updateInput.setVisible(false);
         deleteInput.setVisible(false);
         addBtnPanel.setVisible(true);
         updateBtnPanel.setVisible(false);
         deleteBtnPanel.setVisible(false);
      });

      listBtn.addActionListener(e -> {
         GUIStyles.activeButton(montserrat, listBtn, homeBtn, addBtn, updateBtn, deleteBtn);
         title.setText("List Tasks");
         todoTable.setVisible(true);
         homeName.setVisible(false);
         homeText.setVisible(false);
         horizontalLine.setVisible(false);
         inspirationalQuote.setVisible(false);
         addInput.setVisible(false);
         updateInput.setVisible(false);
         deleteInput.setVisible(false);
         addBtnPanel.setVisible(false);
         updateBtnPanel.setVisible(false);
         deleteBtnPanel.setVisible(false);
      });

      updateBtn.addActionListener(e -> {
         GUIStyles.activeButton(montserrat, updateBtn, addBtn, listBtn, homeBtn, deleteBtn);
         title.setText("Update Task");
         todoTable.setVisible(false);
         homeName.setVisible(false);
         homeText.setVisible(false);
         horizontalLine.setVisible(false);
         inspirationalQuote.setVisible(false);
         addInput.setVisible(false);
         updateInput.setVisible(true);
         deleteInput.setVisible(false);
         addBtnPanel.setVisible(false);
         updateBtnPanel.setVisible(true);
         deleteBtnPanel.setVisible(false);
         txtID.setText("Enter a To-Do ID to Update");
      });

      deleteBtn.addActionListener(e -> {
         GUIStyles.activeButton(montserrat, deleteBtn, updateBtn, addBtn, listBtn, homeBtn);
         title.setText("Delete Task");
         todoTable.setVisible(false);
         homeName.setVisible(false);
         homeText.setVisible(false);
         horizontalLine.setVisible(false);
         inspirationalQuote.setVisible(false);
         addInput.setVisible(false);
         updateInput.setVisible(false);
         deleteInput.setVisible(true);
         addBtnPanel.setVisible(false);
         updateBtnPanel.setVisible(false);
         deleteBtnPanel.setVisible(true);
      });

      addTask.addActionListener(e -> {
         try {
            String todoText = txtTextAdd.getText();
            DateFormat localDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todoDueDate = localDateTimeFormat.format(dtDueDateAdd.getDate());
            localDateTimeFormat = new SimpleDateFormat("HH:mm");
            String todoDueTime = localDateTimeFormat.format(spDueTimeAdd.getValue());
            String todoCategory = txtCategoryAdd.getItemAt(txtCategoryAdd.getSelectedIndex()).toString();
            String todoImportance = txtImportanceAdd.getItemAt(txtImportanceAdd.getSelectedIndex()).toString();

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
            GUIStyles.spinnerStyle(spDueTimeAdd, montserrat);
            txtCategoryAdd.setSelectedIndex(0);
            txtImportanceAdd.setSelectedIndex(0);
            JOptionPane.showMessageDialog(todoFrm, "Task Has Successfully Been Added!", "Successful", JOptionPane.INFORMATION_MESSAGE);
         } catch (Exception exception) {
            JOptionPane.showMessageDialog(todoFrm, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
         }
      });

      updateTask.addActionListener(e -> {
         try {
            TodoDB dataSource = new TodoDB();
            if (!dataSource.openConnection()) {
               System.out.println("Can't connect to the database");
               return;
            }
            if (dataSource.getTodoCount() == 0) {
               JOptionPane.showMessageDialog(todoFrm,"Your To-do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
            } else {
               int getTodoIndex = Integer.parseInt(txtID.getText());
               String todoText = txtTextUpdate.getText();
               DateFormat localDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
               String todoDueDate = localDateTimeFormat.format(dtDueDateUpdate.getDate());
               localDateTimeFormat = new SimpleDateFormat("HH:mm");
               String todoDueTime = localDateTimeFormat.format(spDueTimeUpdate.getValue());
               String todoCategory = txtCategoryUpdate.getItemAt(txtCategoryUpdate.getSelectedIndex()).toString();
               String todoImportance = txtImportanceUpdate.getItemAt(txtImportanceUpdate.getSelectedIndex()).toString();
               String todoStatus = txtStatus.getItemAt(txtStatus.getSelectedIndex()).toString();

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
               txtID.setText("");
               txtTextUpdate.setText("");
               txtCategoryUpdate.setSelectedIndex(0);
               txtImportanceUpdate.setSelectedIndex(0);
               txtStatus.setSelectedIndex(0);
               GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, txtCategoryUpdate, txtImportanceUpdate, txtStatus, updateTask);
               JOptionPane.showMessageDialog(todoFrm, "Task Has Successfully Been Updated!", "Successful", JOptionPane.INFORMATION_MESSAGE);
            }
         }  catch (Exception exception) {
            JOptionPane.showMessageDialog(todoFrm, "Please Enter Valid Details","Error", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
         }
      });

      deleteTask.addActionListener(e -> {
         TodoDB dataSource = new TodoDB();
         if (!dataSource.openConnection()) {
            System.out.println("Can't connect to the database");
            return;
         }
         if (dataSource.getTodoCount() == 0) {
            JOptionPane.showMessageDialog(todoFrm,"Your To-do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
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
                     JOptionPane.showMessageDialog(todoFrm, "Please Check List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
                     break;
                  }

                  if(confirmationBox == JOptionPane.YES_OPTION) {
                     dataSource.deleteTodo(todoIndex);
                     dataSource.closeConnection();
                     todoTableModel.setRowCount(0);
                     GUIHelpers.getTodoRows(todoTableModel);
                     txtDeleteId.setText("");
                     JOptionPane.showMessageDialog(todoFrm,"Task Has Successfully Been Deleted!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                     break;
                  }
               } catch (Exception exception) {
                  JOptionPane.showMessageDialog(todoFrm, "Please Enter a Valid Number!", "Error", JOptionPane.ERROR_MESSAGE);
                  break;
               }
            }
         }
      });
   }
}
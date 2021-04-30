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
import java.util.*;

public class GUI extends JFrame implements KeyListener {
   Font montserrat; // creates a custom font called montserrat
   String[] columns = {"ID", "Text", "Due Date", "Category", "Importance", "Status"}; // creates the column names for the todo table
   DefaultTableModel todoTableModel = new DefaultTableModel(columns, 0); // creates the model of the todo table and passes the column names inside it

   // an array of strings which contain all the category options for the drop-down box
   String[] categories = {"Please select a category", String.valueOf(Category.Red), String.valueOf(Category.White), String.valueOf(Category.Blue),
           String.valueOf(Category.Purple), String.valueOf(Category.Yellow), String.valueOf(Category.Green)};

   // an array of strings which contain all the importance options for the drop-down box
   String[] importance = {"Please select an importance", String.valueOf(Importance.Low), String.valueOf(Importance.Normal), String.valueOf(Importance.High)};

   // an array of strings which contain all the status options for the drop-down box
   String[] status = {"Please select a status", String.valueOf(Status.Pending), String.valueOf(Status.Started), String.valueOf(Status.Partial),
           String.valueOf(Status.Completed)};

   // JPANELS
   JPanel header = new JPanel() {
      @Override
      protected void paintComponent(Graphics graphics) {
         super.paintComponent(graphics);
         Graphics2D graphics2D = (Graphics2D) graphics;
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

   // JLABELS
   JLabel backgroundImage = new JLabel(new ImageIcon(GUIStyles.setBackgroundImage()[new Random().nextInt(GUIStyles.setBackgroundImage().length)]));
   JLabel welcomeText = new JLabel(GUIHelpers.setGreetingMessage(), SwingConstants.CENTER);
   JLabel introText = new JLabel("What are your tasks for today?", SwingConstants.CENTER);
   JLabel quote = new JLabel(GUIStyles.setQuote()[new Random().nextInt(GUIStyles.setQuote().length)], SwingConstants.CENTER);
   JLabel title = new JLabel(GUIStyles.getCurrentDate(), SwingConstants.CENTER);
   JLabel lblID = new JLabel();
   JLabel lblTextAdd = new JLabel();
   JLabel lblDueDateAdd = new JLabel();
   JLabel lblDueTimeAdd = new JLabel();
   JLabel lblCategoryAdd = new JLabel();
   JLabel lblImportanceAdd = new JLabel();
   JLabel lblStatus = new JLabel();
   JLabel lblTextUpdate = new JLabel();
   JLabel lblDueDateUpdate = new JLabel();
   JLabel lblDueTimeUpdate = new JLabel();
   JLabel lblCategoryUpdate = new JLabel();
   JLabel lblImportanceUpdate = new JLabel();
   JLabel lblDeleteId = new JLabel();

   // JTEXTFIELDS
   JTextField txtID = new JTextField();
   JTextField txtTextAdd = new JTextField();
   JTextField txtTextUpdate = new JTextField();
   JTextField txtDeleteId = new JTextField();

   // JXDATEPICKERS
   JXDatePicker dtDueDateAdd = new JXDatePicker();
   JXDatePicker dtDueDateUpdate = new JXDatePicker();

   // JSPINNERS
   JSpinner spDueTimeUpdate = new JSpinner();
   JSpinner spDueTimeAdd = new JSpinner();

   // JCOMBOBOXES
   JComboBox<String> cmbCategoryAdd = new JComboBox<>(categories);
   JComboBox<String> cmbImportanceAdd = new JComboBox<>(importance);
   JComboBox<String> cmbStatus = new JComboBox<>(status);
   JComboBox<String> cmbCategoryUpdate = new JComboBox<>(categories);
   JComboBox<String> cmbImportanceUpdate = new JComboBox<>(importance);

   // JBUTTONS
   JButton homeBtn = new JButton("Home");
   JButton addBtn = new JButton("Add Task");
   JButton listBtn = new JButton("List Tasks");
   JButton updateBtn = new JButton("Update Task");
   JButton deleteBtn = new JButton("Delete Task");
   JButton addTask = new JButton("Add Task");
   JButton updateTask = new JButton("Update Task");
   JButton deleteTask = new JButton("Delete Task");

   // constructor
   public GUI() {
      setTitle("To-Do List App");
      setSize(1270, 790);
      setLocationRelativeTo(null);
      setLayout(new BorderLayout());
      setResizable(false);
      setVisible(true);
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent onCloseEvent) {
            int exitApp = JOptionPane.showConfirmDialog(getParent(),"Are you sure you wish to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            setDefaultCloseOperation(exitApp == JOptionPane.YES_OPTION ? JFrame.DISPOSE_ON_CLOSE : JFrame.DO_NOTHING_ON_CLOSE);
         }
      });

      try {
         montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf"));
         GraphicsEnvironment fontGraphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
         fontGraphics.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf")));
      } catch (IOException | FontFormatException e) {
         System.out.println("Couldn't register this font");
      }

      GridBagConstraints gridConst = new GridBagConstraints();
      gridConst.insets = new Insets(10, 10, 10, 10);
      gridConst.fill = GridBagConstraints.HORIZONTAL;

      header.setPreferredSize(new Dimension(100, 200));
      navBar.setPreferredSize(new Dimension(200, 40));
      todoTable.setMaximumSize(new Dimension((getWidth() + this.getHeight() + 200) / 2, 460));
      homeName.setMaximumSize(new Dimension((this.getWidth() + this.getHeight()) / 2, 200));
      homeText.setMaximumSize(new Dimension((this.getWidth() + this.getHeight()) / 2, 400));
      horizontalLine.setMaximumSize(new Dimension((this.getWidth() + this.getHeight()) / 2, 600));
      inspirationalQuote.setMaximumSize(new Dimension((this.getWidth() + this.getHeight()) / 2, 250));
      addInput.setMaximumSize(new Dimension(this.getWidth(), 250));
      updateInput.setMaximumSize(new Dimension(this.getWidth(), 330));
      deleteInput.setMaximumSize(new Dimension(this.getWidth(), 120));
      addBtnPanel.setMaximumSize(new Dimension(150, 40));
      updateBtnPanel.setMaximumSize(new Dimension(180, 70));
      deleteBtnPanel.setMaximumSize(new Dimension(180, 50));

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

      todoTable.setVisible(false);
      homeText.setVisible(true);
      homeName.setVisible(true);
      addInput.setVisible(false);
      updateInput.setVisible(false);
      deleteInput.setVisible(false);
      addBtnPanel.setVisible(false);
      updateBtnPanel.setVisible(false);
      deleteBtnPanel.setVisible(false);

      welcomeText.setForeground(Color.WHITE);
      introText.setForeground(Color.WHITE);
      quote.setForeground(Color.WHITE);
      title.setForeground(Color.WHITE);
      title.setFont(montserrat.deriveFont(Font.BOLD, 40f));

      welcomeText.setFont(montserrat.deriveFont(Font.BOLD,70f));
      introText.setFont(montserrat.deriveFont(40f));
      quote.setFont(montserrat.deriveFont(14f));

      this.add(mainContent, BorderLayout.CENTER);
      mainContent.add(header, BorderLayout.NORTH);
      mainContent.add(backgroundImage, BorderLayout.CENTER);
      header.add(navBar, BorderLayout.SOUTH);
      header.add(title, BorderLayout.CENTER);
      backgroundImage.add(Box.createRigidArea(new Dimension(100, 50)));
      backgroundImage.add(todoTable, BorderLayout.CENTER);
      backgroundImage.add(homeName, BorderLayout.CENTER);
      backgroundImage.add(homeText, BorderLayout.CENTER);
      backgroundImage.add(horizontalLine, BorderLayout.CENTER);
      backgroundImage.add(inspirationalQuote, BorderLayout.CENTER);
      backgroundImage.add(addInput, BorderLayout.NORTH); // add task
      backgroundImage.add(updateInput, BorderLayout.NORTH); // update task
      backgroundImage.add(deleteInput, BorderLayout.NORTH); // delete task
      backgroundImage.add(addBtnPanel, BorderLayout.SOUTH); // add button
      backgroundImage.add(updateBtnPanel, BorderLayout.SOUTH); // update button
      backgroundImage.add(deleteBtnPanel, BorderLayout.SOUTH); // delete button
      homeName.add(welcomeText);
      homeText.add(introText);
      inspirationalQuote.add(quote);
      addBtnPanel.add(addTask, BorderLayout.SOUTH);
      updateBtnPanel.add(updateTask, BorderLayout.SOUTH); // update button
      deleteBtnPanel.add(deleteTask, BorderLayout.SOUTH);
      todoTable.add(new JScrollPane(GUIHelpers.createTodoTable(montserrat, todoTableModel))); // creates the JTable to view all the to-do list tasks as rows
      // add buttons onto navBar panel
      navBar.add(homeBtn);
      navBar.add(addBtn);
      navBar.add(listBtn);
      navBar.add(updateBtn);
      navBar.add(deleteBtn);

      // add styling to buttons
      GUIStyles.setNavButtonStyles(homeBtn);
      homeBtn.setFont(montserrat.deriveFont(Font.BOLD,20f)); // this must stay here, otherwise "Home" will not be bold
      // since home page is the first active page upon loading, its button will be bold
      GUIStyles.setNavButtonStyles(listBtn);
      GUIStyles.setNavButtonStyles(addBtn);
      GUIStyles.setNavButtonStyles(updateBtn);
      GUIStyles.setNavButtonStyles(deleteBtn);
      GUIStyles.setTaskButtonStyles(addTask, montserrat);
      GUIStyles.setTaskButtonStyles(updateTask, montserrat);
      GUIStyles.setTaskButtonStyles(deleteTask, montserrat);
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

      txtID.addKeyListener(this); // adds a placeholder for the update panel ID text field
      txtDeleteId.addKeyListener(new KeyAdapter() { // adds a placeholder for the delete panel ID text field
         @Override
         public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
               if (txtDeleteId.getText().equals("")) {
                  e.consume();
                  txtDeleteId.setText("Enter a To-Do ID");
               }
            } else {
               if (txtDeleteId.getText().equals("")) txtDeleteId.setText("Enter a To-Do ID");
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
      });

      // add task components
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
      // update task components
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
      // delete task components
      GUIHelpers.setGridCoord(gridConst, 0, 0, deleteInput, lblDeleteId);
      GUIHelpers.setGridCoord(gridConst, 1, 0, deleteInput, txtDeleteId);
      // populates the jTable
      GUIHelpers.getTodoRows(todoTableModel);

      /* DISPLAY HOME PANEL */
      homeBtn.addActionListener(e -> {
         GUIStyles.setActiveButton(montserrat, homeBtn, listBtn, addBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(title, GUIStyles.getCurrentDate(), todoTable, false, homeName, true, homeText, true,
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

      addTask.addActionListener(e -> ActionEvents.addTaskPerformed(this, txtTextAdd, dtDueDateAdd, spDueTimeAdd, cmbCategoryAdd,
              cmbImportanceAdd, todoTableModel, montserrat)); // add task button event

      updateTask.addActionListener(e -> ActionEvents.updateTaskPerformed(this, txtID, txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate,
              cmbImportanceUpdate, cmbStatus, updateTask, todoTableModel, montserrat)); // update task button event

      deleteTask.addActionListener(e -> ActionEvents.deleteTaskPerformed(this, txtDeleteId, todoTableModel)); // delete task button event
   }

   @Override
   public void keyReleased(KeyEvent e) {
      TodoDB dataSource = new TodoDB();
      if (!dataSource.openConnection()) {
         System.out.println("Can't connect to the database");
         return;
      }
      if (dataSource.getTodoCount() == 0) {
         JOptionPane.showMessageDialog(getParent(),"Your To-Do List is Empty!","Empty", JOptionPane.WARNING_MESSAGE);
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
               JOptionPane.showMessageDialog(getParent(), "This To-Do Task Does Not Exist!", "Error", JOptionPane.ERROR_MESSAGE);
               JOptionPane.showMessageDialog(getParent(), "Please Check the List Task Page for To-Do ID", "Information", JOptionPane.INFORMATION_MESSAGE);
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
      if ("Enter a To-Do ID to Update".equals(txtID.getText())) txtID.setText("");
   }
}
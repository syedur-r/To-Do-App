package view;
import controller.Category;
import controller.Importance;
import controller.Status;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GUI extends JFrame {
   Font montserrat; // creates a custom font called montserrat
   String[] columns = {"ID", "Text", "Due Date", "Category", "Importance", "Status"}; // creates the column names for the todo table
   DefaultTableModel todoTableModel = new DefaultTableModel(columns, 0); // creates the model of the todo table and passes the column names inside it

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
   JPanel motivationalQuote = new JPanel();
   JPanel addInput = new JPanel();
   JPanel updateInput = new JPanel();
   JPanel addBtnPanel = new JPanel();
   JPanel updateBtnPanel = new JPanel();
   JPanel deleteInput = new JPanel();
   JPanel deleteBtnPanel = new JPanel();

   // JLABELS
   JLabel backgroundImage = new JLabel(new ImageIcon(GUIHelpers.setBackgroundImage()[new Random().nextInt(GUIHelpers.setBackgroundImage().length)]));
   JLabel welcomeText = new JLabel(GUIHelpers.setGreetingMessage(), SwingConstants.CENTER);
   JLabel introText = new JLabel("What are your tasks for today?", SwingConstants.CENTER);
   JLabel quote = new JLabel(GUIHelpers.setMotivationalQuote()[new Random().nextInt(GUIHelpers.setMotivationalQuote().length)], SwingConstants.CENTER);
   JLabel title = new JLabel(GUIHelpers.getCurrentDate(), SwingConstants.CENTER);
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
   JComboBox<String> cmbCategoryAdd = new JComboBox<>(Category.getAllCategories());
   JComboBox<String> cmbImportanceAdd = new JComboBox<>(Importance.getAllImportance());
   JComboBox<String> cmbCategoryUpdate = new JComboBox<>(Category.getAllCategories());
   JComboBox<String> cmbImportanceUpdate = new JComboBox<>(Importance.getAllImportance());
   JComboBox<String> cmbStatus = new JComboBox<>(Status.getAllStatuses());

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
         GraphicsEnvironment fontGraphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
         montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf"));
         fontGraphics.registerFont(montserrat);
      } catch (IOException | FontFormatException e) {
         System.out.println("Couldn't register this font");
      }

      backgroundImage.setLayout(new BoxLayout(backgroundImage, BoxLayout.Y_AXIS));
      GUIStyles.setHomePanelLabelForeground(welcomeText, introText, quote, title);
      GUIStyles.setHomePanelLabelFont(title, welcomeText, introText, quote, montserrat);

      add(GUIPanels.createMainContentPanel(mainContent), BorderLayout.CENTER);
      mainContent.add(GUIPanels.createHeaderPanel(header), BorderLayout.NORTH);
      mainContent.add(backgroundImage, BorderLayout.CENTER);
      header.add(GUIPanels.createNavBarPanel(navBar), BorderLayout.SOUTH);
      header.add(title, BorderLayout.CENTER);
      backgroundImage.add(Box.createRigidArea(new Dimension(100, 50)));
      backgroundImage.add(GUIPanels.createTodoTablePanel(todoTable, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createHomeMessagePanel(homeName, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createHomeTextPanel(homeText, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createHorizontalLinePanel(horizontalLine, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createQuotePanel(motivationalQuote, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createAddInputPanel(addInput, this), BorderLayout.NORTH); // add task
      backgroundImage.add(GUIPanels.createUpdateInputPanel(updateInput, this), BorderLayout.NORTH); // update task
      backgroundImage.add(GUIPanels.createDeleteInputPanel(deleteInput, this), BorderLayout.NORTH); // delete task
      backgroundImage.add(GUIPanels.createAddBtnPanel(addBtnPanel), BorderLayout.SOUTH); // add button
      backgroundImage.add(GUIPanels.createUpdateBtnPanel(updateBtnPanel), BorderLayout.SOUTH); // update button
      backgroundImage.add(GUIPanels.createDeleteBtnPanel(deleteBtnPanel), BorderLayout.SOUTH); // delete button
      homeName.add(welcomeText);
      homeText.add(introText);
      motivationalQuote.add(quote);
      addBtnPanel.add(addTask, BorderLayout.SOUTH);
      updateBtnPanel.add(updateTask, BorderLayout.SOUTH); // update button
      deleteBtnPanel.add(deleteTask, BorderLayout.SOUTH);
      todoTable.add(new JScrollPane(GUITables.createTodoTable(montserrat, todoTableModel))); // creates the JTable to view all the to-do list tasks as rows
      navBar.add(homeBtn); // adds the home button onto navBar panel
      navBar.add(addBtn); // adds the add button onto navBar panel
      navBar.add(listBtn); // adds the list button onto navBar panel
      navBar.add(updateBtn); // adds the update button onto navBar panel
      navBar.add(deleteBtn); // adds the delete button onto navBar panel

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

      GridBagConstraints gridConst = new GridBagConstraints();
      gridConst.insets = new Insets(10, 10, 10, 10);
      gridConst.fill = GridBagConstraints.HORIZONTAL;

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

      GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
      GUITables.getTodoRows(todoTableModel); // populates the jTable

      KeyEvents.getUpdateIdKeyListener(txtID, this, txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate,
              cmbImportanceUpdate, cmbStatus, updateTask, montserrat); // adds a placeholder for the update panel ID text field
      KeyEvents.getDeleteIdKeyListener(txtDeleteId); // adds a placeholder for the delete panel ID text field

      /* DISPLAY HOME PANEL */
      homeBtn.addActionListener(e -> {
         GUIStyles.setActiveNavButton(montserrat, homeBtn);
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), listBtn, addBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(
                 title, GUIHelpers.getCurrentDate(),
                 todoTable, false,
                 homeName, true,
                 homeText, true,
                 horizontalLine, true,
                 motivationalQuote, true,
                 addInput, false,
                 updateInput, false,
                 deleteInput, false,
                 addBtnPanel, false,
                 updateBtnPanel, false,
                 deleteBtnPanel, false
         );
      });

      /* DISPLAY ADD TASK PANEL */
      addBtn.addActionListener(e -> {
         GUIStyles.setActiveNavButton(montserrat, addBtn);
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), listBtn, homeBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(
                 title, "Add Task",
                 todoTable, false,
                 homeName, false,
                 homeText, false,
                 horizontalLine, false,
                 motivationalQuote, false,
                 addInput, true,
                 updateInput, false,
                 deleteInput, false,
                 addBtnPanel, true,
                 updateBtnPanel, false,
                 deleteBtnPanel, false
         );
      });

      /* DISPLAY LIST TASKS PANEL */
      listBtn.addActionListener(e -> {
         GUIStyles.setActiveNavButton(montserrat, listBtn);
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), homeBtn, addBtn, updateBtn, deleteBtn);
         GUIHelpers.displayPanel(
                 title, "List Tasks",
                 todoTable, true,
                 homeName, false,
                 homeText, false,
                 horizontalLine, false,
                 motivationalQuote, false,
                 addInput, false,
                 updateInput, false,
                 deleteInput, false,
                 addBtnPanel, false,
                 updateBtnPanel, false,
                 deleteBtnPanel, false
         );
      });

      /* DISPLAY UPDATE TASK PANEL */
      updateBtn.addActionListener(e -> {
         GUIStyles.setActiveNavButton(montserrat, updateBtn);
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), addBtn, listBtn, homeBtn, deleteBtn);
         GUIHelpers.displayPanel(
                 title, "Update Task",
                 todoTable, false,
                 homeName, false,
                 homeText, false,
                 horizontalLine, false,
                 motivationalQuote, false,
                 addInput, false,
                 updateInput, true,
                 deleteInput, false,
                 addBtnPanel, false,
                 updateBtnPanel, true,
                 deleteBtnPanel, false
         );
         if (txtID.getText().equals("")) {
            txtID.setText("Enter a To-Do ID to Update");
         }
      });

      /* DISPLAY DELETE TASK PANEL */
      deleteBtn.addActionListener(e -> {
         GUIStyles.setActiveNavButton(montserrat, deleteBtn);
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), updateBtn, addBtn, listBtn, homeBtn);
         GUIHelpers.displayPanel(
                 title, "Delete Task",
                 todoTable, false,
                 homeName, false,
                 homeText, false,
                 horizontalLine, false,
                 motivationalQuote, false,
                 addInput, false,
                 updateInput, false,
                 deleteInput, true,
                 addBtnPanel, false,
                 updateBtnPanel, false,
                 deleteBtnPanel, true
         );
         txtDeleteId.setText("Enter a To-Do ID");
      });

      addTask.addActionListener(e -> ActionEvents.addTaskPerformed(this, txtTextAdd, dtDueDateAdd,
              spDueTimeAdd, cmbCategoryAdd, cmbImportanceAdd, todoTableModel, montserrat)); // add task button event

      updateTask.addActionListener(e -> ActionEvents.updateTaskPerformed(this, txtID, txtTextUpdate,
              dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask,
              todoTableModel, montserrat)); // update task button event

      deleteTask.addActionListener(e -> ActionEvents.deleteTaskPerformed(this, txtDeleteId, todoTableModel)); // delete task button event
   }
}
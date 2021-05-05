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
   JPanel header = new JPanel() { // creates the JPanel for the header, which will contain the titles for all pages
      @Override
      protected void paintComponent(Graphics graphics) { // overrides the paintComponent method to add a gradient colour
         super.paintComponent(graphics); // passes the graphics context off to the header's UI delegate, to paint its background
         Graphics2D graphics2D = (Graphics2D) graphics; // stores the graphics context inside a 2D graphics context
         Color darkColour = new Color(40,48,72); // creates a the first gradient colour
         Color lightColour = new Color(133,147,152); // creates the second gradient colour
         GradientPaint gradientPaint = new GradientPaint(getWidth(), getHeight(), darkColour, 180, getHeight(), lightColour); // constructs the gradient paint object
         graphics2D.setPaint(gradientPaint); // sets the paint attribute for the 2D graphics context
         graphics2D.fillRect(0, 0, getWidth(), getHeight()); // fills in the panel rectangle from the width of the jFrame to the height of the jFrame
      }
   };
   JPanel navBar = new JPanel(); // creates the jPanel for the nav bar, to contain all the navigation buttons
   JPanel mainContent = new JPanel(); // create the jPanel for the main content, to contain all the components in the home panel
   JPanel todoTable = new JPanel(); // creates the jPanel for the todoTable, to contain the jTable of To-Do items
   JPanel greetingText = new JPanel(); // creates a jPanel to contain the "Good Morning/Afternoon/Evening text"
   JPanel questionText = new JPanel(); // creates a jPanel to contain the text "what are your tasks for today?"
   JPanel horizontalLine = new JPanel() { // creates a jPanel to contain the solid border line
      @Override
      protected void paintComponent(Graphics graphics) { // overrides the paintComponent method to add a thick line
         Graphics2D twoDimGraphics = (Graphics2D) graphics; // stores the graphics context inside a 2D graphics context
         twoDimGraphics.setColor(Color.WHITE); // sets the colour of the 2D graphics context
         twoDimGraphics.setStroke(new BasicStroke(5)); // sets the stroke of the 2D graphics context
         twoDimGraphics.drawLine(100, 0, getWidth() - 100, 0); // draws the line from 100 pixels to the right to the width of the jFrame - 100, making it a center line
      }
   };
   JPanel motivationalQuote = new JPanel(); // creates a jPanel to contain the random motivational quotes
   JPanel addInput = new JPanel(); // creates a jPanel to contain all the input fields components for adding a task
   JPanel updateInput = new JPanel(); // creates a jPanel to contain all the input fields components for updating a task
   JPanel deleteInput = new JPanel(); // creates a jPanel to contain all the input fields components for deleting a task
   JPanel addBtnPanel = new JPanel(); // creates a jPanel to contain the 'Add Task' button
   JPanel updateBtnPanel = new JPanel(); // creates a jPanel to contain the 'Update Task' button
   JPanel deleteBtnPanel = new JPanel(); // creates a jPanel to contain the 'Delete Task' button

   // JLABELS
   // creates a jLabel to contain an image icon, which will be set to a random background image file path based on the index given from the array in the GUI Helpers class
   JLabel backgroundImage = new JLabel(new ImageIcon(GUIHelpers.setBackgroundImage()[new Random().nextInt(GUIHelpers.setBackgroundImage().length)]));
   JLabel welcomeText = new JLabel(GUIHelpers.setGreetingMessage(), SwingConstants.CENTER); // creates a jLabel to store the current date
   JLabel introText = new JLabel("What are your tasks for today?", SwingConstants.CENTER); // creates a jLabel to store "what are your tasks for today?" text
   // creates a jLabel to store a random motivational quote from the GUI Helpers class
   JLabel quote = new JLabel(GUIHelpers.setMotivationalQuote()[new Random().nextInt(GUIHelpers.setMotivationalQuote().length)], SwingConstants.CENTER);
   JLabel title = new JLabel(GUIHelpers.getCurrentDate(), SwingConstants.CENTER); // creates a jLabel to contain the current date
   JLabel lblTextAdd = new JLabel(); // creates a jLabel to contain the label for the To-Do Text input in the Add Task panel
   JLabel lblDueDateAdd = new JLabel(); // creates a jLabel to contain the label for the Due Date input field in the Add Task panel
   JLabel lblDueTimeAdd = new JLabel(); // creates a jLabel to contain the label for the Due Time input field in the Add Task panel
   JLabel lblCategoryAdd = new JLabel(); // creates a jLabel to contain the label for the Category selection in the Add Task panel
   JLabel lblImportanceAdd = new JLabel(); // creates a jLabel to contain the label for the Importance selection in the Add Task panel
   JLabel lblID = new JLabel(); // creates a jLabel to contain the label for the ID input field in the Update Task Panel
   JLabel lblTextUpdate = new JLabel();  // creates a jLabel to contain the label for the To-Do Text input in the Update Task panel
   JLabel lblDueDateUpdate = new JLabel(); // creates a jLabel to contain the label for the Due Date input field in the Update Task panel
   JLabel lblDueTimeUpdate = new JLabel(); // creates a jLabel to contain the label for the Due Time input field in the Update Task panel
   JLabel lblCategoryUpdate = new JLabel(); // creates a jLabel to contain the label for the Category selection in the Update Task panel
   JLabel lblImportanceUpdate = new JLabel(); // creates a jLabel to contain the label for the Importance selection in the Update Task panel
   JLabel lblStatus = new JLabel(); // creates a jLabel to contain the label for the Status selection in the Update Task panel
   JLabel lblDeleteId = new JLabel(); // creates a jLabel to contain the label for the ID input field in the Delete Task Panel

   // JTEXTFIELDS
   JTextField txtID = new JTextField(); // creates a jTextfield to display a textbox for entering ID in the Update Task panel
   JTextField txtTextAdd = new JTextField(); // creates a jTextfield to display a textbox for entering a To-Do text in the Add Task panel
   JTextField txtTextUpdate = new JTextField(); // creates a jTextfield to display a textbox for entering a To-Do text in the Update Task panel
   JTextField txtDeleteId = new JTextField(); // creates a jTextfield to display a textbox for entering ID in the Delete Task panel

   // JXDATEPICKERS
   JXDatePicker dtDueDateAdd = new JXDatePicker(); // creates a jxDatePicker to store the due date in the Add Task panel
   JXDatePicker dtDueDateUpdate = new JXDatePicker(); // creates a jxDatePicker to store the due date in the Update Task panel

   // JSPINNERS
   JSpinner spDueTimeAdd = new JSpinner(); // creates a jSpinner to store the due time in the Add Task panel
   JSpinner spDueTimeUpdate = new JSpinner(); // creates a jSpinner to store the due time in the Update Task panel

   // JCOMBOBOXES
   // creates a category drop-down box which stores all the category options, allowing the user to select one in the Add Task panel
   JComboBox<String> cmbCategoryAdd = new JComboBox<>(Category.getAllCategories());
   // creates an importance drop-down box which stores all the importance options, allowing the user to select one in the Add Task panel
   JComboBox<String> cmbImportanceAdd = new JComboBox<>(Importance.getAllImportance());
   // creates a category drop-down box which stores all the category options, allowing the user to select one in the Update Task panel
   JComboBox<String> cmbCategoryUpdate = new JComboBox<>(Category.getAllCategories());
   // creates an importance drop-down box which stores all the importance options, allowing the user to select one in the Update Task panel
   JComboBox<String> cmbImportanceUpdate = new JComboBox<>(Importance.getAllImportance());
   // creates a status drop-down box which stores all the status options, allowing the user to select one in the Update Task panel
   JComboBox<String> cmbStatus = new JComboBox<>(Status.getAllStatuses());

   // JBUTTONS
   JButton homeBtn = new JButton("Home"); // creates a Home button to navigate to the home panel
   JButton addBtn = new JButton("Add Task"); // creates an Add Task button to navigate to the 'Add Task' panel
   JButton listBtn = new JButton("List Tasks"); // creates an List Tasks button to navigate to the 'List Tasks' panel
   JButton updateBtn = new JButton("Update Task"); // creates an Update Task button to navigate to the 'Update Task' panel
   JButton deleteBtn = new JButton("Delete Task"); // creates a Delete Task button to navigate to the 'Delete Task' panel
   JButton addTask = new JButton("Add Task"); // creates an Add Task button to invoke the addTask method in the ActionListeners class
   JButton updateTask = new JButton("Update Task"); // creates an Update Task button to invoke the updateTask method in the ActionListeners class
   JButton deleteTask = new JButton("Delete Task"); // creates an Delete Task button to invoke the deleteTask method in the ActionListeners class


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
         public void windowOpened(WindowEvent e) {
            super.windowOpened(e);
            setVisible(false);
            String inputName = JOptionPane.showInputDialog("Welcome","Please enter your name");
            if (inputName == null || inputName.trim().length() == 0) {
               welcomeText.setText(GUIHelpers.setGreetingMessage());
               setVisible(true);
            } else {
               welcomeText.setText(GUIHelpers.setGreetingMessage() + inputName + ",");
               setVisible(true);
            }
         }

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
      backgroundImage.add(GUIPanels.createGreetingTextPanel(greetingText, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createQuestionTextPanel(questionText, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createHorizontalLinePanel(horizontalLine, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createQuotePanel(motivationalQuote, this), BorderLayout.CENTER);
      backgroundImage.add(GUIPanels.createAddInputPanel(addInput, this), BorderLayout.NORTH); // add task
      backgroundImage.add(GUIPanels.createUpdateInputPanel(updateInput, this), BorderLayout.NORTH); // update task
      backgroundImage.add(GUIPanels.createDeleteInputPanel(deleteInput, this), BorderLayout.NORTH); // delete task
      backgroundImage.add(GUIPanels.createAddBtnPanel(addBtnPanel), BorderLayout.SOUTH); // add button
      backgroundImage.add(GUIPanels.createUpdateBtnPanel(updateBtnPanel), BorderLayout.SOUTH); // update button
      backgroundImage.add(GUIPanels.createDeleteBtnPanel(deleteBtnPanel), BorderLayout.SOUTH); // delete button
      greetingText.add(welcomeText);
      questionText.add(introText);
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
                 greetingText, true,
                 questionText, true,
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
                 greetingText, false,
                 questionText, false,
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
                 greetingText, false,
                 questionText, false,
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
                 greetingText, false,
                 questionText, false,
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
            txtID.setText("Enter a To-Do ID to Update"); // sets the text for the update ID textfield as "Enter a To-Do ID to Update" to represent a placeholder
         }
      });

      /* DISPLAY DELETE TASK PANEL */
      deleteBtn.addActionListener(e -> {
         GUIStyles.setActiveNavButton(montserrat, deleteBtn);
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), updateBtn, addBtn, listBtn, homeBtn);
         GUIHelpers.displayPanel(
                 title, "Delete Task",
                 todoTable, false,
                 greetingText, false,
                 questionText, false,
                 horizontalLine, false,
                 motivationalQuote, false,
                 addInput, false,
                 updateInput, false,
                 deleteInput, true,
                 addBtnPanel, false,
                 updateBtnPanel, false,
                 deleteBtnPanel, true
         );
         txtDeleteId.setText("Enter a To-Do ID"); // sets the text for the delete ID textfield as "Enter a To-Do ID" to represent a placeholder
      });

      // adds an action listener to the 'Add Task' button, to perform the add task operation
      addTask.addActionListener(e -> ActionEvents.addTaskPerformed(this, txtTextAdd, dtDueDateAdd,
              spDueTimeAdd, cmbCategoryAdd, cmbImportanceAdd, todoTableModel, montserrat)); // add task button event

      // adds an action listener to the 'Update Task' button, to perform the update task operation
      updateTask.addActionListener(e -> ActionEvents.updateTaskPerformed(this, txtID, txtTextUpdate,
              dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask,
              todoTableModel, montserrat)); // update task button event

      // adds an action listener to the 'Delete Task' button, to perform the delete task operation
      deleteTask.addActionListener(e -> ActionEvents.deleteTaskPerformed(this, txtDeleteId, todoTableModel)); // delete task button event
   }
}
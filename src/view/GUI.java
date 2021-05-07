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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicReference;

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
      setTitle("To-Do List App"); // sets the title of the jFrame
      setSize(1270, 790); // sets the width and height of the jFrame
      setLocationRelativeTo(null); // sets the location of the jFrame
      setLayout(new BorderLayout()); // sets the layout manager of the jFrame as a border layout
      setResizable(false); // doesn't allow the user to resize the window
      setVisible(true); // makes the jFrame visible to the user
      addWindowListener(new WindowAdapter() { // adds a window listener to the jFrame
         @Override
         public void windowOpened(WindowEvent e) { // overrides the windowOpened method to add an event before opening the app
            setVisible(false); // hides the visibility of the window
            String inputName = JOptionPane.showInputDialog("Welcome","Please enter your name"); // displays an input dialogue box, asking the user to enter their name
            if (inputName == null || inputName.trim().length() == 0 || inputName.equals("Please enter your name")) { // checks if the input is empty or contains empty whitespaces
               welcomeText.setText(GUIHelpers.setGreetingMessage()); // if this is the case, the welcome text will display "Good Morning/Afternoon/Evening" without a name
               setVisible(true); // makes the jFrame visible to the user
            } else {
               welcomeText.setText(GUIHelpers.setGreetingMessage() + inputName + ","); // otherwise, the welcome text will be displayed with the user's name
               setVisible(true); // makes the jFrame visible to the user
            }
         }

         @Override
         public void windowClosing(WindowEvent onCloseEvent) { // overrides the method for closing the app
            // upon closing the app, the user will be displayed with a confirmation dialogue box with a yes or no option
            int exitApp = JOptionPane.showConfirmDialog(getParent(),"Are you sure you wish to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            // if the user click yes, the app will close. However, if they click no the app will continue running
            setDefaultCloseOperation(exitApp == JOptionPane.YES_OPTION ? JFrame.EXIT_ON_CLOSE : JFrame.DO_NOTHING_ON_CLOSE);
         }
      });

      try { // using try and catch to register a custom font and handle errors gracefully, without breaking the app
         GraphicsEnvironment fontGraphics = GraphicsEnvironment.getLocalGraphicsEnvironment(); // returns the graphics environment to allow font registration
         montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Montserrat.ttf")); // initialises a new font from the provided file path
         fontGraphics.registerFont(montserrat); // registers the font into the graphics environment
      } catch (IOException | FontFormatException e) { // catches any input/out exception or font format exception
         System.out.println("Couldn't register this font"); // if the try block fails, the catch block will output an error message
      }

      backgroundImage.setLayout(new BoxLayout(backgroundImage, BoxLayout.Y_AXIS)); // sets the layout manager for the background image as a box layout
      GUIStyles.setHomePanelLabelForeground(welcomeText, introText, quote, title); // sets the foreground colours for all the components in the home panel,
      // using the GUIStyles helper class
      GUIStyles.setHomePanelLabelFont(title, welcomeText, introText, quote, montserrat); // sets the font styles of all the labels in the home panel,
      // using the GUIStyles helper class

      add(GUIPanels.createMainContentPanel(mainContent), BorderLayout.CENTER); // adds the mainContent jPanel to the center of the jFrame
      mainContent.add(GUIPanels.createHeaderPanel(header), BorderLayout.NORTH); // adds the header to the top of the mainContent panel
      mainContent.add(backgroundImage, BorderLayout.CENTER); // adds the background image to the center of the mainContent panel
      header.add(GUIPanels.createNavBarPanel(navBar), BorderLayout.SOUTH); // adds the navbar to the bottom of the header panel
      header.add(title, BorderLayout.CENTER); // adds the title for the navbar sections to the center of the header panel
      backgroundImage.add(Box.createRigidArea(new Dimension(100, 50))); // adds space between the top and left of the background image panel
      backgroundImage.add(GUIPanels.createTodoTablePanel(todoTable, this), BorderLayout.CENTER); // adds the todoTable to the center of the background image panel
      backgroundImage.add(GUIPanels.createGreetingTextPanel(greetingText, this), BorderLayout.CENTER); // adds the "Good Morning/Afternoon/Evening" text to the center of the background image panel
      backgroundImage.add(GUIPanels.createQuestionTextPanel(questionText, this), BorderLayout.CENTER); // adds the "What are your tasks for today?" text to the center of the background image panel
      backgroundImage.add(GUIPanels.createHorizontalLinePanel(horizontalLine, this), BorderLayout.CENTER); // adds the thick border line to the center of the background image panel
      backgroundImage.add(GUIPanels.createQuotePanel(motivationalQuote, this), BorderLayout.CENTER); // adds the motivational quote to the center of the background image panel
      backgroundImage.add(GUIPanels.createAddInputPanel(addInput, this), BorderLayout.NORTH); // adds the add task input panel to the top of the background image panel
      backgroundImage.add(GUIPanels.createUpdateInputPanel(updateInput, this), BorderLayout.NORTH); // adds the update task input panel to the top of the background image panel
      backgroundImage.add(GUIPanels.createDeleteInputPanel(deleteInput, this), BorderLayout.NORTH); // adds the delete task input panel to the top of the background image panel
      backgroundImage.add(GUIPanels.createAddBtnPanel(addBtnPanel), BorderLayout.SOUTH); // adds the 'Add Task' panel to the bottom of the background image panel
      backgroundImage.add(GUIPanels.createUpdateBtnPanel(updateBtnPanel), BorderLayout.SOUTH); // adds the 'Update Task' panel to the bottom of the background image panel
      backgroundImage.add(GUIPanels.createDeleteBtnPanel(deleteBtnPanel), BorderLayout.SOUTH); // adds the 'Delete Task' panel to the bottom of the background image panel
      greetingText.add(welcomeText); // the label which contains "Good Morning/Afternoon/Evening" is added to the greeting text panel
      questionText.add(introText); // the label which contains "What are your tasks for today?" is added to the question text panel
      motivationalQuote.add(quote); // adds the quotes label to the motivationalQuote panel
      addBtnPanel.add(addTask, BorderLayout.SOUTH); // adds the 'Add Task' button to the bottom of the addBtn panel
      updateBtnPanel.add(updateTask, BorderLayout.SOUTH); // adds the 'Update Task' button to the bottom of the updateBtn panel
      deleteBtnPanel.add(deleteTask, BorderLayout.SOUTH); // adds the 'Delete Task' button to the bottom of the deleteBtn panel
      todoTable.add(new JScrollPane(GUITables.createTodoTable(montserrat, todoTableModel))); // creates the JTable to view all the to-do list tasks as rows
      navBar.add(homeBtn); // adds the home button onto navBar panel
      navBar.add(addBtn); // adds the add button onto navBar panel
      navBar.add(listBtn); // adds the list button onto navBar panel
      navBar.add(updateBtn); // adds the update button onto navBar panel
      navBar.add(deleteBtn); // adds the delete button onto navBar panel

      // add styling to buttons
      GUIStyles.setNavButtonStyles(homeBtn);
      // since home page is the first active page upon loading, its nav button will be set to bold
      homeBtn.setFont(montserrat.deriveFont(Font.BOLD,20f)); // this must stay here, otherwise "Home" will not be bold
      GUIStyles.setNavButtonStyles(listBtn); // sets the button style for the 'List Tasks' nav button
      GUIStyles.setNavButtonStyles(addBtn); // sets the button style for the 'Add Task' nav button
      GUIStyles.setNavButtonStyles(updateBtn); // sets the button style for the 'Update Task' nav button
      GUIStyles.setNavButtonStyles(deleteBtn); // sets the button style for the 'Delete Task' nav button
      GUIStyles.setTaskButtonStyles(addTask, montserrat);  // sets the button style for the 'Add Task' action button
      GUIStyles.setTaskButtonStyles(updateTask, montserrat); // sets the button style for the 'Update Task' action button
      GUIStyles.setTaskButtonStyles(deleteTask, montserrat); // sets the button style for the 'Delete Task' action button
      GUIStyles.setLabelStyle(lblTextAdd, montserrat, "To-Do text"); // sets the label style for the To-Do text label in the add task panel
      GUIStyles.setLabelStyle(lblDueDateAdd, montserrat, "Due Date"); // sets the label style for the due date label in the add task panel
      GUIStyles.setLabelStyle(lblDueTimeAdd, montserrat, "Due Time"); // sets the label style for the due time label in the add task panel
      GUIStyles.setLabelStyle(lblCategoryAdd, montserrat, "Category"); // sets the label style for the category label in the add task panel
      GUIStyles.setLabelStyle(lblImportanceAdd, montserrat, "Importance"); // sets the label style for the importance label in the add task panel
      GUIStyles.setLabelStyle(lblID, montserrat, "To-Do ID"); // sets the label style for the To-Do ID label in the update task panel
      GUIStyles.setLabelStyle(lblTextUpdate, montserrat, "To-Do text"); // sets the label style for the To-Do text label in the update task panel
      GUIStyles.setLabelStyle(lblDueDateUpdate, montserrat, "Due Date"); // sets the label style for the due date label in the update task panel
      GUIStyles.setLabelStyle(lblDueTimeUpdate, montserrat, "Due Time"); // sets the label style for the due time label in the update task panel
      GUIStyles.setLabelStyle(lblCategoryUpdate, montserrat, "Category"); // sets the label style for the category label in the update task panel
      GUIStyles.setLabelStyle(lblImportanceUpdate, montserrat, "Importance"); // sets the label style for the importance label in the update task panel
      GUIStyles.setLabelStyle(lblStatus, montserrat, "Status"); // sets the label style for the status label in the update task panel
      GUIStyles.setLabelStyle(lblDeleteId, montserrat, "To-Do ID"); // sets the label style for the To-Do ID label in the delete task panel
      GUIStyles.setTextStyle(txtID, montserrat); // sets the text style for the To-Do ID textbox in the update task panel
      GUIStyles.setTextStyle(txtDeleteId, montserrat); // sets the text style for the To-Do ID textbox in the delete task panel
      GUIStyles.setTextStyle(txtTextAdd, montserrat); // sets the text style for the To-Do text textbox in the add task panel
      GUIStyles.setTextStyle(txtTextUpdate, montserrat); // sets the text style for the To-Do text textbox in the update task panel
      GUIStyles.setSelectionBoxStyle(cmbCategoryAdd, montserrat); // sets the style for the category drop-down box in the add task panel
      GUIStyles.setSelectionBoxStyle(cmbImportanceAdd, montserrat); // sets the style for the importance drop-down box in the add task panel
      GUIStyles.setSelectionBoxStyle(cmbCategoryUpdate, montserrat); // sets the style for the category drop-down box in the update task panel
      GUIStyles.setSelectionBoxStyle(cmbImportanceUpdate, montserrat); // sets the style for the importance drop-down box in the update task panel
      GUIStyles.setSelectionBoxStyle(cmbStatus, montserrat); // sets the style for the status drop-down box in the update task panel
      GUIStyles.setDatePickerStyle(dtDueDateAdd, montserrat); // sets the style for the date picker in the add task panel
      GUIStyles.setDatePickerStyle(dtDueDateUpdate, montserrat); // sets the style for the date picker in the update task panel
      GUIStyles.setSpinnerStyle(spDueTimeAdd, montserrat); // sets the style for the jSpinner in the add task panel
      GUIStyles.setSpinnerStyle(spDueTimeUpdate, montserrat); // sets the style for the jSpinner in the update task panel

      GridBagConstraints gridConst = new GridBagConstraints(); // creates a GridBagConstraint object to set the layout of the components
      gridConst.insets = new Insets(10, 10, 10, 10); // represents the top, left, bottom, right borders of the grid bag constraints
      gridConst.fill = GridBagConstraints.HORIZONTAL; // resizes the grid bag component horizontally

      // add task components
      GUIHelpers.setGridCoord(gridConst, 0, 0, addInput, lblTextAdd); // sets the grid coordinates for the 'To-Do Text' label in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 0, addInput, txtTextAdd); // sets the grid coordinates for the 'To-Do Text' textbox in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 1, addInput, lblDueDateAdd); // sets the grid coordinates for the 'Due Date' label in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 1, addInput, dtDueDateAdd); // sets the grid coordinates for the 'Due Date' datePicker in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 2, addInput, lblDueTimeAdd); // sets the grid coordinates for the 'Due Time' label in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 2, addInput, spDueTimeAdd); // sets the grid coordinates for the 'Due Time' jSpinner in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 3, addInput, lblCategoryAdd); // sets the grid coordinates for the 'Category' label in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 3, addInput, cmbCategoryAdd); // sets the grid coordinates for the 'Category' drop-down box in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 4, addInput, lblImportanceAdd); // sets the grid coordinates for the 'Importance' label in the addInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 4, addInput, cmbImportanceAdd); // sets the grid coordinates for the 'Importance' drop-down box in the addInput panel
      // update task components
      GUIHelpers.setGridCoord(gridConst, 0, 0, updateInput, lblID); // sets the grid coordinates for the 'To-Do ID' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 0, updateInput, txtID); // sets the grid coordinates for the 'To-Do ID' textbox in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 1, updateInput, lblTextUpdate); // sets the grid coordinates for the 'To-Do Text' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 1, updateInput, txtTextUpdate); // sets the grid coordinates for the 'To-Do Text' textbox in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 2, updateInput, lblDueDateUpdate); // sets the grid coordinates for the 'Due Date' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 2, updateInput, dtDueDateUpdate); // sets the grid coordinates for the 'Due Date' datePicker in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 3, updateInput, lblDueTimeUpdate); // sets the grid coordinates for the 'Due Time' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 3, updateInput, spDueTimeUpdate); // sets the grid coordinates for the 'Due Time' jSpinner in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 4, updateInput, lblCategoryUpdate); // sets the grid coordinates for the 'Category' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 4, updateInput, cmbCategoryUpdate); // sets the grid coordinates for the 'Category' drop-down box in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 5, updateInput, lblImportanceUpdate); // sets the grid coordinates for the 'Importance' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 5, updateInput, cmbImportanceUpdate); // sets the grid coordinates for the 'Importance' drop-down box in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 0, 6, updateInput, lblStatus); // sets the grid coordinates for the 'Status' label in the updateInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 6, updateInput, cmbStatus); // sets the grid coordinates for the 'Status' drop-down box in the updateInput panel
      // delete task components
      GUIHelpers.setGridCoord(gridConst, 0, 0, deleteInput, lblDeleteId); // sets the grid coordinates for the 'To-Do ID' label in the deleteInput panel
      GUIHelpers.setGridCoord(gridConst, 1, 0, deleteInput, txtDeleteId); // sets the grid coordinates for the 'To-Do ID' textbox in the deleteInput panel

      // disables all the components in the update panel, until a valid ID is entered
      GUIHelpers.disableUpdateInputs(txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate, cmbImportanceUpdate, cmbStatus, updateTask);
      GUITables.getTodoRows(todoTableModel); // populates the jTable

      KeyEvents.getUpdateIdKeyListener(txtID, this, txtTextUpdate, dtDueDateUpdate, spDueTimeUpdate, cmbCategoryUpdate,
              cmbImportanceUpdate, cmbStatus, updateTask, montserrat); // adds a placeholder for the update panel ID text field
      KeyEvents.getDeleteIdKeyListener(txtDeleteId); // adds a placeholder for the delete panel ID text field

      AtomicReference<Timer> timer = new AtomicReference<>(new Timer()); // creates a new timer object, to be updated atomically
      timer.get().scheduleAtFixedRate(new TimerTask() { // schedules a timer task at a fixed rate
         @Override // overrides the run method for the timer task
         public void run() {
            String currentTime = new SimpleDateFormat("HH:mma").format(new Date()); // creates a new SimpleDateFormat object and sets it in current time format
            title.setText(GUIHelpers.getCurrentDate() + "  " + currentTime); // sets the title in the home page as the current date and time, along with auto update
         }
      }, 0, 1000); // sets the delay as 0 milli-seconds and period as 1000 milli-seconds

      /* DISPLAY HOME PANEL */
      homeBtn.addActionListener(e -> { // adds an action to the home button, to navigate to the home panel
         GUIStyles.setActiveNavButton(montserrat, homeBtn); // sets the active nav button as bold font
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), listBtn, addBtn, updateBtn, deleteBtn); // sets the inactive nav buttons as a plain font
         GUIHelpers.displayPanel( // displays all the panels and their components for the home panel, as well as hiding the panels that are not needed
                 title, GUIHelpers.getCurrentDate(), // displays the title as the current data
                 todoTable, false, // hides the to-do list table
                 greetingText, true, // displays the "Good Morning/Afternoon/Evening" text
                 questionText, true, // displays the "what are your tasks for today?" text
                 horizontalLine, true, // displays the solid horizontal line
                 motivationalQuote, true, // displays the motivational quotes
                 addInput, false, // hides input fields for adding a task
                 updateInput, false, // hides input fields for updating a task
                 deleteInput, false, // hides input fields for deleting a task
                 addBtnPanel, false, // hides the 'Add Task' button
                 updateBtnPanel, false, // hides the 'Update Task' button
                 deleteBtnPanel, false // hides the 'Delete Task' button
         );

         timer.set(new Timer()); // resets the timer object, by re-instantiating it
         timer.get().scheduleAtFixedRate(new TimerTask() { // schedules a timer task at a fixed rate
            @Override // overrides the run method for the timer task
            public void run() {
               String currentTime = new SimpleDateFormat("HH:mma").format(new Date()); // creates a new SimpleDateFormat object and sets it in current time format
               title.setText(GUIHelpers.getCurrentDate() + "  " + currentTime); // sets the title in the home page as the current date and time, along with auto update
            }
         }, 0, 1000); // sets the delay as 0 milli-seconds and period as 1000 milli-seconds
      });

      /* DISPLAY ADD TASK PANEL */
      addBtn.addActionListener(e -> { // adds an action to the add button, to navigate to the 'Add Task' panel
         timer.get().cancel(); // stops the timer
         GUIStyles.setActiveNavButton(montserrat, addBtn); // sets the active nav button as bold font
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), listBtn, homeBtn, updateBtn, deleteBtn); // sets the inactive nav buttons as a plain font
         GUIHelpers.displayPanel( // displays all the panels and their components for the addBtn panel, as well as hiding the panels that are not needed
                 title, "Add Task", // displays the title as the 'Add Task'
                 todoTable, false, // hides the to-do list table
                 greetingText, false, // hides the "Good Morning/Afternoon/Evening" text
                 questionText, false, // hides the "what are your tasks for today?" text
                 horizontalLine, false, // hides the solid horizontal line
                 motivationalQuote, false, // hides the motivational quotes
                 addInput, true, // displays input fields for adding a task
                 updateInput, false, // hides input fields for updating a task
                 deleteInput, false, // hides input fields for deleting a task
                 addBtnPanel, true, // displays the 'Add Task' button
                 updateBtnPanel, false, // hides the 'Update Task' button
                 deleteBtnPanel, false // hides the 'Delete Task' button
         );
      });

      /* DISPLAY LIST TASKS PANEL */
      listBtn.addActionListener(e -> { // adds an action to the list button, to navigate to the 'List Tasks' panel
         timer.get().cancel(); // stops the timer
         GUIStyles.setActiveNavButton(montserrat, listBtn); // sets the active nav button as bold font
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), homeBtn, addBtn, updateBtn, deleteBtn); // sets the inactive nav buttons as a plain font
         GUIHelpers.displayPanel( // displays all the panels and their components for the listBtn panel, as well as hiding the panels that are not needed
                 title, "List Tasks", // displays the title as the 'List Tasks'
                 todoTable, true, // displays the to-do list table
                 greetingText, false, // hides the "Good Morning/Afternoon/Evening" text
                 questionText, false, // hides the "what are your tasks for today?" text
                 horizontalLine, false, // hides the solid horizontal line
                 motivationalQuote, false, // hides the motivational quotes
                 addInput, false, // hides input fields for adding a task
                 updateInput, false, // hides input fields for updating a task
                 deleteInput, false, // hides input fields for deleting a task
                 addBtnPanel, false, // hides the 'Add Task' button
                 updateBtnPanel, false, // hides the 'Update Task' button
                 deleteBtnPanel, false // hides the 'Delete Task' button
         );
      });

      /* DISPLAY UPDATE TASK PANEL */
      updateBtn.addActionListener(e -> { // adds an action to the update button, to navigate to the 'Update Task' panel
         timer.get().cancel(); // stops the timer
         GUIStyles.setActiveNavButton(montserrat, updateBtn); // sets the active nav button as bold font
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), addBtn, listBtn, homeBtn, deleteBtn); // sets the inactive nav buttons as a plain font
         GUIHelpers.displayPanel( // displays all the panels and their components for the updateBtn panel, as well as hiding the panels that are not needed
                 title, "Update Task", // displays the title as the 'Update Task'
                 todoTable, false, // hides the to-do list table
                 greetingText, false, // hides the "Good Morning/Afternoon/Evening" text
                 questionText, false, // hides the "what are your tasks for today?" text
                 horizontalLine, false, // hides the solid horizontal line
                 motivationalQuote, false, // hides the motivational quotes
                 addInput, false, // hides input fields for adding a task
                 updateInput, true, // displays input fields for updating a task
                 deleteInput, false, // hides input fields for deleting a task
                 addBtnPanel, false, // hides the 'Add Task' button
                 updateBtnPanel, true, // displays the 'Update Task' button
                 deleteBtnPanel, false // hides the 'Delete Task' button
         );
         // displays the placeholder text in the update panel, each time the app is loaded
         if (txtID.getText().equals("")) { // checks if the update ID textfield is empty
            txtID.setText("Enter a To-Do ID to Update"); // sets the text for the update ID textfield as "Enter a To-Do ID to Update" to represent a placeholder
         }
      });

      /* DISPLAY DELETE TASK PANEL */
      deleteBtn.addActionListener(e -> { // adds an action to the delete button, to navigate to the 'Delete Task' panel
         timer.get().cancel(); // stops the timer
         GUIStyles.setActiveNavButton(montserrat, deleteBtn); // sets the active nav button as bold font
         GUIStyles.setInActiveNavButtons(new Font("Arial",Font.PLAIN,20), updateBtn, addBtn, listBtn, homeBtn); // sets the inactive nav buttons as a plain font
         GUIHelpers.displayPanel( // displays all the panels and their components for the deleteBtn panel, as well as hiding the panels that are not needed
                 title, "Delete Task", // displays the title as the 'Delete Task'
                 todoTable, false, // hides the to-do list table
                 greetingText, false, // hides the "Good Morning/Afternoon/Evening" text
                 questionText, false, // hides the "what are your tasks for today?" text
                 horizontalLine, false, // hides the solid horizontal line
                 motivationalQuote, false, // hides the motivational quotes
                 addInput, false, // hides input fields for adding a task
                 updateInput, false, // hides input fields for updating a task
                 deleteInput, true, // displays input fields for deleting a task
                 addBtnPanel, false, // hides the 'Add Task' button
                 updateBtnPanel, false, // hides the 'Update Task' button
                 deleteBtnPanel, true // displays the 'Delete Task' button
         );
         // displays the placeholder text in the delete panel, each time the app is loaded
         if (txtDeleteId.getText().equals("")) { // checks if the delete ID textfield is empty
            txtDeleteId.setText("Enter a To-Do ID"); // sets the text for the delete ID textfield as "Enter a To-Do ID" to represent a placeholder
         }
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
package model;

import controller.Category;
import controller.Importance;
import controller.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TodoDB {
   public static final String TODO_TABLE = "todoList"; // represents the names of the table in the database
   public static final String TODO_ID = "_id"; // represents the ID column of the todoList table
   public static final String TODO_NAME = "name"; // represents the name column of the todoList table
   public static final String TODO_DUE_DATE = "dueDate"; // represents the dueDate column of the todoList table
   public static final String TODO_CATEGORY = "category"; // represents the category column of the todoList table
   public static final String TODO_IMPORTANCE = "importance"; // represents the importance column of the todoList table
   public static final String TODO_STATUS = "status"; // represents the status column of the todoList table

   public static final int INDEX_TODO_ID = 1; // represents the index of the ID column of the todoList table
   public static final int INDEX_TODO_NAME = 2; // represents the index of the name column of the todoList table
   public static final int INDEX_TODO_DUE_DATE = 3; // represents the index of the dueDate column of the todoList table
   public static final int INDEX_TODO_CATEGORY = 4; // represents the index of the category column of the todoList table
   public static final int INDEX_TODO_IMPORTANCE = 5; // represents the index of the importance column of the todoList table
   public static final int INDEX_TODO_STATUS = 6; // represents the status index of the column of the todoList table

   // creates the table in the database, if it doesn't already exist
   public static final String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE + " (" +
           TODO_ID + " integer primary key autoincrement, " + // creates a primary key id
           TODO_NAME + " text, " + // creates a name text field to store all the todo names
           TODO_DUE_DATE + " text, " + // creates a due date text field to store all the todo due dates
           TODO_CATEGORY + " text, " + // creates a category text field to store all the todo categories
           TODO_IMPORTANCE + " text, " + // creates an importance text field to store all the todo importance
           TODO_STATUS + " text" + ")"; // creates a status text field to store all the todo status

   public static final String insertTodoQuery = "INSERT INTO " + TODO_TABLE + '(' + // inserts into the todoList table
           TODO_NAME + ", " + // inserts into the name column
           TODO_DUE_DATE + ", " + // inserts into the dueDate column
           TODO_CATEGORY + ", " + // inserts into the category column
           TODO_IMPORTANCE + ", " + // inserts into the importance column
           TODO_STATUS + // inserts into the status column
           " ) VALUES(?, ?, ?, ?, ?)"; // uses user input as values parameter to prevent SQL injection attacks

   public static final String selectTableQuery = "SELECT * FROM " + TODO_TABLE; // query to select all elements from the todoList table
   public static final String selectIdQuery = "SELECT _id AS identifier FROM " + TODO_TABLE; // query to select all id's from the todoList table
   public static final String rowCountQuery = "SELECT COUNT(*) AS count FROM " + TODO_TABLE; // query to select the total number of rows from the todoList table

   public static final String DATA_SOURCE = "todo.db"; // represents the source of the backend data, e.g. the database
   public static final String DATA_CONNECTION = "jdbc:sqlite:datapath/" + DATA_SOURCE; // represents the connection to the database via the jdbc driver

   private PreparedStatement insertIntoTodo; // creates a prepared statement object for executing a pre-compiled SQL statement
   private Connection dbConnect; // creates an instance of the database connection

   // this method will check if a database connection has been established
   public boolean isConnected() {
      try {
         dbConnect = DriverManager.getConnection(DATA_CONNECTION); // gets the database connection from the source of the database
         insertIntoTodo = dbConnect.prepareStatement(insertTodoQuery); // initialises the prepared statement object for executing parameterised SQL insert into the database
         return true; // if a connection is established, the method will return true
      } catch(SQLException e) {
         // otherwise an error message will be displayed
         System.out.println("Couldn't connect to todo database: " + e.getMessage());
         return false; // if the try statement fails, the catch statement will return false
      }
   }

   // this method will close the database connection
   public void closeConnection() {
      try {
         if (insertIntoTodo != null) { // checks if the insertIntoTodo preparedStatement doesn't contain any null queries
            insertIntoTodo.close(); // if the condition is met, the statement will close
         }
         if(dbConnect != null) { // checks if the database connection is not null
            dbConnect.close(); // if the condition is met, the database connection will close
         }
      } catch(SQLException e) {
         // if the try statement fails, the catch statement will output an error message
         System.out.println("Couldn't close connection: " + e.getMessage());
      }
   }

   // this method will execute the createTableQuery statement to create a table in the database
   public void createTable() {
      try (Statement statement = dbConnect.createStatement()) {
         statement.execute(createTableQuery); // executes the createTableQuery using a statement object
      } catch (SQLException e) {
         // if the try statement fails, the catch statement will output an error message
         System.out.println("Could not create Todo Table" + e.getMessage());
      }
   }

   // this method will execute the selectTableQuery statement to select all the data in a table
   public ArrayList<Todo> queryTodoList() {
      try(Statement statement = dbConnect.createStatement();
          // executes the createTableQuery using a statement object
          // and stores the results in a resultSet object
          ResultSet results = statement.executeQuery(selectTableQuery)) {
         ArrayList<Todo> todoList = new ArrayList<>(); // creates an arraylist of type todo to store the todo-lists

         // iterates through the result set to get each result
         while (results.next()) {
            int taskID = results.getInt(INDEX_TODO_ID); // sets the task ID as the results ID index
            String text = results.getString(INDEX_TODO_NAME); // sets the text as the results name index
            LocalDateTime dueDate = LocalDateTime.parse(results.getString(INDEX_TODO_DUE_DATE)); // sets the due date as the results due date index
            Category category = Category.valueOf(results.getString(INDEX_TODO_CATEGORY)); // sets the category as the results category index
            Importance importance = Importance.valueOf(results.getString(INDEX_TODO_IMPORTANCE)); // sets the importance as the results importance index
            Status status = Status.valueOf(results.getString(INDEX_TODO_STATUS)); // sets the status as the results status index
            todoList.add(new Todo(taskID, text, dueDate, category, importance, status)); // adds the properties to the arraylist as a new Todo object
         }
         return todoList; // returns the arraylist
      } catch (SQLException e) {
         // if the try statement fails, the catch statement will output an error message and return null
         System.out.println("Table Selection Query Failed: " + e.getMessage());
         return null;
      }
   }

   // this method will execute the selectIdQuery statement to retrieve all the ID numbers from the database
   public ArrayList<Integer> getAllTodoID() {
      try (Statement statement = dbConnect.createStatement();
           ResultSet results = statement.executeQuery(selectIdQuery)) { // executes the selectIdQuery using a statement object
         ArrayList<Integer> todoIDs = new ArrayList<>(); // creates an arraylist of type todo, to store all the IDs
         int ID; // creates an integer to store an ID
         while (results.next()) { // loops through the resultSet
            ID = results.getInt("identifier"); // stores the resultSets ID into the ID integer
            todoIDs.add(ID); // the arraylist then adds the ID
         }
         return todoIDs; // returns the arraylist
      } catch (SQLException e) {
         // if the try statement fails, the catch statement will output an error message and return null
         System.out.println("ID Selection Query Failed: " + e.getMessage());
         return null;
      }
   }

   // this method will return the total number of rows in the todoList table
   public int getTodoCount() {
      try (Statement statement = dbConnect.createStatement();
           // executes the rowCountQuery using a statement object
           ResultSet results = statement.executeQuery(rowCountQuery)) { // stores the result of the execution inside a resultSet
         return results.getInt("count"); // returns the result as an integer
      } catch (SQLException e) {
         // if the try statement fails, the catch statement will output an error message and return -1
         System.out.println("Row Count Query Failed: " + e.getMessage());
         return -1;
      }
   }

   // this method will return all the fields stored in a specific of column of the table, which also contains a specific ID
   public String getTodoColumns(String columnName, int ID) {
      try (Statement statement = dbConnect.createStatement(); // executes the getTodoColumns query using a statement object
           // stores the result of the statement execution inside of a resultSet
           ResultSet results = statement.executeQuery("SELECT " + columnName + " AS item FROM " + TODO_TABLE + " WHERE " + TODO_ID + " = " + ID)) {
         return results.getString("item"); // returns the result as a string
      } catch (SQLException e) {
         // if the try statement fails, the catch statement will output an error message and return null
         System.out.println("Column Name Query Execution failed: " + e.getMessage());
         return null;
      }
   }

   // this method will execute the insert statement, to insert a record into the database
   public void insertTodo(String text, String dueDate, String category, String importance) throws SQLException {
      insertIntoTodo.setString(1, text); // sets the first parameter of the prepared statement as the To-Do text
      insertIntoTodo.setString(2, dueDate); // sets the second parameter of the prepared statement as the Due Date
      insertIntoTodo.setString(3, category); // sets the third parameter of the prepared statement as the Category
      insertIntoTodo.setString(4, importance); // sets the fourth parameter of the prepared statement as the Importance
      insertIntoTodo.setString(5, String.valueOf(Status.Pending)); // sets the fifth parameter of the prepared statement as the Status
      int rowsAffected = insertIntoTodo.executeUpdate(); // stores the execution of the preparedStatement inside an integer to see if a row has been affected (should be 1)
      if(rowsAffected != 1) throw new SQLException("Unable to insert To-Do task"); // if the rows haven't been affected, an SQL exception error will be thrown
   }

   // this method will execute the update query, to update a record in the database
   public void updateTodo(String todoColumn, String setTodo, int ID) throws SQLException {
      Statement statement = dbConnect.createStatement(); // stores the database connection inside a statement object
      // executes the statement to update a record from where the ID is specified
      statement.execute("UPDATE " + TODO_TABLE + " SET " + todoColumn + " = '" + setTodo + "'" + " WHERE " + TODO_ID + " = " + ID + "");
   }

   // this method will execute the delete query, to delete a record from the database
   public void deleteTodo(int ID) throws SQLException {
      Statement statement = dbConnect.createStatement(); // stores the database connection inside a statement object
      statement.execute("DELETE FROM " + TODO_TABLE + " WHERE " + TODO_ID + "=" + ID); // executes the statement to delete a record from where the ID is specified
      statement.execute("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE NAME = '" + TODO_TABLE + "'"); // updates the sqlite sequence to 0, to reset the auto increment
   }
}
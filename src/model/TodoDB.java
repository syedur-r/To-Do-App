package model;

import controller.Category;
import controller.Importance;
import controller.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TodoDB {
   public static final String DATA_SOURCE = "todo.db"; // represents the source of the backend data, e.g. the database
   public static final String DATA_CONNECTION = "jdbc:sqlite:datapath/" + DATA_SOURCE; // represents the connection to the database via the jdbc driver

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

   public static final String selectTableQuery = "SELECT * FROM " + TODO_TABLE; // query to select all elements from the todoList table
   public static final String selectIdQuery = "SELECT _id AS identifier FROM " + TODO_TABLE; // query to select all id's from the todoList table
   public static final String rowCountQuery = "SELECT COUNT(*) AS count FROM " + TODO_TABLE; // query to select the total number of rows from the todoList table

   private Connection dbConnect; // creates an instance of the database connection

   // this method will check if a database connection has been established
   public boolean isConnected() {
      try {
         dbConnect = DriverManager.getConnection(DATA_CONNECTION); // gets the database connection from the source of the database
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
            Todo todo = new Todo(); // creates an object of type todo called todo
            todo.setTaskID(results.getInt(INDEX_TODO_ID)); // sets the todo ID as the results ID index
            todo.setText(results.getString(INDEX_TODO_NAME)); // sets the todo name as the results ID index
            todo.setDue(LocalDateTime.parse(results.getString(INDEX_TODO_DUE_DATE))); // sets the todo due date as the results due date index
            todo.setCat(Category.valueOf(results.getString(INDEX_TODO_CATEGORY))); // sets the todo category as the results category index
            todo.setImportance(Importance.valueOf(results.getString(INDEX_TODO_IMPORTANCE))); // sets the todo importance as the results importance index
            todo.setCompletion(Status.valueOf(results.getString(INDEX_TODO_STATUS))); // sets the todo status as the results status index
            todoList.add(todo); // adds the todo object to the arraylist
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
      Statement statement = dbConnect.createStatement(); // executes the getTodoColumns query using a statement object
      // executes the insert statement
      statement.execute("INSERT INTO " + TODO_TABLE + " (" + // inserts into todoList
              TODO_NAME + ", " + // inserts into the name column
              TODO_DUE_DATE + ", " + // inserts into the dueDate column
              TODO_CATEGORY + ", " + // inserts into the category column
              TODO_IMPORTANCE + ", " + // inserts into the importance column
              TODO_STATUS + " ) " + // inserts into the status column
              "VALUES(" + "'" + text + "', " + // retrieves the text to be stored in the name column
              "'" + LocalDateTime.parse(dueDate) + "', " + // retrieves the dueDate to be stored in the dueDate column
              "'" + Category.valueOf(category) + "', " + // retrieves the category to be stored in the category column
              "'" + Importance.valueOf(importance) + "', " + // retrieves the importance to be stored in the importance column
              "'" + Status.Pending + "')"); // stores the status as pending by default, into the status column
   }

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
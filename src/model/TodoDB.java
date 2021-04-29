package model;

import controller.Category;
import controller.Importance;
import controller.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TodoDB {
   public static final String DATA_SOURCE = "todo.db";
   public static final String DATA_CONNECTION = "jdbc:sqlite:datapath/" + DATA_SOURCE;

   public static final String TODO_TABLE = "todoList";
   public static final String TODO_ID = "_id";
   public static final String TODO_NAME = "name";
   public static final String TODO_DUE_DATE = "dueDate";
   public static final String TODO_CATEGORY = "category";
   public static final String TODO_IMPORTANCE = "importance";
   public static final String TODO_STATUS = "status";

   public static final int INDEX_TODO_ID = 1;
   public static final int INDEX_TODO_NAME = 2;
   public static final int INDEX_TODO_DUE_DATE = 3;
   public static final int INDEX_TODO_CATEGORY = 4;
   public static final int INDEX_TODO_IMPORTANCE = 5;
   public static final int INDEX_TODO_STATUS = 6;

   public static final String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TODO_TABLE + " (" + TODO_ID + " integer primary key autoincrement, " +
           TODO_NAME + " text, " +
           TODO_DUE_DATE + " text, " +
           TODO_CATEGORY + " text, " +
           TODO_IMPORTANCE + " text, " +
           TODO_STATUS + " text" + ")";

   public static final String selectTableQuery = "SELECT * FROM " + TODO_TABLE;
   public static final String selectIdQuery = "SELECT _id AS identifier FROM " + TODO_TABLE;
   public static final String rowCountQuery = "SELECT COUNT(*) AS count FROM " + TODO_TABLE;

   private Connection dbConnect;

   public boolean openConnection() {
      try {
         dbConnect = DriverManager.getConnection(DATA_CONNECTION);
         return true;
      } catch(SQLException e) {
         System.out.println("Couldn't connect to todo database: " + e.getMessage());
         return false;
      }
   }

   public void closeConnection() {
      try {
         if(dbConnect != null) {
            dbConnect.close();
         }
      } catch(SQLException e) {
         System.out.println("Couldn't close connection: " + e.getMessage());
      }
   }

   public void createTable() {
      try (Statement statement = dbConnect.createStatement()) {
         statement.execute(createTableQuery);
      } catch (SQLException e) {
         System.out.println("Could not create Todo Table" + e.getMessage());
         e.printStackTrace();
      }
   }

   public ArrayList<Todo> queryTodoList() {
      try(Statement statement = dbConnect.createStatement();
          ResultSet results = statement.executeQuery(selectTableQuery)) {
         ArrayList<Todo> todoList = new ArrayList<>();

         while (results.next()) {
            Todo todo = new Todo();
            todo.setTaskID(results.getInt(INDEX_TODO_ID));
            todo.setText(results.getString(INDEX_TODO_NAME));
            todo.setDue(LocalDateTime.parse(results.getString(INDEX_TODO_DUE_DATE)));
            todo.setCat(Category.valueOf(results.getString(INDEX_TODO_CATEGORY)));
            todo.setImportance(Importance.valueOf(results.getString(INDEX_TODO_IMPORTANCE)));
            todo.setCompletion(Status.valueOf(results.getString(INDEX_TODO_STATUS)));
            todoList.add(todo);
         }
         return todoList;
      } catch (SQLException e) {
         System.out.println("Table Selection Query Failed: " + e.getMessage());
         e.printStackTrace();
         return null;
      }
   }

   public ArrayList<Integer> getAllTodoID() {
      try (Statement statement = dbConnect.createStatement();
           ResultSet results = statement.executeQuery(selectIdQuery)) {
         ArrayList<Integer> todoIDs = new ArrayList<>();
         int ID;
         while (results.next()) {
            ID = results.getInt("identifier");
            todoIDs.add(ID);
         }
         return todoIDs;
      } catch (SQLException e) {
         System.out.println("ID Selection Query Failed: " + e.getMessage());
         return null;
      }
   }

   public int getTodoCount() {
      try (Statement statement = dbConnect.createStatement();
           ResultSet results = statement.executeQuery(rowCountQuery)) {
         return results.getInt("count");
      } catch (SQLException e) {
         System.out.println("Row Count Query Failed: " + e.getMessage());
         return -1;
      }
   }

   public String getTodoColumns(String columnName, int ID) {
      try (Statement statement = dbConnect.createStatement();
           ResultSet results = statement.executeQuery("SELECT " + columnName + " AS item FROM " + TODO_TABLE + " WHERE " + TODO_ID + " = " + ID)) {
         return results.getString("item");
      } catch (SQLException e) {
         System.out.println("Column Name Query Execution failed: " + e.getMessage());
         return null;
      }
   }

   public void insertTodo(String text, String dueDate, String category, String importance) throws SQLException {
      Statement statement = dbConnect.createStatement();
      statement.execute("INSERT INTO " + TODO_TABLE + " (" +
              TODO_NAME + ", " +
              TODO_DUE_DATE + ", " +
              TODO_CATEGORY + ", " +
              TODO_IMPORTANCE + ", " +
              TODO_STATUS + " ) " +
              "VALUES(" + "'" + text + "', " +
              "'" + LocalDateTime.parse(dueDate) + "', " +
              "'" + Category.valueOf(category) + "', " +
              "'" + Importance.valueOf(importance) + "', " +
              "'" + Status.Pending + "')");
   }

   public void updateTodo(String todoColumn, String setTodo, int ID) throws SQLException {
      Statement statement = dbConnect.createStatement();
      statement.execute("UPDATE " + TODO_TABLE + " SET " +
              todoColumn + "='" + setTodo + "'" +
              " WHERE " + TODO_ID + "=" + ID + "");
   }

   public void deleteTodo(int ID) throws SQLException {
      Statement statement = dbConnect.createStatement();
      statement.execute("DELETE FROM " + TODO_TABLE + " WHERE " + TODO_ID + "=" + ID); // deletes a record from where the ID is specified
      statement.execute("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE NAME = '" + TODO_TABLE + "'"); // updates the auto increment for the todoDB ID
   }
}

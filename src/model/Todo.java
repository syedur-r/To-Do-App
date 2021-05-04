package model;

import java.time.LocalDateTime;
import controller.Category;
import controller.Importance;
import controller.Status;

public class Todo {
   private int taskID; // creates a private instance for the todolist ID
   private String text; // creates a private instance for the todolist text
   private LocalDateTime due; // creates a private instance for the todolist due date
   private Category cat; // creates a private instance for the todolist category
   private Importance importance; // creates a private instance for the todolist importance
   private Status completion; // creates a private instance for the todolist completion status

   // creates a constructor for the todolist class
   Todo(int taskID, String text, LocalDateTime due, Category cat, Importance importance, Status status) {
      this.taskID = taskID; // sets the current instance of the taskID as the parameters instance of the taskID
      this.text = text; // sets the current instance of text as the parameters instance of text
      this.due = due; // sets the current instance of due as the parameters instance of due
      this.cat = cat; // sets the current instance of cat as the parameters instance of cat
      this.importance = importance; // sets the current instance of importance as the parameters instance of importance
      this.completion = status; // sets the current instance of status as the parameters instance of status
   }

   // getter method for taskID
   public int getTaskID() {
      return taskID; // returns the task ID as a string
   }

   // setter method for taskID
   public void setTaskID(int taskID) {
      this.taskID = taskID; // assigns the task ID given from the parameter onto the current instance of the task ID
   }

   public String getText() {
      // getter method for text
      return text; // returns the text as a string
   }

   // setter method for text
   public void setText(String text) {
      this.text = text; // assigns the text given from the parameter onto the current instance of text
   }

   // getter method for due
   public LocalDateTime getDue() {
      return due; // returns due as type LocalDateTime
   }

   // setter method for due
   public void setDue(LocalDateTime due) {
      this.due = due; // assigns due given from the parameter onto the current instance of due
   }

   // getter method for cat
   public Category getCat() {
      return cat; // returns cat as type controller.Category
   }

   // setter method for cat
   public void setCat(Category cat) {
      this.cat = cat; // assigns cat given from the parameter onto the current instance of cat
   }

   // getter method for importance
   public Importance getImportance() {
      return importance; // returns importance as type controller.Importance
   }

   // setter method for importance
   public void setImportance(Importance importance) {
      this.importance = importance; // assigns importance given from the parameter onto the current instance importance
   }

   // getter method for completion
   public Status getCompletion() {
      return completion; // returns completion as type controller.Status
   }

   // setter method for completion
   public void setCompletion(Status completion) {
      this.completion = completion; // assigns completion given from the parameter onto the current instance of completion
   }
}

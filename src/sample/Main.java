/***************************************************
 * Main class that runs the program and sets the stage.
 * @author Shane Smith
 */
package sample;

//imports

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private static Statement stnt;

  @Override
  //setting the default look of the page
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production Tracker");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
    initializeDB();
  }

  public static void sqlExecute(String sqlStatment) {
    try {
      stnt.executeUpdate(sqlStatment);
      System.out.println(sqlStatment);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /***************************************************
   * Connects to database.
   * @author Shane Smith
   */
  public static void initializeDB() {
    final String jdbcDriver = "org.h2.Driver";
    final String dbUrl = "jdbc:h2:./res/databas";
    final String user = "";
    final String pass = "";
    try {
      Class.forName(jdbcDriver);
      Connection conn = DriverManager.getConnection(dbUrl, user, pass);
      Statement stmt = conn.createStatement();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
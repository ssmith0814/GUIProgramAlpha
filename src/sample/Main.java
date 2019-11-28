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

  @Override
  //setting the default look of the page
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production Tracker");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
  }

  /***************************************************
   * Connects to database.
   * @author Shane Smith
   * @param sql This is the sql string that gets access to the database.
   */
  public static void initializeDB(String sql) {
    System.out.println(sql);
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/PTI";
    final String USER = "";
    final String PASS = "";
    try {
      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sql);
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
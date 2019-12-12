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
    final String jdbcDriver = "org.h2.Driver";
    final String dbUrl = "jdbc:h2:./res/PTI";
    final String user = "";
    final String pass = "";
    try {
      Class.forName(jdbcDriver);
      Connection conn = DriverManager.getConnection(dbUrl, user, pass);
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
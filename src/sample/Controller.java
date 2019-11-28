/***************************************************
 * Makes the GUI have functionality.
 * @Author: Shane Smith
 */

package sample;

//imports

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable {
  //database connectivity
  private static final String JDBC_DRIVER = "org.h2.Driver";
  private static final String DB_URL = "jdbc:h2:./res/databas";

  //adding all the FXid's for GUI
  @FXML
  private TextField EmpNameText;

  @FXML
  private TextField EmpPassText;

  @FXML
  private TableColumn<Object, Object> EmpNameCol;

  @FXML
  private TableColumn<Object, Object> EmpUserCol;

  @FXML
  private TableColumn<Object, Object> EmpEmailCol;

  @FXML
  private TableColumn<Object, Object> EmpPassCol;

  @FXML
  private Button SubmitEmpBtn;

  @FXML
  private TableView<Employee> EmpUserInfo;

  @FXML
  private Label productError;

  @FXML
  private Label ProductionError;

  @FXML
  private ChoiceBox<ItemType> ItemTypeChoiceBox;

  @FXML
  private Button AddProductButton;

  @FXML
  private Button RecordProductionsBtn;

  @FXML
  private TextField ProductNameText;

  @FXML
  private TextField ManufacturerText;

  @FXML
  private ComboBox<String> QuantityComboBox;

  //options for comboBox in the record productions tab
  private ObservableList<String> chooseQuantity =
      FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

  @FXML
  private TextArea ProductionLogTextArea;

  @FXML
  private TableView<Product> ProductLineTableView;

  @FXML
  private ListView<Product> ProduceListView;

  @FXML
  private TableColumn<Product, String> NameColumn;

  @FXML
  private TableColumn<Product, String> ManufacturerColumn;

  @FXML
  private TableColumn<Product, String> ItemTypeColumn;

  //list of products that get added to the listView and tableView
  // in the products and record production tabs
  private ObservableList<Product> productLine = FXCollections.observableArrayList();

  //list of production log that gets put into the production log tab's Text area
  private ObservableList<ProductionRecord> prodLog;

  //list of Employee objects that gets into the Employee tab's tableView
  private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

  /***************************************************
   * Initializes the production record combobox with numbers 1-10.
   * @Author: Shane Smith
   */

  private void initializeComboBox() {
    //Giving the combo box options
    QuantityComboBox.setItems(chooseQuantity);
    //Making the combo box editable
    QuantityComboBox.setEditable(true);
    //making the default selection be the first option
    QuantityComboBox.getSelectionModel().selectFirst();
  }

  /***************************************************
   * Initializes the GUI to be displayed.
   * @Author: Shane Smith
   * @param location used to initialize.
   * @param resources used to initialize.
   */

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ItemTypeChoiceBox.getItems().addAll(ItemType.values());

    //sets up the tableView for products tab
    setupProductionLineTable();
    //populates the listView with the products
    ProduceListView.setItems(productLine);
    //calls the method that populates the tableView with the products
    initializeComboBox();
    loadProductList();
    loadEmployeeList();
    hideErrorLabel();
    hideProductionError();
  }

  /***************************************************
   * Giving the add production button functionality.
   * @Author: Shane Smith
   * @param event when the button is pressed, this method gets called.
   */

  @FXML
  void AddProductBtn(ActionEvent event) {
    AddProduct();
    productLine.clear();
    loadProductList();
  }

  /***************************************************
   * Puts the text/values from the input and uploads them into the database as a Product.
   * @Author: Shane Smith
   */

  @FXML
  private void AddProduct() {
    String name = ProductNameText.getText();
    String manufacturer = ManufacturerText.getText();
    ItemType itemType = ItemTypeChoiceBox.getValue();

    try {
      if (name.equals("")) {
        name = "default";
        showErrorLabel();
      }
      if (manufacturer.equals("")) {
        manufacturer = "default";
        showErrorLabel();
      }
      if (itemType == null) {
        itemType = ItemType.AUDIO;
        showErrorLabel();
      }

      String sql = "INSERT INTO PRODUCT (TYPE, NAME, MANUFACTURER) VALUES (?, ?, ?)";

      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(DB_URL);

      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      //putting product values into database
      preparedStatement.setString(1, String.valueOf(itemType.toString()));
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, manufacturer);

      preparedStatement.executeUpdate();
      conn.close();
      preparedStatement.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    loadProductList();
  }

  /***************************************************
   * Populates tableView and listView with the list of products.
   * @Author: Shane Smith
   */

  @FXML
  private void loadProductList() {
    String sql = "SELECT NAME, MANUFACTURER, TYPE FROM PRODUCT";
    try {
      Connection conn = DriverManager.getConnection(DB_URL);
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        //retrieving info from database to display
        String Name = rs.getString("NAME");
        String Manufacturer = rs.getString("MANUFACTURER");
        String Type = rs.getString("TYPE");

        Product addProduct = new Widget(Name, Manufacturer, Type);
        productLine.add(addProduct);
        setupProductionLineTable();
      }
      conn.close();
      preparedStatement.close();
    } catch (SQLException | IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  /***************************************************
   * Tests if production input is a number or string.
   * @Author: Shane Smith
   * @param quantity input that is tested if it is a number.
   * @return returns true if the input is a number and
   *         false if it is anything else.
   */

  private boolean isInteger(String quantity) {
    final String REGEX_IS_NUM = "(\\d+)";

    Pattern pattern = Pattern.compile(REGEX_IS_NUM);
    return pattern.matcher(quantity).matches();
  }

  /***************************************************
   * Giving the record productions button an output.
   * @Author: Shane Smith
   * @param event when the Record buton is pressed, this method is called.
   */

  @FXML
  void RecordProductionsBtnPressed(ActionEvent event) {
    Product selectedProd = ProduceListView.getSelectionModel().getSelectedItem();
    if (selectedProd == null) {
      ProduceListView.getSelectionModel().selectFirst();
      selectedProd = ProduceListView.getSelectionModel().getSelectedItem();
    }
    String Quantity = String.valueOf(QuantityComboBox.getSelectionModel().getSelectedItem());
    int count = 0;
    ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();
    if (isInteger(Quantity)) {
      count = Integer.parseInt(Quantity);
      hideProductionError();
    } else {
      showProductionError();
    }
    for (int i = 0; i < count; i++) {
      ProductionRecord recordProd = new ProductionRecord(selectedProd, i);
      productionRun.addAll(recordProd);
      addToProductionDB(recordProd);
      loadProductionLog();
    }
  }

  /***************************************************
   * Populates Production Log with production record.
   * @Author: Shane Smith
   */

  private void showProduction() {
    ProductionLogTextArea.appendText(prodLog + "\n");
  }

  /***************************************************
   * Adding production record info into database.
   * @Author: Shane Smith
   * @param productionRecord object that is getting recorded into the database.
   */

  private void addToProductionDB(ProductionRecord productionRecord) {
    int id = productionRecord.getProductID();
    String serialNum = productionRecord.getSerialNumber();
    Timestamp dateProduced = new Timestamp(productionRecord.getDateProduced().getTime());

    try {
      //putting Production info into the database
      String sql = "INSERT INTO PRODUCTIONRECORD (PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
          + " VALUES (?, ?, ?)";
      Connection conn = DriverManager.getConnection(DB_URL);
      //FindBugs says this may fail to clean up java.sql.Statement
      PreparedStatement preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, serialNum);
      preparedStatement.setTimestamp(3, dateProduced);
      preparedStatement.executeUpdate();
      conn.close();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***************************************************
   * preps the production record info for the textArea in the production log tab.
   * @Author: Shane Smith
   */

  private void loadProductionLog() {
    int prodNum;
    int prodID;
    String prodSerial;
    Timestamp prodDateProduced;

    String sql = "SELECT * FROM PRODUCTIONRECORD";
    try {
      Connection conn = DriverManager.getConnection(DB_URL);
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        //retrieving info from database to be displayed
        prodNum = rs.getInt("PRODUCTION_NUM");
        prodID = rs.getInt("PRODUCT_ID");
        prodSerial = rs.getString("SERIAL_NUM");
        prodDateProduced = rs.getTimestamp("DATE_PRODUCED");
        ProductionRecord recordProd = new ProductionRecord(prodNum, prodID, prodSerial,
            prodDateProduced);

        prodLog = FXCollections.observableArrayList();
        prodLog.setAll(recordProd);
      }
      conn.close();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    showProduction();
  }

  /***************************************************
   * Sets up columns in the tableView in the productLine tab.
   * @Author: Shane Smith
   */

  private void setupProductionLineTable() {
    NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    ItemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
    ProductLineTableView.setItems(productLine);
  }

  /***************************************************
   * Giving the submit button in the Employee tab functionality.
   * @Author: Shane Smith
   * @param event when the Submit button is pressed, this method is called.
   */

  @FXML
  void SubmitEmpBtnPressed(ActionEvent event) throws SQLException {
    addEmployee();
    employeeList.clear();
    loadEmployeeList();
  }

  /***************************************************
   * Adds an Employee object into the database.
   * @Author: Shane Smith
   */

  private void addEmployee() throws SQLException {
    String name = EmpNameText.getText();
    String pass = EmpPassText.getText();
    Employee emp = new Employee(name, pass);
    String empName = emp.getName();
    String empEmail = emp.getEmail();
    String empUsername = emp.getUsername();
    String empPass = emp.getPassword();

    String sql;
    Connection conn = null;
    PreparedStatement preparedStatement;

    try {
      //adds info for employee object into database
      sql = "INSERT INTO EMPLOYEES (NAME, USERNAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
      conn = DriverManager.getConnection(DB_URL);
      //FindBugs says this may fail to clean up java.sql.Statement
      preparedStatement = conn.prepareStatement(sql);

      try {
        preparedStatement.setString(1, empName);
        preparedStatement.setString(2, empUsername);
        preparedStatement.setString(3, empEmail);
        preparedStatement.setString(4, empPass);
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
    loadEmployeeList();
  }

  /***************************************************
   * Takes Employee objects from the database to be displayed.
   * @Author: Shane Smith
   */

  private void loadEmployeeList() {
    String sql = "SELECT * FROM EMPLOYEES";
    try {
      Connection conn = DriverManager.getConnection(DB_URL);
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        //retrieves info from database to be displayed
        String empName = rs.getString("NAME");
        String empUsername = rs.getString("USERNAME");
        String empEmail = rs.getString("EMAIL");
        String empPassword = rs.getString("PASSWORD");

        Employee emp = new Employee(empName, empUsername, empEmail, empPassword);
        employeeList.addAll(emp);
        setupEmployeeLineTable();
      }
      conn.close();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /***************************************************
   * Setting up columns fpr the Employee tab's tableView.
   * @Author: Shane Smith
   */

  private void setupEmployeeLineTable() {
    EmpNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    EmpUserCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
    EmpEmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
    EmpPassCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
    EmpUserInfo.setItems(employeeList);
  }

  /***************************************************
   * Reversing a string using recursion.
   * @Author: Shane Smith
   * @param id This is the string that gets reversed.
   * @return the string gets returned in reverse.
   */

  private String reverseString(String id) {
    // Paste the code for your reverseString method here.
    if (id.length() <= 1) {
      return id;
    } else {
      return reverseString(id.substring(1)) + id.charAt(0);
    }
  }

  /***************************************************
   * Displays an error when there is an unexpected input.
   * @Author: Shane Smith
   */

  private void showErrorLabel() {
    productError.setVisible(true);
    System.out.println("Please fill in all areas");
  }

  /***************************************************
   * Hides an error when there is an unexpected input.
   * @Author: Shane Smith
   */

  private void hideErrorLabel() {
    productError.setVisible(false);
  }

  /***************************************************
   * Displays an error when there is an unexpected input.
   * @Author: Shane Smith
   */

  private void showProductionError() {
    ProductionError.setVisible(true);
  }

  /***************************************************
   * Hides an error when there is an unexpected input.
   * @Author: Shane Smith
   */

  private void hideProductionError() {
    ProductionError.setVisible(false);
  }

}



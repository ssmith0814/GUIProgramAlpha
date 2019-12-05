/***************************************************
 * Makes the GUI have functionality.
 * @author Shane Smith
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
  private TextField empNameText;

  @FXML
  private TextField empPassText;

  @FXML
  private TableColumn<Object, Object> empNameCol;

  @FXML
  private TableColumn<Object, Object> empUserCol;

  @FXML
  private TableColumn<Object, Object> empEmailCol;

  @FXML
  private TableColumn<Object, Object> empPassCol;

  @FXML
  private Button submitEmpBtn;

  @FXML
  private TableView<Employee> empUserInfo;

  @FXML
  private Label productError;

  @FXML
  private Label productionError;

  @FXML
  private ChoiceBox<ItemType> itemTypeChoiceBox;

  @FXML
  private Button addProductButton;

  @FXML
  private Button recordProductionsBtn;

  @FXML
  private TextField productNameText;

  @FXML
  private TextField manufacturerText;

  @FXML
  private ComboBox<String> quantityComboBox;

  //options for comboBox in the record productions tab
  private ObservableList<String> chooseQuantity =
      FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

  @FXML
  private TextArea productionLogTextArea;

  @FXML
  private TableView<Product> productTableView;

  @FXML
  private ListView<Product> productListView;

  @FXML
  private TableColumn<Product, String> nameColumn;

  @FXML
  private TableColumn<Product, String> manufacturerColumn;

  @FXML
  private TableColumn<Product, String> itemTypeColumn;

  //list of products that get added to the listView and tableView
  // in the products and record production tabs
  private ObservableList<Product> productLine = FXCollections.observableArrayList();

  //list of production log that gets put into the production log tab's Text area
  private ObservableList<ProductionRecord> prodLog;

  //list of Employee objects that gets into the Employee tab's tableView
  private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

  /***************************************************
   * Initializes the production record combobox with numbers 1-10.
   * @author Shane Smith
   */

  private void initializeComboBox() {
    //Giving the combo box options
    quantityComboBox.setItems(chooseQuantity);
    //Making the combo box editable
    quantityComboBox.setEditable(true);
    //making the default selection be the first option
    quantityComboBox.getSelectionModel().selectFirst();
  }

  /***************************************************
   * Initializes the GUI to be displayed.
   * @author Shane Smith
   * @param location used to initialize.
   * @param resources used to initialize.
   */

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    itemTypeChoiceBox.getItems().addAll(ItemType.values());
    itemTypeChoiceBox.getSelectionModel().selectFirst();
    //sets up the tableView for products tab
    setupProductionLineTable();
    //populates the listView with the products
    productListView.setItems(productLine);
    //calls the method that populates the tableView with the products
    initializeComboBox();
    loadProductList();
    loadEmployeeList();
    hideErrorLabel();
    hideProductionError();
  }

  /***************************************************
   * Giving the add production button functionality.
   * @author Shane Smith
   * @param event when the button is pressed, this method gets called.
   */

  @FXML
  void addProductBtn(ActionEvent event) {
    addProduct();
    productLine.clear();
    loadProductList();
  }

  /***************************************************
   * Puts the text/values from the input and uploads them into the database as a Product.
   * @author Shane Smith
   */

  @FXML
  private void addProduct() {
    String name = productNameText.getText();
    String manufacturer = manufacturerText.getText();
    ItemType itemType = itemTypeChoiceBox.getValue();

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
   * @author Shane Smith
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
        String name = rs.getString("NAME");
        String manufacturer = rs.getString("MANUFACTURER");
        String type = rs.getString("TYPE");

        Product addProduct = new Widget(name, manufacturer, type);
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
   * @author Shane Smith
   * @param quantity input that is tested if it is a number.
   * @return returns true if the input is a number and
   *         false if it is anything else.
   */

  private boolean isInteger(String quantity) {
    final String regexIsNum = "(\\d+)";

    Pattern pattern = Pattern.compile(regexIsNum);
    return pattern.matcher(quantity).matches();
  }

  /***************************************************
   * Giving the record productions button an output.
   * @author Shane Smith
   * @param event when the Record buton is pressed, this method is called.
   */

  @FXML
  void recordProductionsBtnPressed(ActionEvent event) {
    Product selectedProd = productListView.getSelectionModel().getSelectedItem();
    if (selectedProd == null) {
      productListView.getSelectionModel().selectFirst();
      selectedProd = productListView.getSelectionModel().getSelectedItem();
    }
    String quantity = String.valueOf(quantityComboBox.getSelectionModel().getSelectedItem());
    int count = 0;
    ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();
    if (isInteger(quantity)) {
      count = Integer.parseInt(quantity);
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
   * @author Shane Smith
   */

  private void showProduction() {
    productionLogTextArea.appendText(prodLog + "\n");
  }

  /***************************************************
   * Adding production record info into database.
   * @author Shane Smith
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
   * @author Shane Smith
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
   * @author Shane Smith
   */

  private void setupProductionLineTable() {
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
    productTableView.setItems(productLine);
  }

  /***************************************************
   * Giving the submit button in the Employee tab functionality.
   * @author Shane Smith
   * @param event when the Submit button is pressed, this method is called.
   */

  @FXML
  void submitEmpBtnPressed(ActionEvent event) throws SQLException {
    addEmployee();
    employeeList.clear();
    loadEmployeeList();
  }

  /***************************************************
   * Adds an Employee object into the database.
   * @author Shane Smith
   */

  private void addEmployee() throws SQLException {
    String name = empNameText.getText();
    String pass = empPassText.getText();
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
   * @author Shane Smith
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
   * @author Shane Smith
   */

  private void setupEmployeeLineTable() {
    empNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    empUserCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
    empEmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
    empPassCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
    empUserInfo.setItems(employeeList);
  }

  /***************************************************
   * Reversing a string using recursion.
   * @author Shane Smith
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
   * @author Shane Smith
   */

  private void showErrorLabel() {
    productError.setVisible(true);
    System.out.println("Please fill in all areas");
  }

  /***************************************************
   * Hides an error when there is an unexpected input.
   * @author Shane Smith
   */

  private void hideErrorLabel() {
    productError.setVisible(false);
  }

  /***************************************************
   * Displays an error when there is an unexpected input.
   * @author Shane Smith
   */

  private void showProductionError() {
    productionError.setVisible(true);
  }

  /***************************************************
   * Hides an error when there is an unexpected input.
   * @author Shane Smith
   */

  private void hideProductionError() {
    productionError.setVisible(false);
  }

}



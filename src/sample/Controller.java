package sample;
//imports

/*
@Author Shane Smith
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
//database connectivity
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./res/databas";
    public Button AddProductButton;
    public Button RecordProductionsBtn;
//adding all the FXid's for GUI
    @FXML
    private ChoiceBox<ItemType> ItemTypeChoiceBox;

    @FXML
    private TextField ProductNameText;

    @FXML
    private TextField ManufacturerText;

    @FXML
    private ComboBox<String> QuantityComboBox;

    //options for comboBox in the record productions tab
    private ObservableList<String> chooseQuantity = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

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

    //list of products that get added to the listView and tableView in the products and record production tabs
    private ObservableList<Product> productLine = FXCollections.observableArrayList();

    //list of production log that gets put into the production log tab's Text area
    private ObservableList<ProductionRecord> prodLog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Giving the combo box options
        QuantityComboBox.setItems(chooseQuantity);
        //Making the combo box editable
        QuantityComboBox.setEditable(true);
        //making the default selection be the first option
        QuantityComboBox.getSelectionModel().selectFirst();
        ItemTypeChoiceBox.getItems().addAll(ItemType.values());

        //sets up the tableView for products tab
        setupProductionLineTable();
        //populates the listView with the products
        ProduceListView.setItems(productLine);
        //calls the method that populates the tableView with the products
        loadProductList();
    }

    //Giving the add production button functionality
    @FXML
    void AddProductBtn(ActionEvent event) {
        AddProduct();
        productLine.clear();
        loadProductList();
    }

    //puts the text/values from the input and uploads them into the database as a Product
    @FXML
    private void AddProduct() {
        String name = ProductNameText.getText();
        String manufacturer = ManufacturerText.getText();
        ItemType itemType = ItemTypeChoiceBox.getValue();
        try {
            String sql = "INSERT INTO PRODUCT (TYPE, NAME, MANUFACTURER) VALUES (?, ?, ?)";

            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, String.valueOf(itemType.toString()));
            stmt.setString(2, name);
            stmt.setString(3, manufacturer);

            stmt.executeUpdate();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        loadProductList();
    }

    //populates tableView and listView with the list of products
    @FXML
    private void loadProductList() {
        String sql = "SELECT NAME, MANUFACTURER, TYPE FROM PRODUCT";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String Name = rs.getString("NAME");
                String Manufacturer = rs.getString("MANUFACTURER");
                String Type = rs.getString("TYPE");

                Product addProduct = new Widget(Name, Manufacturer, Type);
                productLine.add(addProduct);
                setupProductionLineTable();
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    //Giving the record productions button an output
    @FXML
    void RecordProductionsBtnPressed(ActionEvent event) {

        Product selectedProd = ProduceListView.getSelectionModel().getSelectedItem();
        ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();

        int count = Integer.parseInt(String.valueOf(QuantityComboBox.getValue()));

        for (int i = 0; i < count; i++) {
            ProductionRecord recordProd = new ProductionRecord(selectedProd, i);
            productionRun.addAll(recordProd);
            addToProductionDB(recordProd);
            loadProductionLog();
        }
    }

    //populates Production Log with production record
    private void showProduction() {
        ProductionLogTextArea.appendText(prodLog + "\n");
    }
    //adding production record info into database
    private void addToProductionDB(ProductionRecord productionRecord) {
        int id = productionRecord.getProductID();
        String serialNum = productionRecord.getSerialNumber();
        Timestamp dateProduced = new Timestamp(productionRecord.getDateProduced().getTime());

        try {
            String sql = "INSERT INTO PRODUCTIONRECORD (PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES (?, ?, ?)";
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, serialNum);
            preparedStatement.setTimestamp(3, dateProduced);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //prep's the production record info for the textArea in the production log tab
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
                prodNum = rs.getInt("PRODUCTION_NUM");
                prodID = rs.getInt("PRODUCT_ID");
                prodSerial = rs.getString("SERIAL_NUM");
                prodDateProduced = rs.getTimestamp("DATE_PRODUCED");
                ProductionRecord recordProd = new ProductionRecord(prodNum, prodID, prodSerial, prodDateProduced);

                prodLog = FXCollections.observableArrayList();
                prodLog.setAll(recordProd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showProduction();
    }

    //sets up columns in the tableView in the product tab
    private void setupProductionLineTable() {
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
        ItemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ProductLineTableView.setItems(productLine);
    }
}



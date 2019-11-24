package sample;
//imports
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

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./res/databas";
    public Button AddProductButton;

    @FXML
    private ChoiceBox<ItemType> ItemTypeChoiceBox;

    @FXML
    private TextField ProductNameText;

    @FXML
    private TextField ManufacturerText;

    private ObservableList<String> chooseQuantity = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @FXML // fx:id="QuantityComboBox"
    private ComboBox<String> QuantityComboBox; // Value injected by FXMLLoader

    @FXML
    private TextArea ProductionLogTextArea;

    @FXML
    private TableView<Product> ProductLineTableView;

    @FXML
    private ListView<Product> ProduceListView;

    @FXML
    private TableColumn<Product, String> ProductColumn;

    @FXML
    private TableColumn<Product, String> ManufacturerColumn;

    @FXML
    private TableColumn<Product, String> ItemTypeColumn;

    private ObservableList<Product> productLine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Giving the combo box options
        QuantityComboBox.setItems(chooseQuantity);
        //Making the combo box editable
        QuantityComboBox.setEditable(true);
        //making the default selection be the first option
        QuantityComboBox.getSelectionModel().selectFirst();
        ItemTypeChoiceBox.getItems().addAll(ItemType.values());
        //testMultimedia();

        productLine = FXCollections.observableArrayList();

        setupProductionLineTable();
        ProductLineTableView.setItems(productLine);
        loadProductList();
        loadProductionLog();
    }

    @FXML
    void AddProductBtn(ActionEvent event) {
        AddProduct();
        productLine.clear();
        loadProductList();
    }

    //Giving the add production button an output
    @FXML
    private void AddProduct() {
        String name = ProductNameText.getText();
        String manufacturer = ManufacturerText.getText();
        ItemType itemType = ItemTypeChoiceBox.getSelectionModel().getSelectedItem();
        try {
            String sql = "INSERT INTO PRODUCT (TYPE, NAME, MANUFACTURER) VALUES (?, ?, ?)";

            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,String.valueOf(itemType));
            stmt.setString(2,name);
            stmt.setString(3,manufacturer);

            stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        loadProductList();
    }

    @FXML
    private void loadProductList() {


       String sql = "SELECT NAME, MANUFACTURER, TYPE FROM PRODUCT";
       try {
           Connection conn = DriverManager.getConnection(DB_URL);
           PreparedStatement preparedStatement = conn.prepareStatement(sql);
           ResultSet rs = preparedStatement.executeQuery();

           while (rs.next()) {
               String name = rs.getString("NAME");
               String manufacturer = rs.getString("MANUFACTURER");
               String type = rs.getString("TYPE");

               Product addProduct = new Widget(name, manufacturer, type);
               productLine.addAll(addProduct);
               setupProductionLineTable();
           }
       }catch (SQLException | IllegalArgumentException e){
           e.printStackTrace();
        }
   }
    //Giving the record productions button an output
    @FXML
    void RecordProductionsBtnPressed(ActionEvent event) {
        System.out.println("Production Recorded");
        //ProductionLogTextArea.setText();
    }

    void showProduction(){

    }

    void addToProductionDB(){

    }

   private void loadProductionLog(){

   }

    private void setupProductionLineTable() {
        ProductColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
        ItemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));

        ProductLineTableView.setItems(productLine);
    }

//    public static void testMultimedia() {
//        AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
//                "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
//        Screen newScreen = new Screen("720x480", 40, 22);
//        MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
//                MonitorType.LCD);
//        ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
//        productList.add(newAudioProduct);
//        productList.add(newMovieProduct);
//        for (MultimediaControl p : productList) {
//            System.out.println(p);
//            p.play();
//            p.stop();
//            p.next();
//            p.previous();
//        }
//    }


}



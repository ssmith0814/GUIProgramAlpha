package sample;
//imports
import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.h2.command.dml.Select;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./res/databas";

    @FXML
    private Button RecordProductionsBtn;

    @FXML
    private ChoiceBox<ItemType> ItemTypeChoiceBox;

    @FXML
    private TextField ProductNameText;

    @FXML
    private TextField ManufacturerText;

    private ObservableList<String> chooseQuantity = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @FXML // fx:id="QuantityComboBox"
    private ComboBox<String> QuantityComboBox; // Value injected by FXMLLoader

    //Giving the add production button an output
    @FXML
    void AddProductBtn() {
        String sql = "INSERT INTO PRODUCT (TYPE, NAME, MANUFACTURER) VALUES (?, ?, ?)";

        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,ItemTypeChoiceBox.getValue().toString());
            preparedStatement.setString(2,ProductNameText.getText());
            preparedStatement.setString(3,ManufacturerText.getText());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void listProducts() {
        String printList = "SELECT * FROM PRODUCT ";

        try{
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = conn.prepareStatement(printList);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                System.out.print(rs.getString(3)  + " ");
                System.out.print(rs.getString(4) + " ");
                System.out.println(rs.getString(2));
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Giving the record productions button an output
    @FXML
    void RecordProductionsBtnPressed(ActionEvent event) {
        System.out.println("Production Recorded");
    }

    @FXML
    void AddProductBtnPressed(ActionEvent event){
        AddProductBtn();
        listProducts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Giving the combo box options
        QuantityComboBox.setItems(chooseQuantity);
        //Making the combo box editable
        QuantityComboBox.setEditable(true);
        //making the default selection be the first option
        QuantityComboBox.getSelectionModel().selectFirst();
        ItemTypeChoiceBox.getItems().addAll(ItemType.values());
    }
}



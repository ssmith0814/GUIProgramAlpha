package sample;
//imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    private ObservableList<String> chooseQuantity = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @FXML // fx:id="AddProductBtn"
    private Button AddProductBtn; // Value injected by FXMLLoader

    @FXML // fx:id="RecordProductionsBtn"
    private Button RecordProductionsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="QuantityComboBox"
    private ComboBox<String> QuantityComboBox; // Value injected by FXMLLoader

    //Giving the add production button an output
    @FXML
    void AddProductBtnPressed(ActionEvent event) {
        System.out.println("INSERT INTO Product(type, manufacturer, name)");
    }
    //Giving the record productions button an output
    @FXML
    void RecordProductionsBtnPressed(ActionEvent event) {
        System.out.println("Production Recorded");
    }

    @FXML
    public void initialize() {
        //Giving the combo box options
        QuantityComboBox.setItems(chooseQuantity);
        //Making the combo box editable
        QuantityComboBox.setEditable(true);
        //making the default selection be the first option
        QuantityComboBox.getSelectionModel().selectFirst();
    }

}



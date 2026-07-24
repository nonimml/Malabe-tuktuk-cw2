package com.cw_malabe_tutuk2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DealerViewController {

    Inventory inventory = new Inventory();
    FileHandler fileHandler = new FileHandler();

    @FXML private TableView<Dealer> DealerTable;
    @FXML private TableColumn<Dealer, String> dealerContact;
    @FXML private TableColumn<Dealer, String> dealerId;
    @FXML private TableColumn<Dealer, String> dealerLocation;
    @FXML private TableColumn<Dealer, String> dealerName;

    @FXML private void ViewRandomDealers(){
        dealerId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        dealerContact.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        dealerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        dealerLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        fileHandler.dealerData(inventory);
        List<Dealer> sortedlist = inventory.RandomDealers();
        ObservableList<Dealer> observableList  = FXCollections.observableArrayList(sortedlist);
        DealerTable.setItems(observableList);
    }
}

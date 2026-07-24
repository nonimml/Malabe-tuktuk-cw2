package com.cw_malabe_tutuk2;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DealerViewController {

    @FXML private TableView<Dealer> DealerTable;
    @FXML private TableColumn<Dealer, String> dealerContact;
    @FXML private TableColumn<Dealer, String> dealerId;
    @FXML private TableColumn<Dealer, String> dealerLocation;
    @FXML private TableColumn<Dealer, String> dealerName;
}

package com.cw_malabe_tutuk2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    @FXML private TableView<Product> inventoryTable;
    @FXML private TableColumn<Product, String> Code;
    @FXML private TableColumn<Product, String> Name;
    @FXML private TableColumn<Product, String> Brand;
    @FXML private TableColumn<Product, Double> Price;
    @FXML private TableColumn<Product, Integer> Quantity;
    @FXML private TableColumn<Product, String> Type;
    @FXML private TableColumn<Product, String> Date;
    @FXML private TableColumn<Product, String> Image;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private TextField maxPriceInput;
    @FXML private TextField minPriceInput;
    @FXML private Label totalCountLabel;
    @FXML private Label totalValueLabel;
    @FXML private Button updateBtn;
    @FXML private TableColumn<Product,Void> addToCart;
    @FXML private TableView<Product> posTable;
    @FXML private Button clearTable;
    @FXML private TableColumn<Product, String> partCode;
    @FXML private TableColumn<Product, String> partName;
    @FXML private TableColumn<Product, Double> unitPrice;
    @FXML private TableColumn<Product, Integer> partQuantity;
    @FXML private Label subtotalLabel;
    @FXML private Label bulkDiscountLabel;
    @FXML private Label synergyDiscountLabel;
    @FXML private Label netTotalLabel;

    @FXML
    private void AddProduct(ActionEvent event) {
        try {
            MainApplication mainApplication = new MainApplication();
            mainApplication.UpdateInventory(null);
        } catch (Exception e) {
            System.out.println("can't open the Add Part Menu");
        }
    }

    @FXML private void UpdateProduct(ActionEvent event){
        try {
            Product selectedProduct = inventoryTable.getSelectionModel().getSelectedItem();

            if(event.getSource() == updateBtn){
                if(selectedProduct == null){
                    System.out.println("select the row First");
                    return;
                }
            }
            MainApplication mainApplication = new MainApplication();
            mainApplication.UpdateInventory(selectedProduct);
        }catch (Exception e){
            System.out.println("can't open the Update Menu");
        }
    }
}

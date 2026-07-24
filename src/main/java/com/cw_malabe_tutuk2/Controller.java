package com.cw_malabe_tutuk2;

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
}

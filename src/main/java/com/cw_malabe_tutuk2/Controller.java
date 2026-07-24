package com.cw_malabe_tutuk2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Inventory inventory = new Inventory();
    private FileHandler fileHandler = new FileHandler();
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

    @FXML
    private void openDealerWindow(ActionEvent event){
        try {
            MainApplication mainApplication = new MainApplication();
            mainApplication.ViewDealers();
        } catch (Exception e) {
            System.out.println("Can't open the Dealer Window");
        }
    }

    @FXML private void FilterInventory(){

        boolean isCategoryEmpty = categoryFilter.getSelectionModel().getSelectedItem() != null;
        boolean isMinPriceEmpty = !minPriceInput.getText().trim().isEmpty();
        boolean isMaxPriceEmpty = !maxPriceInput.getText().trim().isEmpty();
        if(!isCategoryEmpty && !isMinPriceEmpty && !isMaxPriceEmpty) {
            System.out.println("Filter is Empty add a filter first");
            return;
        }

        try{
            String selectedCategory = isCategoryEmpty ? categoryFilter.getSelectionModel().getSelectedItem() : null;
            double minPrice = isMinPriceEmpty ? Double.parseDouble(minPriceInput.getText()): 0.0;
            double maxPrice = isMaxPriceEmpty ? Double.parseDouble(maxPriceInput.getText()):0.0;

            List<Product> filterProduct = new ArrayList<>();

            for(Product product : inventory.getProduct()){
                boolean matchCategory = (selectedCategory == null) || product.getType().equalsIgnoreCase(selectedCategory);
                boolean matchMinPrice = product.getPrice() >= minPrice;
                boolean matchMaxPrice = product.getPrice() <= maxPrice;

                if(matchCategory && matchMinPrice && matchMaxPrice){
                    filterProduct.add(product);
                }

            }

            ObservableList<Product> observableList  = FXCollections.observableArrayList(filterProduct);
            inventoryTable.setItems(observableList);

        }catch(NullPointerException e){
            System.out.println("values can't be null");
        }
    }

    @FXML private void ResetFilters(){
        categoryFilter.getSelectionModel().clearSelection();
        minPriceInput.clear();
        maxPriceInput.clear();

        List<Product> sortedList = inventory.ViewInventory();
        ObservableList<Product> observableList  = FXCollections.observableArrayList(sortedList);
        inventoryTable.setItems(observableList);

    }

}

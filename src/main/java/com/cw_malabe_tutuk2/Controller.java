package com.cw_malabe_tutuk2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final ObservableList<Product> inventoryData = FXCollections.observableArrayList();
    private static Controller refresh;

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

    public Controller() {
        refresh = this;
    }
    public static Controller getMainController() {
        return refresh;
    }

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

            inventoryData.setAll(filterProduct);

        }catch(NullPointerException e){
            System.out.println("values can't be null");
        }
    }

    @FXML private void ResetFilters(ActionEvent event){
        categoryFilter.getSelectionModel().clearSelection();
        minPriceInput.clear();
        maxPriceInput.clear();
        refreshInventory();
    }

    @FXML private void DeletePart(ActionEvent event){
        Product deleteProduct = inventoryTable.getSelectionModel().getSelectedItem();
        if(deleteProduct == null){
            System.out.println("First Select the item you want to Delete");
            return;
        }
        inventory.getProduct().remove(deleteProduct);
        fileHandler.DataWriter(inventory);
        refreshInventory();

    }

    public void InventoryStatus(){
        loadCategory();
        double totalPrice = 0.0;
        for(int i=0;i<inventory.ViewInventory().size();i++){
            Product product = inventory.ViewInventory().get(i);
            totalPrice += (product.getPrice() * product.getQuantity());
        }

        totalCountLabel.setText("Total Parts: "+ inventory.ViewInventory().size());
        totalValueLabel.setText("Total Inventory Value: Rs. "+totalPrice);
    }

    public void loadCategory(){
        List<Product> products = inventory.getProduct();
        ObservableList<String> categories = FXCollections.observableArrayList();

        for(int i=0;i<products.size();i++){
            String category = products.get(i).getType();
            if(category != null && !categories.contains(category)){
                categories.add(category);
            }
        }
        categoryFilter.setItems(categories);
    }

    public void AddToCartColumn(){
        addToCart.setCellFactory(column -> new TableCell<Product,Void>(){
            private final TextField qtyInput = new TextField();
            private final Button addItemCart = new Button("Add");
            private final HBox container = new HBox(10,qtyInput,addItemCart);
            {
                qtyInput.setPrefWidth(50);
                qtyInput.setPromptText("qty");
                addItemCart.setOnAction(event -> {
                    try {
                        Product product = (Product) getTableRow().getItem();
                        if (product != null) {
                            if(qtyInput.getText().isEmpty()){
                                System.out.println("quantity is empty");
                                return;
                            }

                            int quantity = Integer.parseInt(qtyInput.getText().trim());

                            if (quantity < 1) {
                                System.out.println("quantity should be greater than zero");
                                return;
                            }
                            boolean itemExist = false;
                            for(int i=0;i<posTable.getItems().size();i++){
                                Product item = posTable.getItems().get(i);
                                if(item.getCode().equalsIgnoreCase(product.getCode())){
                                    itemExist = true;
                                    item.setQuantity(item.getQuantity() + quantity);
                                    posTable.getItems().set(i,item);
                                    qtyInput.clear();
                                    break;
                                }
                            }
                            if(!itemExist) {
                                Product cartItem = new Product(
                                        product.getCode(),
                                        product.getName(),
                                        product.getPrice(),
                                        quantity,
                                        product.getType());
                                posTable.getItems().add(cartItem);
                                qtyInput.clear();
                            }
                            CartTotal();
                        }
                    }catch (NumberFormatException e){
                        System.out.println("quantity should be a number not a string");
                    }
                });
                emptyProperty().addListener(
                        (value, wasEmpty, isEmpty) ->
                                setGraphic(isEmpty ? null : container)
                );
            }
        });
    }


    public void CartTotal(){
        double subTotal = 0.0;
        double bulkDiscount = 0.0;
        double synergyDiscount = 0.0;
        boolean hasEngine = false;
        boolean hasElectricalItem = false;

        for(int i= 0; i<posTable.getItems().size();i++){
            Product item = posTable.getItems().get(i);
            subTotal = item.getPrice()*item.getQuantity();
            subTotal += subTotal;

            if(item.getQuantity()>=3){
                double itemBulkDiscount = subTotal * 0.5;
                bulkDiscount += itemBulkDiscount;
            }

            if(item.getType() != null){
                String category = item.getType().trim().toUpperCase();
                if(category.equalsIgnoreCase("ENGINE")){
                    hasEngine = true;
                }
                else if (category.equalsIgnoreCase("ELECTRICAL")){
                    hasElectricalItem = true;
                }
            }
        }
        double temp = subTotal - bulkDiscount;

        if(hasEngine && hasElectricalItem){
            synergyDiscount = temp *0.10;
        }
        double netTotal = temp -  synergyDiscount;
        subtotalLabel.setText("Subtotal: Rs."+ subTotal);
        bulkDiscountLabel.setText("Bulk Discount: Rs."+ bulkDiscount);
        synergyDiscountLabel.setText("Synergy Discount: Rs."+ synergyDiscount);
        netTotalLabel.setText("Net Total: Rs."+netTotal);

    }

    public void checkOut(ActionEvent event){
        if(!posTable.getItems().isEmpty()){
            if(event.getSource() == clearTable){
                posTable.getItems().clear();
                CartTotal();
                return;
            }
            for(int i = 0; i<posTable.getItems().size();i++){
                Product cartItems = posTable.getItems().get(i);
                inventory.addToCart(cartItems);
            }
            posTable.getItems().clear();
            CartTotal();
        }else {
            System.out.println("Cart is empty load some Items");
        }
    }

    public void showImage() {
        Image.setCellValueFactory(new PropertyValueFactory<>("image"));

        Image.setCellFactory(column -> new TableCell<Product, String>() {
            ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(60);
                imageView.setFitHeight(60);
                imageView.setPreserveRatio(true);
                setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(String fileName, boolean empty) {
                super.updateItem(fileName, empty);

                if (empty || fileName == null) {
                    setGraphic(null);
                    return;
                }

                File imageFile = new File("src/main/java/com/cw_malabe_tutuk2/data/Images/" + fileName.trim());

                if (imageFile.exists()) {
                    imageView.setImage(new Image(imageFile.toURI().toString()));
                    setGraphic(imageView);
                } else {
                    setGraphic(null);
                }
            }
        });
    }


    public void loadData() {
        Code.setCellValueFactory(new PropertyValueFactory<>("code"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        showImage();

        partCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        AddToCartColumn();

        inventoryTable.setItems(inventoryData);
        refreshInventory();
    }

    public void refreshInventory() {
        fileHandler.itemsData(inventory);
        inventoryData.setAll(inventory.ViewInventory());
        InventoryStatus();
    }

}

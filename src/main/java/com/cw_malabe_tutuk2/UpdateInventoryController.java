package com.cw_malabe_tutuk2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class UpdateInventoryController {

    Inventory inventory = new Inventory();
    FileHandler fileHandler = new FileHandler();

    @FXML private TextField brand;
    @FXML private TextField codeField;
    @FXML private TextField date;
    @FXML private TextField imagePath;
    @FXML private TextField name;
    @FXML private TextField price;
    @FXML private TextField stock;
    @FXML private TextField type;
    @FXML private TextField lowStock;
    private boolean UpdateMode = false;

    @FXML private void Cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public String update(Product product,boolean updateMode){
        this.UpdateMode = updateMode;

        if (product != null) {
            codeField.setText(product.getCode());
            codeField.setDisable(true);
            brand.setText(product.getBrand());
            name.setText(product.getName());
            price.setText(String.valueOf(product.getPrice()));
            stock.setText(String.valueOf(product.getQuantity()));
            type.setText(product.getType());
            date.setText(product.getDate());
            imagePath.setText(product.getImage());
            lowStock.setText(String.valueOf(product.getMinThreshold()));
        }
        return null;
    }

    @FXML private void insert(ActionEvent event){
        fileHandler.itemsData(inventory);
        if(codeField.getText().isEmpty() || brand.getText().isEmpty() || name.getText().isEmpty()
                || price.getText().isEmpty() || stock.getText().isEmpty()
                || type.getText().isEmpty() || date.getText().isEmpty()
                || imagePath.getText().isEmpty() || lowStock.getText().isEmpty()) {
            System.out.println("text filed is empty");
            return;
        }
        try {
            String Code = readItemCode(codeField.getText());
            String Brand = brand.getText();
            String Name =   name.getText();
            double Price = readPrice(price.getText());
            int Quantity = readInt(stock.getText());
            String Type = type.getText().toUpperCase();
            String Date = readDate(date.getText());
            String ImagePath = imagePath.getText();
            int LowStock = readInt(lowStock.getText());

            Product product = new Product(Code,Brand,Name,Price,Quantity,Type,Date,ImagePath);
            product.setMinThreshold(LowStock);

            if(!this.UpdateMode){
                inventory.getProduct().add(product);
            }else{
                List<Product> products = inventory.getProduct();
                for(int i=0;i<products.size();i++){
                    if(products.get(i).getCode().equalsIgnoreCase(Code)){
                        products.set(i,product);
                    }
                }
            }
            System.out.println("Done!");
            fileHandler.DataWriter(inventory);

        }catch (Exception e){
            System.out.println("Error: Save Canceled");
        }

    }

    private String readItemCode(String itemCode) {

        if (itemCode == null || itemCode.length() != 4) {
            System.out.println("Code should have 4 character and can't be empty");
            return null;

        }
        boolean flagCodeChar = (itemCode.charAt(0) == 'P');

        boolean flagCodedigit = true;
        for (int i = 1; i < 4; i++) {
            if (!Character.isDigit(itemCode.charAt(i))) {
                flagCodedigit = false;
            }
        }

        boolean flagCodeExist = false;
        if (flagCodeChar && flagCodedigit) {
            for (int j = 0; j < inventory.getProduct().size(); j++) {
                if (itemCode.equals(inventory.getProduct().get(j).getCode())) {
                    flagCodeExist = true;
                    break;

                }
            }
        }
    }


}

package com.cw_malabe_tutuk2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class UpdateInventoryController {

    Inventory inventory = new Inventory();
    FileHandler fileHandler = new FileHandler();
    Controller main = Controller.getMainController();

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
    private boolean isError = false;

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
            showAlert("text filed is empty","Insert Error");
            return;
        }
        try {
            String Code = readItemCode(codeField.getText());
            String Brand = brand.getText();
            String Name = name.getText();
            double Price = readPrice(price.getText());
            int Quantity = readInt(stock.getText());
            String Type = type.getText().toUpperCase();
            String Date = readDate(date.getText());
            String ImagePath = AddImage(imagePath.getText());
            int LowStock = readInt(lowStock.getText());
            if(!isError) {
                Product product = new Product(Code, Brand, Name, Price, Quantity, Type, Date, ImagePath,LowStock);
                if (!this.UpdateMode) {
                    inventory.getProduct().add(product);
                    fileHandler.AuditLogger("ADD_PRODUCT",Code,Quantity);
                } else {
                    List<Product> products = inventory.getProduct();
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getCode().equalsIgnoreCase(Code)) {
                            products.set(i, product);
                        }
                    }
                    fileHandler.AuditLogger("UPDATE_PRODUCT",Code,Quantity);
                }
                fileHandler.DataWriter(inventory);
                main.refreshInventory();
                System.out.println("successful write data!");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

            }
        }catch (Exception e){
            showAlert("Save Canceled! ","Insert Error");
            isError = true;
        }

    }

    private String readItemCode(String itemCode) {

        if (itemCode == null || itemCode.length() != 4) {
            showAlert("Code should have 4 character and can't be empty","Item Code Error");
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
        if (!flagCodeChar) {
            showAlert("Code should contain 'P' as the first character", "Item Code Error");
            isError = true;
            return null;
        } else if (!flagCodedigit) {
            showAlert("code should contain 3 digits after the 'P' ", "Item Code Error");
            isError = true;
            return null;
        } else if (flagCodeExist) {
            showAlert("Code already exists in inventory", "Item Code Error");
            isError = true;
            return null;
        }
        return itemCode;

    }


    private double readPrice(String value){
        try {
            Double Price =  Double.parseDouble(value);
            if(!(Price < 1)){
                return Price;
            }
            isError = true;
            showAlert("Price can't be Negative","Price Error");
        } catch (Exception e) {
            isError = true;
            showAlert("Enter a number not a String","Price Error");
        }
        return 0;
    }

    private Integer readInt(String value){
        try {
            int Number = Integer.parseInt(value);
            if(!(Number < 0)){
                return Number;
            }
            isError = true;
            showAlert("The Value can't be negative","Value Error");
        } catch (Exception e) {
            showAlert("Enter a numerical value not a String","Value Error");
            isError = true;
        }
        return 0;
    }

    private String readDate(String date) {
        String[] dateValues;

        if (!date.contains("/") || date.length() != 10){
            showAlert("invalid format : formate DD/MM/YYYY","Date Error");
            isError = true;
            return null;
        }
        dateValues = date.split("/");

        if (dateValues[0].length() != 2 || dateValues[1].length() != 2 || dateValues[2].length() != 4 ) {
            showAlert("invalid format date should be: DD/MM/YYYY","Date Error");
            isError = true;
            return null;
        }

        try{
            int day = Integer.parseInt(dateValues[0]);
            int month = Integer.parseInt(dateValues[1]);
            int year = Integer.parseInt(dateValues[2]);

            if(month < 1 || month > 12){
                showAlert("months can only be (1-12)","Date Error");
                isError = true;
                return null;
            }
            else if (year < 2000 || year > 2100){
                showAlert("year is invalid it should be 2000-2100","Date Error");
                isError = true;
                return null;
            }

            int[] monthdays = {31,28,31,30,31,30,31,30,31,30,31,30};

            for(int i = 0;i<monthdays.length;i++) {

                if ((month - 1) == i) {
                    if ((i == 2) && (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                        if (day < 1 || day > 29){
                            showAlert(" (leap year) on February days can only be (1-29) ","Date Error");
                            isError = true;
                            return null;

                        }

                    }

                    else{
                        if (day < 1 || day > monthdays[i]) {
                            showAlert("  month and days aren't matching ","Date Error");
                            isError = true;
                            return null;
                        }
                    }
                    break;
                }
            }
            return date;

        }catch (NumberFormatException e){
            showAlert("date can't contain String","Date Error");
            isError = true;
        }
        return null;
    }

    private String AddImage(String path){
        Path sourcePath = Paths.get(path.replace("\"","").trim());
        File targetDirectory = new File("src/main/java/com/cw_malabe_tutuk2/data/Images");
        String fileName = null;
        if(     sourcePath.toString().contains(".jpg") ||
                sourcePath.toString().contains(".png") ||
                sourcePath.toString().contains(".jpeg"))
        {
            fileName = sourcePath.getFileName().toString().toLowerCase();
            try{
                if(targetDirectory.exists()){
                    Path targetPath = Paths.get(targetDirectory+"/"+fileName);
                    Files.copy(sourcePath,targetPath);
                }
            }catch (FileAlreadyExistsException e){
                showAlert("image  already in the system change the name or add a new image","Image Error");
                isError = true;
                return null;
            }catch (IOException e){
            showAlert("Fail to copy the image","Image Error");
            isError = true;
            return null;
            }
        }else{
            showAlert("file format should be either (.jpg/.png/.jpeg)","Image Error");
            isError = true;
            return null;
        }
        return fileName;
    }

    private void showAlert(String prompt,String headText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headText);
        alert.setContentText(prompt);
        alert.showAndWait();
    }
}

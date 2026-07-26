package com.cw_malabe_tutuk2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            String ImagePath = AddImage(imagePath.getText());
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
        if (!flagCodeChar) {
            System.out.println("Code should contain 'P' as the first character");
            return null;
        } else if (!flagCodedigit) {
            System.out.println("code should contain 3 digits after the 'P' ");
            return null;
        } else if (flagCodeExist) {
            System.out.println("Code already exists in inventory");
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
            System.out.println("Price can't be Negative");
        } catch (Exception e) {
            System.out.println("Enter a number not a String");
        }
        return 0;
    }

    private Integer readInt(String value){
        try {
            int Number = Integer.parseInt(value);
            if(!(Number < 0)){
                return Number;
            }
            System.out.println("The Value can't be negative");
        } catch (Exception e) {
            System.out.println("Enter a numerical value not a String");
        }
        return 0;
    }

    private String readDate(String date) {
        String[] dateValues;

        if (!date.contains("/") || date.length() != 10){
            System.out.println("invalid format : formate DD/MM/YYYY");
            return null;
        }
        dateValues = date.split("/");

        if (dateValues[0].length() != 2 || dateValues[1].length() != 2 || dateValues[2].length() != 4 ) {
            System.out.println("invalid format date should be: DD/MM/YYYY");
            return null;
        }

        try{
            int day = Integer.parseInt(dateValues[0]);
            int month = Integer.parseInt(dateValues[1]);
            int year = Integer.parseInt(dateValues[2]);

            if(month < 1 || month > 12){
                System.out.println("months can only be (1-12) not "+month);
                return null;
            }
            else if (year < 2000 || year > 2100){
                System.out.println("year is invalid it should be 2000-2100");
                return null;
            }

            int[] monthdays = {31,28,31,30,31,30,31,30,31,30,31,30};

            for(int i = 0;i<monthdays.length;i++) {

                if ((month - 1) == i) {
                    if ((i == 2) && (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                        if (day < 1 || day > 29){
                            System.out.println("Invalid day entered: " + day);
                            return null;

                        }

                    }

                    else{
                        if (day < 1 || day > monthdays[i]) {
                            System.out.println("days : " + monthdays[i] + "month" + month);
                            return null;
                        }
                    }
                    break;
                }
            }
            return date;

        }catch (NumberFormatException e){
            System.out.println("Error: date can't contain String");
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
            }catch (IOException e){
                
                System.out.println("Fail to copy the image");
                return null;
            }
        }else{
            System.out.println("file format should be either (.jpg/.png/.jpeg)");
            return null;
        }
        return fileName;
    }


}

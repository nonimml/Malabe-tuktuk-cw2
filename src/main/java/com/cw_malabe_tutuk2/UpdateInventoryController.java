package com.cw_malabe_tutuk2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateInventoryController {

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
}

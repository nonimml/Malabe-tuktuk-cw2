package com.cw_malabe_tutuk2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 625);
        Controller controller =fxmlLoader.getController();
        controller.loadData();
        stage.setTitle("Malabe Spares Depot");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public UpdateInventoryController UpdateInventory(Product select) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("UpdateData.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 625);
        UpdateInventoryController controller = fxmlLoader.getController();
        if(select != null){
            controller.update(select,true);
        }
        Stage newStage = new Stage();
        newStage.setTitle("Update Inventory Menu");
        newStage.setScene(scene);
        newStage.show();

        return controller;
    }

    @FXML
    public void ViewDealers() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Dealers_Deatails.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage newStage = new Stage();
        newStage.setTitle("Dealers Details");
        newStage.setScene(scene);
        newStage.show();
    }
}

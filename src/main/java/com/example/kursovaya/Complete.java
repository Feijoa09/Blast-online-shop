package com.example.kursovaya;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Complete {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    void initialize() {
        BackButton.setOnAction(event -> {
            openFXML("CatalogueController.fxml");

        });
    }
    private void openFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();

            //сцена
            Scene scene = new Scene(root);

            Stage Stage = (Stage) BackButton.getScene().getWindow();

            Stage.setScene(scene);
            Stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

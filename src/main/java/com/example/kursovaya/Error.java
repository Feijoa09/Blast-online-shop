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

public class Error {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    void initialize() {
        BackButton.setOnAction(event -> {
            openFXML("hello-view.fxml");
        });

    }
    private void openFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage Stage = (Stage) BackButton.getScene().getWindow();
            Scene buttonScene = BackButton.getScene();
            if (buttonScene != null) {
                Stage stage = (Stage) buttonScene.getWindow();
                if (stage != null) {
                    stage.setScene(scene);
                    stage.show();
                } else {
                    System.err.println("Stage is null");
                }
            } else {
                System.err.println("Button scene is null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

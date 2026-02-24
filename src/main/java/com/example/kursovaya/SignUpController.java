package com.example.kursovaya;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private Button singUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    private TextField Name_field;

    @FXML
    private TextField Surname_field;

    @FXML
    void initialize() {
        singUpButton.setOnAction(event -> {
            SingUpNewUser();
            openFXML("CatalogueController.fxml");
        });

    }
    private void SingUpNewUser() {
        String firstname =  Name_field.getText();
        String lastname =  Surname_field.getText();
        String login =  login_field.getText();
        String password =  password_field.getText();

        DatabaseHandler dbbHandler = new DatabaseHandler();

        User user = new User(firstname,lastname,login,password);
        dbbHandler.signUpUser(user);
    }
    private void openFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage Stage = (Stage) singUpButton.getScene().getWindow();
            Scene buttonScene = singUpButton.getScene();
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

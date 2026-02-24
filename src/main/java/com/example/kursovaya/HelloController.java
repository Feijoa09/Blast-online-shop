package com.example.kursovaya;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {


    @FXML
    private Button authSignInButton;
    @FXML
    private Button loginSignUpButton;
    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
        authSignInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals(""))
                loginUser(loginText, loginPassword);
            else
                System.out.println("Login and password is empty");
            openFXML("Error.fxml");

            openFXML("MenuController.fxml");
        });
        loginSignUpButton.setOnAction(event -> {
            openFXML("SignUpController.fxml");
        });

    }

    private void loginUser(String loginText, String loginPassword) {
        if (loginText.equals("admin") && loginPassword.equals("admin")) {
            openFXML("AdminController.fxml");
        } else if (loginText.equals("operator") && loginPassword.equals("operator")) {
            openFXML("OperatorController.fxml");
        } else {
            DatabaseHandler dbHandler = new DatabaseHandler();
        }
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getUser(user);
        int counter = 0;

        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if (counter >= 1) {
            System.out.println("Success!");
            openFXML("MenuController.fxml");
        }
    }
    private void openFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage Stage = (Stage) authSignInButton.getScene().getWindow();
            Scene buttonScene = authSignInButton.getScene();
            if (buttonScene != null) {
                Stage stage = (Stage) buttonScene.getWindow();
                if (stage != null) {
                    stage.setScene(scene);
                    stage.show();
                } else {
                    System.err.println("Null");
                }
            } else {
                System.err.println("Button scene is null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

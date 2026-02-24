package com.example.kursovaya;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CatalogueController {

    @FXML
    private Button BackButton;

    @FXML
    private Button Black1Button;

    @FXML
    private Button Black2Button;

    @FXML
    private Button Green1Button;

    @FXML
    private Button Green2Button;

    @FXML
    private Button Puer1Button;

    @FXML
    private Button Puer2Button;

    @FXML
    private Button White1Button;

    @FXML
    private Button White2Button;

    private static final String DB_URL = "jdbc:mysql://localhost:65336/new_schema";
    private static final String USER = "root1";
    private static final String PASS = "root";

    @FXML
    void initialize() {
        BackButton.setOnAction(event -> openFXML("MenuController.fxml"));

        Black1Button.setOnAction(event -> handlePurchase("Black1"));
        Black2Button.setOnAction(event -> handlePurchase("Black2"));
        Green1Button.setOnAction(event -> handlePurchase("Green1"));
        Green2Button.setOnAction(event -> handlePurchase("Green2"));
        Puer1Button.setOnAction(event -> handlePurchase("Puer1"));
        Puer2Button.setOnAction(event -> handlePurchase("Puer2"));
        White1Button.setOnAction(event -> handlePurchase("White1"));
        White2Button.setOnAction(event -> handlePurchase("White2"));
    }

    private void openFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertTeaItem(String name) {
        String query = "INSERT INTO item (Tea) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Item inserted successfully: " + name);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handlePurchase(String name) {
        insertTeaItem(name);
        openFXML("CartController.fxml");
    }
}

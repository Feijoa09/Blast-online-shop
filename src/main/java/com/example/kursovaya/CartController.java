package com.example.kursovaya;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.sql.Connection;
import javafx.scene.control.TextField;


public class CartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BackButton;

    @FXML
    private Button OrderButton;

    @FXML
    private TextField EmailField;



    @FXML
    void initialize() {

        BackButton.setOnAction(event -> {
            openFXML("MenuController.fxml");

        });

        OrderButton.setOnAction(Event -> {
            String Phonenumbers = EmailField.getText();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:65336/new_schema", "root1", "root");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO new_schema.email (emailcol) VALUES (?)");
                statement.setString(1, Phonenumbers);
                openFXML("Complete.fxml");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Данные успешно добавлены в базу данных!");
                }

                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка при выполнении SQL-запроса: " + e.getMessage());
            }


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
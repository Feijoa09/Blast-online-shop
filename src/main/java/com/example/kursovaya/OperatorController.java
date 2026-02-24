package com.example.kursovaya;

import java.io.IOException;

import java.sql.*;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class OperatorController {

    @FXML
    private TableColumn<DataModel, String> EmailColumn;
    @FXML
    private TableColumn<DataModel, String> TeaColumn;
    @FXML
    private TableColumn<DataModel, Integer> idColumn;
    @FXML
    private TableView<DataModel> tableView;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField searchfield;
    @FXML
    private Button BackButton;

    private ObservableList<DataModel> data = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        BackButton.setOnAction(event -> openFXML("hello-view.fxml"));
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> filterTableView(newValue));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("emailcol"));
        TeaColumn.setCellValueFactory(new PropertyValueFactory<>("tea"));

        loadAllDataFromDatabase();
        tableView.setItems(data);

        sortTableView();

        deleteButton.setOnAction(event -> {
            deleteSelectedItem();
        });
    }

    private void loadAllDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:65336/new_schema", "root1", "root")) {
            // Получаем все уникальные айди из таблицы email
            String idQuery = "SELECT DISTINCT id FROM email";
            try (PreparedStatement idStatement = connection.prepareStatement(idQuery);
                 ResultSet idResultSet = idStatement.executeQuery()) {
                while (idResultSet.next()) {
                    int id = idResultSet.getInt("id");
                    loadDataForId(connection, id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadDataForId(Connection connection, int id) {
        try {
            String query = "SELECT email.id, email.emailcol, item.Tea FROM email LEFT JOIN item ON email.id = item.Id WHERE email.id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("emailcol");
                        String item = resultSet.getString("Tea");
                        data.add(new DataModel(id, name, item));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    private void sortTableView() {
        tableView.getSortOrder().clear();

        if (EmailColumn.getSortType() == TableColumn.SortType.ASCENDING) {
            EmailColumn.setSortType(TableColumn.SortType.DESCENDING);
        } else {
            EmailColumn.setSortType(TableColumn.SortType.ASCENDING);
        }

        tableView.getSortOrder().add(EmailColumn);
    }
    private void filterTableView(String keyword) {
        FilteredList<DataModel> filteredData = new FilteredList<>(data, dataModel -> {
            if (keyword == null || keyword.isEmpty()) {
                return true;
            }
            String lowerCaseKeyword = keyword.toLowerCase();
            return dataModel.getEmailcol().toLowerCase().contains(lowerCaseKeyword) ||
                    dataModel.getTea().toLowerCase().contains(lowerCaseKeyword);
        });

        tableView.setItems(filteredData);
    }
    private void deleteSelectedItem() {
        DataModel selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int idToDelete = selectedItem.getId();
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:65336/new_schema", "root1", "root")) {
                String query = "DELETE FROM email WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, idToDelete);
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Record deleted from the database.");
                    } else {
                        System.out.println("Error deleting the record from the database.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            tableView.getItems().remove(selectedItem);
        }
    }
    public static class DataModel {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty emailcol;
        private final SimpleStringProperty tea;

        public DataModel(int id, String name, String item) {
            this.id = new SimpleIntegerProperty(id);
            this.emailcol = new SimpleStringProperty(name);
            this.tea = new SimpleStringProperty(item);
        }

        public int getId() {
            return id.get();
        }

        public String getEmailcol() {
            return emailcol.get();
        }

        public String getTea() {
            return tea.get();
        }
    }
}

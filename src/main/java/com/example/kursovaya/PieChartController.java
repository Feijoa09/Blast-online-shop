package com.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PieChartController {

    @FXML
    private PieChart PieChart; // Поле для круговой диаграммы

    @FXML
    void initialize() {
        // Получаем данные из базы данных и устанавливаем их в диаграмму
        ObservableList<PieChart.Data> pieChartData = getDataFromDatabase();
        PieChart.setData(pieChartData);
    }
    private ObservableList<PieChart.Data> getDataFromDatabase() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        String url = "jdbc:mysql://127.0.0.1:65336/new_schema";
        String user = "root1";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Запрос для получения общего количества чаев
            String totalQuery = "SELECT COUNT(*) AS total_count FROM item";
            try (PreparedStatement totalStatement = connection.prepareStatement(totalQuery);
                 ResultSet totalResultSet = totalStatement.executeQuery()) {
                if (totalResultSet.next()) {
                    int totalCount = totalResultSet.getInt("total_count");

                    // Запрос для получения количества каждого вида чая
                    String itemQuery = "SELECT Tea, COUNT(*) AS count FROM item GROUP BY Tea";
                    try (PreparedStatement itemStatement = connection.prepareStatement(itemQuery);
                         ResultSet itemResultSet = itemStatement.executeQuery()) {
                        while (itemResultSet.next()) {
                            String itemName = itemResultSet.getString("Tea");
                            int count = itemResultSet.getInt("count");
                            double percentage = (double) count / totalCount * 100; // Вычисление процента
                            pieChartData.add(new PieChart.Data(itemName + " (" + (int) percentage + "%)", count)); // Отображение процента
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса: " + e.getMessage());
        }

        return pieChartData;
    }
}

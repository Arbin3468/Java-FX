package com.example.javafxdemo.controller;

import com.example.javafxdemo.Application;
import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DashboardController {
    @FXML
    private Label accountNameText;

    private Application application;

    public void setAccountName(String accountName) {
        accountNameText.setText("Welcome, " + accountName);
    }
    @FXML
    private void LoadResults(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/result-view.fxml"));
            Parent root = loader.load();

            // Get the ListView from the loaded FXML file
            ListView<String> resultsViewList = (ListView<String>) root.lookup("#resultsViewList");

            // Check if the ListView is found
            if (resultsViewList != null) {
                // Clear any previous items in the ListView
                resultsViewList.getItems().clear();

                // Sample code for reading CSV using OpenCSV:
                CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/user_ans.csv"));
                List<String[]> csvData = csvReader.readAll();

                // Process the CSV data and load questions into the ListView
                for (String[] row : csvData) {
                    // Assuming CSV columns are in a specific order, e.g., question, answer
                    String question = row[0];
                    String answer = row[1];
                    resultsViewList.getItems().add("Score: " + question + ", Email: " + answer);
                }

                // Close the CSV reader
                csvReader.close();
            } else {
                // Print an error message if the ListView is not found
                System.err.println("ListView not found in result-view.fxml");
            }

            // Create a new stage for displaying the results
            Stage stage = new Stage();
            stage.setTitle("Results");
            Scene scene = new Scene(root);

            // Set the scene for the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print the stack trace in case of an IOException
            e.printStackTrace();
        } catch (Exception e) {
            // Print the stack trace for any other exceptions
            e.printStackTrace();
        }
    }




    @FXML
    private void playQuizButtonClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/quiz-page.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Quiz Page");
            Scene scene = new Scene(root);


            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

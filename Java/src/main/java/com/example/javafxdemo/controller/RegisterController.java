package com.example.javafxdemo.controller;

import com.example.javafxdemo.Application;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterController {
    @FXML
     TextField userName;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<String> userGender;
    @FXML
    private ComboBox<String> userNationality;
    @FXML
    private TextField userAddress;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Button registerBtn;
    @FXML
    private Label emailError;

    private Application application;
    static String name;
    static String nationality;
    static String gender;
    private boolean isFormValid;

    public void setApplication(Application application) {
        this.application = application;
    }

    public void goback(ActionEvent event) throws IOException {
        application.loginScene();
    }

    public void register(ActionEvent event) throws IOException {
         name = userName.getText();
        String userEmail = email.getText();
        String birthYear = dateOfBirth.getValue() != null ? dateOfBirth.getValue().toString() : "";
         gender = userGender.getValue();
        nationality = userNationality.getValue();
        String address = userAddress.getText();
        String password = userPassword.getText();
        String pathToCSV = "src/main/resources/userData.csv";

        // Validate email format
        if (!isValidEmail(userEmail)) {
            emailError.setText("Invalid email format");
            email.getStyleClass().add("error-background");
            isFormValid = false;
        } else {
            isFormValid = true;
        }

        // Additional form validation...

        if (isFormValid) {
            try {
                FileWriter fileWriter = new FileWriter(pathToCSV, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter);

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                String[] csvData = {name, userEmail, birthYear, gender, nationality, address, hashedPassword};
                csvWriter.writeNext(csvData);
                csvWriter.close();
                clearFormElements();
            } catch (IOException e) {
                e.printStackTrace();
            }
            application.loginScene();
        }
    }

    private boolean isValidEmail(String email) {
        // Use a simple regex for basic email validation
        String emailRegex = "^[^@]+@[^@]+$";
        return email.matches(emailRegex);
    }

    public void clearFormElements() {
        userName.clear();
        email.clear();
        dateOfBirth.setValue(null);
        userGender.getSelectionModel().clearSelection();
        userNationality.getSelectionModel().clearSelection();
        userAddress.clear();
        userPassword.clear();
        emailError.setText("");
        email.getStyleClass().remove("error-background");
    }
}

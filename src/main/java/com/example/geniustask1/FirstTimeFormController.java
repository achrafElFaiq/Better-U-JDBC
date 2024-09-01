package com.example.geniustask1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;

public class FirstTimeFormController {

    @FXML
    private TextField fullName;

    @FXML
    public  TextField userName;

    @FXML
    private TextField email;


    @FXML
    private Text resultText;
    @FXML
    private Button nextButton;






    public void validateTextField(TextField textField) {
        if(textField.getId().equals("email")){
            String text = textField.getText().trim();
            if (!text.isEmpty() && text.matches(".+@gmail\\.com$")) {
                textField.setStyle("-fx-border-color: green;");
            } else {
                textField.setStyle("-fx-border-color: red;");
            }

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                String newText = newValue.trim();
                if (!newText.isEmpty() && text.matches(".+@gmail\\.com$")) {
                    textField.setStyle("-fx-border-color: green;");
                } else {
                    textField.setStyle("-fx-border-color: red;");
                }
            });
        }
        else {
            String text = textField.getText().trim();
            if (!text.isEmpty()) {
                textField.setStyle("-fx-border-color: green;");
            } else {
                textField.setStyle("-fx-border-color: red;");
            }

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                String newText = newValue.trim();
                if (!newText.isEmpty()) {
                    textField.setStyle("-fx-border-color: green;");
                } else {
                    textField.setStyle("-fx-border-color: red;");
                }
            });
        }
    }

    @FXML
    private void handleTextFieldAction() throws Exception {
        validateTextField(fullName);
        validateTextField(userName);
        validateTextField(email);

    }

    public void startapp() throws IOException {

        boolean field1Valid = fullName.getStyle().contains("-fx-border-color: green;");
        boolean field2Valid = userName.getStyle().contains("-fx-border-color: green;");
        boolean field3Valid = email.getStyle().contains("-fx-border-color: green;");

        if(field1Valid && field2Valid && field3Valid){
            String emailText = email.getText();
            String userNameText = userName.getText();
            String fullNameText = fullName.getText();

            DBConnection connectNow = new DBConnection();
            Connection connection = connectNow.getConnection();

            String registerUser = "INSERT INTO appuser (fullName, userName,email) VALUES (?,?,?)";
            try {
                PreparedStatement registerStmt = connection.prepareStatement(registerUser);
                registerStmt.setString(1, fullNameText);
                registerStmt.setString(2, userNameText);
                registerStmt.setString(3, emailText);
                int rowsInserted = registerStmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.print("Registration successful");
                    String sql = "UPDATE appuser SET survey='completed' WHERE username=?";
                    try {
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, userNameText);
                        statement.executeUpdate();
                        System.out.println("Survey completed for user " + userNameText);
                    } catch (SQLException e) {
                        System.out.println("Error updating survey status for user " + userNameText);
                        e.printStackTrace();
                    }

                } else {
                    System.out.print("Registration failed");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

            loadhome();

        }
        else{
            resultText.setText("Please Fill all the information");
        }

    }









    public void loadhome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = nextButton.getScene();
        scene.setRoot(root);
    }

    @FXML

    private void handleButtonExit() {
        nextButton.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleButtonHover( ) {
        nextButton.setStyle("-fx-background-color: #336699;");
    }


}

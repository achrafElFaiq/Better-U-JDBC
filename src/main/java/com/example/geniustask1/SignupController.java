package com.example.geniustask1;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupController {

    @FXML
    private ImageView HomeIcon;

    @FXML
    private ImageView back;

    @FXML
    private Button login;

    @FXML
    private Button signup;

    @FXML
    private TextField cemail;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Label resultLabel;
    @FXML
    private void loadlogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void handleButtonExit() {
        login.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleButtonHover( ) {
        login.setStyle("-fx-background-color: #336699;");
    }
    @FXML
    private void handleSignupButtonExit() {
        signup.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleSignupButtonHover( ) {
        signup.setStyle("-fx-background-color: #336699;");
    }

    @FXML
    private void loadhome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }

    @FXML
    private void zoomImage(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), HomeIcon);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
    }

    @FXML
    private void zoomOut(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), HomeIcon);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    public void registerUser() {
        String email1 = email.getText();
        String confirmEmail = cemail.getText();
        String password1 = password.getText();

        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();


        if (!email.getText().equals(cemail.getText())) {
            resultLabel.setText("Emails do not match");
            return;
        }

        // Vérifier si l'e-mail est déjà utilisé
        String checkEmail = "SELECT count(*) FROM users WHERE email = ?";
        try {
            PreparedStatement checkEmailStmt = connection.prepareStatement(checkEmail);
            checkEmailStmt.setString(1, email1);
            ResultSet emailResult = checkEmailStmt.executeQuery();

            emailResult.next();
            int count = emailResult.getInt(1);
            if (count > 0) {
                resultLabel.setText("Email already in use");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Insérer l'utilisateur dans la base de données
        String registerUser = "INSERT INTO users (email, password) VALUES (?, ?)";
        try {
            PreparedStatement registerStmt = connection.prepareStatement(registerUser);
            registerStmt.setString(1, email1);
            registerStmt.setString(2, password1);
            int rowsInserted = registerStmt.executeUpdate();

            if (rowsInserted > 0) {
                resultLabel.setText("Registration successful");
            } else {
                resultLabel.setText("Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}

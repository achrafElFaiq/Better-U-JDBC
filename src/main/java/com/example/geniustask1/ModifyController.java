package com.example.geniustask1;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyController {

    @FXML
    private ImageView back;

    @FXML
    private TextField email;

    @FXML
    private Button cancel;

    @FXML
    private TextField fullName;

    @FXML
    private ImageView home;

    @FXML
    private Button modify;

    @FXML
    private TextField username;






    @FXML
    private void handleButtonExit() {
        modify.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleButtonHover( ) {
        modify.setStyle("-fx-background-color: #336699;");
    }

    @FXML
    private void handleCancelButtonExit() {
        cancel.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleCancelButtonHover( ) {
        cancel.setStyle("-fx-background-color: #336699;");
    }

    @FXML
    private void zoomImage(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), home);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
    }

    @FXML
    private void zoomOut(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), home);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }


    @FXML
    private void loadhome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }
    @FXML
    private void loadProfile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }



    public void modify() {
        String emailText = email.getText();
        String usernameText = username.getText();
        String fullNameText = fullName.getText();

        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();


        // Insérer l'utilisateur dans la base de données
        String modifyFullName  ="UPDATE appuser SET fullname = ?";
        String modifyUserlName  ="UPDATE appuser SET username =?";
        String modifyemail  =" UPDATE appuser SET email = ?";

        try {
            PreparedStatement fullnameStmt = connection.prepareStatement(modifyFullName);
            PreparedStatement usernameStmt = connection.prepareStatement(modifyUserlName);
            PreparedStatement emailStmt = connection.prepareStatement(modifyemail);
            fullnameStmt.setString(1, fullNameText);
            usernameStmt.setString(1, usernameText);
            emailStmt.setString(1, emailText);
            fullnameStmt.executeUpdate();
            usernameStmt.executeUpdate();
            emailStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

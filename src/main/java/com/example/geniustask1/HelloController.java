package com.example.geniustask1;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    @FXML
    private ImageView HomeIcon;


    @FXML
    private Text username;



    @FXML
    private ImageView login;

    @FXML
    private ImageView tasks;

    @FXML
    private ImageView chatbot;



    public void profile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) login.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void loadtasks() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tasks.fxml"));
        Scene scene = tasks.getScene();
        scene.setRoot(root);
    }
    @FXML
    private void loadchatBot() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("chatBot.fxml"));
        Scene scene = chatbot.getScene();
        scene.setRoot(root);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBConnection connectNow = new DBConnection();
            Connection connection = connectNow.getConnection();
            String selectQuery = "SELECT username FROM appuser WHERE id = (SELECT MIN(id) FROM appuser)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                username.setText(result.getString("username"));
                username.setFill(Color.BLUE);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.example.geniustask1;

import com.example.geniustask1.model.Questionnaire;
import com.example.geniustask1.model.Task;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.ResourceBundle;

public class TasksController implements Initializable {

    @FXML
    private VBox taskContainer;

    @FXML
    private ImageView HomeIcon;

    @FXML
    private ImageView account;

    @FXML
    private ImageView back;

    @FXML
    private Button login;

    static List<Task> tasks;



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


    @FXML
    private void loadhome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            tasks = new ArrayList<>();

            DBConnection connectNow = new DBConnection();
            Connection connection = connectNow.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title, description, status FROM tache ");

            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                String status = rs.getString("status");
                Task task = new Task(title, status,description);
                tasks.add(task);
            }

            rs.close();
            stmt.close();
            connection.close();

            for (int i = 0; i < tasks.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("task.fxml"));
                VBox vbox = loader.load();
                vbox.setStyle("-fx-background-radius: 15; -fx-border-radius: 15");
                TaskController tc = loader.getController();
                tc.setData(tasks.get(i));
                taskContainer.getChildren().add(vbox);
            }
        }
        catch(IOException | SQLException e ){
            throw new RuntimeException(e);
        }
    }

    public void loadcreatenewtask() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newTask.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }
}


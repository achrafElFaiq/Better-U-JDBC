package com.example.geniustask1;

import com.example.geniustask1.model.Task;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class NewTaskController {
    @FXML
    private ImageView HomeIcon;

    @FXML
    private ImageView account;

    @FXML
    private TextField duration;

    @FXML
    private TextField title;

    @FXML
    private TextArea descriptionText;

    @FXML
    private ImageView back;

    @FXML
    private BorderPane borderPane;

    static Stage stage;


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

    @FXML
    private void loadlogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }
    @FXML
    private void loadsurvey() throws IOException {
        String titleText = title.getText();
        String descriptiontext = descriptionText.getText();
        int durationTime = Integer.parseInt(duration.getText());
        Date currentDate = new Date(System.currentTimeMillis());

        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();

        String createTask = "INSERT INTO tache (title, description) VALUES (?, ?)";
        String createSyrvey = "INSERT INTO questionnaire (creation_date,task_id,last_response_date,number_of_answers,duree) VALUES (?,?,?,1,?)" ;
        String gettaskId = "SELECT id FROM Tache WHERE title = ?";

        try {
            PreparedStatement getId = connection.prepareStatement(gettaskId);
            PreparedStatement registerStmt = connection.prepareStatement(createTask);
            PreparedStatement surveystmt = connection.prepareStatement(createSyrvey);
            surveystmt.setDate(1,currentDate);
            registerStmt.setString(1, titleText);
            registerStmt.setString(2, descriptiontext);

            getId.setString(1,titleText);
            registerStmt.executeUpdate();


            ResultSet idResult = getId.executeQuery();
            idResult.next();
            int id = idResult.getInt(1);

            surveystmt.setDate(1,currentDate);
            surveystmt.setLong(2,id);
            surveystmt.setDate(3,currentDate);
            surveystmt.setLong(4,durationTime);
            surveystmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
            loadhome();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("survey.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        SurveyController controller = loader.getController();
        controller.setBack(back);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }








}

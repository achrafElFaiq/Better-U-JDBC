package com.example.geniustask1;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;


import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    @FXML
    private ImageView back;

    @FXML
    private Label email;

    @FXML
    private Button modify;
    @FXML
    private Label fullName;

    @FXML
    private ImageView home;

    @FXML
    private Button reset;

    @FXML
    private Label userName;




    @FXML
    private void handleButtonExit() {
        reset.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleButtonHover( ) {
        reset.setStyle("-fx-background-color: #336699;");
    }

    @FXML
    private void handleModifyButtonExit() {
        modify.setStyle("-fx-background-color: rgba(255,255,255,0);");
    }


    @FXML
    private void handleModifyButtonHover( ) {
        modify.setStyle("-fx-background-color: #336699;");
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


    public void setEmail(Label email) {
        this.email = email;
    }

    public void setFullName(Label fullName) {
        this.fullName = fullName;
    }

    public void setUserName(Label userName) {
        this.userName = userName;
    }


    public void reset(){
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();

        String resetAppUser = "DELETE FROM appuser";
        String resetTache = "DELETE FROM tache";
        String resetQuestionnaire = "DELETE FROM questionnaire";
        String resetQuestion = "DELETE FROM question";
        String resetReponse = "DELETE FROM reponse";



        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resetAppUser);
            PreparedStatement preparedStatement1 = connection.prepareStatement(resetTache);
            PreparedStatement preparedStatement2 = connection.prepareStatement(resetQuestionnaire);
            PreparedStatement preparedStatement3 = connection.prepareStatement(resetQuestion);
            PreparedStatement preparedStatement4 = connection.prepareStatement(resetReponse);

            preparedStatement.executeUpdate();
            preparedStatement4.executeUpdate();
            preparedStatement3.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement1.executeUpdate();

            loadSurvey();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loadSurvey() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstTimeForm.fxml"));
        Scene scene = reset.getScene();
        scene.setRoot(root);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            DBConnection connectNow = new DBConnection();
            Connection connection = connectNow.getConnection();
            String selectQuery = "SELECT * FROM appuser WHERE id = (SELECT MIN(id) FROM appuser)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {

                fullName.setText(result.getString("fullname"));
                email.setText(result.getString("email"));
                userName.setText(result.getString("username"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadmodify() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("modify.fxml"));
        Scene scene = modify.getScene();
        scene.setRoot(root);
    }




}

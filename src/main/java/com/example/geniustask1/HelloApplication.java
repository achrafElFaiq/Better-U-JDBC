package com.example.geniustask1;

import com.example.geniustask1.model.Questionnaire;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class HelloApplication extends Application {

    static boolean surveyCompleted = false;
    static String userName;
    static String email;

    static String fullName;

    static int numberOfAnswers;

    static  Stage notificationStage;



    @Override
    public void start(Stage stage) throws IOException {

        try {
            loadUserProfile();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader;
        if (surveyCompleted){
            List<Questionnaire> questionnairesToAnswer = Questionnaire.getQuestionnairesToAnswer(Questionnaire.getAllQuestionnaires());
            for(Questionnaire questionnaire : questionnairesToAnswer){
                if(questionnaire.getNumberOfresponses() < questionnaire.getDuree()){
                    QuestionnaireController.setQuestionnaireId(questionnaire.getId());
                    fxmlLoader = new FXMLLoader(getClass().getResource("questionnaire.fxml"));
                    notificationStage = new Stage();
                    notificationStage.setScene(new Scene(fxmlLoader.load(), 600, 400));
                    notificationStage.showAndWait();
                    System.out.println("le questionnaire "+ questionnaire.getId() +" est a repondre ");
                }

            }


            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        }
        else{
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstTimeForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loadUserProfile() throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();
        String selectQuery = "SELECT * FROM appuser WHERE id = (SELECT MIN(id) FROM appuser)";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()) {
            String survey = result.getString("survey");
            userName=result.getString("username");
            email=result.getString("email");
            fullName=result.getString("fullname");
            if(survey.equals("completed")) surveyCompleted=true;
        }
        connection.close();
    }
}
package com.example.geniustask1.model;

import com.example.geniustask1.DBConnection;
import com.example.geniustask1.HelloApplication;
import com.example.geniustask1.QuestionnaireController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Questionnaire {

    int id;
    Date creationDate;
    Date lastResponseDate;
    int taskId;

    int duree;

    int numberOfresponses;

    public int getNumberOfresponses() {
        return numberOfresponses;
    }

    public Questionnaire(int id, Date lastResponseDate, int taskId, int numberOfresponses,int duree) {
        this.id = id;
        this.lastResponseDate = lastResponseDate;
        this.taskId = taskId;
        this.numberOfresponses=numberOfresponses;
        this.duree=duree;
    }

    public int getDuree() {
        return duree;
    }

    public static List<Questionnaire> getAllQuestionnaires() {
        List<Questionnaire> questionnaires = new ArrayList<>();
        try {
            DBConnection connectNow = new DBConnection();
            Connection connection = connectNow.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, last_response_date, task_id,number_of_answers,duree FROM questionnaire");

            while (rs.next()) {
                int id = rs.getInt("id");
                Date lastResponseDate = rs.getDate("last_response_date");
                int taskId = rs.getInt("task_id");
                int numberOfAnswers= rs.getInt("number_of_answers");
                int duree = rs.getInt("duree");

                Questionnaire questionnaire = new Questionnaire(id, lastResponseDate, taskId,numberOfAnswers,duree);
                questionnaires.add(questionnaire);
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionnaires;
    }

    public int getId() {
        return id;
    }

    public static List<Questionnaire> getQuestionnairesToAnswer(List<Questionnaire> questionnaires) {
        List<Questionnaire> questionnairesToAnswer = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());

        for (Questionnaire questionnaire : questionnaires) {
            Date lastResponseDate = questionnaire.lastResponseDate;
            long diffInMillis = currentDate.getTime() - lastResponseDate.getTime();
            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

            if (diffInDays >= 7) {
                System.out.println("Ce questionnaire est a repondre");
                questionnairesToAnswer.add(questionnaire);
            }
        }



        return questionnairesToAnswer;
    }

    public static void loadQuestionnairePage(int questionnaireId, Stage stage) throws IOException {
        QuestionnaireController.setQuestionnaireId(questionnaireId);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("questionnaire.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }


}

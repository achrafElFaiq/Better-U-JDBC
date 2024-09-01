package com.example.geniustask1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    @FXML
    private Label question;


    static List<String> reponses;
    static List<Date> reponsesDates;

    public void setData(String contenu){
        question.setText(contenu);
    }

    public void loadResponses() throws IOException {
        String contenu = question.getText();
        reponses=new ArrayList<>();
        reponsesDates=new ArrayList<>();
        String query = "SELECT id FROM question WHERE contenu = ? AND questionnaire_id = ?";
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, contenu);
            statement.setInt(2, TaskController.loadedQuestionnaireId);
            ResultSet resultSet = statement.executeQuery();
            int questionId=0;
            if (resultSet.next()) {
                questionId = resultSet.getInt("id");
            } else {
                System.out.println("Question non trouvée");
            }

            String query1 = "SELECT * FROM reponse WHERE question_id = ?";

            try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
                statement1.setInt(1, questionId);
                ResultSet resultSet1 = statement1.executeQuery();
                while (resultSet1.next()) {
                    int reponseId = resultSet1.getInt("id");
                    String reponse = resultSet1.getString("reponseText");
                    Date responseDate = resultSet1.getDate("response_date");
                    reponses.add(reponse);
                    reponsesDates.add(responseDate);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadReponsesPage();


    }

    private void loadReponsesPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("reponses.fxml"));
        Scene scene = question.getScene();
        scene.setRoot(root);

    }



}

package com.example.geniustask1;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionnaireController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button answer;
    @FXML
    private ScrollPane scrollPane;

    static int questionnaireId;

    public static void setQuestionnaireId(int questionnaireId) {
        QuestionnaireController.questionnaireId = questionnaireId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();


        try{
            String sql = "SELECT id, contenu FROM question WHERE questionnaire_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, questionnaireId);

            ResultSet resultSet = statement.executeQuery();

            double yPosition = 10.0; // Position verticale initiale

            while (resultSet.next()) {
                int questionId = resultSet.getInt("id");
                String questionContenu = resultSet.getString("contenu");

                // Créer un champ de texte pour la question
                Label questionTextField = new Label(questionContenu);
                questionTextField.setPrefWidth(320);
                questionTextField.setPrefHeight(25);
                questionTextField.setStyle("-fx-padding:5;-fx-font-family: 'DIN Condensed'; -fx-font-size: 13px;-fx-background-color: #d6d6d6;-fx-border-radius: 6px;");
                questionTextField.setLayoutX(95.0);
                questionTextField.setLayoutY(yPosition);

                // Créer un champ de texte pour la réponse
                TextField reponseTextField = new TextField();
                reponseTextField.setPrefWidth(320);
                reponseTextField.setPrefHeight(25);
                reponseTextField.setStyle("-fx-padding:3;-fx-font-family: 'DIN Condensed'; -fx-font-size: 13px;-fx-background-color: #d6d6d6;-fx-border-radius: 6px;");
                reponseTextField.setPromptText("Entrer une reponse");
                reponseTextField.setStyle("-fx-font-family: \"DIN Condensed\";\n" +
                        "    -fx-font-size: 13px;");
                reponseTextField.setLayoutX(95.0);
                reponseTextField.setLayoutY(yPosition +33);

                // Ajouter les champs de texte à l'AnchorPane
                anchorPane.getChildren().addAll(questionTextField, reponseTextField);

                yPosition += 65.0; // Incrémenter la position verticale
            }

            resultSet.close();
            statement.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }




    }

    public void storeData(){
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();

        // Liste pour stocker les questions (TextField impairs)
        List<Label> questions = new ArrayList<>();

        // Liste pour stocker les réponses (TextField pairs)
        List<TextField> reponses = new ArrayList<>();

        // Parcours des enfants de l'AnchorPane
        ObservableList<Node> children = anchorPane.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            if (node instanceof Label label) {
                questions.add(label);
            }
            else if(node instanceof TextField textField){
                reponses.add(textField);
            }
        }

        try{
            for (int i = 0; i < questions.size(); i++) {
                Label questionTextField = questions.get(i);
                TextField reponseTextField = reponses.get(i);

                String question = questionTextField.getText();
                String reponse = reponseTextField.getText();

                // Récupération de l'ID de la question (TextField impaire) précédente
                int questionId=0;
                String idQuery = "SELECT id FROM question WHERE contenu=? AND questionnaire_id=?";
                PreparedStatement idQueryStmnt = connection.prepareStatement(idQuery);
                idQueryStmnt.setString(1,question);
                idQueryStmnt.setInt(2,questionnaireId);
                ResultSet rs = idQueryStmnt.executeQuery();
                while(rs.next()){
                    questionId = rs.getInt("id");
                }



                // Requête SQL pour insérer les données dans la table 'reponse'
                String query = "INSERT INTO reponse (question_id, response_date, reponseText) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, questionId);
                statement.setDate(2, new Date(System.currentTimeMillis()));
                statement.setString(3, reponse);

                statement.executeUpdate();



            }
            String updateQuery = "UPDATE questionnaire SET number_of_answers = number_of_answers + 1,last_response_date = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);

            // Spécifier l'identifiant de la question à mettre à jour
            int questionId = questionnaireId; // Remplacez par l'identifiant de la question souhaitée
            pstmt.setDate(1,new Date(System.currentTimeMillis()));
            pstmt.setInt(2, questionId);

            // Exécuter la mise à jour
            int rowsAffected = pstmt.executeUpdate();

            // Vérifier le nombre de lignes affectées
            if (rowsAffected > 0) {
                System.out.println("La colonne 'number_of_answers' de la question a été mise à jour avec succès !");
            } else {
                System.out.println("Aucune ligne mise à jour.");
            }

            System.out.println("Données insérées avec succès !");

            String selectQuery = "SELECT number_of_answers,duree FROM questionnaire WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1,questionnaireId);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                int nOfAnswers = result.getInt("number_of_answers");
                int duree = result.getInt("duree");

                if(nOfAnswers == duree){
                    String selectQuery1 = "SELECT task_id FROM questionnaire WHERE id = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(selectQuery1);
                    preparedStatement1.setInt(1,questionnaireId);
                    ResultSet result1 = preparedStatement1.executeQuery();
                    if(result.next()){
                        int id = result1.getInt("task_id");
                        String statusQuery = "UPDATE tache SET status = 'complete' WHERE id=?";
                        PreparedStatement statement= connection.prepareStatement(statusQuery);
                        statement.setInt(1, id);
                        statement.executeUpdate();
                    }




                }
            }
        }

        catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion des données : " + e.getMessage());
        }
        Stage stage = (Stage) HelloApplication.notificationStage.getScene().getWindow();
        stage.close();


    }
}

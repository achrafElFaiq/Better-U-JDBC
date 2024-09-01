package com.example.geniustask1;

import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyController {


    @FXML
    private ImageView HomeIcon;

    @FXML
    private ImageView account;

    @FXML
    private Button addquestion;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView back;
    public void ajouterTextFields() {
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();

        double nextY = 50.0; // position Y du prochain TextField
        List<Node> children = anchorPane.getChildren();
        if (!children.isEmpty()) {
            Node lastNode = children.get(children.size() - 1);
            nextY = lastNode.getLayoutY() + lastNode.getBoundsInLocal().getHeight() + 10.0;
        }
        for (int i = 0; i < 2; i++) {
            TextField textField = new TextField();
            textField.setPrefWidth(320);
            textField.setPrefHeight(25);
            if (i == 0) {
                textField.setPromptText("Question");
                textField.setStyle(" -fx-font-family: 'DIN Condensed'; -fx-font-size: 10px;-fx-background-color:  #D6D6D6;");
            } else if (i == 1) {
                textField.setPromptText("Answer");
                textField.setStyle(" -fx-font-family: 'DIN Condensed'; -fx-font-size: 10px;-fx-background-color:  #D6D6D6;");
            }
            anchorPane.getChildren().add(textField);

            AnchorPane.setTopAnchor(textField, nextY);
            AnchorPane.setLeftAnchor(textField, 95.0);

            nextY += 35.0;
        }

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

    @FXML
    private void loadlogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }
    @FXML
    private void loadtasks() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tasks.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }

    @FXML
    public void setBack(ImageView imageView){
        this.back = imageView;
    }

    @FXML
    private void removeLastTextFields() {
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();
        List<Node> children = anchorPane.getChildren();

        if (children.size() > 6) {
            int lastIndex = children.size() - 1;
            anchorPane.getChildren().remove(lastIndex);
            anchorPane.getChildren().remove(lastIndex - 1);
        }
    }

    public void completeTaskInfo() throws SQLException, IOException {

        List<String> questions = new ArrayList<>();
        List<String> reponses = new ArrayList<>();

        List<TextField> textFields = new ArrayList<>();

        // Parcourir tous les nœuds de l'AnchorPane
        for (javafx.scene.Node node : anchorPane.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
            }
        }

        // Parcourir les TextField et stocker les valeurs dans les listes appropriées
        boolean allTextarecomplete = true;
        for (int i = 0; i < textFields.size(); i++) {
            TextField textField = textFields.get(i);
            String text = textField.getText();
            if(text.isEmpty()) allTextarecomplete=false;

            if (i % 2 == 0) {
                questions.add(text);
            } else {
                reponses.add(text);
            }

        }
        if(allTextarecomplete) {
            DBConnection connectNow = new DBConnection();
            Connection connection = connectNow.getConnection();

            String lastQuestionnaireId = "SELECT MAX(id) AS max_id FROM questionnaire;";
            String storeQuestionData = " INSERT INTO question (questionnaire_id,contenu) VALUES (?,?)";
            String lastQuestionId = "SELECT MAX(id) AS last_question_id FROM question;";
            String storeResponseData = "INSERT INTO reponse (question_id,response_date,reponseText) VALUES (?,?,?) ";
            Date currentDate = new Date(System.currentTimeMillis());

            PreparedStatement getQuestionnaireId = connection.prepareStatement(lastQuestionnaireId);
            PreparedStatement storeQuestionD = connection.prepareStatement(storeQuestionData);
            PreparedStatement getLastQuestionId = connection.prepareStatement(lastQuestionId);
            PreparedStatement storeResponseD = connection.prepareStatement(storeResponseData);

            ResultSet idResult = getQuestionnaireId.executeQuery();
            idResult.next();
            int Qid = idResult.getInt(1);

            for (int i = 0; i < questions.size(); i++) {
                String question = questions.get(i);
                String reponse = reponses.get(i);
                storeQuestionD.setInt(1, Qid);
                storeQuestionD.setString(2, question);
                storeQuestionD.executeUpdate();
                ResultSet id = getLastQuestionId.executeQuery();
                id.next();
                int idResultInt = id.getInt(1);
                System.out.print(idResultInt);
                storeResponseD.setInt(1, idResultInt);
                storeResponseD.setDate(2, currentDate);
                storeResponseD.setString(3, reponse);
                storeResponseD.executeUpdate();
            }

            loadtasks();
            Stage stage = (Stage) NewTaskController.stage.getScene().getWindow();
            stage.close();
        }
        else{
            System.out.print("Please fill minimum two questions");
        }

    }




}

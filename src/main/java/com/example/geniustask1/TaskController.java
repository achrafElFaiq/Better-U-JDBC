package com.example.geniustask1;

import com.example.geniustask1.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    @FXML
    private Text description;



    @FXML
    private Label title;

    @FXML
    private VBox vbox;

    static List<String> questions ;

    static int loadedQuestionnaireId;




    public void setData(Task task) throws SQLException {
        description.setText(task.getDescription());
        title.setText(task.getTitle());
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();
        String statusText= "";

        String selectQuery = "SELECT status FROM tache WHERE title = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, title.getText());
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()) {
            String status = result.getString("status");
            if(status.equals("complete")){
                vbox.setStyle("-fx-background-radius: 15; -fx-border-radius: 15;-fx-background-color: #145914");
            }
            else{
                vbox.setStyle("-fx-background-color: rgb(182,50,50);-fx-background-radius: 15; -fx-border-radius: 15");
            }


        }








    }

    public void loadtaskQuestions() throws SQLException, IOException {

        questions=new ArrayList<>();
        DBConnection connectNow = new DBConnection();
        Connection connection = connectNow.getConnection();

        String sql = "SELECT id FROM tache WHERE title = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title.getText());

        ResultSet resultSet = statement.executeQuery();
        int taskId=0;

        if (resultSet.next()) {
            taskId = resultSet.getInt("id");
        }

        System.out.println("a"+taskId);



        String sql1 = "SELECT id FROM questionnaire WHERE task_id = ?";

        PreparedStatement statement1 = connection.prepareStatement(sql1);
        statement1.setInt(1, taskId);

        ResultSet resultSet1 = statement1.executeQuery();
        int questionnaireId=0;
        if (resultSet1.next()) {
            questionnaireId = resultSet1.getInt("id");
        }

        System.out.println("b"+questionnaireId);



        String sql2 = "SELECT * FROM question WHERE questionnaire_id = ?";

        PreparedStatement statement2 = connection.prepareStatement(sql2);
        statement2.setInt(1, questionnaireId);

        ResultSet resultSet2 = statement2.executeQuery();

        while (resultSet2.next()) {
            int questionId = resultSet2.getInt("id");
            String questionContent = resultSet2.getString("contenu");
            questions.add(questionContent);
            System.out.println(questionContent);
        }
        loadedQuestionnaireId=questionnaireId;



        loadQuestionsPage();






    }

    private void loadQuestionsPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("questions.fxml"));
        Scene scene = title.getScene();
        scene.setRoot(root);

    }
}

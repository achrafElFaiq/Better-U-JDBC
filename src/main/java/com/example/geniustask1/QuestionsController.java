package com.example.geniustask1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;

public class QuestionsController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView back;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> list= TaskController.questions;
        try{
            double nextY = 0.0;
            for (int i = 0; i < list.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("question.fxml"));
                AnchorPane vbox = loader.load();
                vbox.setStyle("-fx-background-radius: 15; -fx-border-radius: 15");
                QuestionController tc = loader.getController();
                tc.setData(list.get(i));
                anchorPane.getChildren().add(vbox);
                AnchorPane.setTopAnchor(vbox, nextY);
                AnchorPane.setLeftAnchor(vbox, 0.0);
                nextY += 35.0;
            }
        }
        catch(IOException e ){
            throw new RuntimeException(e);
        }

    }

    public void loadTasks() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("tasks.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }
}

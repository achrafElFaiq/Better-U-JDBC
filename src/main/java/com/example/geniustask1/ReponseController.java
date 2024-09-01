package com.example.geniustask1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.Date;

public class ReponseController {

    @FXML
    private Label date;

    @FXML
    private Label reponse;

    public void setData(Date date1,String contenu){
        date.setText(" "+ date1.toString());
        reponse.setText("  "+contenu);
    }



}

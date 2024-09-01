package com.example.geniustask1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChatBotController implements Initializable {
    @FXML
    private ListView<String> listView;
    @FXML
    private Text response;

    @FXML
    private ImageView back;

    private Map<String,String> qr= new HashMap<>();



    String selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        qr.put("Comment puis-je créer un nouveau compte utilisateur dans l'application ?",
                "Si un un compte est profil y est deja, Suit les instructions suivante depuis la page d'acceuil: Account Icon -> Reset , puis entre les information du nouveau utilisateur de l'application. ");
        qr.put("Comment puis-je ajouter des questions à un questionnaire associé à une tâche ?", "Pendant le processus de creation de la tache l'ajout des questions est final apres le questionnaire ne pourra pas etre modifie pour la tache.");
        qr.put("Est-ce que je peux modifier une tâche existante ?", "Cette fonctionnalite n'est pas encore presente dans l'application");
        qr.put("Comment puis-je supprimer une tâche de l'application ?", "Cette fonctionnalite n'est pas encore presente dans l'application");
        qr.put(" Comment puis-je voir mes tâches en cours et leur progression ?", "Sur l'onglet tasks (2eme icone dans la bare a droite) tu pourra voir l'ensemble de tes taches,si une tache est affiche en vert cad qu'elle est terminer sinon elle n'est pas encore complete.");
        qr.put("Est-ce que je peux exporter les résultats de mes questionnaires ?", "Cette fonctionnalite n'est pas encore presente dans l'application");
        qr.put("Comment puis-je consulter les réponses que j'ai fournies pour un questionnaire spécifique ?", "Sur l'onglet tasks, tu pourra cliquer sur le titre de la tache, l'ensemble des questions des sera affichees , puis en cliquant sur n'importe question toute les reponses donnees pour cette question seront afficher associe de la date de chaque reponse");
        qr.put("Comment puis-je utiliser le chatbot pour obtenir des réponses à mes questions ?", "Le chat bot pour le moment n'est que pour guider votre experience dans l'application");
        qr.put("Est-ce que l'application envoie des notifications pour me rappeler de répondre aux questionnaires ?", "Le questionnaire est envoye une fois par semaine , si tu ne reponds pas la prochaine fois que tu relances l'applications chaque ");
        qr.put("Comment puis-je personnaliser les paramètres de notification de l'application ?", "Cette fonctionnalite n'est pas encore presente dans l'application");

        listView.getItems().addAll(qr.keySet());
    }
    public void respond (){
        selected = listView.getSelectionModel().getSelectedItem();
        response.setText(qr.get(selected));
    }

    @FXML
    private void loadhome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = back.getScene();
        scene.setRoot(root);
    }
}

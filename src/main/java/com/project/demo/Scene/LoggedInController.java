package com.project.demo.Scene;

import com.project.demo.model.DBconnect;
import com.project.demo.model.DBget;
import com.project.demo.model.Utente;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoggedInController  {
    //Implements initializible

    @FXML
    //private Button btn_logout;

    //@FXML
    private Label label_welcome;

   // @Override
    /*public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "login-view.fxml", "Login", null);
            }
        });

    }*/
    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null);
    }

    public void Home(ActionEvent event) {
        DBUtils.changeScene(event, "loggedin-view.fxml", "Home!", null);
        //TODO add getUsername from previous fxml scene
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Manage news!", null);
        //TODO add getUsername from previous fxml scene
    }

    public void Utenti(ActionEvent event){
        DBUtils.changeScene(event, "Utenti.fxml", "Manage user!", null);
        //TODO add getUsername from previous fxml scene
        //visualizzaUtenti();
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "Commenti.fxml", "Manage comments!", null);
        //TODO add getUsername from previous fxml scene
    }

    public void importaEsporta(ActionEvent event){
        DBUtils.changeScene(event, "importa-esporta.fxml", "Manage Import-Export!", null);
        //TODO add getUsername from previous fxml scene
    }

    public void visualizzaUtenti(Stage stage, int first){ //first = first element to show
        DBget dBget = new DBget();
        ArrayList< Utente > utenti = dBget.getUserList();
        System.out.println(utenti);

        //TODO da adattare alla situazione
        int y = 1;
        if(utenti.size()<=10){
            for(int i = first; i< utenti.size(); i++){
                //scrivi nel gridpane
                y++;
            }
        }
        else if((utenti.size()-first)<=10){
            for(int i = first; i< utenti.size(); i++){
                //scrivi nel gridpane
                y++;
            }
        }
        else {
            for (int i = first; i < (first + 10); i++) {
                //scrivi nel gridpane
                y++;
            }
        }
    }

    public void setUserInfoForWelcome(String username){
        label_welcome.setText("Welcome " + username +  "!");
    }
}

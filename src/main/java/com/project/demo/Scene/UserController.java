package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserController {
    /* TODO tableview, + dentro id primo elemento tabella per aggiungere utente, controllo se l'utente effettivamente esista
        su telegram?, aggiungi dati a table view (non c'e' limite alla quantita' di dati) collega scrollbar a table view!*/
    public void visualizzaUtenti(GridPane grid, int first){ //first = first element to show
        DBget dBget = new DBget();
        ArrayList<Utente> utenti = dBget.getUserList();
        System.out.println(utenti);
        //TODO da adattare alla situazione, non servono tre casi ma un solo ciclo, forse
        int y = 1;
        if(utenti.size()<=10){
            for(int i = first; i< utenti.size(); i++){
                grid.add(new Label("" + utenti.get(i).getId()), 1,y);// column=1 row=y
                grid.add(new Label("@"+ utenti.get(i).getUsername()),2,y);
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
}

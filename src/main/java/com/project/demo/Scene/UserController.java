package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.ObservableList;
import com.project.demo.model.Utente;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class UserController {
    /* TODO tableview, COME SI USA OBSERVABLELIST? + dentro id primo elemento tabella per aggiungere utente,
        controllo se l'utente effettivamente esista su telegram?, aggiungi dati a table view
        (non c'e' limite alla quantita' di dati) collega scrollbar a table view!*/

    public static void visualizzaUtenti(TableView<Utente> table){ //first = first element to show

        DBget dBget = new DBget();
        ObservableList<Utente> teamMembers =  dBget.getUserList();
        table = new TableView(teamMembers);
        //table.setItems(teamMembers);

        TableColumn<Utente,String> id = new TableColumn<Utente,String>("id");
        id.setCellValueFactory(new PropertyValueFactory("uno"));
        TableColumn<Utente,String> username = new TableColumn<Utente,String>("username");
        username.setCellValueFactory(new PropertyValueFactory("due"));
        TableColumn<Utente,String> ruolo = new TableColumn<Utente,String>("ruolo");
        ruolo.setCellValueFactory(new PropertyValueFactory("tre"));
        table.getColumns().setAll(id, username, ruolo);

        /*
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
        }*/
    }
}

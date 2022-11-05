package com.project.demo.Scene;

import com.project.demo.model.Commento;
import com.project.demo.model.DBget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentiController implements Initializable {
    @FXML
    private TableView<Commento> commenti;
    @FXML
    private TableColumn<Commento, Integer> id_commento;
    @FXML
    private TableColumn<Commento, Integer> id_notizia;
    @FXML
    private TableColumn<Commento, String> testo;
    @FXML
    private TableColumn<Commento, String> username;
    @FXML
    private TableColumn<Commento, Button> delete;
    private ObservableList<Commento> list;

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
        visualizza();
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "Commenti.fxml", "Manage comments!", null);
        //TODO add getUsername from previous fxml scene
    }

    public void importaEsporta(ActionEvent event){
        DBUtils.changeScene(event, "importa-esporta.fxml", "Manage Import-Export!", null);
        //TODO add getUsername from previous fxml scene
    }

    //public void setUserInfoForWelcome(String username){
    //label_welcome.setText("Welcome " + username +  "!");
    //}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visualizza();
        id_commento.setCellValueFactory(new PropertyValueFactory<Commento, Integer>("id_commento"));
        id_notizia.setCellValueFactory(new PropertyValueFactory<Commento, Integer>("id_notizia"));
        testo.setCellValueFactory(new PropertyValueFactory<Commento, String>("testo"));
        username.setCellValueFactory(new PropertyValueFactory<Commento, String>("username"));
        delete.setCellValueFactory(new PropertyValueFactory<Commento, Button>("delete"));

        this.commenti.setItems(list);
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getComments());
    }
}

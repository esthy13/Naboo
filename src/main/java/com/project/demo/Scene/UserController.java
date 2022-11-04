package com.project.demo.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Label label_welcome;
    @FXML
    private TableView<Utente> users;
    @FXML
    private TableColumn<Utente, String> id;
    @FXML
    private TableColumn<Utente, String> username;
    @FXML
    private TableColumn<Utente, String> ruolo;

    ObservableList<Utente> list = FXCollections.observableArrayList(
            new Utente("123", "esthy13", "user"),
            new Utente("224", "yassy67", "user"),
            new Utente("165", "stagno183", "Admin")
    );

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

    public void visualizza() {
        ArrayList<Utente> utenti = new ArrayList<>();
        utenti.add(new Utente("" + 224, "esthy13", "user"));
        utenti.add(new Utente("" + 225, "gagli03", "user"));
        ObservableList<Utente> team = FXCollections.observableArrayList(utenti);
        users = new TableView(team);
        users.getColumns().setAll(id, username, ruolo);
    }

    public void setUserInfoForWelcome(String username){
        label_welcome.setText("Welcome " + username +  "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Utente, String>("id"));
        username.setCellValueFactory(new PropertyValueFactory<Utente, String>("username"));
        ruolo.setCellValueFactory(new PropertyValueFactory<Utente, String>("ruolo"));

        users.setItems(list);
    }
}

package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import com.project.demo.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField password;
    @FXML
    private ChoiceBox role;
    @FXML
    private Button add;
    @FXML
    private TableView<Utente> users;
    @FXML
    private TableColumn<Utente, Integer> id;
    @FXML
    private TableColumn<Utente, String> username;
    @FXML
    private TableColumn<Utente, String> ruolo;
    @FXML
    private TableColumn<Utente, Button> modify;
    @FXML
    private TableColumn<Utente, Button> delete;
    private ObservableList<Utente> list;


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
        role.getItems().add("User");
        role.getItems().add("Amministratore");

        visualizza();
        id.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("id"));
        username.setCellValueFactory(new PropertyValueFactory<Utente, String>("username"));
        ruolo.setCellValueFactory(new PropertyValueFactory<Utente, String>("ruolo"));
        modify.setCellValueFactory(new PropertyValueFactory<Utente, Button>("modify"));
        delete.setCellValueFactory(new PropertyValueFactory<Utente, Button>("delete"));

        this.users.setItems(list);
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getUserList());
    }

    public void addUser(ActionEvent actionEvent){
        DBinsert dBinsert = new DBinsert();
        System.out.println(name.getText());
        System.out.println(role.getValue().toString());
        dBinsert.insertUser(name.getText(), role.getValue().toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Utente inserito correttamente");
        alert.show();
        DBUtils.changeScene(actionEvent, "Utenti.fxml", "Manage Import-Export!", null);
        //TODO se i campi sono vuoti non inserire l'utente + alert di errore
        //TODO se l'utente e' amministratore aggiungere metodo db che prende anche password in input
        // altrimenti alert di errore
    }
}

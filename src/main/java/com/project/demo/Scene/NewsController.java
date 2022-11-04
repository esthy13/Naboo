package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.Notizia;
import com.project.demo.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class NewsController implements Initializable {
    @FXML
    private TableView<Notizia> news;
    @FXML
    private TableColumn<Notizia, Integer> id;
    @FXML
    private TableColumn<Notizia, Integer> like;
    @FXML
    private TableColumn<Notizia, Integer> dislike;
    @FXML
    private TableColumn<Notizia, Integer> report;
    @FXML
    private TableColumn<Notizia, String> titolo;
    @FXML
    private TableColumn<Notizia, String> pubblicazione;
    @FXML
    private TableColumn<Notizia, String> fonte;
    private ObservableList<Notizia> list;


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
        //visualizza();
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
        id.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("id_notizia"));
        titolo.setCellValueFactory(new PropertyValueFactory<Notizia, String>("titolo"));
        pubblicazione.setCellValueFactory(new PropertyValueFactory<Notizia, String>("pubblicazione"));
        fonte.setCellValueFactory(new PropertyValueFactory<Notizia, String>("fonte"));
        like.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("like"));
        dislike.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("dislike"));
        report.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("report"));

        this.news.setItems(list);
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getAllNews());
    }
}

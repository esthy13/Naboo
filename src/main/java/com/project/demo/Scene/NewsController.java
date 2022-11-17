package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.Notizia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

//TODO da sistemare questione cancellazione notizie

public class NewsController implements Initializable {
    @FXML
    private Text myusername;
    public static Text text;
    @FXML
    private TableView<Notizia> news;
    @FXML
    private TableColumn<Notizia, String> titolo;
    @FXML
    private TableColumn<Notizia, String> pubblicazione;
    @FXML
    private TableColumn<Notizia, String> fonte;
    @FXML
    private TableColumn<Notizia, Integer> like;
    @FXML
    private TableColumn<Notizia, Integer> dislike;
    @FXML
    private TableColumn<Notizia, Integer> report;
    @FXML
    private TableColumn<Notizia, Button> delete;
    @FXML
    private TextField search_txt;
    public static TextField search;
    private ObservableList<Notizia> list;


    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null, null);
    }

    public void Home(ActionEvent event) {
        DBUtils.changeScene(event, "Fonti.fxml", "Benvenuto!", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Gestisci le notizie", getMyusername(), getSearch_txt());
        //TODO add getUsername from previous fxml scene
    }

    public void Utenti(ActionEvent event){
        DBUtils.changeScene(event, "utenti.fxml", "Gestisci gli utenti", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
        //visualizza();
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "commenti.fxml", "Gestisci i commenti", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }

    public void Fonti(ActionEvent event){
        DBUtils.changeScene(event, "Fonti.fxml", "Gestisci le fonti", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }

    //public void setUserInfoForWelcome(String username){
    //label_welcome.setText("Welcome " + username +  "!");
    //}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search = search_txt;
        text = myusername;
        visualizza();
        titolo.setCellValueFactory(new PropertyValueFactory<Notizia, String>("titolo"));
        pubblicazione.setCellValueFactory(new PropertyValueFactory<Notizia, String>("pubblicazione"));
        fonte.setCellValueFactory(new PropertyValueFactory<Notizia, String>("fonte"));
        like.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("like"));
        dislike.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("dislike"));
        report.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("report"));
        delete.setCellValueFactory(new PropertyValueFactory<Notizia, Button>("delete"));
        news.setItems(list);
        news.setTableMenuButtonVisible(true);

        FilteredList<Notizia> filteredData = new FilteredList<>(list, b->true);
        search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(notizia -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchWord = "" + newValue.toLowerCase();
                if(("" + notizia.getId_notizia()).indexOf(searchWord) > -1){
                    return true;
                }
                else if(notizia.getPubblicazione().indexOf(searchWord) > -1){
                    return true;
                }
                else if(notizia.getTitolo().toLowerCase().indexOf(searchWord) > -1){
                    return true;
                }
                else if(notizia.getFonte().toLowerCase().indexOf(searchWord) > -1){
                    return true;
                }
                /*else if(("" + notizia.getLike()).indexOf(searchWord) > -1){
                    return true;
                }
                else if(("" + notizia.getDislike()).indexOf(searchWord) > -1){
                    return true;
                }
                else if(("" + notizia.getReport()).indexOf(searchWord) > -1){
                    return true;
                }*/
                else
                    return false;
            });
        });
        SortedList<Notizia> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(news.comparatorProperty());
        news.setItems(sortedList);
        news.refresh();
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getAllNews());
    }

    public String getMyusername() {
        return myusername.getText();
    }

    public String getSearch_txt() {
        return search_txt.getText();
    }
}

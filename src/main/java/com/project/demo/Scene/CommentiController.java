package com.project.demo.Scene;

import com.project.demo.model.Commento;
import com.project.demo.model.DBget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentiController implements Initializable {
    @FXML
    private Text myusername;
    public static Text text;
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
    @FXML
    private TextField search_txt;
    public static TextField search;
    private ObservableList<Commento> list;

    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null, null);
    }

    public void Fonti(ActionEvent event) {
        DBUtils.changeScene(event, "fonti.fxml", "Fonti", getMyusername(), null);
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Notizie", getMyusername(), null);
    }

    public void Utenti(ActionEvent event){
        DBUtils.changeScene(event, "utenti.fxml", "Utenti", getMyusername(), null);
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "commenti.fxml", "Commenti", getMyusername(), getSearch_txt());
    }

    public void Home(ActionEvent event){
        DBUtils.changeScene(event, "home.fxml", "Home", getMyusername(), null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search = search_txt;
        text = myusername;
        visualizza();
        id_commento.setCellValueFactory(new PropertyValueFactory<Commento, Integer>("id_commento"));
        id_notizia.setCellValueFactory(new PropertyValueFactory<Commento, Integer>("id_notizia"));
        testo.setCellValueFactory(new PropertyValueFactory<Commento, String>("testo"));
        username.setCellValueFactory(new PropertyValueFactory<Commento, String>("username"));
        delete.setCellValueFactory(new PropertyValueFactory<Commento, Button>("delete"));

        this.commenti.setItems(list);

        FilteredList<Commento> filteredData = new FilteredList<>(list, b->true);
        search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(commento -> {
                if (newValue == null  || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchWord = "" + newValue.toLowerCase();
                if(("" + commento.getId_commento()).indexOf(searchWord) > -1){
                    return true;
                }
                else if(("" + commento.getId_notizia()).indexOf(searchWord) > -1){
                    return true;
                }
                else if(commento.getUsername().indexOf(searchWord) > -1){
                    return true;
                }
                else if(commento.getTesto().indexOf(searchWord) > -1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Commento> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(commenti.comparatorProperty());
        commenti.setItems(sortedList);



    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getComments());
    }

    public String getMyusername() {
        return myusername.getText();
    }

    public String getSearch_txt() {
        return search_txt.getText();
    }
}

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private AnchorPane parent;
    @FXML
    private Button buttonMode;
    public static Button mode;
    private ObservableList<Notizia> list;


    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null, null, null);
    }

    public void Fonti(ActionEvent event) {
        DBUtils.changeScene(event, "fonti.fxml", "Fonti", getMyusername(), null, getButtonMode());
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Notizie", getMyusername(), getSearch_txt(), getButtonMode());
    }

    public void Utenti(ActionEvent event){
        DBUtils.changeScene(event, "utenti.fxml", "Utenti", getMyusername(), null, getButtonMode());
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "commenti.fxml", "Commenti", getMyusername(), null, getButtonMode());
    }

    public void Home(ActionEvent event){
        DBUtils.changeScene(event, "home.fxml", "Home", getMyusername(), null, getButtonMode());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search = search_txt;
        text = myusername;
        mode = buttonMode;
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
    public void changeMode(ActionEvent event){
        if(getButtonMode().equals("light")){
            parent.getStylesheets().set(0,DBUtils.class.getResource("darkMode.css").toString());
            System.out.println(parent.getStylesheets());
            setButtonMode("dark");
        }
        else if(getButtonMode().equals("dark")){
            parent.getStylesheets().set(0,DBUtils.class.getResource("lightMode.css").toString());
            System.out.println(parent.getStylesheets());
            setButtonMode("light");
        }
    }
    public String getButtonMode() {
        return buttonMode.getAccessibleText();
    }

    public void setButtonMode(String buttonMode) {
        this.buttonMode.setAccessibleText(buttonMode);
    }

    public String getMyusername() {
        return myusername.getText();
    }

    public String getSearch_txt() {
        return search_txt.getText();
    }
}

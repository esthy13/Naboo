package com.project.demo.Scene;

import com.project.demo.model.Commento;
import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import com.project.demo.model.Fonte;
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

public class FontiController implements Initializable {
    @FXML
    private Text myusername;
    public static Text text;
    @FXML
    private TableView<Fonte> fonti;
    @FXML
    private TableColumn<Fonte, String> rss;
    @FXML
    private TableColumn<Fonte, Button> update;
    @FXML
    private TableColumn<Fonte, Button> delete;
    @FXML
    private Label label_welcome;
    @FXML
    private Button search_btn;
    @FXML
    private Button addRss_btn;
    @FXML
    private TextField search_txt;
    public static TextField search;
    @FXML
    private TextField linkRss;
    private ObservableList<Fonte> list;

    private FontiController fontiController;

    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null, null);
    }

    public void Fonti(ActionEvent event) {
        DBUtils.changeScene(event, "Fonti.fxml", "Gestisci le fonti", getMyusername(), getSearch_txt());
        //TODO add getUsername from previous fxml scene
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Gestisci le notizie", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }

    public void Utenti(ActionEvent event){
        DBUtils.changeScene(event, "utenti.fxml", "Gestisci gli utenti", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "commenti.fxml", "Gestisci i commenti", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }

    public void Home(ActionEvent event){
        DBUtils.changeScene(event, "Fonti.fxml", "Benvenuto!", getMyusername(), null);
        //TODO add getUsername from previous fxml scene
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search = search_txt;
        text = myusername;
        visualizza();
        rss.setCellValueFactory(new PropertyValueFactory<Fonte, String>("link_rss"));
        update.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("update"));
        delete.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("delete"));
        this.fonti.setItems(list);
        //TODO AGGIUNGI FUNZIONALITA' DI RICERCA
        FilteredList<Fonte> filteredData = new FilteredList<>(list, b->true);
        search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fonte -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchWord = "" + newValue.toLowerCase();
                if( fonte.getRss().indexOf(searchWord) > -1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Fonte> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(fonti.comparatorProperty());
        fonti.setItems(sortedList);
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getFonti());
    }

    public void addRss(ActionEvent event){
        if(linkRss.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setHeaderText("Link feed RSS mancante");
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.show();
        }
        else {
            DBinsert dBinsert = new DBinsert();
            dBinsert.insertFonte(linkRss.getText());
            DBUtils.changeScene(event, "Fonti.fxml", "Manage user!", getMyusername(), null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setHeaderText("Feed RSS aggiunto!");
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.show();
        }
    }

    public String getMyusername() {
        return myusername.getText();
    }

    public String getSearch_txt() {
        return search_txt.getText();
    }
}


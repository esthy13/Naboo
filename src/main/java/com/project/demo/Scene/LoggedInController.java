package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import com.project.demo.model.Fonte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

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
    @FXML
    private TextField linkRss;
    private ObservableList<Fonte> list;

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
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "Commenti.fxml", "Manage comments!", null);
        //TODO add getUsername from previous fxml scene
    }

    public void importaEsporta(ActionEvent event){
        DBUtils.changeScene(event, "importa-esporta.fxml", "Manage Import-Export!", null);
        //TODO add getUsername from previous fxml scene
    }

    public void setUserInfoForWelcome(String username){
        label_welcome.setText("Welcome " + username +  "!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visualizza();
        rss.setCellValueFactory(new PropertyValueFactory<Fonte, String>("link_rss"));
        update.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("update"));
        delete.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("delete"));
        this.fonti.setItems(list);
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
            DBUtils.changeScene(event, "loggedin-view.fxml", "Manage user!", null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setHeaderText("Feed RSS aggiunto!");
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
            dialog.getStyleClass().add("dialog");
            alert.show();
        }
    }
}


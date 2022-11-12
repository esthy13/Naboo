package com.project.demo.Scene;

import com.project.demo.model.DBdelete;
import com.project.demo.model.DBget;
import com.project.demo.model.Fonte;
import com.project.demo.model.Notizia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FontiController implements Initializable {
    @FXML
    private TableView<Fonte> fonti;
    @FXML
    private TableColumn<Fonte, String> rss;
    @FXML
    private TableColumn<Fonte, Button> update;
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
        //visualizza();
        rss.setCellValueFactory(new PropertyValueFactory<Fonte, String>("link_rss"));
        update.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("update"));


        this.fonti.setItems(list);
        /*
        this.fonti.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setHeaderText("Eliminare definitivamente la notizia " + news.getSelectionModel().getSelectedItem().getId_notizia());
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                dialog.getStyleClass().add("dialog");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()){}
                else if(result.get() == ButtonType.OK){
                    DBdelete dBdelete = new DBdelete();
                    dBdelete.deleteNotizia(news.getSelectionModel().getSelectedItem().getId_notizia());
                    news.getItems().remove(news.getSelectionModel().getSelectedIndex());
                }
                else if(result.get() == ButtonType.CANCEL) {}

            }
        });*/
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getFonti());
    }
}

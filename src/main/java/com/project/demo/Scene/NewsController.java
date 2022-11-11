package com.project.demo.Scene;

import com.project.demo.model.DBdelete;
import com.project.demo.model.DBget;
import com.project.demo.model.Notizia;
import com.project.demo.model.Utente;
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

public class NewsController implements Initializable {
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
        titolo.setCellValueFactory(new PropertyValueFactory<Notizia, String>("titolo"));
        pubblicazione.setCellValueFactory(new PropertyValueFactory<Notizia, String>("pubblicazione"));
        fonte.setCellValueFactory(new PropertyValueFactory<Notizia, String>("fonte"));
        like.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("like"));
        dislike.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("dislike"));
        report.setCellValueFactory(new PropertyValueFactory<Notizia, Integer>("report"));

        this.news.setItems(list);
        news.setEditable(true);
        this.news.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        });
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getAllNews());
    }

    public void delete(ActionEvent event){

    }
}

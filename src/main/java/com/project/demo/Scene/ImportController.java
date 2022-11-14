package com.project.demo.Scene;

import com.project.demo.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class ImportController implements Initializable{
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button File_Chooser;
    @FXML
    private Button Directory_Chooser;
    @FXML
    private Button buttonMode;
    @FXML
    private AnchorPane parent;
    private Boolean LightMode=true;
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

    //public void setUserInfoForWelcome(String username){
    //label_welcome.setText("Welcome " + username +  "!");
    //}

  /*  public void ForFilePath(){
        File_Path.getText();
    }*/

    public void ForDirectoryPath(){

    }

   public void Importa() {
       DBget dBget = new DBget();
       if(isNull(comboBox.getValue()) || comboBox.getValue().trim().isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Link RSS mancante");
           alert.show();
       }
       else {
           if(!dBget.getListFonti().contains(comboBox.getValue())){
               DBinsert dBinsert = new DBinsert();
               dBinsert.insertFonte(comboBox.getValue());
           }
           FileChooser fileChooser = new FileChooser();
           fileChooser.setTitle("Open Resource File");
           fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.csv"));
           File selectedfile = fileChooser.showOpenDialog(Window.getWindows().get(0));
           DBinsert dBinsert = new DBinsert().readCSV(selectedfile.getAbsolutePath(),comboBox.getValue().trim());
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setHeaderText("Notizie caricate correttamente sul database");
           alert.show();
       }

   }

   public void Esporta(){
       DirectoryChooser chooser = new DirectoryChooser();
       chooser.setTitle("Scegli Cartella");
       File selectedDirectory = chooser.showDialog( Window.getWindows().get(0));
       DBinsert dBinsert = new DBinsert().DatabaseToCSV(selectedDirectory.getAbsolutePath());
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setHeaderText("Notizie scaricate correttamente dal database");
       alert.show();
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBget dBget = new DBget();
        comboBox.getItems().addAll(dBget.getListFonti());
    }
    public void changeMode(ActionEvent event){
        if(LightMode==false){
            setLightMode();
            LightMode = true;
        }
        else{
            setDarkMode();
            LightMode = false;
        }
    }
    public void setLightMode(){
        parent.getStylesheets().remove(getClass().getResource("darkMode.css"));
        parent.getStylesheets().add(getClass().getResource("lightMode.css").toString());
        buttonMode.setText("L");
    }
    public void setDarkMode(){
        parent.getStylesheets().remove(getClass().getResource("lightMode.css"));
        parent.getStylesheets().add(getClass().getResource("darkMode.css").toString());
        buttonMode.setText("D");
    }
}


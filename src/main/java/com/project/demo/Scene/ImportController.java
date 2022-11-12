package com.project.demo.Scene;

import com.project.demo.model.Commento;
import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ImportController{
    @FXML
    Button File_Chooser;
    @FXML
    Button Directory_Chooser;
    @FXML
    TextField File_Path;
    @FXML
    TextField Directory_Path;

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
       if(File_Path.getText().trim().isEmpty()) {
           //String rss = File_Path.getText();
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Link RSS mancante");
           alert.show();
       }
       else {
           FileChooser fileChooser = new FileChooser();
           fileChooser.setTitle("Open Resource File");

           fileChooser.getExtensionFilters().addAll(
                   new FileChooser.ExtensionFilter("Text Files", "*.csv"));
           File selectedfile = fileChooser.showOpenDialog(Window.getWindows().get(0));
           //System.out.println(selectedfile.getAbsolutePath());
           DBinsert dBinsert = new DBinsert().readCSV(selectedfile.getAbsolutePath(),File_Path.getText());
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setHeaderText("Notizie caricate correttamente sul database");
           alert.show();
       }

   }

   public void Esporta(){
       DirectoryChooser chooser = new DirectoryChooser();
       chooser.setTitle("Scegli Cartella");

       /*File defaultDirectory = new File("c:/dev/javafx");
       chooser.setInitialDirectory(defaultDirectory);*/

       File selectedDirectory = chooser.showDialog( Window.getWindows().get(0));
      DBinsert dBinsert = new DBinsert().DatabaseToCSV(selectedDirectory.getAbsolutePath());
   }
}


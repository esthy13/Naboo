package com.project.demo.Scene;

import com.project.demo.Main;
import com.project.demo.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class HomeController implements Initializable{
    @FXML
    private Text myusername;
    public static Text text;
    @FXML
    private Text utenti;
    @FXML
    private Text notizie;
    @FXML
    private PieChart chartFonti;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button File_Chooser;
    @FXML
    private Button Directory_Chooser;
    @FXML
    private Button buttonMode;
    public static Button mode;
    @FXML
    private AnchorPane parent;
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
        DBUtils.changeScene(event, "commenti.fxml", "Commenti", getMyusername(), null);
    }

    public void Home(ActionEvent event){
        DBUtils.changeScene(event, "home.fxml", "Home", getMyusername(), null);
    }

   public void Importa() {
       DBget dBget = new DBget();
       if(isNull(comboBox.getValue()) || comboBox.getValue().trim().isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Link RSS mancante");
           alert.setTitle("Errore");
           DialogPane dialog = alert.getDialogPane();
           dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
           dialog.getStyleClass().add("dialog");
           Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
           stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
           alert.show();
       }
       else {
           if(!dBget.getListFonti().contains(comboBox.getValue())){
               DBinsert dBinsert = new DBinsert();
               dBinsert.insertFonte(comboBox.getValue());
           }
           try {
               FileChooser fileChooser = new FileChooser();
               fileChooser.setTitle("Open Resource File");
               fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.csv"));
               File selectedfile = fileChooser.showOpenDialog(Window.getWindows().get(0));
               DBinsert dBinsert = new DBinsert().readCSV(selectedfile.getAbsolutePath(), comboBox.getValue().trim());
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText("Notizie caricate correttamente sul database");
               alert.setTitle("Info");
               DialogPane dialog = alert.getDialogPane();
               dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
               dialog.getStyleClass().add("dialog");
               Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
               stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
               alert.show();
           }
           catch( NullPointerException ignore){
               // ... No action is required, just ignore.
           }
       }

   }

   public void Esporta(){
       try {
           DirectoryChooser chooser = new DirectoryChooser();
           chooser.setTitle("Scegli Cartella");
           File selectedDirectory = chooser.showDialog(Window.getWindows().get(0));
           DBinsert dBinsert = new DBinsert().DatabaseToCSV(selectedDirectory.getAbsolutePath());
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setHeaderText("Notizie scaricate correttamente dal database");
           alert.setTitle("Info");
           DialogPane dialog = alert.getDialogPane();
           dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
           dialog.getStyleClass().add("dialog");
           Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
           stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
           alert.show();
       }
       catch( NullPointerException ignore){
           // ... No action is required, just ignore.
       }
   }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBget dBget = new DBget();
        text = myusername;
        //mode = buttonMode;
        utenti.setText(dBget.getCountUser());
        notizie.setText(dBget.getCountNews());
        initPieChart();
        chartFonti.setLabelsVisible(true);
        chartFonti.setLegendVisible(false);
        comboBox.getItems().addAll(dBget.getListFonti());
    }

   /*TODO DA RIPRISTINARE

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
    }*/
    public String getMyusername() {
        return myusername.getText();
    }

    public void initPieChart(){
        DBget dBget = new DBget();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(dBget.getCount());
        chartFonti.setData(pieChartData);
    }
    public void changeMode(ActionEvent event){
        //DBUtils.changeSceneMode(event,"home.fxml","Home", getMyusername(), null,getButtonMode());(
        if(getButtonMode().equals("dark")){
            parent.getStylesheets().removeAll("lightMode.css");
            parent.getStylesheets().add(DBUtils.class.getResource("darkMode.css").toString());
            System.out.println(parent.getStylesheets());
            setButtonMode("light");
            System.out.println(getButtonMode());
            //System.out.println("dark");
        }
        else if(getButtonMode().equals("light")){
            parent.getStylesheets().removeAll("darkMode.css");
            parent.getStylesheets().add(DBUtils.class.getResource("lightMode.css").toString());
            System.out.println(parent.getStylesheets());
            setButtonMode("dark");
            //System.out.println("light");
            System.out.println(getButtonMode());
        }
    }
    public String getButtonMode() {
        return buttonMode.getAccessibleText();
    }

    public void setButtonMode(String buttonMode) {
        this.buttonMode.setAccessibleText(buttonMode);
    }
}


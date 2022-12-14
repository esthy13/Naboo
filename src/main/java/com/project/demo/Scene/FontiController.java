package com.project.demo.Scene;

import com.project.demo.Main;
import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import com.project.demo.model.Fonte;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

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
    @FXML
    private AnchorPane parent;
    @FXML
    private  Button buttonMode;
    public static Button mode;
    private ObservableList<Fonte> list;

    private FontiController fontiController;

    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null, null, null);
    }

    public void Fonti(ActionEvent event) {
        DBUtils.changeScene(event, "fonti.fxml", "Fonti", getMyusername(), getSearch_txt(), getButtonMode());
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Notizie", getMyusername(), null, getButtonMode());
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
        text = myusername;
        search = search_txt;
        mode = buttonMode;
        visualizza();
        rss.setCellValueFactory(new PropertyValueFactory<Fonte, String>("link_rss"));
        update.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("update"));
        delete.setCellValueFactory(new PropertyValueFactory<Fonte, Button>("delete"));
        this.fonti.setItems(list);

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
            alert.setTitle("Errore");
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
            dialog.getStyleClass().add("dialog");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
            alert.show();
        }
        else {
            DBinsert dBinsert = new DBinsert();
            dBinsert.insertFonte(linkRss.getText());
            DBUtils.changeScene(event, "fonti.fxml", "Manage user!", getMyusername(), null, getButtonMode());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.getDialogPane().setHeaderText("Feed RSS aggiunto!");
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
            dialog.getStyleClass().add("dialog");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
            alert.show();
        }
    }
    public void changeMode(ActionEvent event){
        FontAwesomeIconView icon = new FontAwesomeIconView();
        if(getButtonMode().equals("light")){
            parent.getStylesheets().set(0,DBUtils.class.getResource("darkMode.css").toString());
            setButtonMode("dark");
            icon = new FontAwesomeIconView(FontAwesomeIcon.SUN_ALT);
            icon.fillProperty().set(WHITE);
            icon.setSize("20");
            buttonMode.setGraphic(icon);
        }
        else if(getButtonMode().equals("dark")){
            parent.getStylesheets().set(0,DBUtils.class.getResource("lightMode.css").toString());
            setButtonMode("light");
            icon = new FontAwesomeIconView(FontAwesomeIcon.MOON_ALT);
            icon.fillProperty().set(BLACK);
            icon.setSize("20");
            buttonMode.setGraphic(icon);
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


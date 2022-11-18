package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import com.project.demo.model.Utente;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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

public class UserController implements Initializable {
    @FXML
    private Text myusername;
    public static Text text;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private ChoiceBox role;
    @FXML
    private Button add;
    @FXML
    private TableView<Utente> users;
    @FXML
    private TableColumn<Utente, Integer> id;
    @FXML
    private TableColumn<Utente, String> username;
    @FXML
    private TableColumn<Utente, String> ruolo;
    @FXML
    private TableColumn<Utente, Button> modify;
    @FXML
    private TableColumn<Utente, Button> delete;
    @FXML
    private TextField search_txt;
    public static TextField search;
    private ObservableList<Utente> list;


    public void logout(ActionEvent event){
        DBUtils.changeScene(event, "login-view.fxml", "Login", null, null);
    }

    public void Fonti(ActionEvent event) {
        DBUtils.changeScene(event, "fonti.fxml", "Fonti!", getMyusername(), null);
    }
    public void News(ActionEvent event) {
        DBUtils.changeScene(event, "news.fxml", "Manage news!", getMyusername(), null);
    }

    public void Utenti(ActionEvent event){
        DBUtils.changeScene(event, "utenti.fxml", "Manage user!", getMyusername(), getSearch_txt());
    }

    public void Commenti(ActionEvent event){
        DBUtils.changeScene(event, "commenti.fxml", "Manage comments!", getMyusername(), null);
    }

    public void Home(ActionEvent event){
        DBUtils.changeScene(event, "home.fxml", "Manage Import-Export!", getMyusername(), null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text = myusername;
        search = search_txt;
        name.setFocusTraversable(false);    //true
        password.setFocusTraversable(false);
        role.setFocusTraversable(false);
        role.getItems().add("User");
        role.getItems().add("Amministratore");
        visualizza();
        id.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("id"));
        username.setCellValueFactory(new PropertyValueFactory<Utente, String>("username"));
        ruolo.setCellValueFactory(new PropertyValueFactory<Utente, String>("ruolo"));
        modify.setCellValueFactory(new PropertyValueFactory<Utente, Button>("modify"));
        delete.setCellValueFactory(new PropertyValueFactory<Utente, Button>("delete"));
        users.setItems(list);

        FilteredList<Utente> filteredData = new FilteredList<>(list, b->true);
        search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utente -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchWord = "" + newValue.toLowerCase();
                if(("" + utente.getId()).indexOf(searchWord) > -1){
                    return true;
                }
                else if(utente.getUsername().toLowerCase().indexOf(searchWord) > -1){
                    return true;
                }
                else if(utente.getRuolo().toLowerCase().indexOf(searchWord) > -1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Utente> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(users.comparatorProperty());
        users.setItems(sortedList);
    }

    public void visualizza() {
        DBget dBget = new DBget();
        this.list = FXCollections.observableArrayList(dBget.getUserList());
    }

    public void addUser(ActionEvent actionEvent){
        DBinsert dBinsert = new DBinsert();
        DBget dBget = new DBget();
        System.out.println(role.getValue().toString());
        if (name.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Username necessario");
            alert.show();
        }
        else if(dBget.userExists(name.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Utente già registrato");
            alert.show();
        }
        else if (role.getValue().toString().trim().equals("Ruolo")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Ruolo necessario");
                alert.show();
        } else if (!name.getText().trim().isEmpty() && (role.getValue().toString().equals("Amministratore"))
                && (!password.getText().trim().isEmpty()) ) {
            Encryptor En = new Encryptor();
            String key = "Bar12345Bar12345"; // 128-bit key
            String initVector = "RandomInitVector"; // 16 bytes IV
            String encrypt_pass = En.encrypt(key, initVector, password.getText());
            dBinsert.insertUser(name.getText(), encrypt_pass, role.getValue().toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Utente inserito correttamente");
            alert.show();
            DBUtils.changeScene(actionEvent, "utenti.fxml", "Manage Import-Export!", getMyusername(), getSearch_txt());
        }
        else if (!name.getText().trim().isEmpty() && (role.getValue().toString().equals("Amministratore"))
                && (password.getText().trim().isEmpty()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password necessaria");
            alert.show();
        }
        else if (!name.getText().trim().isEmpty() && (role.getValue().toString().equals("User"))
                && (!password.getText().trim().isEmpty()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password non richiesta per utente user");
            alert.show();
        }
        else {
            dBinsert.insertUser(name.getText(), role.getValue().toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Utente inserito correttamente");
            alert.show();
            DBUtils.changeScene(actionEvent, "utenti.fxml", "Manage Import-Export!", getMyusername(), getSearch_txt());

        }

    }

    public String getMyusername() {
        return myusername.getText();
    }

    public String getSearch_txt() {
        return search_txt.getText();
    }
}

package com.project.demo.Scene;

import com.project.demo.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button btn_signup;

    @FXML
    private Button btn_login;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_username.setFocusTraversable(false);
        txt_password.setFocusTraversable(false);

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!txt_username.getText().trim().isEmpty() && (!txt_password.getText().trim().isEmpty())){
                    DBUtils.signUpUser(event, txt_username.getText(), txt_password.getText());
                }else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all fields to sign up!");
                    alert.setTitle("Errore");
                    DialogPane dialog = alert.getDialogPane();
                    dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                    dialog.getStyleClass().add("dialog");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
                    alert.show();
                }
            }
        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login-view.fxml", "Login", null, null);
            }
        });
    }
}

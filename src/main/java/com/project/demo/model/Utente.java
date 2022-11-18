package com.project.demo.model;

import com.project.demo.Main;
import com.project.demo.Scene.DBUtils;
import com.project.demo.Scene.Encryptor;
import com.project.demo.Scene.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Optional;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*;
import static javafx.scene.paint.Color.*;

public class Utente {
    int id;
    String username;
    String ruolo;
    Button modify;
    Button delete;

    public Utente(int id, String username, String ruolo){
        this.id = id;
        this.username = username;
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRuolo() {
        return ruolo;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Button getModify() {
        return modify;
    }

    public void setModify(Button modify) {
        this.modify = modify;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public void setModDelete(){
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
        icon.fillProperty().set(ORANGE);
        icon.setSize("20");
        Node modifyIcon = icon;
        this.modify = new Button();
        this.modify.setPrefSize(70,20);
        this.modify.setCenterShape(true);
        this.modify.backgroundProperty().set(Background.EMPTY);
        this.modify.setGraphic(modifyIcon);
        this.modify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String ruolo = getRuolo();
                DBinsert dBinsert = new DBinsert();
                if(ruolo.equals("User")){ //cambio il valore attuale con quello da inserire nel DB
                    ruolo = "Amministratore";
                }
                else {
                    ruolo = "User";
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setHeaderText("Rendere l'utente @" + getUsername()+ " " +ruolo);
                alert.setTitle("Conferma");
                Button cancel = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL);
                cancel.setText("Annulla");
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                dialog.getStyleClass().add("dialog");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
                Optional<ButtonType> result = alert.showAndWait();
                    if(!result.isPresent()){}
                    // alert is exited, no button has been pressed
                    else if(result.get() == ButtonType.OK){
                        if(ruolo.equals("User")){ //cambio il valore attuale con quello da inserire nel DB
                            dBinsert.modifyPassword(getId());
                        }
                        else {
                            dBinsert.modifyPasswordCrypt(getId(),Encryptor.getNewPassword(8));
                        }
                        dBinsert.modifyRole(getId(),ruolo);  //Cancellazione dell'utente
                        System.out.println("delete " + getId());
                        DBUtils.changeScene(actionEvent, "utenti.fxml", "Utenti", UserController.text.getText(), UserController.search.getText());
                }
                    else if(result.get() == ButtonType.CANCEL) {
                        // cancel button is pressed
                        //System.out.println("cancel");
                    }
                System.out.println("modifica");
            }
        });
        icon = new FontAwesomeIconView(MINUS);
        icon.fillProperty().set(RED);
        icon.setSize("15");
        Node deleteIcon = icon;
        this.delete = new Button();
        this.delete.setPrefSize(70,10);
        this.delete.setCenterShape(true);
        this.delete.backgroundProperty().set(Background.EMPTY);
        this.delete.setGraphic(deleteIcon);
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setHeaderText("Eliminare definitivamente l'utente @" + getUsername());
                alert.setTitle("Conferma");
                Button cancel = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL);
                cancel.setText("Annulla");
                //cancel.setBackground();
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                dialog.getStyleClass().add("dialog");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()){}
                // alert is exited, no button has been pressed
                else if(result.get() == ButtonType.OK){
                    //ok button is pressed
                    //System.out.println("ok");
                    DBdelete dBdelete = new DBdelete();
                    dBdelete.InteragisconoCheck(); //prima cancello righe di interagiscono se dovessero esserci
                    dBdelete.completeDeleteUser(getId());  //Cancellazione dell'utente
                    System.out.println("delete " + getId());
                    DBUtils.changeScene(actionEvent, "utenti.fxml", "Utenti", UserController.text.getText(), UserController.search.getText());
                }
                else if(result.get() == ButtonType.CANCEL) {
                    // cancel button is pressed
                    //System.out.println("cancel");
                }
            }
        });
    }
}

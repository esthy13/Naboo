package com.project.demo.model;

import com.project.demo.Main;
import com.project.demo.Scene.CommentiController;
import com.project.demo.Scene.DBUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.util.Optional;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.CLOSE;
import static javafx.scene.paint.Color.RED;

public class Commento {
    private int id_commento;
    private int id_notizia;
    private String testo;
    private String username;
    private Button delete;

    public Commento(int id_notizia, int id_commento, String username,String testo) {
        this.id_notizia = id_notizia;
        this.id_commento = id_commento;
        this.testo = testo;
        this.username = username;
    }

    public int getId_notizia() {
        return id_notizia;
    }

    public void setId_notizia(int id_notizia) {
        this.id_notizia = id_notizia;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getId_commento() {
        return id_commento;
    }

    public void setId_commento(int id_commento) {
        this.id_commento = id_commento;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public String toString(){
        return "@"+this.username + ": " +
                this.testo +"\n";
    }

    public void setDelete(){
        FontAwesomeIconView icon = new FontAwesomeIconView(CLOSE);
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
                alert.getDialogPane().setHeaderText("Eliminare definitivamente il commento" + getId_commento());
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
                    //ok button is pressed
                    //System.out.println("ok");
                    DBdelete dBdelete = new DBdelete();
                    DBget dBget = new DBget();
                    dBdelete.deleteOneComment(getId_commento(), getId_notizia());  //Cancellazione dell'utente
                    System.out.println("delete " + getId_commento());
                    DBUtils.changeScene(actionEvent, "commenti.fxml", "Commenti", CommentiController.text.getText(), CommentiController.search.getText());
                }
                else if(result.get() == ButtonType.CANCEL) {
                    // cancel button is pressed
                    //System.out.println("cancel");
                }
            }
        });
    }
}


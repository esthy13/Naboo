package com.project.demo.model;

import com.project.demo.Scene.DBUtils;
import com.project.demo.Scene.FontiController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Background;
import javafx.stage.Window;

import java.util.Optional;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class Fonte {
    private String link_rss;
    private Button update;
    private Button delete;

    public Fonte(){}

    public Fonte(String link_rss){
        this.link_rss = link_rss;
        setUpdateFonti();
        setDelete();
    }

    public String getRss() {
        return link_rss;
    }

    public void setRss(String rss) {
        this.link_rss = rss;
    }

    public String getLink_rss() {
        return link_rss;
    }

    public void setLink_rss(String link_rss) {
        this.link_rss = link_rss;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public void setUpdateFonti(){
        FontAwesomeIconView icon = new FontAwesomeIconView(ROTATE_RIGHT);
        icon.fillProperty().set(BLUE);
        icon.setSize("15");
        Node updateIcon = icon;
        this.update = new Button();
        this.update.setPrefSize(70,10);
        this.update.setCenterShape(true);
        this.update.backgroundProperty().set(Background.EMPTY);
        this.update.setGraphic(updateIcon);
        this.update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setHeaderText("Aggiornare le notizie provenienti da: " + getRss());
                Button cancel = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL);
                cancel.setText("Annulla");
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                dialog.getStyleClass().add("dialog");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()){}
                else if(result.get() == ButtonType.OK){
                    try {
                        LettoreRSS newsCollector = new LettoreRSS(getLink_rss());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                        /*
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.getDialogPane().setHeaderText("Errore nell'aggiornamento:");
                        alert.setContentText(e.getMessage());
                        dialog = alert.getDialogPane();
                        dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                        dialog.getStyleClass().add("dialog");
                        alert.show();
                        */
                    }
                    alert.setHeaderText("Notizie aggiornate sul database");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.show();
                }
                else if(result.get() == ButtonType.CANCEL) {}
            }
        });
    }
    public void setDelete(){
        FontAwesomeIconView icon = new FontAwesomeIconView(MINUS);
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
                alert.getDialogPane().setHeaderText("Eliminare definitivamente " + getRss());
                Button cancel = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL);
                cancel.setText("Annulla");
                cancel.setStyle("-fx-border-radius: 50");
                cancel.setStyle("-fx-background-radius: 50");
                Button ok = (Button) alert.getDialogPane().lookupButton( ButtonType.OK);
                ok.setStyle("-fx-border-radius: 50");
                ok.setStyle("-fx-background-radius: 50");
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                dialog.getStyleClass().add("dialog");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()){}
                else if(result.get() == ButtonType.OK){
                    DBdelete dBdelete = new DBdelete();
                    dBdelete.deleteFonte(getRss());  //Cancellazione dell'RSS
                    DBUtils.changeScene(actionEvent, "Fonti.fxml", "Gestisci le fonti", FontiController.text.getText());
                }
                else if(result.get() == ButtonType.CANCEL) {
                }
            }
        });
    }
}

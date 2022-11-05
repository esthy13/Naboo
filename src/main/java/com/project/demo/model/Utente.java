package com.project.demo.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.layout.Background;
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
                System.out.println("delete");
                //DBdelate dBdelete = new DBdelate();         //rimozione utente da DB
                //dBdelete.deleteUser(getUsername());
                //ricaricare la tabella per aggiornarla ? Cancella elemento da Observable List? O dall'ArrayList?
            }
        });
    }
}

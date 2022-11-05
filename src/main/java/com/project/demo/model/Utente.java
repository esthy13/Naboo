package com.project.demo.model;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIconUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.text.Font;

import static de.jensd.fx.glyphs.GlyphsStack.create;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.*;

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
        Node modifyIcon = new FontAwesomeIconView(PENCIL_SQUARE);
        this.modify = new Button();
        this.modify.setGraphic(modifyIcon);
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
}

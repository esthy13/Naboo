package com.project.demo.model;

public class Utente {
    String id;
    String username;
    String ruolo;

    public Utente(String id, String username, String ruolo){
        this.id = id;
        this.username = username;
        this.ruolo = ruolo;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRuolo() {
        return ruolo;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}

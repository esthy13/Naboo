package com.project.demo.model;

public class Utente {
     int id;
    String username;
    String ruolo;

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
}

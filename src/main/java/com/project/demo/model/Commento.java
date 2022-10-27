package com.project.demo.model;

public class Commento {
    private String id_commento;
    private String id_notizia;
    private String testo;
    private String username;

    public Commento(String id_notizia, String id_commento, String username,String testo) {
        this.id_notizia = id_notizia;
        this.id_commento = id_commento;
        this.testo = testo;
        this.username = username;
    }

    public String getId_notizia() {
        return id_notizia;
    }

    public void setId_notizia(String id_notizia) {
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
    public String getId_commento() {
        return id_commento;
    }

    public void setId_commento(String id_commento) {
        this.id_commento = id_commento;
    }

    public String toString(){
        return "@"+this.username + ": " +
                this.testo +"\n";
    }
}


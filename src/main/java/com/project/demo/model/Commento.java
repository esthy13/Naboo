package com.project.demo.model;

public class Commento {
    private int id_commento;
    private int id_notizia;
    private String testo;
    private String username;

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

    public String toString(){
        return "@"+this.username + ": " +
                this.testo +"\n";
    }
}


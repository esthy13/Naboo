package com.project.demo.model;



public class Notizia {

    private String titolo;
    private String pubblicazione;
    private String descrizione;
    private String autore;
    private String fonte;
    private String link;
    private String image; //link all'immagine

    private int id_notizia;
    private int like;
    private int dislike;
    private int report;


    public Notizia(String titolo, String pubblicazione, String descrizione,
                   String autore, String fonte, String link, String image){
            this.titolo = titolo;
            this.pubblicazione =  pubblicazione;
            this.descrizione = descrizione;
            this.autore = autore;
            this.fonte = fonte;
            this.link = link;
            this.image = image;
        }
    public Notizia(int id_notizia, String titolo, String pubblicazione, String descrizione,
                   String autore, String fonte, String link, String image){
        this.titolo = titolo;
        this.pubblicazione =  pubblicazione;
        this.descrizione = descrizione;
        this.autore = autore;
        this.fonte = fonte;
        this.link = link;
        this.image = image;
        this.id_notizia = id_notizia;
    }

    public Notizia(){}
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubblicazione() {
        return pubblicazione;
    }

    public void setPubblicazione(String pubblicazione) {
        this.pubblicazione = pubblicazione;
    }
    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getAutore() {
        return autore;
    }

    public String getFonte() {
        return fonte;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }
    public int getId_notizia() {
        return id_notizia;
    }

    public void setId_notizia(int id_notizia) {
        this.id_notizia = id_notizia;
    }

    public String toString(){
        return  this.pubblicazione + "\n" +
                this.titolo + "\n" +
                this.descrizione + "\n" +
                this.autore + "\n" +
                this.link+ "\n";

    }


}

package com.project.demo.model;

import com.project.demo.Main;
import com.project.demo.Scene.DBUtils;
import com.project.demo.Scene.NewsController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.Optional;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.CLOSE;
import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.MINUS;
import static javafx.scene.paint.Color.RED;

public class Notizia {

    private int id_notizia;
    private String titolo;
    private String pubblicazione;
    private String fonte;
    private int like;
    private int dislike;
    private int report;
    private String descrizione;
    private String autore;

    private String link;
    private String image; //link all'immagine
    private Button delete;



    public Notizia(int id_notizia, String titolo, String pubblicazione,
                   String fonte, int like, int dislike, int report){
        this.pubblicazione = pubblicazione;
        this.titolo = titolo;
        this.fonte = fonte;
        this.like = like;
        this.dislike = dislike;
        this.report = report;
        this.id_notizia = id_notizia;
        setOnDelete();
    }
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
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

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public String toString(){
        return  this.pubblicazione + "\n" +
                this.titolo + "\n" +
                this.descrizione + "\n" +
                this.autore + "\n" +
                this.link+ "\n";

    }

    public String stampa(){
        return  this.id_notizia + "\n" +
                this.titolo + "\n" +
                this.pubblicazione + "\n" +
                this.fonte + "\n" +
                this.like + "\n" +
                this.dislike + "\n" +
                this.report+ "\n";
    }

    public void setOnDelete(){
        FontAwesomeIconView icon = new FontAwesomeIconView(CLOSE);
        icon.fillProperty().set(RED);
        icon.setSize("15");
        icon.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        Node deleteIcon = icon;
        this.delete = new Button();
        this.delete.setText("");
        this.delete.setPrefSize(10,5);
        this.delete.backgroundProperty().set(Background.EMPTY);
        this.delete.setGraphic(deleteIcon);
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setHeaderText("Eliminare definitivamente la notizia " + getTitolo());
                alert.setTitle("Conferma");
                Button cancel = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL);
                cancel.setText("Annulla");
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("StyleDialogPane.css").toString());
                dialog.getStyleClass().add("dialog");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                } else if (result.get() == ButtonType.OK) {
                    DBdelete dBdelete = new DBdelete();
                    dBdelete.deleteNotizia(getId_notizia());
                    DBUtils.changeScene(actionEvent, "news.fxml", "Notizie", NewsController.text.getText(), NewsController.search.getText(), NewsController.mode.getAccessibleText());
                } else if (result.get() == ButtonType.CANCEL) {
                }
            }

        });
    }

}

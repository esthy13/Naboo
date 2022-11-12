package com.project.demo.model;

import javafx.scene.control.Button;

public class Fonte {
    private String link_rss;
    private Button update;

    public Fonte(){}

    public Fonte(String link_rss){
        this.link_rss = link_rss;
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
}

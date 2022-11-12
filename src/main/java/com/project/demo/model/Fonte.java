package com.project.demo.model;

public class Fonte {
    private String link_rss;

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
}

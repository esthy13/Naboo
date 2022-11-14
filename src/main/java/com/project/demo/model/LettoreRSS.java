package com.project.demo.model;

import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LettoreRSS {
    public LettoreRSS(String linkRss)throws Exception{
        //La nostra sorgente Feed
        //URL url  = new URL("http://xml2.corriereobjects.it/rss/homepage.xml"); //Link del RSS
        URL url  = new URL(linkRss);
        // l'url dovrà essere scelto dall'amministrazione quindi url cambierà
        XmlReader reader = null;
        try {
            DBinsert dBinsert = new DBinsert();
            DBconnect dBconnect = new DBconnect();

            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);

            for (SyndEntry entry : feed.getEntries()) {
                //Attenzione: ogni RSS e' diverso, quindi può capitare che ci siano delle incongruenze
                // --> sopratutto nella descrizione
                Notizia N = new Notizia();
                String dateStr;
                switch (url.toString()) {
                    case "http://xml2.corriereobjects.it/rss/homepage.xml":
                        N.setTitolo(entry.getTitle().replaceAll("'", " "));
                        N.setPubblicazione(entry.getPublishedDate().toString());
                        N.setLink(entry.getLink().replaceAll("'", " "));
                        N.setAutore(entry.getAuthor().replaceAll("'", " "));
                        N.setFonte(feed.getTitle().replaceAll(".it - Homepage", ""));
                        N.setDescrizione(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf("/><p>") + 5, entry.getDescription().getValue().indexOf("</p>")).replaceAll("'", ""));
                        N.setImage(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf("src=") + 5, entry.getDescription().getValue().indexOf("title") - 2));
                        dateStr = N.getPubblicazione();
                        String date = parseDate(dateStr,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyyMMddHHmmss");
                        N.setPubblicazione(date);
                        //Inserimento nel DB
                        dBinsert.InsertNews(N.getTitolo(),N.getPubblicazione(),N.getDescrizione(),N.getAutore(),N.getFonte(),N.getLink(),N.getImage(),0,0,0,url.toString());
                        break;
                    case "https://www.gazzetta.it/rss/home.xml":
                        N.setTitolo(entry.getTitle());
                        N.setPubblicazione(entry.getPublishedDate().toString());
                        /*Conversione di data*/
                        dateStr = N.getPubblicazione();
                        date = parseDate(dateStr,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyyMMddHHmmss");
                        N.setPubblicazione(date);
                        N.setLink(entry.getLink());
                        N.setAutore(entry.getAuthor());
                        N.setFonte(feed.getTitle());
                        N.setDescrizione(entry.getDescription().getValue().replaceAll("<br/>",""));
                        N.setImage(null);
                        dBinsert.InsertNews(N.getTitolo(),N.getPubblicazione(),N.getDescrizione(),N.getAutore(),N.getFonte(),N.getLink(),N.getImage(),0,0,0,url.toString());
                        break;
                    case "https://www.fanpage.it/feed/":
                        N.setTitolo(entry.getTitle());
                        N.setPubblicazione(entry.getPublishedDate().toString());
                        /*Conversione di data*/
                        dateStr = N.getPubblicazione();
                        date = parseDate(dateStr,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyyMMddHHmmss");
                        N.setPubblicazione(date);
                        N.setLink(entry.getLink());
                        N.setAutore(entry.getAuthor());
                        N.setFonte(feed.getTitle());
                        N.setDescrizione(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf(" /><br />") + 5, entry.getDescription().getValue().indexOf(" /><br />") -1) );
                        N.setImage(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf("src=") + 5, entry.getDescription().getValue().indexOf(" /><br />") -1) );
                        System.out.println(N.getDescrizione());
                        break;
                    default:
                        N.setTitolo(entry.getTitle());
                        N.setPubblicazione(entry.getPublishedDate().toString());
                        /*Conversione di data*/
                        dateStr = N.getPubblicazione();
                        date = parseDate(dateStr,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyyMMddHHmmss");
                        N.setPubblicazione(date);
                        N.setLink(entry.getLink());
                        N.setAutore(entry.getAuthor());
                        N.setFonte(feed.getTitle());
                        N.setDescrizione(entry.getDescription().getValue().replaceAll("<br/>",""));
                        N.setImage(null);
                        dBinsert.InsertNews(N.getTitolo(),N.getPubblicazione(),N.getDescrizione(),N.getAutore(),N.getFonte(),N.getLink(),N.getImage(),0,0,0,url.toString());

                }


            }

            //messaggio telegram da inviare agli utenti
            /**SendMessage sendMessage = new SendMessage();
             sendMessage.setText(N.toString());
             execute(sendMessage);**/
        } finally {
            //Chiudiamo lo stream precedentemente aperto.
            if (reader != null) reader.close();
        }


    }

    public String parseDate(String dateTime,String inputPattern, String outputPattern) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = inputFormat.parse(dateTime);
        String str = outputFormat.format(date);
        return str;
    }


    public static void main(String args[])throws Exception{
        //LettoreRSS rss = new LettoreRSS("https://www.fanpage.it/feed/");
        DBconnect dBconnect = new DBconnect();
        DBdelete dBdelete = new DBdelete();
        DBinsert dBinsert = new DBinsert();
        dBinsert.modifyPasswordCrypt(7,"Come va");


    }
}
package com.project.demo.model;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

                Notizia N = new Notizia();

                switch (url.toString()) {
                    case "http://xml2.corriereobjects.it/rss/homepage.xml":
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //DB.inspector prenderà il valore della fonte
                        Date secondDate = sdf.parse(dBconnect.inspector("Corriere"));
                        N.setTitolo(entry.getTitle().replaceAll("'", " "));
                        N.setPubblicazione(entry.getPublishedDate().toString());
                        N.setLink(entry.getLink().replaceAll("'", " "));
                        N.setAutore(entry.getAuthor().replaceAll("'", " "));
                        N.setFonte(feed.getTitle().replaceAll(".it - Homepage", ""));
                        N.setDescrizione(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf("/><p>") + 5, entry.getDescription().getValue().indexOf("</p>")).replaceAll("'", ""));
                        N.setImage(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf("src=") + 5, entry.getDescription().getValue().indexOf("title") - 2));
                        String dateStr = N.getPubblicazione();
                        String date1 = parseDate(dateStr,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd HH:mm:ss");
                        Date firstDate = sdf.parse(date1);
                        if(firstDate.after(secondDate)){
                            System.out.println("firstDate >  secondDate");
                            String date = parseDate(dateStr,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyyMMddHHmmss");
                            N.setPubblicazione(date);
                            //Inserimento nel DB
                            dBinsert.insertData(N.getTitolo(),N.getPubblicazione(),N.getDescrizione(),N.getAutore(),N.getFonte(),N.getLink(),N.getImage(),0,0,0,url.toString());

                        }
                        break;
                    case "https://www.ilsole24ore.com/rss/mondo.xml":
                        N.setTitolo(entry.getTitle().replaceAll("'", " "));
                        N.setPubblicazione(entry.getPublishedDate().toString());

                        /*Conversione di data*/
                        String dateStra = N.getPubblicazione();
                        String date = parseDate(dateStra,"EEE MMM dd HH:mm:ss zzz yyyy", "yyyyMMddHHmmss");
                        N.setPubblicazione(date);

                        N.setLink(entry.getLink().replaceAll("'", " "));
                        N.setAutore(entry.getAuthor().replaceAll("'", " "));
                        N.setFonte(feed.getTitle().replaceAll("'", " "));
                        N.setDescrizione(entry.getDescription().getValue().substring(entry.getDescription().getValue().indexOf("![CDATA[") + 5, entry.getDescription().getValue().indexOf("]]") - 2));
                        //N.setImage();
                        dBinsert.insertDataDefault(N.getTitolo(),N.getPubblicazione(),N.getDescrizione(),N.getAutore(),N.getFonte(),N.getLink(),N.getImage(),url.toString());

                        break;
                    default:
                        N.setImage("");
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
        LettoreRSS rss = new LettoreRSS("http://xml2.corriereobjects.it/rss/homepage.xml");
        DBconnect dBconnect = new DBconnect();
        DBdelete dBdelete = new DBdelete();


    }
}
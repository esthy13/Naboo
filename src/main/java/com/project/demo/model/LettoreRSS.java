package com.project.demo.model;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LettoreRSS {
    public LettoreRSS()throws Exception{
        //La nostra sorgente Feed
        URL url  = new URL("http://xml2.corriereobjects.it/rss/homepage.xml"); //Link del RSS
        XmlReader reader = null;
        try {
            DBinsert dBinsert = new DBinsert();
            DBconnect dBconnect = new DBconnect();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //DB.inspector prenderà il valore della fonte
            Date secondDate = sdf.parse(dBconnect.inspector("Corriere"));

            reader = new XmlReader(url);
            SyndFeed feed = new SyndFeedInput().build(reader);
            for (SyndEntry entry : feed.getEntries()) {

                Notizia N = new Notizia();

                switch (url.toString()) {
                    case "http://xml2.corriereobjects.it/rss/homepage.xml":
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
                    case "https://www.ansa.it/sito/ansait_rss.xml":
                        /*DA AGGIUNGERE*/
                        //set fonte ANSA
                        //set descrizione ANSA
                        N.setImage(" ");
                        break;
                    default:
                        N.setImage("");
                }


            }

            //dBinsert.readCSV("C:\\Users\\giuli\\OneDrive\\Documenti\\Esame\\Naboo\\Notizia.csv");

            //Invio di notizia all'utente TELEGRAM
            //System.out.println(N.toString());
            //db.getidnotizia
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
        //LettoreRSS rss = new LettoreRSS();
        DBconnect dBconnect = new DBconnect();
        DBdelate dBdelate = new DBdelate();
        /*Delate User*/
        DBdelate.deleteUser(2); //manca l'eliminazione di possiede;

        /*Delate Comment IS WORKING 11:37*/
        //DBdelate.deleteComment(828,2);
        /*Errore iniziale: Lo esegue dopo il terzo tentativo (perchè?) anche su mysql --> (problema più valori nella tabella: risolto) */

        /*Delate Notizia*/
        //DBdelate.deleteNotizia(777);  //errore con la chiave: errore inner join ;
    }
}
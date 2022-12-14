package com.project.demo.controller;

import com.project.demo.Scene.Encryptor;
import com.project.demo.model.Notizia;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public abstract class MyBot extends Buttons{
    public void help(String chatId, String id_notizia){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode("MarkdownV2");
        sendMessage.setText("*Funzionamento dei pulsanti del bot:*" +
                            "\n" + like_emoji + " per dare un voto positivo alla notizia" +
                            "\n" + dislike_emoji + " per dare un voto negativo alla notizia" +
                            "\n_se cambi idea, schiaccia nuovamente su_ " + like_emoji+ "/" +dislike_emoji +
                            " _per annullare il voto_" +
                            "\n" + save_emoji + " per salvarti le notizie tra i messaggi fissati" +
                            "\n" + comment_emoji + " per scrivere un commento alla notizia" +
                            "\n" + occhio_emoji + comment_emoji + " per visualizzare i commenti di una notizia" +
                            "\n" + "*share* per inviare la notizia ad altri tuoi contatti telegram " +
                            "\n" + "*whatsapp*, *facebook* e *twitter* per condividere le notizie sui social"+
                            "\n" + search_emoji +"*notizia* per cercare notizie in base a parole chiave del titolo" +
                            "\n" + search_emoji +"*data* per cercare notizie in base alla data di pubblicazione" +
                            "\n" + search_emoji +"*fonte* per cercare notizie in base al giornale online" +
                            "\n" + close_emoji +" per chiudere e cancellare un messaggio" +
                            "\n" + back_emoji + next_emoji + " per scorrere avanti e indietro notizie e commenti" +
                            "\n" + comment_emoji + " *password*: per modificare la tua password se sei un amministratore" +
                            "\n" + report_emoji + " per segnalare notizie con contenuti inappropriati" +
                            "\n" + call_emoji + help_emoji + " per ricevere supporto tecnico da uno sviluppatore "
                            );
        sendMessage.setReplyMarkup(keyboardHelp());
        sendMsg(sendMessage);
    }
    public void searchNews(Update update, ArrayList<Notizia> notizie, int first, String tipo){
        if(notizie.isEmpty()){
            sendMsg(update.getMessage().getChatId().toString(), "Non ci sono notizie inerenti alla tua ricerca " + sad_emoji);
        }
        else{
            sendNotizie(notizie, update, first, tipo);
        }
    }
    public void addComment(Update update){
        String id_utente = dBget.getId_user(update.getMessage().getFrom().getUserName());
        String id_notizia = dBget.getId_notizia(id_utente);
        System.out.println(id_notizia);
        dBinsert.insertComment(update.getMessage().getText(), id_notizia, id_utente);
        sendMsg(update.getMessage().getChatId().toString(),"Il tuo commento ?? stato aggiunto correttamente "
                + festa_emoji, keyboardVizualizza(update,id_notizia,dBget.getNumCommenti(id_notizia)));
        System.out.println(dBget.getNumCommenti(id_notizia));
        dBdelete.deleteInteragiscono(id_notizia, id_utente);

    }
    public void start(Update update){
        if(isNull(update.getMessage().getFrom().getUserName())){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setParseMode("MarkdownV2");
            sendMessage.setText("Per poter usufruire dei servizi offerti dal bot devi aggiungere uno username al" +
                    " tuo profilo\\. Semplicemente vai nelle impostazioni di telegram per aggiungere uno" +
                    " username al tuo account\\. Una volta aggiunto lo username fai /start\\." +
                    "\nhttps://youtu.be/pEqKj56vAh0");
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            sendMsg(update.getMessage().getChatId().toString(), "Benvenuto! " + wave_emoji +
                    "\nOra puoi cercare, leggere, commentare e condividere con i tuoi amici le notizie " +
                    "di attualit?? prese da fonti controllate dagli amministratori del bot!" +
                    "\nSe hai bisogno di aiuto usa /help " + help_emoji +
                    "\nPer accedere al men?? del bot usa /menu " + menu_emoji +
                    "\n" + robot_emoji +" @GediNabooBot" +
                    "\nhttps://youtu.be/pEqKj56vAh0", menuKeyboard("0"));
            dBinsert.insertUser(update.getMessage().getFrom().getUserName(), "User");
        }
    }

    public void callHelp(String id_notizia, String text, String username) throws IOException {
        //https://api.telegram.org/bot5782337102:AAEMwjHCFgJY8mod-K-spPjEkPEOBrILjnQ/sendMessage?chat_id=-1001834755647&text=Esempio%20messaggio%20richiesta%20help

        String urlString = "https://api.telegram.org/bot5782337102:AAEMwjHCFgJY8mod-K-spPjEkPEOBrILjnQ/sendMessage?chat_id=-1001834755647&text=";
        String botName = "%40GediNabooBot%3a%0a";
        if(!id_notizia.equals("0")) {
            id_notizia = "Problema%20associato%20alla%20notizia%20n%20" + id_notizia + "%0a";
        }
        else if(id_notizia.equals("0")){
            id_notizia = "";
        }
        urlString = urlString + botName + id_notizia +text.trim().replaceAll(" ", "%20") + "%0a%40" + username;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        StringBuilder sb = new StringBuilder();
        InputStream is = new BufferedInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        System.out.println(sb.toString());
    }
    public void verifyPassword(Update update){
        String key = "Bar12345Bar12345";
        String initVector = "RandomInitVector";
        String encrypted = dBget.getEncryptedPass(update.getMessage().getFrom().getUserName());
        String password = Encryptor.decrypt(key, initVector, encrypted);
        if(update.getMessage().getText().trim().equals(password)){
            ricerca(update.getMessage(),"Password corrente corretta, scrivere una nuova password " +
                    "per modificarla, altrimenti digita /close\\.", "Nuova password:");
        }
        else if(update.getMessage().isReply() && update.getMessage().getReplyToMessage().getText().equals("Password corrente" +
                " sbagliata, prova a ridigitare la password corrente, altrimenti per uscire dalla modifica password digita /close.")) {
            sendMsg("" + update.getMessage().getChatId(),"Spiacenti, non ?? possibile modificare la password, perch??  quella inserita" +
                    " non corrisponde a quella salvata sul database. " + sad_emoji);
        }
        else{
            ricerca(update.getMessage(),"Password corrente sbagliata, prova a ridigitare la password " +
                    "corrente, altrimenti per uscire dalla modifica password digita /close\\.", "Password corrente:");
        }
    }

}

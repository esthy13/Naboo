package com.project.demo.controller;

import com.project.demo.model.Notizia;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.UnpinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Naboo extends MyBot {
    @Override
    public void onUpdateReceived(Update update) {

        /* COMMANDS */
        if(update.hasMessage() && update.getMessage().getText().equals("/start")){
            start(update);
        }
        else if(update.hasMessage() && update.getMessage().getText().equals("/close")){
            close(update);
        }
        else if(update.hasMessage() && update.getMessage().getText().equals("/menu")){
            menu(update);
        }
        else if(update.hasMessage() && update.getMessage().getText().equals("/help")){
            help(update.getMessage().getChatId().toString(), "" + 0);
        }
        else if(update.hasMessage() && update.getMessage().getText().equals("/news")){
            ArrayList<Notizia> notizie = dBget.lastNews();
            for(Notizia n : notizie) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(n.toString());
                sendMessage.setChatId(update.getMessage().getChatId());
                System.out.println("last news - " + n.getId_notizia());
                keyboardNotizia("" + n.getId_notizia(), n.getLink().trim(), sendMessage);
                sendMsg(sendMessage);
            }
        }

        /* ANSWER TO USER */
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Scrivi un commento alla notizia,"
                + " altrimenti utilizza /close per tornare alle notizie")){
            addComment(update);
        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Scrivi alcune parole " +
                "per cercare la notizia che ti interessa, altrimenti schiaccia /close per tornare alle notizie")){
            System.out.println(dBget.ricercaTitolo(update.getMessage().getText()));
            searchNews(update,dBget.ricercaTitolo(update.getMessage().getText()), 0, "titolo");
        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Scrivi la data nel formato yyyy-mm-dd hh"
                +" per cercare le notizie della giornata che ti interessa, altrimenti schiaccia /close per tornare alle notizie")){
            searchNews(update,dBget.ricercaData(update.getMessage().getText()), 0, "data");
        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Scrivi il nome del giornale" +
                " di cui vuoi leggere le ultime notizie, altrimenti schiaccia /close per tornare alle notizie")){
            searchNews(update,dBget.ricercaFonte(update.getMessage().getText()), 0, "fonte");
        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Scrivi la tua richiesta di aiuto agli sviluppatori del Bot. \nSe" +
                " necessario  @esthy_13, @daaniel o @tanom02 ti contatteranno in privato. Oppure digita /close")){
            String text = update.getMessage().getText();
            String username = update.getMessage().getFrom().getUserName();
            try {
                callHelp("0", text, username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage()) &&
            update.getMessage().getReplyToMessage().getText().contains("Scrivi la tua richiesta di aiuto inerente questa" +
            " notizia agli sviluppatori del Bot. \nSe necessario  @esthy_13, @daaniel o @tanom02 ti contatteranno in privato." +
                    " Oppure digita /close")){
            String id_notizia = update.getMessage().getReplyToMessage().getText();
            id_notizia = id_notizia.substring(12,id_notizia.indexOf("S"));
            System.out.println(id_notizia);
            String text = update.getMessage().getText();
            String username = update.getMessage().getFrom().getUserName();
            try {
                callHelp(id_notizia.trim(), text, username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Per confermare l'azione di modifica password inserisci la password " +
                "corrente, altrimenti scrivi /close.")){
            verifyPassword(update);

        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Password corrente corretta, " +
                        "scrivere una nuova password per modificarla, altrimenti digita /close.")){
            dBinsert.modifyPasswordCrypt(Integer.parseInt(dBget.getId_user(update.getMessage().getFrom().getUserName())),update.getMessage().getText().trim());
            sendMsg("" + update.getMessage().getChatId(),"Password aggiornata con successo!");
        }
        else if(update.hasMessage() && !isNull(update.getMessage().getReplyToMessage())
                && update.getMessage().getReplyToMessage().getText().equals("Password corrente sbagliata, " +
                "prova a ridigitare la password corrente, altrimenti per uscire dalla modifica password digita /close.")){
            verifyPassword(update);
        }

        /*COMANDI NON RICONOSCIUTI*/
        else if(update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Comando non riconosciuto");
            try {
                execute(sendMessage); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        /* ON BUTTON PRESSED*/
        else if(update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery().getData());
            String callback = update.getCallbackQuery().getData().substring(0,update.getCallbackQuery().getData().indexOf('-'));
            int id = 0;
            if(isNull(update.getCallbackQuery().getData().substring((update.getCallbackQuery().getData().indexOf('-'))+1))){
                id = 5;
            }
            else id = Integer.parseInt(update.getCallbackQuery().getData().substring((update.getCallbackQuery().getData().indexOf('-'))+1));

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());

            switch(callback) {
                case "like":
                    like(update, id);
                    break;
                case "togliLike":
                    dBdelete.minusLike(id);
                    String callback2 = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(1).getCallbackData();
                    callback2 = callback2.substring(0, callback2.indexOf('-'));
                    EditMessageReplyMarkup togliLike = twoButtons(update, 0, like_emoji, "like", dBget.selectC_liked(id),
                            1, dislike_emoji, callback2, dBget.selectC_disliked(id));
                    try {
                        execute(togliLike);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "dislike":
                    dislike(update, id);
                    break;
                case "togliDislike":
                    dBdelete.minusDislike(id);
                    String callback4 = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(0).getCallbackData();
                    callback4 = callback4.substring(0, callback4.indexOf('-'));
                    EditMessageReplyMarkup togliDislike = twoButtons(update, 0, like_emoji, callback4, dBget.selectC_liked(id),
                            1, dislike_emoji, "dislike", dBget.selectC_disliked(id));
                    try {
                        execute(togliDislike);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "save":
                    PinChatMessage pinChatMessage = new PinChatMessage();
                    pinChatMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                    pinChatMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    EditMessageReplyMarkup pin = modify(update, 2, save_emoji, "unsave");
                    try {
                        execute(pinChatMessage);
                        execute(pin);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "unsave":
                    UnpinChatMessage unpinChatMessage = new UnpinChatMessage();
                    unpinChatMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                    unpinChatMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    EditMessageReplyMarkup unpin = modify(update, 2, save_emoji, "save");
                    try {
                        execute(unpinChatMessage);
                        execute(unpin);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "visualizza":
                    if(dBget.getCommentsList(id).isEmpty()) {
                        sendMsg(update.getCallbackQuery().getMessage().getChatId().toString(), "Non sono ancora presenti" +
                                " commenti per questa notizia " + sad_emoji);
                    }
                    else{ visualizzaCommenti(dBget.getCommentsList(id),update.getCallbackQuery(),0);}
                    break;
                case "menu":
                    menu(update);
                    break;
                case "notizia": {
                    String sms = "Scrivi alcune parole per cercare la notizia che ti interessa, altrimenti schiaccia"
                            + " /close per tornare alle notizie";
                    String IFPh = "Titolo:";
                    ricerca(update.getCallbackQuery().getMessage(),sms, IFPh);
                    break; }
                case "data": {
                    String sms = "Scrivi la data nel formato *yyyy\\-mm\\-dd hh* per cercare le notizie della " +
                            "giornata che ti interessa, altrimenti schiaccia /close per tornare alle notizie";
                    String IFPh = "yyyy-mm-dd hh";
                    ricerca(update.getCallbackQuery().getMessage(), sms, IFPh);
                }
                    break;
                case "fonte": {
                    String sms = "Scrivi il nome del giornale di cui vuoi leggere le ultime notizie"
                            + ", altrimenti schiaccia /close per tornare alle notizie";
                    String IFPh = "Giornale:";
                    ricerca(update.getCallbackQuery().getMessage(),sms,IFPh);
                }
                    break;
                case "profilo":
                    profilo(update.getCallbackQuery(),Integer.parseInt(dBget.getId_user(update.getCallbackQuery().getFrom().getUserName())));
                    break;
                case "help":
                    help(update.getCallbackQuery().getMessage().getChatId().toString(), ""+id);
                    break;
                case "comment": //inserInteragisce(int id, int id_utente)
                    dBinsert.inserInteragisce(id, Integer.parseInt(dBget.getId_user(update.getCallbackQuery().getFrom().getUserName())));
                    sendForceReplyMsg(update.getCallbackQuery().getMessage().getChatId().toString(),
                            "Scrivi un commento alla notizia, altrimenti utilizza /close per tornare alle notizie");
                    break;
                case "news" :
                    Notizia n = dBget.getNotizia(id);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(n.toString());
                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                    keyboardNotizia("" + id, n.getLink().trim(), sendMessage);
                    sendMsg(sendMessage);
                    break;
                case "commentiScorri":
                    scorriCommenti(update.getCallbackQuery(),id);
                    break;
                case "titoloScorri" :
                    scorriNotizie(update.getCallbackQuery(),"titolo");
                    break;
                case "dataScorri" :
                    scorriNotizie(update.getCallbackQuery(),"data");
                    break;
                case "fonteScorri" :
                    scorriNotizie(update.getCallbackQuery(),"fonte");
                    break;
                case "close" :
                    close(update);
                    break;
                case "report" :
                    //TODO add report
                    report(update,id);
                    break;
                case "disreport" :
                    //TODO delete report
                    disreport(update,id);
                    break;
                case "callHelp" :
                    String IFph = "Problema riscontrato: ";
                    if(id==0) {
                        ricerca(update.getCallbackQuery().getMessage(), "Scrivi la tua richiesta di aiuto agli sviluppatori del Bot\\. \nSe" +
                                    " necessario  @esthy\\_13, @daaniel o @tanom02 ti contatteranno in privato\\. Oppure digita /close", IFph);
                    }
                    else{
                        ricerca(update.getCallbackQuery().getMessage(), "notizia n \\= " + id + "\nScrivi la tua richiesta di aiuto inerente questa" +
                                        " notizia agli sviluppatori del Bot\\. \nSe necessario  @esthy\\_13, @daaniel " +
                                        "o @tanom02 ti contatteranno in privato\\. Oppure digita /close", IFph);

                    }
                    break;
                case "modificaPass" :
                    IFph = "Password corrente:";
                    ricerca(update.getCallbackQuery().getMessage(), "Per confermare l'azione di modifica password inserisci la password " +
                            "corrente, altrimenti scrivi /close\\.", IFph);
                    break;
            }
            try{
                execute(answerCallbackQuery);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

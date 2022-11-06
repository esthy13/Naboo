package com.project.demo.controller;

import com.project.demo.model.Notizia;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.UnpinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
            help(update.getMessage().getChatId().toString());
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
        /*METODO DI MIRROR*/
        /*else if(update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(update.getMessage().getText());
            sendMessage.setAllowSendingWithoutReply(true);
            String ids = "43" ;
            String link = "www.google.com";
            MessageInlineKeyboards notizia = new MessageInlineKeyboards(ids,link, sendMessage);
            try {
                execute(sendMessage); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }*/

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
                    dBdelate.minusLike(id);
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
                    dBdelate.minusDislike(id);
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
                    ricerca(update.getCallbackQuery(),sms, IFPh);
                    break; }
                case "data": {
                    String sms = "Scrivi la data nel formato *yyyy\\-mm\\-dd hh* per cercare le notizie della " +
                            "giornata che ti interessa, altrimenti schiaccia /close per tornare alle notizie";
                    String IFPh = "yyyy-mm-dd hh";
                    ricerca(update.getCallbackQuery(), sms, IFPh);
                }
                    break;
                case "fonte": {
                    String sms = "Scrivi il nome del giornale di cui vuoi leggere le ultime notizie"
                            + ", altrimenti schiaccia /close per tornare alle notizie";
                    String IFPh = "Giornale:";
                    ricerca(update.getCallbackQuery(),sms,IFPh);
                }
                    break;
                case "profilo":
                    profilo(update.getCallbackQuery(),id);
                    break;
                case "help":
                    help(update.getCallbackQuery().getMessage().getChatId().toString());
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
            }
            try{
                execute(answerCallbackQuery);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
